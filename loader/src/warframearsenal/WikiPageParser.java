package warframearsenal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.ini4j.Ini;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import warframearsenal.exceptions.BadValueException;

public class WikiPageParser {
	private final String path;
	private final Connection connection;
	private final boolean skipExisting;
	private final ArrayList<String> rangedWeapons = new ArrayList(50);
	private final ArrayList<String> meleeWeapons = new ArrayList(50);
	
	public static void main(String[] args) {
		boolean skip = false;
		String path = "";
		if (args.length == 2 && args[0].equals("--skip")) {
			skip = true;
			path = args[1];
		} else if (args.length == 1) {
			path = args[0];
		} else {
			System.err.println("Bad command line parameters.");
			System.err.println("Usage: java -jar WarframeArsenal.jar [--skip] DESTINATION");
		}
		try {
			WikiPageParser parser = new WikiPageParser(path, skip);
//			parser.parseRangedWeapon("Karak Wraith");
			parser.parseAllWeapons();
		} catch (SQLException | IOException ex) {
			System.err.println("UNCAUGHT EXCEPTION: " + ex);
			ex.printStackTrace();
		}
		ErrorHandler.INSTANCE.printErrorReport();
		ErrorHandler.INSTANCE.printMissingFieldReport();
	}

	public WikiPageParser(String path, boolean skip) throws SQLException, IOException {
		if (path.charAt(path.length()-1) != '/') {
			path += '/';
		}
		this.path = path;
		this.skipExisting = skip;
		Ini ini = new Ini(new File("settings.ini"));
		connection = DriverManager.getConnection("jdbc:mysql://localhost/arsenal",
					ini.get("database", "user"), ini.get("database", "password"));
	}
	
	public void parseAllWeapons() throws SQLException {
		getAllWeapons();
		
		PreparedStatement statement = connection.prepareStatement(
				"SELECT 1 FROM ranged_weapons WHERE name = ?");
		for (String weapon : rangedWeapons) {
			boolean skip = skipExisting;
			if (skipExisting) {
				statement.setString(1, weapon);
				ResultSet rs = statement.executeQuery();
				skip = rs.next();
			}
			if (!skip) {
				parseRangedWeapon(weapon);
			}
		}
		
		//TODO enable melee weapons
//		for (String weapon : meleeWeapons) {
//			parseMeleeWeapon(weapon);
//		}
	}
	
	public void getAllWeapons() {
		String url = "http://warframe.wikia.com/wiki/Weapons";
		try {			
			// Download page
			Document doc = Jsoup.connect(url).get();
			
			// Get navboxes containing weapon links
			Elements navboxes = doc.select("table.navbox");
			if (navboxes.size() < 3) {
				error("Did not find the expected navboxes.");
				return;
			}
			
			// Process the navboxes "Primary", "Secondary" and "Melee"
			parseNavBox(navboxes.get(0), rangedWeapons);
//			parseNavBox(navboxes.get(1), rangedWeapons); //TODO enable secondary weapons
//			parseNavBox(navboxes.get(2), meleeWeapons); //TODO enable melee weapons
			
		} catch (IOException ex) {
			error("Error while connecting to \"" + url + "\": " + ex.getMessage());
		}
	}

	private void parseNavBox(Element navbox, ArrayList<String> array) {
		Elements links = navbox.getElementsByTag("a");
		for (Element link : links) {
			String title = link.attr("title");
			if (!title.startsWith("Category")) {
				array.add(title);
			}
		}
	}
	
	public void parseRangedWeapon(String weaponName) {
		parseWeapon(weaponName, new RangedWeaponBuilder(weaponName));
	}
	
	public void parseMeleeWeapon(String weaponName) {
		parseWeapon(weaponName, new MeleeWeaponBuilder(weaponName));
	}
	
	public void parseWeapon(String weaponName, WeaponBuilder builder) {
		ErrorHandler.INSTANCE.setCurrentWeapon(weaponName);
		String url = "http://warframe.wikia.com/wiki/" + weaponName;
		try {
			// Download page
			Document doc = Jsoup.connect(url).get();
			
			// Select infobox
			Elements infoboxes = doc.select("table.infobox");
			if (infoboxes.isEmpty()) {
				error("Could not find infobox: \""+ weaponName +"\" does not seem to be a weapon.");
				return;
			} else if (infoboxes.size() > 1) {
				error("More than 1 infobox! (failing fast)");
				return;
			}
			
			// Iterate table rows
			Elements rows = infoboxes.first().getElementsByTag("tr");
			for (Element row : rows) {
				processRow(row, builder);
			}
			
			// Save to database
			if (builder.isValid()) {
				builder.save(connection);
			} else {
				error("Trying to save in invalid state.");
			}
			
		} catch (IOException ex) {
			error("Error while connecting to \"" + url + "\": " + ex.getMessage() 
					+ "\n    Either \""+ weaponName +"\" is not a weapon or the wiki is unaccessible.");
		} catch (BadValueException ex) {
			error("Parsing failed: " + ex.getMessage());
		} catch (SQLException ex) {
			error("Database error - did not save weapon: " + ex.getMessage());
		}
	}
	
	public void processRow(Element row, WeaponBuilder builder) throws BadValueException {
		Element child = row.child(0);
		String childClass = child.className();
		if (childClass.equals("left")) {
			builder.set(child.text(), row.child(1).text());
		} else if (childClass.equals("name")) {
			// Not doing anything.
		} else if (childClass.equals("image")) {
			Elements imgs = row.getElementsByTag("img");
			if (imgs.isEmpty() || imgs.size() > 2) {
				throw new BadValueException("Unexpected structure in image row");
			}
			downloadImage(path + "images/" + builder.name, imgs.last().attr("src"));
		} else if (childClass.equals("category")) {
			builder.setCategory(child.text());
		} else if (!childClass.isEmpty()) {
			error("Ignoring unidentified element in downloaded HTML! (row with class=\"" + child.className() + "\")");
		}
		// else ignore, because the row just contains a grouping table, whose 
		// contents will be processed individually.
	}
	
	public void downloadImage(String filename, String url) throws BadValueException {
		try {
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(filename);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (MalformedURLException ex) {
			throw new BadValueException("Bad image URL (" + ex.getMessage() + ")", ex);
		} catch (IOException ex) {
			throw new BadValueException("IOException while downloading image (" + ex.getMessage() + ")", ex);
		}
	}
	
	
	public void error(String message) {
		ErrorHandler.INSTANCE.error(message);
	}
	
	public void error(Exception ex) {
		ErrorHandler.INSTANCE.error(ex);
	}
}

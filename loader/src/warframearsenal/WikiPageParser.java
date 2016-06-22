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
import java.sql.SQLException;
import org.ini4j.Ini;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import warframearsenal.exceptions.BadValueException;

public class WikiPageParser {
	private final Connection connection;

	public static void main(String[] args) {
		try {
			WikiPageParser parser = new WikiPageParser();
			parser.parseRangedWeapon("Latron Prime");
			parser.parseRangedWeapon("Latron");
			parser.parseRangedWeapon("Braton");
			parser.parseRangedWeapon("Soma");
			parser.parseRangedWeapon("Grakata");
			parser.parseRangedWeapon("Gorgon");
	//		parseRangedWeapon("Sobek");
	//		parseRangedWeapon("Afuris");
	//		parseMeleeWeapon("Skana");
	//		parseMeleeWeapon("Heat Sword");
		} catch (SQLException | IOException ex) {
			System.err.println("ERROR: " + ex);
		}
	}

	public WikiPageParser() throws SQLException, IOException {
		Ini ini = new Ini(new File("settings.ini"));
		connection = DriverManager.getConnection("jdbc:mysql://localhost/arsenal",
					ini.get("database", "user"), ini.get("database", "password"));
	}
	
	public void parseRangedWeapon(String weaponName) {
		parseWeapon(weaponName, new RangedWeaponBuilder(weaponName));
	}
	
	public void parseMeleeWeapon(String weaponName) {
		parseWeapon(weaponName, new MeleeWeaponBuilder(weaponName));
	}
	
	public void parseWeapon(String weaponName, WeaponBuilder builder) {
		String url = "http://warframe.wikia.com/wiki/" + weaponName;
		try {
			System.out.println("\n" + weaponName);
			
			// Download page
			Document doc = Jsoup.connect(url).get();
			
			// Select infobox
			Elements infoboxes = doc.select("table.infobox");
			if (infoboxes.isEmpty()) {
				System.err.println("ERROR: Could not find infobox: \""+ weaponName +"\" does not seem to be a weapon.");
				return;
			} else if (infoboxes.size() > 1) {
				System.err.println("WARNING: More than 1 infobox! Trying to process first one...");
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
				System.err.println("ERROR: Trying to save in invalid state.");
			}
			
		} catch (IOException ex) {
			System.err.println("ERROR: Error while connecting to \"" + url + "\": " + ex.getMessage());
			System.err.println("       Either \""+ weaponName +"\" is not a weapon or the wiki is unaccessible.");
		} catch (BadValueException ex) {
			System.err.println("ERROR: Parsing \""+ weaponName +"\" failed: " + ex.getMessage());
		} catch (SQLException ex) {
			System.err.println("ERROR: Database error - did not save weapon \""+ weaponName +"\": " + ex.getMessage());
		}
	}
	
	public void processRow(Element row, WeaponBuilder builder) throws BadValueException {
		Element child = row.child(0);
		String childClass = child.className();
		if (childClass.equals("left")) {
			builder.set(child.text(), row.child(1).text());
//			System.out.println("  " + child.text() + " = " + row.child(1).text());
		} else if (childClass.equals("name")) {
//			System.out.println(child.text().toUpperCase());
		} else if (childClass.equals("image")) {
			Elements imgs = row.getElementsByTag("img");
			if (imgs.size() != 2) {
				System.err.println("WARNING: unexpected structure in image row");
			}
			downloadImage("/var/www/html/warframe/images/" + builder.name, imgs.get(1).attr("src"));
		} else if (childClass.equals("category")) {
			builder.setCategory(child.text());
//			System.out.println(child.text());
		} else if (!childClass.isEmpty()) {
			System.err.println("unidentified element!");
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
}

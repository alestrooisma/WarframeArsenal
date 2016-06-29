package warframearsenal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Ale Strooisma
 */
public enum ErrorHandler {

	INSTANCE;

	private String currentWeapon;
	private final Map<String, ArrayList<String>> errors = new HashMap();
	private final Map<String, ArrayList<String>> missingFields = new HashMap();

	public void setCurrentWeapon(String currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	public void error(String message) {
		register(message, errors);
		System.err.println(currentWeapon.toUpperCase() + ": " + message);
	}

	public void error(Exception ex) {
		error(ex.getMessage());
	}

	public void unknownField(String field) {
		register(field, missingFields);
		System.out.println(currentWeapon + ": Ignoring unknown field \"" + field + "\".");
	}

	public void printErrorReport() {
		printReport("Errors occurred:", errors);
	}

	public void printMissingFieldReport() {
		printReport("Missing fields:", missingFields);
	}

	private void register(String key, Map<String, ArrayList<String>> map) {
		ArrayList<String> list = map.get(key);
		if (list == null) {
			list = new ArrayList<>();
			map.put(key, list);
		}
		list.add(currentWeapon);
	}

	private void printReport(String header, Map<String, ArrayList<String>> map) {
		System.out.println();
		System.out.println(header);
		for (Entry<String, ArrayList<String>> entry : map.entrySet()) {
			ArrayList<String> list = entry.getValue();
			System.out.print(String.format("%4dx    %-60s(%s", list.size(), entry.getKey(), list.get(0)));
			for (int i = 1; i < list.size(); i++) {
				System.out.print(", " + list.get(i));
			}
			System.out.println(")");
		}
	}
}

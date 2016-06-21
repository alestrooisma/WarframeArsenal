package warframearsenal;

import java.sql.Connection;
import java.sql.SQLException;
import warframearsenal.enums.WeaponSlot;
import warframearsenal.enums.WeaponType;
import warframearsenal.exceptions.BadValueException;

public abstract class WeaponBuilder {

	protected final String name;
	protected int masteryLevel = -1;
	protected WeaponSlot slot = null;
	protected WeaponType type = null;
	//TODO polarities

	public WeaponBuilder(String name) {
		this.name = name;
	}

	public void set(String key, String value) throws BadValueException {
		try {
			if (key.equals("Mastery Level")) {
				masteryLevel = parseInt(value);
			} else if (key.equals("Weapon Slot")) {
				slot = getWeaponSlot(value);
			} else if (key.equals("Weapon Type")) {
				type = getWeaponType(value);
			} else if (!isIgnored(key)) {
				System.out.println("WARNING: Ignoring unknown field \"" + key + "\".");
			}
		} catch (NumberFormatException ex) {
			throw new BadValueException("Failed parsing \"" + value + "\" to a number for field \"" + key + "\".", ex);
		}
	}

	public boolean isIgnored(String key) {
		return false;
	}

	public boolean isValid() {
		return masteryLevel >= 0 && slot != null && type != null;
	}

	public abstract boolean save(Connection connection) throws SQLException;
	
	protected int parseInt(String value) {
		return Integer.parseInt(value);
	}
	
	protected int parseInt(String value, String suffix) {
		return Integer.parseInt(removeSuffix(value, suffix));
	}
	
	protected float parseFloat(String value) {
		return Float.parseFloat(value);
	}
	
	protected float parseFloat(String value, String suffix) {
		return Float.parseFloat(removeSuffix(value, suffix));
	}
	
	private String removeSuffix(String value, String suffix) {
		int pos = value.indexOf(suffix);
		if (pos > 0) {
			return value.substring(0, pos).trim();
		} else {
			return value;
		}
	}
	
	private WeaponSlot getWeaponSlot(String value) throws BadValueException {
		switch (value) {
			case "Primary":
				return WeaponSlot.PRIMARY;
			case "Secondary":
				return WeaponSlot.SECONDARY;
			case "Melee":
				return WeaponSlot.MELEE;
			default:
				throw new BadValueException("Unknown weapon slot \"" + value + "\".");
		}
	}

	private WeaponType getWeaponType(String value) throws BadValueException {
		switch (value) {
			case "Assault Rifle":
				return WeaponType.ASSAULT_RIFLE;
			default:
				throw new BadValueException("Unknown weapon type \"" + value + "\".");
		}
	}
}

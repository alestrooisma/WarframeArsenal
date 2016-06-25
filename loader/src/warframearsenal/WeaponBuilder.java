package warframearsenal;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import warframearsenal.enums.WeaponSlot;
import warframearsenal.enums.WeaponType;
import warframearsenal.exceptions.BadValueException;

public abstract class WeaponBuilder {

	protected BuilderState state;
	private final DecimalFormat numberParser;

	protected final String name;
	protected int masteryLevel = -1;
	protected WeaponSlot slot = null;
	protected WeaponType type = null;
	//TODO polarities

	public WeaponBuilder(String weaponName) {
		state = new StatisticsState();
		numberParser = new DecimalFormat();
		name = weaponName;
	}

	public abstract void setCategory(String category) throws BadValueException;

	public void set(String key, String value) throws BadValueException {
		key = key.replace('\u00A0', ' ');
		value = value.replace('\u00A0', ' ');
		try {
			if (!state.set(key, value)) {
				if (key.equals("Mastery Level")) {
					masteryLevel = parseInt(value);
				} else if (key.equals("Weapon Slot")) {
					slot = getWeaponSlot(value);
				} else if (key.equals("Weapon Type")) {
					type = getWeaponType(value);
				} else if (!isIgnored(key)) {
					System.out.println("WARNING: Ignoring unknown field \"" + key + "\".");
				}
			}
		} catch (NumberFormatException ex) {
			throw new BadValueException("Failed parsing \"" + value + "\" to a number for field \"" + key + "\" (" + ex.getMessage() + ").", ex);
		}
	}

	public boolean isIgnored(String key) {
		switch (key) {
			case "Conclave":
			case "Introduced":
			case "Weapon Users":
				return true;
			default:
				return false;
		}
	}

	public boolean isValid() {
		return masteryLevel >= 0 && slot != null && type != null;
	}

	public abstract boolean save(Connection connection) throws SQLException;

	protected int parseInt(String value) {
		try {
			return numberParser.parse(value).intValue();
		} catch (ParseException ex) {
			throw new NumberFormatException(ex.getMessage());
		}
	}

	protected int parseInt(String value, String suffix) {
		return parseInt(removeSuffix(value, suffix));
	}

	protected float parseFloat(String value) {
		try {
			return numberParser.parse(value).floatValue();
		} catch (ParseException ex) {
			throw new NumberFormatException(ex.getMessage());
		}
	}

	protected float parseFloat(String value, String suffix) {
		return parseFloat(removeSuffix(value, suffix));
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

	public abstract class BuilderState {

		public abstract boolean set(String key, String value) throws BadValueException, NumberFormatException;
	}

	protected class StatisticsState extends BuilderState {

		@Override
		public boolean set(String key, String value) throws BadValueException, NumberFormatException {
			switch (key) {
				case "Mastery Level":
					masteryLevel = parseInt(value);
					return true;
				case "Weapon Slot":
					slot = getWeaponSlot(value);
					return true;
				case "Weapon Type":
					type = getWeaponType(value);
					return true;
				default:
					return false;
			}
		}
	}
}

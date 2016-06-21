package warframearsenal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import warframearsenal.enums.TriggerType;
import warframearsenal.exceptions.BadValueException;

public class RangedWeaponBuilder extends WeaponBuilder {
	private TriggerType triggerType = null;
	private float fireRate = -1;
	private float accuracy = -1;
	private int magazineSize = -1;
	private int maxAmmo = -1;

	public RangedWeaponBuilder(String name) {
		super(name);
	}

	@Override
	public void set(String key, String value) throws BadValueException {
		value = value.replace('\u00A0',' ');
		try {
			switch (key) {
				case "Trigger Type":
					triggerType = getTriggerType(value);
					break;
				case "Fire Rate":
					fireRate = parseFloat(value, "rounds/sec");
					break;
				case "Accuracy":
					accuracy = parseFloat(value);
					break;
				case "Magazine Size":
					magazineSize = parseInt(value, "rounds/mag");
					break;
				case "Max Ammo":
					maxAmmo = parseInt(value, "rounds");
					break;
				default:
					super.set(key, value);
					break;
			}
		} catch (NumberFormatException ex) {
			throw new BadValueException("Failed parsing \"" + value + "\" to a number for field \"" + key + "\".", ex);
		}
	}

	@Override
	public boolean isIgnored(String key) {
		switch (key) {
			case "Noise Level":
			case "Physical Damage":
			case "Conclave":
			case "Introduced":
				return true;
			default:
				return super.isIgnored(key);
		}
	}
	
	@Override
	public boolean isValid() {
		return super.isValid() && triggerType != null && fireRate > 0 
				&& accuracy > 0 && magazineSize > 0 && maxAmmo > 0;
	}

	@Override
	public boolean save(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		String query = "INSERT INTO ranged_weapons "
				+ "(name, mastery_level, slot, type, trigger_type, fire_rate, accuracy, mag_size, max_ammo) " 
				+ "VALUES ('" + name + "', " + masteryLevel + ", "+ slot.getId() 
				+ ", " + type.getId() + ", " + triggerType.getId() + ", " 
				+ fireRate + ", " + accuracy + ", " + magazineSize + ", " + maxAmmo + ")";
		System.out.println(query);
		int number = statement.executeUpdate(query);
		if (number != 1) {
			throw new SQLException("Failed to save \"" + name + "\" (" + number + " rows updated).");
		}
		return true;
	}

	private TriggerType getTriggerType(String value) throws BadValueException {
		switch (value) {
			case "Semi-Auto":
				return TriggerType.SEMI_AUTO;
			case "Auto":
				return TriggerType.AUTO;
			default:
				throw new BadValueException("Unknown trigger type \"" + value + "\".");
		}
	}
}

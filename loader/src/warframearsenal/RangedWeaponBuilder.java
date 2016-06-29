package warframearsenal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import warframearsenal.enums.TriggerType;
import warframearsenal.exceptions.BadValueException;

public class RangedWeaponBuilder extends WeaponBuilder {
	
	private TriggerType triggerType = null;
	private TriggerType alternateTriggerType = null;
	private float fireRate = -1;
	private float accuracy = -1;
	private int magazineSize = -1;
	private int maxAmmo = -1;
	//TODO flight speed
//	private FlightSpeedType flightSpeedType = null;
//	private float flightSpeed = -1;
	private float reloadTime = -1;
	private float impact = 0;
	private float puncture = 0;
	private float slash = 0;
	private float critChance = -1;
	private float critMultiplier = -1;
	private float statusChance = -1;
	private boolean secondaryAttack = false;

	public RangedWeaponBuilder(String name) {
		super(name);
	}

	@Override
	public void setCategory(String category) throws BadValueException {
		switch (category) {
			case "Statistics":
				state = new StatisticsState2();
				break;
			case "Utility":
				state = new UtilityState();
				break;
			case "Normal Attacks":
				state = new NormalAttacksState();
				break;
			case "Secondary Attacks":
				secondaryAttack = true;
				state = new SecondaryAttackState();
				break;
			case "Miscellaneous":
				state = new MiscellaneousState();
				break;
			default:
				throw new BadValueException("Unknown category \"" + category + "\"");
		}
	}

	@Override
	public boolean isIgnored(String key) {
		switch (key) {
			case "Noise Level":
			case "Physical Damage":
			case "Ammo Type":
			case "Flight Speed": //TODO don't ignore Flight Speed
			case "Polarities": //TODO don't ignore Polarities
				return true;
			default:
				return super.isIgnored(key);
		}
	}
	
	@Override
	public boolean isValid() {
		return super.isValid() && triggerType != null && fireRate > 0 
				&& accuracy > 0 && magazineSize > 0 && maxAmmo > 0 
				&& reloadTime > 0 && impact >= 0 && puncture >= 0 && slash >= 0
				&& critChance >= 0 && critMultiplier >= 1 && statusChance >= 0;
	}

	@Override
	public boolean save(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		String query = "INSERT INTO ranged_weapons "
				+ "(name, mastery_level, slot, type, trigger_type, alt_trigger_type, fire_rate,"
				+ " accuracy, mag_size, max_ammo, reload_time, impact, puncture,"
				+ " slash, crit_chance, crit_multiplier, status_chance, secondary_attack) " 
				+ "VALUES ('" + name + "', " + masteryLevel + ", "+ slot.getId() 
				+ ", " + type.getId() + ", " + triggerType.getId() + ", " 
				+ (alternateTriggerType != null ? alternateTriggerType.getId() : "NULL") + ", " 
				+ fireRate + ", " + accuracy + ", " + magazineSize + ", " 
				+ maxAmmo + ", " + reloadTime + ", " + impact + ", " 
				+ puncture + ", " + slash + ", " + critChance + ", "
				+ critMultiplier + ", " + statusChance + ", " 
				+ (secondaryAttack ? "TRUE" : "FALSE") + ")";
		int number = statement.executeUpdate(query);
		if (number != 1) {
			throw new SQLException("Failed to save \"" + name + "\" (" + number + " rows updated).");
		}
		return true;
	}

	private TriggerType getTriggerType(String value) throws BadValueException {
		switch (value) {
			case "Semi-Auto":
			case "Semi-auto":
			case "Active":
			case "Duplex-Auto":
				return TriggerType.SEMI_AUTO;
			case "Auto":
			case "Held":
			case "Continuous":
				return TriggerType.AUTO;
			case "Burst":
				return TriggerType.BURST;
			case "Charge":
				return TriggerType.CHARGE;
			default:
				throw new BadValueException("Unknown trigger type \"" + value + "\".");
		}
	}
	
	private class StatisticsState2 extends StatisticsState {

		@Override
		public boolean set(String key, String value) throws BadValueException {
			if (key.equals("Trigger Type")) {
				String[] triggers = value.split("/");
				if (triggers.length == 1) {
					triggerType = getTriggerType(triggers[0]);
				} else if (triggers.length == 2) {
					triggerType = getTriggerType(triggers[0]);
					alternateTriggerType = getTriggerType(triggers[1]);
				} else {
					throw new BadValueException("Bad number of triggers types: " + triggers.length);
				}
				return true;
			} else {
				return super.set(key, value);
			}
		}
	}
	
	private class UtilityState extends BuilderState {

		@Override
		public boolean set(String key, String value) throws BadValueException {
			switch (key) {
//				case "Flight Speed":
//					if (value.equals("Hit-Scan")) {
//						flightSpeedType = FlightSpeedType.HITSCAN;
//					} else {
//						flightSpeedType = FlightSpeedType.PROJECTILE;
//						fireRate = parseFloat(value, "m/s");
//					}
//					return true;
				case "Fire Rate":
					fireRate = parseFloat(value, "rounds/sec");
					return true;
				case "Accuracy":
					accuracy = parseFloat(value);
					return true;
				case "Magazine Size":
					magazineSize = parseInt(value, "rounds/mag");
					return true;
				case "Max Ammo":
					maxAmmo = parseInt(value, "rounds");
					return true;
				case "Reload Time":
					reloadTime = parseFloat(value, "s");
					return true;
				default:
					return false;
			}
		}
	}

	private class NormalAttacksState extends BuilderState {

		@Override
		public boolean set(String key, String value) throws BadValueException, NumberFormatException {
			switch (key) {
				case "Impact":
					impact = parseFloat(value);
					return true;
				case "Puncture":
					puncture = parseFloat(value);
					return true;
				case "Slash":
					slash = parseFloat(value);
					return true;
				case "Crit Chance":
					critChance = parseFloat(value, "%");
					return true;
				case "Crit Multiplier":
					critMultiplier = parseFloat(value, "x");
					return true;
				case "Status Chance":
					statusChance = parseFloat(value, "%");
					return true;
				default:
					return false;
			}
		}
	}

	private class SecondaryAttackState extends BuilderState {

		@Override
		public boolean set(String key, String value) throws BadValueException, NumberFormatException {
			return true;
		}
	}

	private class MiscellaneousState extends BuilderState {

		@Override
		public boolean set(String key, String value) throws BadValueException, NumberFormatException {
			return false;
		}
	}
}

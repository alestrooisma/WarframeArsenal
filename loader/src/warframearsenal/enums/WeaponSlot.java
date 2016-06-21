package warframearsenal.enums;

public enum WeaponSlot {
	PRIMARY(1), SECONDARY(2), MELEE(3);
	
	private final int id;

	private WeaponSlot(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

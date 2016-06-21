package warframearsenal.enums;

public enum WeaponType {
	ASSAULT_RIFLE(1);
//	SHOTGUN, SNIPER_RIFLE, BOW, LAUNCHER,
//	PISTOL, AKIMBO, THROWN,
//	SWORD, DUAL_SWORDS, DAGGER, DUAL_DAGGERS, FISTS, MACHETE, NIKANA, STAFF, 
//	POLEARM, GLAIVE
	
	private final int id;

	private WeaponType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

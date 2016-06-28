package warframearsenal.enums;

public enum WeaponType {
	ASSAULT_RIFLE(1), SHOTGUN(2), SNIPER_RIFLE(3), BOW(4), LAUNCHER(5);
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

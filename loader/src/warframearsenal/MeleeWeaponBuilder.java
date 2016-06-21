package warframearsenal;

import java.sql.Connection;
import java.sql.SQLException;

public class MeleeWeaponBuilder extends WeaponBuilder {

	public MeleeWeaponBuilder(String name) {
		super(name);
	}

	@Override
	public boolean save(Connection connection) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}

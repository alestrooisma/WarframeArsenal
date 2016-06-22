package warframearsenal;

import java.sql.Connection;
import java.sql.SQLException;
import warframearsenal.exceptions.BadValueException;

public class MeleeWeaponBuilder extends WeaponBuilder {

	public MeleeWeaponBuilder(String name) {
		super(name);
	}

	@Override
	public void setCategory(String category) throws BadValueException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean save(Connection connection) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warframearsenal.enums;

/**
 *
 * @author Ale Strooisma
 */
public enum TriggerType {
	SEMI_AUTO(1), AUTO(2), BURST(3), CHARGE(4);
	
	private final int id;

	private TriggerType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

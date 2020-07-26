package com.ephoenix.lmsportal.generic.code;
/**
 * 
 * @author arnabn639
 *
 */
public enum ActiveConstants {
	ACTIVE('Y'), INACTIVE('N');
	
	private Character isActive;
	
	public Character getIsActive() {
		return isActive;
	}

	private ActiveConstants(Character isActive) {
		this.isActive = isActive;
	}	
}

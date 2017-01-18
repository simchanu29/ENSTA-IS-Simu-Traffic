package fr.ensta.lerouxlu.simu;

public enum EntityState {
	
	NONE,
	BORN,
	IDLE,
	HELD,
	ACTIVE,
	DEAD;

	public boolean isBusy() {
		return (this == HELD) || (this == ACTIVE);
	}
	
}

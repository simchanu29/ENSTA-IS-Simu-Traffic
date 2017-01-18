package fr.ensta.lerouxlu.simu;

import java.util.HashSet;
import java.util.Set;

public abstract class SimEntity {
	
	private EntityState state;
	private Set<ISimEvent> events = new HashSet<>();
	private SimEngine engine;

	String type;
	
	public String getType() {
		return type;
	}

	public SimEngine getEngine() {
		return engine;
	}

	public SimEntity(SimEngine engine,String type) {
		this.engine = engine;
		engine.addEntity(this);
		setState(EntityState.BORN);
		this.type=type;
	}
	
	public void terminate() {
		setState(EntityState.DEAD);
	}
	
	public void initialize() {
		setState(EntityState.IDLE);
	}
	
	public void activate() {
		setState(EntityState.ACTIVE);
	}
	
	public void deactivate() {
		setState(EntityState.BORN);
	}
	
	public void pause() {
		setState(EntityState.IDLE);
	}
	
	public void lock() {
		setState(EntityState.HELD);
	}
	
	public void release() {
		setState(EntityState.ACTIVE);
	}
	
	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}
	
	public void addEvent(ISimEvent event) {
		events.add(event);
		engine.onEventPosted(event);
	}
	
	public boolean isAffectedBy(ISimEvent event) {
		return events.contains(event);
	}

}

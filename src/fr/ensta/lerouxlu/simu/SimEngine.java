package fr.ensta.lerouxlu.simu;

import java.util.HashSet;
import java.util.Set;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.ISimulationDateProvider;
import enstabretagne.simulation.core.SortedList;

public class SimEngine implements ISimulationDateProvider, IEventObserver {

	private LogicalDateTime currentTime;
	private LogicalDateTime maxTime;
	private SortedList<ISimEvent> echeancier = new SortedList<>();
	private Set<SimEntity> entities = new HashSet<>();
	private double germe;
	MoreRandom random;
	private int nbVoiture;
	
	public long getRandomDuration() {
		return Math.round(random.nextDouble()*10);
	}
	
	public int getNbVoiture() {
		return nbVoiture;
	}
	
	public void addVoiture(int nb) {
		nbVoiture+=nb;
	}
	
	
	public SimEngine(long germe, LogicalDateTime begin, LogicalDuration maxTime) {
		this.germe=germe;
		random=new MoreRandom(germe);
		currentTime = begin;
		this.maxTime = currentTime.add(maxTime);
		echeancier.add(new StopEvent(this.maxTime));
		nbVoiture=0;
	}
	
	
	@Override
	public void onEventPosted(ISimEvent event) {
		echeancier.add(event);
	}
	
	public void initialize() {
		System.out.println("Simulation commence à "+currentTime);
		for (SimEntity entity : entities)
			entity.initialize();
	}
	
	public void pause() {
		for (SimEntity entity : entities)
			entity.pause();
	}
	
	public void resume() {
		System.out.println("resume");
		for (SimEntity entity : entities)
			entity.activate();
	}
	
	public boolean triggerNextEvent() {
		
		if(echeancier.size()==0 ){			
			return false;
		}
		ISimEvent nextEvent = echeancier.first();
		echeancier.remove(nextEvent);
		
		currentTime = nextEvent.scheduleDate();
		
		
		for (SimEntity entity : entities) {
			if (entity.isAffectedBy(nextEvent))
				entity.lock();
		}
		
		nextEvent.process();
		for (SimEntity entity : entities) {
			if (entity.isAffectedBy(nextEvent))
				entity.release();
		}
		return true;
	}

	public void addEntity(SimEntity simEntity) {
		entities.add(simEntity);
	}

	public SimEntity trouver(String type) {
		for (SimEntity entity : entities) {
			if(entity.getType().compareTo(type)==0)
				return entity;
		}
		
		return null;
		
	}

	class StopEvent extends SimEvent {

		public StopEvent(LogicalDateTime scheduledDate) {
			super(scheduledDate);
		}

		@Override
		public void process() {
				echeancier.clear();
				for (SimEntity entity : entities)
					entity.deactivate();
				System.out.println("Simulation terminée à " + currentTime);
		}
		
	}

	@Override
	public LogicalDateTime SimulationDate() {
		return currentTime;
	}

	public SortedList<ISimEvent> getEcheancier() {
		return echeancier;
	}

	public void setEcheancier(SortedList<ISimEvent> echeancier) {
		this.echeancier = echeancier;
	}
	
}

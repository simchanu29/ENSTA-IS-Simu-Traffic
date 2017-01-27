package fr.ensta.lerouxlu.simu;

import enstabretagne.base.time.LogicalDateTime;
import simEntity.Voiture.Voiture;

public interface ISimEvent extends Comparable<ISimEvent> {
 
	void process();
	LogicalDateTime scheduleDate();
	void resetProcessDate(LogicalDateTime simulationDate);
	Voiture getV();
	
}

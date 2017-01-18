package simEntity.Monitor;

import java.util.HashMap;
import java.util.LinkedList;

import de.vogella.algorithms.dijkstra.test.DijkstraRoutier;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.simulation.core.ISimulationDateProvider;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Voiture.Voiture;

public class SimMonitor extends SimEntity{
	int nbVoiture;
	MoreRandom random;
	String name;
	DijkstraRoutier map; 
	
	public SimMonitor(SimEngine engine, String name){
		super(engine,"SimMonitor");
		this.name=name;
		random = new MoreRandom(MoreRandom.globalSeed);	
		map =new DijkstraRoutier();
	}

	public void initialize() {
		super.activate();
		Logger.Information(this.name, "activate", "Monitor se reveille");		
	
	}
	
	public void activate() {
		super.activate();
		double d=random.nextExp(30);
		LogicalDuration t = LogicalDuration.ofSeconds(d);
		LogicalDateTime e = getEngine().SimulationDate().add(t);
	
		addEvent(new NouvelleVoitureEvent(e));
	}
	
	
	public String getName() {
		return name;
	}
	
	
	
	
	
	class NouvelleVoitureEvent extends SimEvent {
		public NouvelleVoitureEvent(LogicalDateTime scheduledDate) {
			super(scheduledDate);
		}

		@Override
		public void process() {
			CarrefourNames nameL = CarrefourNames.randomCarrefour();
			CarrefourNames nameD = CarrefourNames.randomCarrefour();
			while (nameL==nameD){
				nameD = CarrefourNames.randomCarrefour();
			}
			String name = "Voiture_"+String.valueOf(nbVoiture);
			int indexL=CarrefourNames.valueOf(nameL.toString()).ordinal()+1;
			int indexD=CarrefourNames.valueOf(nameD.toString()).ordinal()+1;
			
			LinkedList<CarrefourNames> path= map.chemin(indexL, indexD);
			Voiture v = new Voiture(getEngine(), name, nameL,nameD, path );
			Logger.Information(name, "NouvelleVoiture", name + " est créée en "+nameL);
			LogicalDateTime d = getNextTimeForVoiture();
			if(d!=null) addEvent(new NouvelleVoitureEvent(d));
			v.activate();
			System.out.println("Path "+v.getName()+" :  " + v.getPath());
			nbVoiture++;
			
		}
	}
	
	LogicalDateTime getNextTimeForVoiture() {		
		double d=random.nextExp(5);
		LogicalDuration t = LogicalDuration.ofMinutes(10);
		LogicalDateTime possibleVoitureArrival = getEngine().SimulationDate().add(t);
	
		return possibleVoitureArrival;
	}
	
}

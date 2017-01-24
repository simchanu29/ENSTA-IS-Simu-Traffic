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

/**
 * Cette classe a pas un super nom par arpport à ce qu'elle fait. Il semble qu'elle mette en place l'apparition des
 * voiture et leur trajet
 * TODO : verifier la redondance avec Quartier
 */
public class SimMonitor extends SimEntity{
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
	
		//addEvent(new NouvelleVoitureEvent(e));
	}
	
	
	public String getName() {
		return name;
	}
	
	
	/*
	
	class NouvelleVoitureEvent extends SimEvent {
		public NouvelleVoitureEvent(LogicalDateTime scheduledDate) {
			super(scheduledDate);
		}

		@Override
		public void process() {
			CarrefourNames nameL = CarrefourNames.randomCarrefour();
			CarrefourNames nameD = calculDestination(nameL);

			String name = "Voiture_"+String.valueOf(nbVoiture);
			Voiture v = new Voiture(getEngine(), name, nameL,nameD);
			Logger.Information(name, "NouvelleVoiture", name + " est créée en "+nameL);
			LogicalDateTime d = getNextTimeForVoiture();
			if(d!=null) addEvent(new NouvelleVoitureEvent(d));
			v.activate();
			System.out.println("Path "+v.getName()+" :  " + v.getChemin().getPath());
			nbVoiture++;
			
		}
	}
	
	LogicalDateTime getNextTimeForVoiture() {		
		double d=random.nextExp(5);
		LogicalDuration t = LogicalDuration.ofMinutes(10);
		LogicalDateTime possibleVoitureArrival = getEngine().SimulationDate().add(t);
	
		return possibleVoitureArrival;
	}
	
	CarrefourNames calculDestination(CarrefourNames departure){
		CarrefourNames destination=null;
		double pDest = random.nextDouble()*100;
		switch(departure){
			case P1:
				if(pDest<=5) destination=CarrefourNames.P2;
				if(pDest>5 && pDest<=15) destination=CarrefourNames.P3;
				if(pDest>15 && pDest<=25) destination=CarrefourNames.P4;
				if(pDest>25 && pDest<=30) destination=CarrefourNames.P5;
				if(pDest>30 && pDest<=65) destination=CarrefourNames.P6;
				if(pDest>65 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P2:
				if(pDest<=10) destination=CarrefourNames.P1;
				if(pDest>10 && pDest<=15) destination=CarrefourNames.P3;
				if(pDest>15 && pDest<=35) destination=CarrefourNames.P4;
				if(pDest>35 && pDest<=55) destination=CarrefourNames.P5;
				if(pDest>55 && pDest<=80) destination=CarrefourNames.P6;
				if(pDest>80 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P3:
				if(pDest<=15) destination=CarrefourNames.P1;
				if(pDest>15 && pDest<=30) destination=CarrefourNames.P2;
				if(pDest>30 && pDest<=50) destination=CarrefourNames.P4;
				if(pDest>50 && pDest<=70) destination=CarrefourNames.P5;
				if(pDest>70 && pDest<=90) destination=CarrefourNames.P6;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P4:
				if(pDest<=15) destination=CarrefourNames.P1;
				if(pDest>15 && pDest<=25) destination=CarrefourNames.P2;
				if(pDest>25 && pDest<=35) destination=CarrefourNames.P3;
				if(pDest>35 && pDest<=55) destination=CarrefourNames.P5;
				if(pDest>55 && pDest<=95) destination=CarrefourNames.P6;
				if(pDest>95 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P5:
				if(pDest<=10) destination=CarrefourNames.P1;
				if(pDest>10 && pDest<=40) destination=CarrefourNames.P2;
				if(pDest>40 && pDest<=50) destination=CarrefourNames.P3;
				if(pDest>50 && pDest<=60) destination=CarrefourNames.P4;
				if(pDest>60 && pDest<=70) destination=CarrefourNames.P6;
				if(pDest>70 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P6:
				if(pDest<=20) destination=CarrefourNames.P1;
				if(pDest>20 && pDest<=30) destination=CarrefourNames.P2;
				if(pDest>30 && pDest<=70) destination=CarrefourNames.P3;
				if(pDest>70 && pDest<=80) destination=CarrefourNames.P4;
				if(pDest>80 && pDest<=90) destination=CarrefourNames.P5;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P7:
				if(pDest<=20) destination=CarrefourNames.P1;
				if(pDest>20 && pDest<=40) destination=CarrefourNames.P2;
				if(pDest>40 && pDest<=60) destination=CarrefourNames.P3;
				if(pDest>60 && pDest<=80) destination=CarrefourNames.P4;
				if(pDest>80 && pDest<=90) destination=CarrefourNames.P5;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P6;
				break;
		default:
			break;
				
		}
		
		return destination;
	}
	*/
}

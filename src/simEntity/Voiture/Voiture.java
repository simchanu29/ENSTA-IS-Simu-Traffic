package simEntity.Voiture;


import java.util.LinkedList;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.CarrefourNames;

public  class Voiture extends SimEntity {

		private String name;
		private CarrefourNames location;
		private CarrefourNames destination;
		private LinkedList<CarrefourNames> path;
		


		public Voiture(SimEngine engine, String name, CarrefourNames location, CarrefourNames destination, LinkedList<CarrefourNames> path) {
			super(engine,"Voiture");
			this.name=name;
			this.location=location;
			this.destination=destination;
			this.path=path;
		}
		
		@Override
		public void initialize() {
			super.initialize();
			Logger.Information(this, "initialize", name + " s initialise");
		}
		
		public void goTo() {
			
			Logger.Information(this, "goTo",name+ " go to "+ this.destination);	
			long randomDuration = getEngine().getRandomDuration();
			addEvent(new IsArrived(getEngine().SimulationDate().add(LogicalDuration.ofHours(randomDuration))));
		}
		
		public void isArrived() {

			Logger.Information(this, "isArrived",name+ " is arrived at " + this.destination);
			
		}
		
		public class IsArrived extends SimEvent {

			public IsArrived(LogicalDateTime scheduledDate){
				super(scheduledDate);
			}
			@Override
			public void process() {
				isArrived();
				
			}
			
		}

		@Override
		public String toString() {
			return name;
		}
		
		@Override
		public void activate() {
			super.activate();
			Logger.Information(this, "activate", name +" se reveille");
			this.addEvent(new GoTo(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(2))));
					
		}
		
		@Override
		public void deactivate() {
			super.deactivate();
			Logger.Information(this, "deactivate", "je suis desactivé");
		}
		@Override
		public void terminate() {
			super.terminate();
			Logger.Information(this, "terminate","je suis terminé");

		}
		public class GoTo extends SimEvent {

			public GoTo(LogicalDateTime scheduledDate){
				super(scheduledDate);
			}
			@Override
			public void process() {
				goTo();
				
			}
			
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public CarrefourNames getLocation() {
			return location;
		}
		public CarrefourNames getDestination() {
			return destination;
		}
		public LinkedList<CarrefourNames> getPath() {
			return path;
		}

		public void setLocation(CarrefourNames location) {
			this.location = location;
		}
		
		public void setDestination(CarrefourNames destination) {
			this.destination = destination;
		}
	}
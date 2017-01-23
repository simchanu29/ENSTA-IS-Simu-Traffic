package simEntity.Voiture;


import java.util.LinkedList;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourNames;

public  class Voiture extends SimEntity implements IRecordable {

		private String name;
		private CarrefourNames departure;
		private CarrefourNames destination;
		private Path chemin;
		private LogicalDuration tempsOptimal;


		public Voiture(SimEngine engine, String name, CarrefourNames departure, CarrefourNames destination) {

			super(engine,"Voiture");
			this.name=name;
			this.departure=departure;
			this.destination=destination;

			this.chemin=new Path(departure,destination);
			this.tempsOptimal=chemin.getTime2next();

		}
		
		
		public class IsArrived extends SimEvent {
			public IsArrived(LogicalDateTime scheduledDate){
				super(scheduledDate);
			}
			@Override
			public void process() {
				Logger.Information(name, "isArrived",name+ " is arrived at " + chemin.getNext());
				chemin.etape();
				if (chemin.getNext()!=chemin.getLast()){
					addEvent(new GoTo(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(2))));
				}
			}			

		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public void activate() {
			super.activate();
			//Logger.Information(this, "activate", name +" se reveille");
			this.addEvent(new GoTo(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(2))));
					
		}

		@Override
		public void initialize() {
			super.initialize();
			//Logger.Information(this, "initialize", name + " s initialise");
		}
		
		@Override
		public void deactivate() {
			super.deactivate();
			//Logger.Information(this, "deactivate", "je suis desactivé");
		}

		@Override
		public void terminate() {
			super.terminate();
			//Logger.Information(this, "terminate","je suis terminé");

		}

        public class IsArrived extends SimEvent {
        public IsArrived(LogicalDateTime scheduledDate){
            super(scheduledDate);
        }
        @Override
        public void process() {
            Logger.Information(name, "isArrived",name+ " is arrived at " + destination);
        }
    }

		@Override
		public String[] getTitles() {
			String[] titles={"D�part","Arriv�e","Dur�e Trajet"};
			return titles;
		}

		@Override
		public String[] getRecords() {
			return new String[]{getDeparture().toString(),getDestination().toString(),getTempsOptimal().toString()};
		}

		@Override
		public String getClassement() {
			return "Voiture";
		}
		
		public class GoTo extends SimEvent {

			public GoTo(LogicalDateTime scheduledDate){
				super(scheduledDate);
			}
			@Override
			public void process() {

				Logger.Information(name, "goTo",name+ " go to "+ chemin.getNext());
				addEvent(new IsArrived(getEngine().SimulationDate().add(chemin.getTime2next())));
				
			}
			
		}

		//TODO : isArrived in a queue
        //TODO : leave un carrefour

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public CarrefourNames getDeparture() {
			return departure;
		}
        public CarrefourNames getDestination() {
			return destination;
		}
        public Path getChemin() {
			return chemin;
		}
        public LogicalDuration getTempsOptimal() {
			return tempsOptimal;
		}

		public void setDeparture(CarrefourNames departure) {
			this.departure = departure;
		}
		
		public void setDestination(CarrefourNames destination) {
		
			this.destination = destination;
		}
		
		
	}
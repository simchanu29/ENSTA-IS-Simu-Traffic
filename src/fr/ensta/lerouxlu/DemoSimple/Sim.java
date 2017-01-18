package fr.ensta.lerouxlu.DemoSimple;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;

public  class Sim extends SimEntity {

		private String name;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Sim(SimEngine engine) {
			super(engine,"Sim");
		}
		
		@Override
		public void initialize() {
			super.initialize();
			Logger.Information(this, "initialize", name + " s initialise");
		}
		
		public void sayHelloTo(Sim other) {

			Logger.Information(this, "sayhello", "Hello "+ other);
			other.answerHelloTo(this);
			
		}

		public void answerHelloTo(Sim other) {
			//Calcul du prochain instant où on se dit bonjour
			long randomduration = getEngine().getRandomDuration();
			addEvent(new SayHello(getEngine().SimulationDate().add(LogicalDuration.ofHours(randomduration)),other));
			
		}
		@Override
		public String toString() {
			return name;
		}
		
		@Override
		public void activate() {
			super.activate();
			Logger.Information(this, "activate", name +" se reveille");
			
			Sim sim = (Sim)getEngine().trouver("Sim");
			if(sim!=null)
				if(sim.getName().compareTo(this.getName())!=0){
					this.addEvent(new SayHello(getEngine().SimulationDate().add(LogicalDuration.ofHours(2)),sim));
				}
		
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
		public class SayHello extends SimEvent {

			public Sim collegue;
			public SayHello(LogicalDateTime scheduledDate,Sim collegue){
				super(scheduledDate);
				this.collegue = collegue;
			}
			@Override
			public void process() {
				sayHelloTo(collegue);
				
			}
			
		}
		
	}
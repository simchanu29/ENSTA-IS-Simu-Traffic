package fr.ensta.lerouxlu.simu;
import simEntity.Voiture.Voiture;

import enstabretagne.base.time.LogicalDateTime;

public  abstract class SimEvent implements ISimEvent {

		public LogicalDateTime scheduledDate;
		public Voiture v;
		
		public SimEvent(LogicalDateTime scheduledDate){
			this.scheduledDate = scheduledDate;
		}
		public SimEvent(LogicalDateTime scheduledDate, Voiture v){
			this.scheduledDate = scheduledDate;
			this.v=v;
		}
		
		@Override
		public int compareTo(ISimEvent arg0) {
			if (arg0 instanceof SimEvent == false)
				return 0;
			SimEvent other = (SimEvent) arg0;
			return this.scheduledDate.compareTo(other.scheduledDate);
		}

		@Override
		public LogicalDateTime scheduleDate() {
			return scheduledDate;
		}

		@Override
		public void resetProcessDate(LogicalDateTime simulationDate) {
			scheduledDate = simulationDate;
		}
		public LogicalDateTime getScheduledDate() {
			return scheduledDate;
		}
		public void setScheduledDate(LogicalDateTime scheduledDate) {
			this.scheduledDate = scheduledDate;
		}
		public Voiture getV() {
			return v;
		}
		public void setV(Voiture v) {
			this.v = v;
		}
		
		
	}
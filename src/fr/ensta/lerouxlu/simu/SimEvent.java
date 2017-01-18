package fr.ensta.lerouxlu.simu;

import enstabretagne.base.time.LogicalDateTime;

public  abstract class SimEvent implements ISimEvent {

		public LogicalDateTime scheduledDate;
		
		public SimEvent(LogicalDateTime scheduledDate){
			this.scheduledDate = scheduledDate;
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
		
	}
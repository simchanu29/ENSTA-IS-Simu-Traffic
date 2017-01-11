
package simEntity.Voiture;
import enstabretagne.simulation.components.SimFeatures;

import simEntity.Carrefour.CarrefoursNames;
public class VoitureFeatures extends SimFeatures {

	CarrefoursNames originCarrefour;
	CarrefoursNames destination;
	
	public CarrefoursNames getOriginCarrefour() {
		return originCarrefour;
	}
	
	public CarrefoursNames getDestination() {
		return destination;
	}
	
	public VoitureFeatures(String id, CarrefoursNames originCarrefour,
			CarrefoursNames destination) {
		super(id);
		this.originCarrefour=originCarrefour;
		this.destination = destination;
	}

}



package simEntity.Voiture;
import enstabretagne.simulation.components.SimFeatures;

import simEntity.Carrefour.CarrefourNames;
public class VoitureFeatures extends SimFeatures {

	CarrefourNames originCarrefour;
	CarrefourNames destination;
	
	public CarrefourNames getOriginCarrefour() {
		return originCarrefour;
	}
	
	public CarrefourNames getDestination() {
		return destination;
	}
	
	public VoitureFeatures(String id, CarrefourNames originCarrefour,
			CarrefourNames destination) {
		super(id);
		this.originCarrefour=originCarrefour;
		this.destination = destination;
	}

}


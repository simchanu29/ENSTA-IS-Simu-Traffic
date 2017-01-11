package simEntity.Quartier;

import java.util.List;

import enstabretagne.simulation.components.SimFeatures;
import simEntity.Carrefour.CarrefourFeatures;

public class QuartierFeatures extends SimFeatures {
	int frequenceObservationNbVoitures;	
	List<CarrefourFeatures> carrefourFeatures;

	public QuartierFeatures(String id,
			int frequenceObservationNbVoitures,
			List<CarrefourFeatures> CarrefourFeatures) {
		super(id);
		this.frequenceObservationNbVoitures = frequenceObservationNbVoitures;
	}
	
	public int getFrequenceObservationNbVoitures() {
		return frequenceObservationNbVoitures;
	}
	
	
	public List<CarrefourFeatures> getCarrefourFeatures() {
		return carrefourFeatures;
	}
}

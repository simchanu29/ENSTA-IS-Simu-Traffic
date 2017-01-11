package simEntity.Quartier;

import java.time.DayOfWeek;
import java.util.List;

import enstabretagne.simulation.components.SimFeatures;
import simEntity.Carrefour.CarrefourFeatures;

public class QuartierFeatures extends SimFeatures {
	int frequenceObservationTailleFileAtente;	

	public QuartierFeatures(String id,
			int frequenceObservationTailleFileAtente,
			List<CarrefourFeatures> CarrefourFeatures) {
		super(id);
		this.frequenceObservationTailleFileAtente = frequenceObservationTailleFileAtente;
	}
	
	public int getFrequenceObservationTailleFileAtente() {
		return frequenceObservationTailleFileAtente;
	}
}

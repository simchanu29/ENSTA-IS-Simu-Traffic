/**
* Classe SimTrafficScenarioFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package Scenarios;

import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimFeatures;
import simEntity.Quartier.QuartierFeatures;
import simEntity.Quartier.QuartierInit;

public class SimTrafficScenarioFeatures extends SimFeatures {

	double frequenceArriveeVoitureParHeure;
	String debutArriveeVoiture;
	String finArriveeVoiture;
	QuartierFeatures QuartierFeatures;

	QuartierInit QuartierInit;
	private CategoriesGenerator delaiAttenteRecordingCatGen;

	public SimTrafficScenarioFeatures(String id,
									  double frequenceArriveeVoitureParHeure,
									  QuartierFeatures QuartierFeatures,
									  QuartierInit QuartierInit, CategoriesGenerator arrivalDelayRecordingCatGen, CategoriesGenerator delaiAttenteRecordingCatGen) {
		super(id);
		this.frequenceArriveeVoitureParHeure = frequenceArriveeVoitureParHeure;
		this.QuartierFeatures = QuartierFeatures;
		this.QuartierInit = QuartierInit;
		this.ArrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
		this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}

	public CategoriesGenerator getDelaiAttenteRecordingCatGen() {
		return delaiAttenteRecordingCatGen;
	}

	public void setDelaiAttenteRecordingCatGen(
			CategoriesGenerator delaiAttenteRecordingCatGen) {
		this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}

	CategoriesGenerator ArrivalDelayRecordingCatGen;

	public QuartierFeatures getQuartierFeatures() {
		return QuartierFeatures;
	}

	public void setQuartierFeatures(QuartierFeatures QuartierFeatures) {
		this.QuartierFeatures = QuartierFeatures;
	}

	public QuartierInit getQuartierInit() {
		return QuartierInit;
	}

	public void setQuartierInit(QuartierInit QuartierInit) {
		this.QuartierInit = QuartierInit;
	}

	public CategoriesGenerator getArrivalDelayRecordingCatGen() {
		return ArrivalDelayRecordingCatGen;
	}

	public void setArrivalDelayRecordingCatGen(
			CategoriesGenerator arrivalDelayRecordingCatGen) {
		ArrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
	}

	public void setFrequenceArriveeVoitureParHeure(
			double frequenceArriveeVoitureParHeure) {
		this.frequenceArriveeVoitureParHeure = frequenceArriveeVoitureParHeure;
	}

	public double getFrequenceArriveeVoitureParHeure() {
		return frequenceArriveeVoitureParHeure;
	}
}

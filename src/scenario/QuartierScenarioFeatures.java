package scenario;

import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimFeatures;


public class QuartierScenarioFeatures extends SimFeatures {
	double frequenceArriveeVoitureParHeure;
	String debutArriveeVoiture;
	String finArriveeVoiture;
	
	private CategoriesGenerator delaiAttenteRecordingCatGen;
	
	public QuartierScenarioFeatures(String id,
			double frequenceArriveeVoitureParHeure, 
			String debutArriveeVoiture,String finArriveeVoiture, 
			CategoriesGenerator arrivalDelayRecordingCatGen,CategoriesGenerator delaiAttenteRecordingCatGen) {
		super(id);
		this.frequenceArriveeVoitureParHeure = frequenceArriveeVoitureParHeure;
		this.debutArriveeVoiture = debutArriveeVoiture;
		this.finArriveeVoiture = finArriveeVoiture;
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

	public void setDebutArriveeVoiture(String debutArriveeVoiture) {
		this.debutArriveeVoiture = debutArriveeVoiture;
	}

	public void setFinArriveeVoiture(String finArriveeVoiture) {
		this.finArriveeVoiture= finArriveeVoiture;
	}

	public String getDebutArriveeVoiture() {
		return debutArriveeVoiture;
	}

	public String getFinArriveeVoiture() {
		return finArriveeVoiture;
	}

	public double getFrequenceArriveeVoitureParHeure() {
		return frequenceArriveeVoitureParHeure;
	}


}

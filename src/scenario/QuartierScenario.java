package scenario;

import base.Message.Messages;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;
import simEntity.Carrefour.CarrefoursNames;
import simEntity.Voiture.Voiture;
import simEntity.Voiture.VoitureFeatures;
import simEntity.Voiture.VoitureInit;

public class QuartierScenario extends SimScenario  {
	MoreRandom random;
	double lambda_arrivee_voiture; 
	
	LogicalDuration startVoitureArrival;
	LogicalDuration endVoitureArrival;

	CategoriesGenerator arrivalDelayRecordingCatGen;
	CategoriesGenerator delaiAttenteRecordingCatGen;
	
	int nbVoiture;
	
	public QuartierScenario(SimEngine engine,
			ScenarioId scenarioId, 
			SimFeatures features,LogicalDateTime start, LogicalDateTime end) {
		super(engine,scenarioId, features,start,end);
		QuartierScenarioFeatures scsf = (QuartierScenarioFeatures) features;

		arrivalDelayRecordingCatGen = scsf.getArrivalDelayRecordingCatGen();
		delaiAttenteRecordingCatGen=scsf.getDelaiAttenteRecordingCatGen();

		random = new MoreRandom(MoreRandom.globalSeed);
		lambda_arrivee_voiture = scsf.getFrequenceArriveeVoitureParHeure()/3600;
		
		//Add(new Action_EntityCreation(Quartier.class, scsf.getQuartierFeatures().getId(), scsf.getQuartierFeatures(), scsf.getQuartierInit()));
	
	}
	
	class NouvelleVoitureEvent extends SimEvent {
		int dureeNextClient;
		@Override
		public void Process() {
			int destCarrefourProba=(int)random.nextDouble()*7;
			int originCarrefourProba=(int)random.nextDouble()*7;
			while (originCarrefourProba==destCarrefourProba){
				originCarrefourProba=(int)random.nextDouble()*7;
			}
			VoitureFeatures vf ;
			vf=new VoitureFeatures("Voiture_"+nbVoiture++,CarrefoursNames.valueOf("P"+originCarrefourProba),CarrefoursNames.valueOf("P"+destCarrefourProba));
			
			Voiture v=(Voiture)createChild(getEngine(),Voiture.class, vf.getId(), vf);
			
			v.Initialize(new VoitureInit(delaiAttenteRecordingCatGen));
			Logger.Information(this.Owner(), "NouvelleVoiture", Messages.NouvelleVoiture,vf.getId());
			LogicalDateTime d = getNextTimeForVoiture();
			if(d!=null) Post(new NouvelleVoitureEvent(),d);

			v.activate();
			
		}
	}
	
	LogicalDateTime getNextTimeForVoiture() {		
		double d=random.nextExp(lambda_arrivee_voiture);
		LogicalDuration t = LogicalDuration.ofSeconds(d);
		LogicalDateTime possibleClientArrival = getCurrentLogicalDate().add(t);
	
		return possibleClientArrival;
	}
}

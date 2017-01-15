/**
* Classe SimTrafficScenario.java
*@author Olivier VERRON
*@version 1.0.
*/
package Scenarios;


import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Quartier.Quartier;
import simEntity.Voiture.Voiture;
import simEntity.Voiture.VoitureFeatures;
import simEntity.Voiture.VoitureInit;

public class SimTrafficScenario extends SimScenario {
	

	MoreRandom random;
	double lambda_arrivee_Voiture;
	
	LogicalDuration startVoitureArrival;
	LogicalDuration endVoitureArrival;

	CategoriesGenerator arrivalDelayRecordingCatGen;
	CategoriesGenerator delaiAttenteRecordingCatGen;
	
	public SimTrafficScenario(SimEngine engine,
                              ScenarioId scenarioId,
                              SimFeatures features, LogicalDateTime start, LogicalDateTime end) {
		super(engine,scenarioId, features,start,end);
		SimTrafficScenarioFeatures scsf = (SimTrafficScenarioFeatures) features;

		arrivalDelayRecordingCatGen = scsf.getArrivalDelayRecordingCatGen();
		delaiAttenteRecordingCatGen=scsf.getDelaiAttenteRecordingCatGen();

		random = new MoreRandom(MoreRandom.globalSeed);
		lambda_arrivee_Voiture = scsf.getFrequenceArriveeVoitureParHeure()/3600;
		
		Add(new Action_EntityCreation(Quartier.class, scsf.getQuartierFeatures().getId(), scsf.getQuartierFeatures(), scsf.getQuartierInit()));
		
	}
	
	int nbVoiture;

    /**
     * Evenement une nouvelle voiture arrive dans la simulation
     */
    class NouveauVoitureEvent extends SimEvent {

		int dureeNextVoiture;
		@Override
		public void Process() {
			double VoitureAvecFavoriProba=random.nextDouble()*10;
			VoitureFeatures cf ;

			//Condition non faite

			Voiture c=(Voiture)createChild(getEngine(),Voiture.class, cf.getId(), cf);
			
			c.Initialize(new VoitureInit(delaiAttenteRecordingCatGen));
			Logger.Information(this.Owner(), "NouveauVoiture", Messages.NouveauVoiturePotentiel,cf.getId());
			LogicalDateTime d = getNextTimeForVoiture();
			if(d!=null) Post(new NouveauVoitureEvent(),d);

			c.activate();
			
		}
		
		
	}

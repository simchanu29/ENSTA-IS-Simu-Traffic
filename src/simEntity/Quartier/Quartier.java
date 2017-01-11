package simEntity.Quartier;

import java.util.HashMap;
import java.util.LinkedList;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.Coiffeur;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurFeature;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.SalonFeatures;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourFeatures;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Voiture.Voiture;

public class Quartier extends SimEntity implements IRecordable{

	HashMap<CarrefourNames, Carrefour> listeCarrefours;
	LinkedList<Carrefour> coiffeursParOrdreCroissantPopularite=new LinkedList<Carrefour>();
	
	LinkedList<Voiture> nbVoiture;
	
	public Quartier(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
		QuartierFeatures qf = (QuartierFeatures) features;
		
		nbVoiture = new LinkedList<>();
		
		listeCarrefours = new HashMap<>();
		for(CarrefourFeatures cf: qf.getCarrefourFeatures()) {
			Carrefour c = (Carrefour)createChild(engine,Carrefour.class, cf.getCarrefourName().toString(), cf);
			listeCarrefours.put(c.getCarrefourName(), c);
		}

		//Optimisation : on privilégie que le moins populaire soit celui qui est choisi en priorité par un client n'ayant pas de préférences
		carrefoursParOrdreCroissantPopularite=new LinkedList<Carrefour>();
		carrefoursParOrdreCroissantPopularite.addAll(listeCarrefours.values());
	}
}
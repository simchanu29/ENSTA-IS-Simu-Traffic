package simEntity.Carrefour.Regle;

import enstabretagne.base.time.LogicalDuration;
import fr.ensta.lerouxlu.simu.SimEngine;
import simEntity.Carrefour.Carrefour;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 */
public class Stop extends CarrefourRegle {

    /**
     * Duree de l'arret recommandé au STOP
     * Cette duree est en int pour rendre le constructeur moins long à l'ecriture
     */
    private int dureeArretStop;

    public Stop(SimEngine engine, int dureeArretStop) {
		super(engine);
		this.dureeArretStop = dureeArretStop;
	}

	//TODO : create events for STOP
    //TODO : Quand une voiture arrive, elle s'arrête 2 sec et checke si il n'y a personne dans les files qui coupent son trajet. Si c'est bon elle traverse.
}

package simEntity.Carrefour.Regle;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import simEntity.Carrefour.Carrefour;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 *
 * TODO : initialize et compagnie (les fonctions de simEntity)
 */
public abstract class CarrefourRegle extends SimEntity{

    public CarrefourRegle(SimEngine engine){
        super(engine,"CarrefourRegle");
    }

    public abstract boolean voiturePasse(Voiture voiture, Carrefour carrefour);

}

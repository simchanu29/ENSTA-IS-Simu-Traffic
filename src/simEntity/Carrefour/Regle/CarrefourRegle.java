package simEntity.Carrefour.Regle;

import simEntity.Carrefour.Carrefour;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 */
public abstract class CarrefourRegle{

    public CarrefourRegle(){

    }

    public abstract boolean voiturePasse(Voiture voiture, Carrefour carrefour);

}

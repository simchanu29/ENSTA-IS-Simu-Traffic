package simEntity.Carrefour.Regle;

import simEntity.Carrefour.Carrefour;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 */
public class FeuRouge extends CarrefourRegle {

    @Override
    public boolean voiturePasse(Voiture voiture, Carrefour carrefour) {
        return false;
    }
}

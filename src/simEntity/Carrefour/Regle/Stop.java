package simEntity.Carrefour.Regle;

import fr.ensta.lerouxlu.simu.SimEngine;
import simEntity.Carrefour.Carrefour;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 */
public class Stop extends CarrefourRegle {

    public Stop(SimEngine engine) {
		super(engine);
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean voiturePasse(Voiture voiture, Carrefour carrefour) {
        return false;
    }
}

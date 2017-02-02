package simEntity.Carrefour.Regle;

import fr.ensta.lerouxlu.simu.SimEngine;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Carrefour.QueueNames;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 02/02/2017.
 */
public abstract class Feu extends CarrefourRegle{
    public Feu(SimEngine engine) {
        super(engine);
    }

    /**
     * Here the rules are triggered by the time, not the arrival of a car
     */
    @Override
    public void triggerRule(Voiture voiture, Carrefour carrefour){}

    /**
     * La voiture peut sortir
     * si elle ne coupe pas de voie
     * OU
     * si elle coupe une voie que celle-ci soit vide ou bloquee
     * @param voiture
     * @param carrefour
     * @return autorisationPassage
     */
    @Override
    public boolean voitureSort(Voiture voiture, Carrefour carrefour){
        return prioDroite(voiture, carrefour);
    }
}

package simEntity.Carrefour.Regle;

import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.QueueNames;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 */
public class FeuRouge extends CarrefourRegle {

    /**
     * Duree du feu rouge Nord-Sud en secondes
     */
    private int dureeFeuRougeNS;
    /**
     * Duree du feu vert Nord-Sud en secondes
     */
    private int dureeFeuVertNS;

    public FeuRouge(){}

    @Override
    public boolean voiturePasse(Voiture voiture, Carrefour carrefour) {
        QueueNames voitureQueue = carrefour.getQueueOfVoiture(voiture);

        switch (voitureQueue.name()){
            case "Nord":
                break;
            case "Sud":
                break;
            case "Est":
                break;
            case "Ouest":
                break;
        }

        return false;
    }
}

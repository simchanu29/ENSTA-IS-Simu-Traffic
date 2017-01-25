package simEntity.Carrefour.Regle;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.QueueNames;
import simEntity.Voiture.Voiture;

/**
 * Created by Tag on 18/01/2017.
 *
 * Le principe c'est que ce sont des feux croisés alternés. Les différentes variables sont pour plus de lisibilité
 * concernant les évenements
 */
public class FeuRougeCroises extends CarrefourRegle {

    /**
     * Duree du feu rouge Nord-Sud en secondes
     * On laisse des int pour que le constructeur soit plus court
     */
    private int dureeFeuRougeNS;
    /**
     * Duree du feu vert Nord-Sud en secondes
     * On laisse des int pour que le constructeur soit plus court
     */
    private int dureeFeuVertNS;
    /**
     * Duree du feu rouge Est-Ouest en secondes
     * On laisse des int pour que le constructeur soit plus court
     */
    private int dureeFeuRougeEO;
    /**
     * Duree du feu vert Est-Ouest en secondes
     * On laisse des int pour que le constructeur soit plus court
     */
    private int dureeFeuVertEO;


    private boolean feuNSRouge;

    public FeuRougeCroises(SimEngine engine, int dureeFeuRougeNS, int dureeFeuVertNS){
        super(engine);

        this.dureeFeuRougeNS = dureeFeuRougeNS;
        this.dureeFeuVertNS = dureeFeuVertNS;
        this.dureeFeuRougeEO = dureeFeuVertNS;
        this.dureeFeuVertEO = dureeFeuRougeNS;

        this.addEvent( new FeuRougeNS(getEngine().SimulationDate()) );
        this.addEvent( new FeuVertEO(getEngine().SimulationDate()) );
    }

    //Event

    /**
     * Gestion des feux. Ceux ci sont initialisés à leur création.
     * L'évênement en lui-même crée l'évenement suivant.
     */
    public class FeuRougeNS extends SimEvent{

        public FeuRougeNS(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            //On met le trigger que sur les premiers event car ils sont tous liés
            triggerUpdate();

            getAuthorizationEnterCarrefour().put(QueueNames.Nord,false);
            getAuthorizationEnterCarrefour().put(QueueNames.Sud,false);
            FeuRougeCroises.this.addEvent( new FeuVertNS(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuRougeNS))) );
        }
    }
    public class FeuVertNS extends SimEvent{

        public FeuVertNS(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            triggerUpdate();

            getAuthorizationEnterCarrefour().put(QueueNames.Nord,true);
            getAuthorizationEnterCarrefour().put(QueueNames.Sud,true);
            FeuRougeCroises.this.addEvent( new FeuRougeNS(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertNS))) );
        }
    }
    public class FeuRougeEO extends SimEvent{

        public FeuRougeEO(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            getAuthorizationEnterCarrefour().put(QueueNames.Est,false);
            getAuthorizationEnterCarrefour().put(QueueNames.Ouest,false);
            FeuRougeCroises.this.addEvent( new FeuVertEO(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuRougeEO))) );
        }
    }
    public class FeuVertEO extends SimEvent{

        public FeuVertEO(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }
        @Override
        public void process() {
            getAuthorizationEnterCarrefour().put(QueueNames.Est,true);
            getAuthorizationEnterCarrefour().put(QueueNames.Ouest,true);
            FeuRougeCroises.this.addEvent( new FeuRougeEO(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertEO))) );
        }
    }
}

package simEntity.Carrefour.Regle;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.QueueNames;

/**
 * Created by Tag on 18/01/2017.
 */
public class FeuRougeIndiv extends Feu{

    private int dureeFeuVertN;
    private int dureeFeuVertS;
    private int dureeFeuVertE;
    private int dureeFeuVertO;

    /**
     * Constructeur
     *
     * Si un feu n'existe pas on peut mettre la duree du feu vert à 0.
     * @param engine
     * @param dureeFeuVertN
     */
    public FeuRougeIndiv(SimEngine engine,int dureeFeuVertN,int dureeFeuVertS,int dureeFeuVertE, int dureeFeuVertO) {
        super(engine);

        this.dureeFeuVertN = dureeFeuVertN;
        this.dureeFeuVertS = dureeFeuVertS;
        this.dureeFeuVertE = dureeFeuVertE;
        this.dureeFeuVertO = dureeFeuVertO;

        this.addEvent( new FeuVertN(getEngine().SimulationDate()) );
    }

    public void setFeuIndiv(QueueNames direction){
        switch (direction){
            case Nord:
                getAuthorizationEnterCarrefour().put(QueueNames.Nord,true);
                getAuthorizationEnterCarrefour().put(QueueNames.Sud,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Est,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Ouest,false);
                break;
            case Sud:
                getAuthorizationEnterCarrefour().put(QueueNames.Nord,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Sud,true);
                getAuthorizationEnterCarrefour().put(QueueNames.Est,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Ouest,false);
                break;
            case Est:
                getAuthorizationEnterCarrefour().put(QueueNames.Nord,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Sud,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Est,true);
                getAuthorizationEnterCarrefour().put(QueueNames.Ouest,false);
                break;
            case Ouest:
                getAuthorizationEnterCarrefour().put(QueueNames.Nord,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Sud,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Est,false);
                getAuthorizationEnterCarrefour().put(QueueNames.Ouest,true);
                break;
        }
    }

    /**
     * Gestion des feux. Ceux ci sont initialisés à leur création.
     * L'évênement en lui-même crée l'évenement suivant.
     *
     * Si la duree du feu est à 0 l'evenement n'est pas loggé
     */
    public class FeuVertS extends SimEvent {

        public FeuVertS(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Sud);
            triggerUpdate();
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertO(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertS))) );
            System.out.println("["+getEngine().SimulationDate()+"][INFO](FeuVertS) in "+getCarrefour().getNom()+" triggers FeuVertO in "+ getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertS)));

        }
    }
    public class FeuVertN extends SimEvent{

        public FeuVertN(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Nord);

            triggerUpdate();
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertE(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertN))) );
            System.out.println("["+getEngine().SimulationDate()+"][INFO](FeuVertN) in "+getCarrefour().getNom()+" triggers FeuVertE in "+ getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertN)));

        }
    }
    public class FeuVertE extends SimEvent{

        public FeuVertE(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Est);
            triggerUpdate();
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertS(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertE))) );
            System.out.println("["+getEngine().SimulationDate()+"][INFO](FeuVertE) in "+getCarrefour().getNom()+" triggers FeuVertS in "+ getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertE)));
        }
    }
    public class FeuVertO extends SimEvent{

        public FeuVertO(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Ouest);
            triggerUpdate();
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertN(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertO))) );
            System.out.println("["+getEngine().SimulationDate()+"][INFO](FeuVertO) in "+getCarrefour().getNom()+" triggers FeuVertN in "+ getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertO)));
        }
    }
}

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
 */
public class FeuRougeIndiv extends CarrefourRegle{

    private int dureeFeuVertN;
    private int dureeFeuVertS;
    private int dureeFeuVertE;
    private int dureeFeuVertO;

    private boolean feuVertN;
    private boolean feuVertS;
    private boolean feuVertE;
    private boolean feuVertO;

    /**
     * Constructeur
     *
     * Si un feu n'existe pas on peut mettre la duree du feu vert à -1.
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

    @Override
    public boolean voiturePasse(Voiture voiture, Carrefour carrefour) {
        QueueNames voitureQueue = carrefour.getQueueNameOfVoiture(voiture);

        switch (voitureQueue){
            case Nord:
                return feuVertN;
            case Sud:
                return feuVertS;
            case Est:
                return feuVertE;
            case Ouest:
                return feuVertO;
        }

        return false;
    }

    public void setFeuIndiv(QueueNames direction){
        switch (direction){
            case Nord:
                this.feuVertN = true;
                this.feuVertS = false;
                this.feuVertE = false;
                this.feuVertO = false;
            case Sud:
                this.feuVertN = false;
                this.feuVertS = true;
                this.feuVertE = false;
                this.feuVertO = false;
            case Est:
                this.feuVertN = false;
                this.feuVertS = false;
                this.feuVertE = true;
                this.feuVertO = false;
            case Ouest:
                this.feuVertN = false;
                this.feuVertS = false;
                this.feuVertE = false;
                this.feuVertO = true;
        }
    }

    /**
     * Gestion des feux. Ceux ci sont initialisés à leur création.
     * L'évênement en lui-même crée l'évenement suivant.
     *
     * Si la duree du feu est à -1 l'evenement n'est pas loggé
     */
    public class FeuVertS extends SimEvent {

        public FeuVertS(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Sud);
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertE(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertS))) );
        }
    }
    public class FeuVertN extends SimEvent{

        public FeuVertN(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Nord);
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertO(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertN))) );
        }
    }
    public class FeuVertE extends SimEvent{

        public FeuVertE(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Est);
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertS(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertE))) );
        }
    }
    public class FeuVertO extends SimEvent{

        public FeuVertO(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }

        @Override
        public void process() {
            FeuRougeIndiv.this.setFeuIndiv(QueueNames.Ouest);
            FeuRougeIndiv.this.addEvent( new FeuRougeIndiv.FeuVertN(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(dureeFeuVertO))) );
        }
    }
}

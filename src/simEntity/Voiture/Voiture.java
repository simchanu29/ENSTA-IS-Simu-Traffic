package simEntity.Voiture;


import java.util.LinkedList;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Quartier.Quartier;

public  class Voiture extends SimEntity implements IRecordable {

    private String name;
    private Quartier quartier;
    private CarrefourNames departure;
    private CarrefourNames destination;
    private Path chemin;
    private LogicalDuration tempsOptimal;

    public Voiture(SimEngine engine, String name, Quartier quartier, CarrefourNames departure, CarrefourNames destination) {

        super(engine,"Voiture");
        this.name=name;
        this.quartier = quartier;
        this.departure=departure;
        this.destination=destination;

        this.chemin=new Path(departure,destination);
        this.tempsOptimal=chemin.getTime2next();

    }

    //=== EVENT ===
    // On considère que la voiture est toujours en transition.

    /* TEMP
     * J'aime pas l'idée actuelle de la conception : a savoir que le goto chaine infiniement avec le isArrived
     * Il faudrait plutôt ça comme event :
     *  - ArriveToQueue
     *      On arrive dans la file du carrefour, ne déclenche aucun évenements.
     *  - CrossCarrefour
     *      Dès que la voiture peut passer, cet evenement est déclenché. Utilise le isArrived déjà configuré.
     *      La voiture peut passer dès qu'on lui dit que c'est ok. Il faut que le check se fasse dès que la voiture
     *      arrive en 1ère position, dès qu'une voiture passe
     *
     *      DONC il faut un evenement déclenché par le carrefour : firstInQueue
     *           il faut un evenement du carrefour : voitureCrossing qui déclenche les évenements des voiture*/

    /**
     * EVENT
     * crossCarrefour
     * déclenché par FirstInQueue si la voiture peut passer
     */
    public class CrossCarrefour extends SimEvent {
			public CrossCarrefour(LogicalDateTime scheduledDate){
				super(scheduledDate);
			}
			@Override
			public void process() {
				Logger.Information(name, "crossCarrefour",name+ " is crossing " + chemin.getNext());

				// On avance d'une etape. Le next qui est celui auquel on est arrivé. L'avancement d'étape le
                // transforme en last.
				chemin.etape();
				if (chemin.getNext()!=chemin.getLast()){
				    //La voiture déclenche l'évenemenement pour se deplacer au carrefour suivant.
					addEvent(new GoTo(getEngine().SimulationDate()));
					//TODO : addEvent ACArBecomesFirst sur le carrefour où viens de quitter la voiture (donc ici c'est Last)
				}
			}
		}

    /**
     * EVENT
     * Le GoTo déclenche le ArriveToQueue
     * C'est le deplacement de la voiture jusqu'à la queue suivante.
     * Déclenché par CrossCarrefour
     */
    public class GoTo extends SimEvent {

        public GoTo(LogicalDateTime scheduledDate){
            super(scheduledDate);
        }
        @Override
        public void process() {
            Logger.Information(name, "goTo",name+ " go to "+ chemin.getNext());
            addEvent(new ArriveToQueue(getEngine().SimulationDate().add(chemin.getTime2next())));
        }
    }

    /**
     * EVENT
     * Place la voiture dans la queue.
     * Declenché par GoTo
     */
    public class ArriveToQueue extends SimEvent {
        public ArriveToQueue(LogicalDateTime scheduledDate){
            super(scheduledDate);
        }
        @Override
        public void process() {
            // Le next c'est celui après la queue
            Carrefour nextCarr = quartier.getDicCarrefour().get(chemin.getNext());
            //System.out.println("origin :"+ voiture.this.origin+    +newtCarre  "+nextCarr);
            nextCarr.addToQueue(Voiture.this);
        }
    }

    /**
     * EVENT
     * La voiture est la premiere dans la queue.
     * On declenche donc la verification si on peut passer.
     * Cet evenement est declenche par le carrefour lorsqu'une voiture deviens la première dans la queue sur une
     * des files du carrefour.
     */
    public class FirstInQueue extends SimEvent {
        public FirstInQueue(LogicalDateTime scheduledDate){
            super(scheduledDate);
        }
        @Override
        public void process() {
            Carrefour carrefourActuel = quartier.getDicCarrefour().get(chemin.getNext());
            boolean peutPasser = carrefourActuel.authorisationPassage(Voiture.this);

            if(peutPasser){
                addEvent(new CrossCarrefour(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(1))));
            }
        }
    }

    //=== OVERRIDE ===

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void activate() {
        super.activate();
        //Logger.Information(this, "activate", name +" se reveille");
        this.addEvent(new GoTo(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(2))));
    }

    @Override
    public void initialize() {
        super.initialize();
        //Logger.Information(this, "initialize", name + " s initialise");
    }

    @Override
    public void deactivate() {
        super.deactivate();
        //Logger.Information(this, "deactivate", "je suis desactivé");
    }

    @Override
    public void terminate() {
        super.terminate();
        //Logger.Information(this, "terminate","je suis terminé");

    }

    //=== GETTER AND SETTERS ===

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public CarrefourNames getDeparture() {
        return departure;
    }
    public CarrefourNames getDestination() {
        return destination;
    }
    public Path getChemin() {
        return chemin;
    }
    public LogicalDuration getTempsOptimal() {
        return tempsOptimal;
    }

    @Override
    public String[] getTitles() {
        String[] titles={"D�part","Arriv�e","Dur�e Trajet"};
        return titles;
    }

    @Override
    public String[] getRecords() {
        return new String[]{getDeparture().toString(),getDestination().toString(),getTempsOptimal().toString()};
    }

    @Override
    public String getClassement() {
        return "Voiture";
    }

    public void setDeparture(CarrefourNames departure) {
        this.departure = departure;
    }
    public void setDestination(CarrefourNames destination) { this.destination = destination; }


}
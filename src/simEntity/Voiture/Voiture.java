package simEntity.Voiture;


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
    // On considÃ¨re que la voiture est toujours en transition.

    /* TEMP
     * J'aime pas l'idÃ©e actuelle de la conception : a savoir que le goto chaine infiniement avec le isArrived
     * Il faudrait plutÃ´t Ã§a comme event :
     *  - ArriveToQueue
     *      On arrive dans la file du carrefour, ne dÃ©clenche aucun Ã©venements.
     *  - CrossCarrefour
     *      DÃ¨s que la voiture peut passer, cet evenement est dÃ©clenchÃ©. Utilise le isArrived dÃ©jÃ  configurÃ©.
     *      La voiture peut passer dÃ¨s qu'on lui dit que c'est ok. Il faut que le check se fasse dÃ¨s que la voiture
     *      arrive en 1Ã¨re position, dÃ¨s qu'une voiture passe
     *
     *      DONC il faut un evenement dÃ©clenchÃ© par le carrefour : firstInQueue
     *           il faut un evenement du carrefour : voitureCrossing qui dÃ©clenche les Ã©venements des voiture*/

    /**
     * EVENT
     * crossCarrefour
     * dÃ©clenchÃ© par FirstInQueue si la voiture peut passer
     */
    public class CrossCarrefour extends SimEvent {
			public CrossCarrefour(LogicalDateTime scheduledDate){
				super(scheduledDate);
			}
			@Override
			public void process() {
				Logger.Information(name, "crossCarrefour",name+ " is crossing " + chemin.getNext());

				// On avance d'une etape. Le next qui est celui auquel on est arrivÃ©. L'avancement d'Ã©tape le
                // transforme en last.
				chemin.etape();
				if (chemin.getNext()!=chemin.getLast()){
				    //La voiture dÃ©clenche l'Ã©venemenement pour se deplacer au carrefour suivant.
					addEvent(new GoTo(getEngine().SimulationDate()));
					//TODO : addEvent ACArBecomesFirst sur le carrefour oÃ¹ viens de quitter la voiture (donc ici c'est Last)
				}
			}
		}

    /**
     * EVENT
     * Le GoTo dÃ©clenche le ArriveToQueue
     * C'est le deplacement de la voiture jusqu'Ã  la queue suivante.
     * DÃ©clenchÃ© par CrossCarrefour
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
     * DeclenchÃ© par GoTo
     */
    public class ArriveToQueue extends SimEvent {
        public ArriveToQueue(LogicalDateTime scheduledDate){
            super(scheduledDate);
        }
        @Override
        public void process() {
            // Le next c'est celui aprÃ¨s la queue
            Carrefour nextCarr = quartier.getDicCarrefour().get(chemin.getNext());
            System.out.println("origin :"+ Voiture.this.departure+ "  destination :"+Voiture.this.destination   +"  newtCarre  "+nextCarr.getNom());
            nextCarr.addToQueue(Voiture.this);
        }
    }

    /**
     * EVENT
     * La voiture est la premiere dans la queue.
     * On declenche donc la verification si on peut passer.
     * Cet evenement est declenche par le carrefour lorsqu'une voiture deviens la premiÃ¨re dans la queue sur une
     * des files du carrefour.
     */
    public class CheckPassage extends SimEvent {
        public CheckPassage(LogicalDateTime scheduledDate){
            super(scheduledDate);
        }
        @Override
        public void process() {
            Carrefour carrefourActuel = quartier.getDicCarrefour().get(chemin.getNext());
            boolean peutPasser = carrefourActuel.autorisationPassageEntree(Voiture.this);

            if(peutPasser){
                addEvent(new CheckPrio(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(1))));
            }
        }
    }

    public class CheckPrio extends SimEvent{
        public CheckPrio(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }
        @Override
        public void process(){
            Carrefour carrefourActuel = quartier.getDicCarrefour().get(chemin.getNext());
            boolean peutPasser = carrefourActuel.autorisationPassageSortie(Voiture.this);

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
        //Logger.Information(this, "deactivate", "je suis desactivÃ©");
    }

    @Override
    public void terminate() {
        super.terminate();
        //Logger.Information(this, "terminate","je suis terminÃ©");

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
        String[] titles={"Dï¿½part","Arrivï¿½e","Durï¿½e Trajet"};
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
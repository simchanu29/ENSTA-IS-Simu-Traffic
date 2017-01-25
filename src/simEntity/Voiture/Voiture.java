package simEntity.Voiture;


import java.util.Queue;

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

    /**
     * Si la voiture est pas "dans un carrefour"
     */
    private boolean insideCarrefour;
    private boolean insideRoute;

    public Voiture(SimEngine engine, String name, Quartier quartier, CarrefourNames departure, CarrefourNames destination) {

        super(engine,"Voiture");
        this.name=name;
        this.quartier = quartier;
        this.departure=departure;
        this.destination=destination;

        this.insideCarrefour = true;
        this.insideRoute = false;

        this.chemin=new Path(departure,destination);
        this.tempsOptimal=chemin.getTime2next();

    }

    //=== EVENT ===
    // On considère que la voiture est toujours en transition.

    /* TEMP TODO : erase this if not necessary
     * J'aime pas l'idée actuelle de la conception : a savoir que le goto chaine infiniement avec le isArrived
     * Il faudrait plutôt ça comme event :
     *  - ArriveToQueue
     *      On arrive dans la file du carrefour, ne dÃ©clenche aucun Ã©venements.
     *  - CrossCarrefour
     *      DÃ¨s que la voiture peut passer, cet evenement est déclenchéé. Utilise le isArrived déjà  configuré.
     *      La voiture peut passer dès qu'on lui dit que c'est ok. Il faut que le check se fasse dÃ¨s que la voiture
     *      arrive en 1ère position, dès qu'une voiture passe
     *
     *      DONC il faut un evenement déclenché par le carrefour : firstInQueue
     *           il faut un evenement du carrefour : voitureCrossing qui dÃ©clenche les Ã©venements des voiture*/

    /**
     * EVENT
     * crossCarrefour
     * déclenché par FirstInQueue si la voiture peut passer
     */
    public class CrossCarrefour extends SimEvent {
			public CrossCarrefour(LogicalDateTime scheduledDate){
				super(scheduledDate,Voiture.this);
			}
			@Override
			public void process() {
				Logger.Information(name, "crossCarrefour",name+ " is crossing " + chemin.getNext());

				// On avance d'une etape. Le next qui est celui auquel on est arrivé. L'avancement d'étape le
                // transforme en last.
				chemin.etape();

				System.out.println("(crossCarrefour)   Chemin de "+name+" :  "+chemin.toString());
				//System.out.println("(crossCarrefour)   Previous : "+chemin.getPrevious()+ "          Next  : " +chemin.getNext());

                if (chemin.getNext()!=chemin.getPrevious()){

                    //La voiture déclenche l'évenemenement pour se deplacer au carrefour suivant.
					addEvent(new GoTo(getEngine().SimulationDate()));

					//update carrefour qu'on vient de quitter (previous)
					Carrefour prevCarr = quartier.getDicCarrefour().get(chemin.getPrevious());
                    prevCarr.updateCarrefour();
				}
			}
		}

    /**
     * EVENT
     * Le GoTo déclenche le ArriveToQueue
     * C'est le deplacement de la voiture jusqu'à  la queue suivante.
     * Déclenché par CrossCarrefour
     */
    public class GoTo extends SimEvent {

        public GoTo(LogicalDateTime scheduledDate){
            super(scheduledDate,Voiture.this);
        }
        @Override
        public void process() {
            Logger.Information(name, "goTo",name+ " go to "+ chemin.getNext());
            setInsideRoute(true);
            setInsideCarrefour(false);

            if(chemin.getNext()!=destination){
            	addEvent(new ArriveToQueue(getEngine().SimulationDate().add(chemin.getTime2next())));
            }
            else{
            	Logger.Information(name, "isArrived", "is arrived at" + chemin.getNext());
            }
        }
    }

    /**
     * EVENT
     * Place la voiture dans la queue.
     * Declenché par GoTo
     */
    public class ArriveToQueue extends SimEvent {
        public ArriveToQueue(LogicalDateTime scheduledDate){
            super(scheduledDate,Voiture.this);
        }
        @Override
        public void process() {
            // Le next c'est celui après la queue
            Carrefour nextCarr = quartier.getDicCarrefour().get(chemin.getNext());
            //System.out.println("origin :"+ Voiture.this.departure+ "  destination :"+Voiture.this.destination   +"  newtCarre  "+nextCarr.getNom());
            nextCarr.addToQueue(Voiture.this);
            //Déclencher CheckCarrefour si la voiture est la 1ere dans sa file
            //TODO : suppression
            Queue<Voiture> myQueue=nextCarr.getQueueOfVoiture(Voiture.this);

            //UpdateCarrefour pour voir si c'est la 1ere dans la file et déclencher CheckPassage quand ça sera le cas
            nextCarr.updateCarrefour();
        }
    }

    /**
     * EVENT
     * La voiture est la premiere dans la queue.
     * On declenche donc la verification si on peut passer.
     * Cet evenement est declenche par le carrefour lorsqu'une voiture deviens la première dans la queue sur une
     * des files du carrefour.
     */
    public class CheckPassage extends SimEvent {
        public CheckPassage(LogicalDateTime scheduledDate){
            super(scheduledDate);

        }
        @Override
        public void process() {
            setInsideRoute(false);

            // Le !insideCarrefour est juste une sécurité, on pourrait ajouter un else if !insideCarrefour et log une
            // erreur si ça arrivait
            if(!insideCarrefour) {
                Carrefour carrefourActuel = quartier.getDicCarrefour().get(chemin.getNext());
                boolean peutPasser = carrefourActuel.autorisationPassageEntree(Voiture.this);

                if (peutPasser) {
                    //On entre dans le carrefour et quitte la file
                    setInsideCarrefour(true);

                    //On rajoute la voiture dans le bon buffer
                    carrefourActuel.setBufferFromQueue(Voiture.this);

                    //dequeue voiture de la file du carrefour qu'on vient de quitter
                    carrefourActuel.rmFromQueue(Voiture.this);

                    addEvent(new CheckPrio(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(1))));
                    //Logger.Information(this, "checkPassage", name +" ok pass in 1s");
                }
            }
            else{
                System.out.println("[ERROR] checkPassage while inside carrefour");
            }
        }
    }

    /**
     * EVENT
     * Une fois que la voiture attend au milieu du carrefour elle verifie si elle peut passer sans griller la priorité
     */
    public class CheckPrio extends SimEvent{
        public CheckPrio(LogicalDateTime scheduledDate) {
            super(scheduledDate);
        }
        @Override
        public void process(){

            if(insideCarrefour) {
                Carrefour carrefourActuel = quartier.getDicCarrefour().get(chemin.getNext());
                boolean peutPasser = carrefourActuel.autorisationPassageSortie(Voiture.this);

                if (peutPasser && insideCarrefour) {
                    //On quitte le carrefour
                    setInsideCarrefour(false);

                    //On enlève lavoiture du buffer
                    carrefourActuel.setBufferOfVoiture(Voiture.this, null);

                    addEvent(new CrossCarrefour(getEngine().SimulationDate().add(LogicalDuration.ofSeconds(1))));
                }
            }
            else{
                System.out.println("[ERROR] checkPrio while outside or crossing carrefour");
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
        String[] titles={"Départ","Arrivée","Durée Trajet"};
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
    public boolean isInsideCarrefour() {
        return insideCarrefour;
    }

    public void setInsideCarrefour(boolean insideCarrefour) {
        this.insideCarrefour = insideCarrefour;
    }
    public void setInsideRoute(boolean insideRoute) {
        this.insideRoute = insideRoute;
    }

    public boolean isInsideRoute() {
        return insideRoute;
    }
    public void setDeparture(CarrefourNames departure) {
        this.departure = departure;
    }
    public void setDestination(CarrefourNames destination) { this.destination = destination; }




}
package simEntity.Carrefour;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Quartier.Quartier;
import simEntity.Voiture.Voiture;

import java.util.LinkedList;
import java.util.Queue;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;

import fr.ensta.lerouxlu.simu.SimEvent;


/**
 * Created by Tag on 18/01/2017.
 *
 * TODO : initialize et compagnie (les fonctions de simEntity)
 */
public class Carrefour extends SimEntity {

    private CarrefourNames nom;
    private CarrefourRegle regle;

    /**
     * Le carrefour sait dans quel quartier il est
     */
    private Quartier quartier;

    /**
     * Les variables qui indiquent au carrefour à quel carrefour il est relié
     * TODO : utile ?
     */
    private Carrefour carrefourSud;
    private Carrefour carrefourEst;
    private Carrefour carrefourNord;
    private Carrefour carrefourOuest;

    /**
     * Les ports d'entrée dans le carrefour
     * Les queues sont vide à l'initialisation
     */
    private Queue<Voiture> queueSud;
    private Route RouteSud;
    private Queue<Voiture> queueEst;
    private Route RouteEst;
    private Queue<Voiture> queueNord;
    private Route RouteNord;
    private Queue<Voiture> queueOuest;
    private Route RouteOuest;

    MoreRandom random;
    private LinkedList<Integer> freqPopVoiture; // Faire une liste des frequences en fonction des heures
    private LinkedList<Voiture> listFirstInQueue;

    private Voiture bufferCarrefourNE;
    private Voiture bufferCarrefourSO;

    /**
     *  Constructeur du carrefour : intersection
     * @param engine
     * @param quartier
     * @param nom
     * @param regle
     */
    public Carrefour(SimEngine engine, Quartier quartier, CarrefourNames nom, CarrefourRegle regle){
    	super(engine,"Carrefour");
        regle.setCarrefour(this);

    	this.nom=nom;
        this.regle=regle;
    	this.quartier = quartier;

        queueSud=new LinkedList<Voiture>();
        queueNord=new LinkedList<Voiture>();
        queueOuest=new LinkedList<Voiture>();
        queueEst=new LinkedList<Voiture>();
        listFirstInQueue=new LinkedList<Voiture>();
    }

    /**
     * Constructeur du spot de pop
     * @param engine
     * @param quartier
     * @param nom
     * @param freqPopVoiture
     */
    public Carrefour(SimEngine engine, Quartier quartier, CarrefourNames nom, LinkedList<Integer>freqPopVoiture){
    	super(engine,"Carrefour");

    	this.nom=nom;
    	this.quartier = quartier;

        random = new MoreRandom(MoreRandom.globalSeed);
        this.freqPopVoiture=freqPopVoiture;
    }

    /**
     * override functions
     **/
    public void initialize() {
        super.activate();
        Logger.Information(nom, "activate", "Carrefour se reveille");

    }

    public void activate() {
        super.activate();
        LogicalDateTime e = getEngine().SimulationDate();
        //Si le carrefour est un g�n�rateur (ie un des 7 premiers noms de l'�num�ration)
        if (CarrefourNames.valueOf(nom.toString()).ordinal()<7){
            addEvent(new NouvelleVoitureEvent(e));
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        Logger.Information(this, "deactivate", "je suis desactivé");
    }
    @Override
    public void terminate() {
        super.terminate();
        Logger.Information(this, "terminate","je suis terminé");

    }

    //=== UTILITY FUNCTIONS ===

    /**
     * La methode principale pour autoriser les voitures à passer
     * @param voiture
     * @return true si la voiture peux passer
     */
    public boolean autorisationPassageEntree(Voiture voiture){
        // Lui il a besoin de savoir où est la voiture qui veut passer et si les autres files prioritaires sont vide
        // Il a donc besoin de connaitre l'etat des autres files(recupérer l'objet) et la file dans laquelle est la
        // voiture (le carrefour ?)
        boolean bufferAvailable = getBufferFromQueue(voiture)==null;
        return (regle.voitureEntre(voiture,this) && bufferAvailable);
    }

    public boolean autorisationPassageSortie(Voiture voiture){
        return regle.voitureSort(voiture,this);
    }

    /**
     * updateCarrefour déclenche
     *  - CheckPassage pour toutes les voitures en 1ere place dans chaque file d'attente du carrefour qui n'a pas reagi
     *    suite a un precedent evenement (En train de resoudre checkPrio donc)
     *  - CheckPrio pour toutes les voitures dans les buffer si elles n'ont pas reagi suite a un precedent evenement
     */
    public void updateCarrefour(){
    	//System.out.println("(updateCarrefour)  "+ nom);

    	//CheckPassage
        if( queueNord.peek()!=null && !queueNord.peek().isInsideCarrefour()){
    			addEvent(queueNord.peek().new CheckPassage(getEngine().SimulationDate()));
    	}
    	if(  queueSud.peek()!=null && !queueSud.peek().isInsideCarrefour()){
    			addEvent(queueSud.peek().new CheckPassage(getEngine().SimulationDate()));
    	}
    	if(queueOuest.peek()!=null && !queueOuest.peek().isInsideCarrefour()){
    			addEvent(queueOuest.peek().new CheckPassage(getEngine().SimulationDate()));
    	}
    	if(  queueEst.peek()!=null && !queueEst.peek().isInsideCarrefour()){
    			addEvent(queueEst.peek().new CheckPassage(getEngine().SimulationDate()));
    	}

    	//CheckPrio
    	if(bufferCarrefourNE!=null && bufferCarrefourNE.isInsideCarrefour()){
    		    System.out.println("(carrefour) Add Event checkPrio  "+bufferCarrefourNE.getChemin().getNext()+" pour  "+bufferCarrefourNE.getName()+"  at  "+getEngine().SimulationDate()+"  for  "+getEngine().SimulationDate());
                addEvent(bufferCarrefourNE.new CheckPrio(getEngine().SimulationDate()));
        }
        if(bufferCarrefourSO!=null && bufferCarrefourSO.isInsideCarrefour()){
        	 	
        		System.out.println("(carrefour) Add Event checkPrio  "+bufferCarrefourSO.getChemin().getNext()+" pour  "+bufferCarrefourSO.getName()+"  at  "+getEngine().SimulationDate()+"  for  "+getEngine().SimulationDate());
                addEvent(bufferCarrefourSO.new CheckPrio(getEngine().SimulationDate()));
                
        }

//    	if(queueNord.peek()!=null && !this.listFirstInQueue.contains(queueNord.peek())){
//    			this.listFirstInQueue.add(queueNord.peek());
//    			addEvent(queueNord.peek().new CheckPassage(getEngine().SimulationDate()));
//    	}
//    	if(queueSud.peek()!=null && !this.listFirstInQueue.contains(queueSud.peek())){
//    			this.listFirstInQueue.add(queueSud.peek());
//    			addEvent(queueSud.peek().new CheckPassage(getEngine().SimulationDate()));
//    	}
//    	if(queueOuest.peek()!=null && !this.listFirstInQueue.contains(queueOuest.peek())){
//    			this.listFirstInQueue.add(queueOuest.peek());
//    			addEvent(queueOuest.peek().new CheckPassage(getEngine().SimulationDate()));
//    	}
//    	if(queueEst.peek()!=null && !this.listFirstInQueue.contains(queueEst.peek())){
//    			this.listFirstInQueue.add(queueEst.peek());
//    			addEvent(queueEst.peek().new CheckPassage(getEngine().SimulationDate()));
//    	}

    	//addEvent(prevCarr.new UpdateCarrefour(getEngine().SimulationDate()));
    }

    CarrefourNames calculDestination(CarrefourNames departure){
        CarrefourNames destination=null;
        double pDest = random.nextDouble()*100;
        switch(departure){
            case P1:
                if(pDest<=5) destination=CarrefourNames.P2;
                if(pDest>5  && pDest<=15 ) destination = CarrefourNames.P3;
                if(pDest>15 && pDest<=25 ) destination = CarrefourNames.P4;
                if(pDest>25 && pDest<=30 ) destination = CarrefourNames.P5;
                if(pDest>30 && pDest<=65 ) destination = CarrefourNames.P6;
                if(pDest>65 && pDest<=100) destination = CarrefourNames.P7;
                break;
            case P2:
                if(pDest<=10) destination=CarrefourNames.P1;
                if(pDest>10 && pDest<=15 ) destination = CarrefourNames.P3;
                if(pDest>15 && pDest<=35 ) destination = CarrefourNames.P4;
                if(pDest>35 && pDest<=55 ) destination = CarrefourNames.P5;
                if(pDest>55 && pDest<=80 ) destination = CarrefourNames.P6;
                if(pDest>80 && pDest<=100) destination = CarrefourNames.P7;
                break;
            case P3:
                if(pDest<=15) destination=CarrefourNames.P1;
                if(pDest>15 && pDest<=30 ) destination = CarrefourNames.P2;
                if(pDest>30 && pDest<=50 ) destination = CarrefourNames.P4;
                if(pDest>50 && pDest<=70 ) destination = CarrefourNames.P5;
                if(pDest>70 && pDest<=90 ) destination = CarrefourNames.P6;
                if(pDest>90 && pDest<=100) destination = CarrefourNames.P7;
                break;
            case P4:
                if(pDest<=15) destination=CarrefourNames.P1;
                if(pDest>15 && pDest<=25 ) destination = CarrefourNames.P2;
                if(pDest>25 && pDest<=35 ) destination = CarrefourNames.P3;
                if(pDest>35 && pDest<=55 ) destination = CarrefourNames.P5;
                if(pDest>55 && pDest<=95 ) destination = CarrefourNames.P6;
                if(pDest>95 && pDest<=100) destination = CarrefourNames.P7;
                break;
            case P5:
                if(pDest<=10) destination=CarrefourNames.P1;
                if(pDest>10 && pDest<=40 ) destination = CarrefourNames.P2;
                if(pDest>40 && pDest<=50 ) destination = CarrefourNames.P3;
                if(pDest>50 && pDest<=60 ) destination = CarrefourNames.P4;
                if(pDest>60 && pDest<=70 ) destination = CarrefourNames.P6;
                if(pDest>70 && pDest<=100) destination = CarrefourNames.P7;
                break;
            case P6:
                if(pDest<=20) destination=CarrefourNames.P1;
                if(pDest>20 && pDest<=30 ) destination = CarrefourNames.P2;
                if(pDest>30 && pDest<=70 ) destination = CarrefourNames.P3;
                if(pDest>70 && pDest<=80 ) destination = CarrefourNames.P4;
                if(pDest>80 && pDest<=90 ) destination = CarrefourNames.P5;
                if(pDest>90 && pDest<=100) destination = CarrefourNames.P7;
                break;
            case P7:
                if(pDest<=20) destination=CarrefourNames.P1;
                if(pDest>20 && pDest<=40 ) destination = CarrefourNames.P2;
                if(pDest>40 && pDest<=60 ) destination = CarrefourNames.P3;
                if(pDest>60 && pDest<=80 ) destination = CarrefourNames.P4;
                if(pDest>80 && pDest<=90 ) destination = CarrefourNames.P5;
                if(pDest>90 && pDest<=100) destination = CarrefourNames.P6;
                break;
            default:
                break;

        }
        return destination;
    }

    public void addToQueue(Voiture voiture){
        Carrefour lastCarr = quartier.getDicCarrefour().get(voiture.getChemin().getPrevious());
        QueueNames queue = getQueueByCarrefour(lastCarr);

        System.out.println("(AddToQueue)   " +voiture.getName()+"  added to queue : " + nom+"/"+queue.name());

        getQueueByName(queue).add(voiture);
    }

    public void rmFromQueue(Voiture voiture){

        System.out.println("(rmFromQueue)       "+this.getQueueOfVoiture(voiture).peek().getName()+"  will be removed from  "+ nom +"/"+this.getQueueNameOfVoiture(voiture));

        //this.listFirstInQueue.remove(voiture);     //supprime de la liste des voitures en 1ere place des files d'attente
        this.getQueueOfVoiture(voiture).remove();  //supprime de la file d'attente
    }

    //=== EVENT ===

    /**
     * EVENT
     */
    class NouvelleVoitureEvent extends SimEvent {

		public NouvelleVoitureEvent(LogicalDateTime scheduledDate) {
			super(scheduledDate);
		}
        @Override
		public void process() {
            CarrefourNames origin = nom;
            CarrefourNames destination = calculDestination(origin);

			String name = "Voiture_"+String.valueOf(getEngine().getNbVoiture());
			Voiture v = new Voiture(getEngine(), name, quartier, origin, destination);
			Logger.Information(name, "NouvelleVoiture", name + " est créée en " + origin);
			LogicalDateTime d = getNextTimeForVoiture();
			if(d!=null) addEvent(new NouvelleVoitureEvent(d));
			v.activate();
			getEngine().addVoiture(1);
		}
	}

	//=== GETTER AND SETTERS ===

    public Voiture getBufferFromQueue(Voiture voiture){
        //On utilise getQueueByVoiture car la voiture est forcément dans une queue
        QueueNames queueName = getQueueNameOfVoiture(voiture);
        switch (queueName){
            case Nord: case Est:
                return bufferCarrefourNE;
            case Sud: case Ouest:
                return bufferCarrefourSO;
        }
        return null;
    }
    public void setBufferFromQueue(Voiture voiture){
        //On utilise getQueueByVoiture car la voiture est forcément dans une queue
        QueueNames queueName = getQueueNameOfVoiture(voiture);
        switch (queueName){
            case Nord: case Est:
                this.bufferCarrefourNE=voiture;
                break;
            case Sud: case Ouest:
                this.bufferCarrefourSO=voiture;
                break;
        }
    }
    public Voiture setBufferOfVoiture(Voiture voiture,Voiture setValue){
        if(voiture==bufferCarrefourNE){
            this.bufferCarrefourNE=setValue;
        }
        else if(voiture==bufferCarrefourSO){
            this.bufferCarrefourSO=setValue;
        }
        return null;
    }
    public Queue getQueueByName(QueueNames queue){
        switch (queue){
            case Nord:
                return queueNord;
            case Sud:
                return queueSud;
            case Est:
                return queueEst;
            case Ouest:
                return queueOuest;
            case Not_a_queue:
                System.out.println("ERREUR : queue inconnue");
                //TODO : log error with logger
                break;
        }
        return null;
    }
    public LogicalDateTime getNextTimeForVoiture() {
        int currentFreqPopVoiture=freqPopVoiture.get(0);
        int simHour=getEngine().SimulationDate().getHour();

        if (simHour<=7) currentFreqPopVoiture=freqPopVoiture.get(0);
        if (simHour>7  && simHour<=9 ) currentFreqPopVoiture = freqPopVoiture.get(1);
        if (simHour>9  && simHour<=17) currentFreqPopVoiture = freqPopVoiture.get(2);
        if (simHour>17 && simHour<=19) currentFreqPopVoiture = freqPopVoiture.get(3);
        if (simHour>19 && simHour<=24) currentFreqPopVoiture = freqPopVoiture.get(4);

        LogicalDuration t = LogicalDuration.ofSeconds(Math.floor(3600/currentFreqPopVoiture));
        LogicalDateTime possibleVoitureArrival = getEngine().SimulationDate().add(t);

        return possibleVoitureArrival;
    }
    public QueueNames getQueueNameOfVoiture(Voiture voiture){
        if(queueEst.contains(voiture)){
            return QueueNames.Est;
        }else if(queueNord.contains(voiture)){
            return QueueNames.Nord;
        }else if(queueOuest.contains(voiture)){
            return QueueNames.Ouest;
        }else if(queueSud.contains(voiture)){
            return QueueNames.Sud;
        }else{
            return QueueNames.Not_a_queue;
        }
    }
    public Queue<Voiture> getQueueOfVoiture(Voiture voiture){
        if(queueEst.contains(voiture)){
            return queueEst;
        }else if(queueNord.contains(voiture)){
            return queueNord;
        }else if(queueOuest.contains(voiture)){
            return queueOuest;
        }else if(queueSud.contains(voiture)){
            return queueSud;
        }else{
        	System.err.println(voiture.getName()+" doesn't have a valid queue");
            return null;
        }
    }
    public QueueNames getQueueByCarrefour(Carrefour carrefour){
        // J'utilise la compraison par carrefour car c'est bien plus rapide de comparer deux adresses que des String
        // Mais si nécessaire on peut changer par les nom des carrefours.
        if(     this.carrefourNord !=null && carrefour==this.carrefourNord ){return QueueNames.Nord;}
        else if(this.carrefourSud  !=null && carrefour==this.carrefourSud  ){return QueueNames.Sud ;}
        else if(this.carrefourEst  !=null && carrefour==this.carrefourEst  ){return QueueNames.Est ;}
        else if(this.carrefourOuest!=null && carrefour==this.carrefourOuest){return QueueNames.Ouest;}
        else{
            return QueueNames.Not_a_queue;
            // TODO : log l'erreur
        }
    }
    public QueueNames getQueueByCarrefourName(CarrefourNames carrefour){
        // J'utilise la compraison par carrefour car c'est bien plus rapide de comparer deux adresses que des String
        // Mais si nécessaire on peut changer par les nom des carrefours.
        if(     this.carrefourNord !=null && carrefour.equals(this.carrefourNord.getNom() )){return QueueNames.Nord ;}
        else if(this.carrefourSud  !=null && carrefour.equals( this.carrefourSud.getNom() )){return QueueNames.Sud  ;}
        else if(this.carrefourEst  !=null && carrefour.equals( this.carrefourEst.getNom() )){return QueueNames.Est  ;}
        else if(this.carrefourOuest!=null && carrefour.equals(this.carrefourOuest.getNom())){return QueueNames.Ouest;}
        else{
            return QueueNames.Not_a_queue;
            // TODO : log l'erreur
        }
    }
    public CarrefourNames getNom() {
        return nom;
    }
    public CarrefourRegle getRegle() {
        return regle;
    }
    public Carrefour getCarrefourEst() {
        return carrefourEst;
    }
    public Carrefour getCarrefourNord() {
        return carrefourNord;
    }
    public Carrefour getCarrefourOuest() {
        return carrefourOuest;
    }
    public Carrefour getCarrefourSud() {
        return carrefourSud;
    }
    public void setCarrefourNord(Carrefour carrefourNord) { this.carrefourNord = carrefourNord; }
    public void setCarrefourSud(Carrefour carrefourSud) { this.carrefourSud = carrefourSud; }
    public void setCarrefourEst(Carrefour carrefourEst) { this.carrefourEst = carrefourEst; }
    public void setCarrefourOuest(Carrefour carrefourOuest) { this.carrefourOuest = carrefourOuest; }
    public Queue<Voiture> getQueueEst() {
        return queueEst;
    }
    public Queue<Voiture> getQueueNord() {
        return queueNord;
    }
    public Queue<Voiture> getQueueOuest() {
        return queueOuest;
    }
    public Queue<Voiture> getQueueSud() {
        return queueSud;
    }
    public Voiture getBufferCarrefourNE() {
        return bufferCarrefourNE;
    }
    public void setBufferCarrefourNE(Voiture bufferCarrefourNE) {
        this.bufferCarrefourNE = bufferCarrefourNE;
    }
    public Voiture getBufferCarrefourSO() {
        return bufferCarrefourSO;
    }
    public void setBufferCarrefourSO(Voiture bufferCarrefourSO) {
        this.bufferCarrefourSO = bufferCarrefourSO;
    }
}

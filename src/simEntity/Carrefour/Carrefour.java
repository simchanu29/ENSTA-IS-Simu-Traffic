package simEntity.Carrefour;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Voiture.Voiture;

import java.util.LinkedList;
import java.util.Queue;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
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
     * Les variables qui indiquent au carrefour à quel carrefour il est relié
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
    private Queue<Voiture> queueEst;
    private Queue<Voiture> queueNord;
    private Queue<Voiture> queueOuest;
    
    MoreRandom random;
    private LinkedList<Integer> freqPopVoiture; // Faire une liste des frequences en fonction des heures

    /**
     * Constructeur carrefour, si le lien est vide, mettre NULL
     *
     * Le carrefourNames est necessaire pour l'instant pour Dijkistra
     * Malgré tout on a un doublon avec la definition du carrefour
     * TODO : investiguer si c'est un probleme.
     */
    
    public Carrefour(SimEngine engine, CarrefourNames nom, CarrefourRegle regle,Carrefour carrSud,Carrefour carrEst,Carrefour carrNord,Carrefour carrOuest, LinkedList<Integer>freqPopVoiture){
    	super(engine,"Carrefour");
    	carrefourEst=carrEst;
        carrefourNord=carrNord;
        carrefourOuest=carrOuest;
        carrefourSud=carrSud;

        this.nom=nom;
        this.regle=regle;
        random = new MoreRandom(MoreRandom.globalSeed);	
        this.freqPopVoiture=freqPopVoiture;
    }
    
    public Carrefour(SimEngine engine, CarrefourNames nom, LinkedList<Integer>freqPopVoiture){
    	super(engine,"Carrefour");
    	this.nom=nom;
    	this.freqPopVoiture=freqPopVoiture;
    	random = new MoreRandom(MoreRandom.globalSeed);	
    }

    /**
     * La methode principale pour authoriser les voitures à passer
     * @param voiture
     * @return true si la voiture peux passer
     */
    public boolean authorisationPassage(Voiture voiture){

        // Lui il a besoin de savoir où est la voiture qui veut passer et si les autres files prioritaires sont vide
        // Il a donc besoin de connaitre l'etat des autres files(recupérer l'objet) et la file dans laquelle est la
        // voiture (le carrefour ?)
        return regle.voiturePasse(voiture,this);
    }

    public QueueNames getQueueOfVoiture(Voiture voiture){
        if(queueEst.contains(voiture)){
            return QueueNames.Est;
        }else if(queueNord.contains(voiture)){
            return QueueNames.Nord;
        }else if(queueOuest.contains(voiture)){
            return QueueNames.Ouest;
        }else if(queueSud.contains(voiture)){
            return QueueNames.Sud;
        }else{
            return null;
        }
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
    public CarrefourNames getNom() {
        return nom;
    }
    public CarrefourRegle getRegle() {
        return regle;
    }
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

    //Evenements : ceux qui arrivent d'un point de vue global sur l'etat du carrefour
   /* *
    * - Feu vert/Feu rouge
    * */
    
    /** Camille is working here **/
	public void initialize() {
		super.activate();
		Logger.Information(nom, "activate", "Carrefour se reveille");		
	
	}
	
	public void activate() {
		super.activate();
		LogicalDateTime e = getEngine().SimulationDate();
		
		//Si le carrefour est un g�n�rateur (ie un des 7 premiers noms de l'�num�ration)
		if (CarrefourNames.valueOf(nom.toString()).ordinal()<=7){
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

    
	class NouvelleVoitureEvent extends SimEvent {
		public NouvelleVoitureEvent(LogicalDateTime scheduledDate) {
			super(scheduledDate);
		}

		@Override
		public void process() {
			CarrefourNames nameL = nom;
			CarrefourNames nameD = calculDestination(nameL);

			String name = "Voiture_"+String.valueOf(getEngine().getNbVoiture());
			Voiture v = new Voiture(getEngine(), name, nameL,nameD);
			Logger.Information(name, "NouvelleVoiture", name + " est cr��e en "+nameL);
			LogicalDateTime d = getNextTimeForVoiture();
			if(d!=null) addEvent(new NouvelleVoitureEvent(d));
			v.activate();
			getEngine().addVoiture(1);
			
		}
	}
	
	LogicalDateTime getNextTimeForVoiture() {		
		int currentFreqPopVoiture=freqPopVoiture.get(0);
		int simHour=getEngine().SimulationDate().getHour();
		
		if (simHour<=7) currentFreqPopVoiture=freqPopVoiture.get(0);
		if (simHour>7&&simHour<=9)currentFreqPopVoiture=freqPopVoiture.get(1);
		if (simHour>9&&simHour<=17)currentFreqPopVoiture=freqPopVoiture.get(2);
		if (simHour>17&&simHour<=19)currentFreqPopVoiture=freqPopVoiture.get(3);
		if (simHour>19&&simHour<=24)currentFreqPopVoiture=freqPopVoiture.get(4);
	
		LogicalDuration t = LogicalDuration.ofSeconds(Math.floor(3600/currentFreqPopVoiture));
		LogicalDateTime possibleVoitureArrival = getEngine().SimulationDate().add(t);
	
		return possibleVoitureArrival;
	}
	
	CarrefourNames calculDestination(CarrefourNames departure){
		CarrefourNames destination=null;
		double pDest = random.nextDouble()*100;
		switch(departure){
			case P1:
				if(pDest<=5) destination=CarrefourNames.P2;
				if(pDest>5 && pDest<=15) destination=CarrefourNames.P3;
				if(pDest>15 && pDest<=25) destination=CarrefourNames.P4;
				if(pDest>25 && pDest<=30) destination=CarrefourNames.P5;
				if(pDest>30 && pDest<=65) destination=CarrefourNames.P6;
				if(pDest>65 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P2:
				if(pDest<=10) destination=CarrefourNames.P1;
				if(pDest>10 && pDest<=15) destination=CarrefourNames.P3;
				if(pDest>15 && pDest<=35) destination=CarrefourNames.P4;
				if(pDest>35 && pDest<=55) destination=CarrefourNames.P5;
				if(pDest>55 && pDest<=80) destination=CarrefourNames.P6;
				if(pDest>80 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P3:
				if(pDest<=15) destination=CarrefourNames.P1;
				if(pDest>15 && pDest<=30) destination=CarrefourNames.P2;
				if(pDest>30 && pDest<=50) destination=CarrefourNames.P4;
				if(pDest>50 && pDest<=70) destination=CarrefourNames.P5;
				if(pDest>70 && pDest<=90) destination=CarrefourNames.P6;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P4:
				if(pDest<=15) destination=CarrefourNames.P1;
				if(pDest>15 && pDest<=25) destination=CarrefourNames.P2;
				if(pDest>25 && pDest<=35) destination=CarrefourNames.P3;
				if(pDest>35 && pDest<=55) destination=CarrefourNames.P5;
				if(pDest>55 && pDest<=95) destination=CarrefourNames.P6;
				if(pDest>95 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P5:
				if(pDest<=10) destination=CarrefourNames.P1;
				if(pDest>10 && pDest<=40) destination=CarrefourNames.P2;
				if(pDest>40 && pDest<=50) destination=CarrefourNames.P3;
				if(pDest>50 && pDest<=60) destination=CarrefourNames.P4;
				if(pDest>60 && pDest<=70) destination=CarrefourNames.P6;
				if(pDest>70 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P6:
				if(pDest<=20) destination=CarrefourNames.P1;
				if(pDest>20 && pDest<=30) destination=CarrefourNames.P2;
				if(pDest>30 && pDest<=70) destination=CarrefourNames.P3;
				if(pDest>70 && pDest<=80) destination=CarrefourNames.P4;
				if(pDest>80 && pDest<=90) destination=CarrefourNames.P5;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P7:
				if(pDest<=20) destination=CarrefourNames.P1;
				if(pDest>20 && pDest<=40) destination=CarrefourNames.P2;
				if(pDest>40 && pDest<=60) destination=CarrefourNames.P3;
				if(pDest>60 && pDest<=80) destination=CarrefourNames.P4;
				if(pDest>80 && pDest<=90) destination=CarrefourNames.P5;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P6;
				break;
		default:
			break;
				
		}
		
		return destination;
	}

}

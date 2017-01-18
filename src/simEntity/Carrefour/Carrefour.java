package simEntity.Carrefour;

import org.apache.commons.lang.ObjectUtils;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Voiture.Voiture;

import java.util.Queue;

/**
 * Created by Tag on 18/01/2017.
 */
public class Carrefour {

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

    /**
     * Constructeur carrefour, si le lien est vide, mettre NULL
     *
     * Le carrefourNames est necessaire pour l'instant pour Dijkistra
     * Malgré tout on a un doublon avec la definition du carrefour
     * TODO : investiguer si c'est un probleme.
     */
    public Carrefour(CarrefourNames nom, CarrefourRegle regle,Carrefour carrSud,Carrefour carrEst,Carrefour carrNord,Carrefour carrOuest){
        carrefourEst=carrEst;
        carrefourNord=carrNord;
        carrefourOuest=carrOuest;
        carrefourSud=carrSud;

        this.nom=nom;
        this.regle=regle;
    }

    public boolean authorisationPassage(Voiture voiture){

        // Lui il a besoin de savoir où est la voiture qui veut passer et si les autres files prioritaires sont vide
        // Il a donc besoin de connaitre l'etat des autres files(recupérer l'objet) et la file dans laquelle est la
        // voiture (le carrefour ?)
        return regle.voiturePasse(voiture,this);
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

}

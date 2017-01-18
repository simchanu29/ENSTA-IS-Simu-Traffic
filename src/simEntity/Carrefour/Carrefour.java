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

    //Evenements : ceux qui arrivent d'un point de vue global sur l'etat du carrefour
   /* *
    * - Feu vert/Feu rouge
    * */

}

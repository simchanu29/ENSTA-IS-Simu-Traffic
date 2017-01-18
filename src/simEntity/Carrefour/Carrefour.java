package simEntity.Carrefour;

import org.apache.commons.lang.ObjectUtils;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Voiture.Voiture;

import java.util.Queue;

/**
 * Created by Tag on 18/01/2017.
 */
public class Carrefour {

    /**
     * Les variables qui indiquent au carrefour à quel carrefour il est relié
     */
    private Carrefour carrefourSud;
    private Carrefour carrefourEst;
    private Carrefour carrefourNord;
    private Carrefour carrefourOuest;

    /**
     * Les ports d'entrée dans le carrefour
     */
    private Queue<Voiture> queueSud;
    private Queue<Voiture> queueEst;
    private Queue<Voiture> queueNord;
    private Queue<Voiture> queueOuest;

    /**
     * Constructeur carrefour, si le lien est vide, mettre NULL
     *
     * Le carrefourNames est necessaire pour l'instant pour Dijkistra
     */
    public Carrefour(CarrefourNames nom, CarrefourRegle regle,Carrefour carrSud,Carrefour carrEst,Carrefour carrNord,Carrefour carrOuest){

    }

}

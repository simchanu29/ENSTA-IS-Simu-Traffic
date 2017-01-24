package simEntity.Carrefour.Regle;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.QueueNames;
import simEntity.Voiture.Voiture;

import java.util.HashMap;

/**
 * Created by Tag on 18/01/2017.
 *
 * TODO : initialize et compagnie (les fonctions de simEntity)
 */
public abstract class CarrefourRegle extends SimEntity{

    /**
     * Est ce que les voitures peuvent passer
     * Si il n'y a pas de règle la voie n'existe pas
     */
    private HashMap<QueueNames,Boolean> authorizationEnterCarrefour;

    public CarrefourRegle(SimEngine engine){
        super(engine,"CarrefourRegle");
    }

    public abstract boolean voiturePasse(Voiture voiture, Carrefour carrefour);

    /* TMP
     * TODO : effacer ça quand il n'y en aura plus besoin.
     * J'ai besoin de gérer 2 problèmes : la gestion des priorité lorsqu'une voiture tourne sur une voie dont
     * elle est pas prioritaire et la gestion de la simultanéité des voitures.
     *
     * Le but ça serai de faire une classe générale de règles de priorité. C'est à dire un carrefour à entrée variable
     * (max 4 : les 4 directions des queues)
     * Donc on a de manière générale les canPass sur les chaques entrées : hashmap
     *
     * De manière générale on peut passer ou pas et on a le nombre de voie du carrefour. Pour mettre en place cette
     * modularité on va mettre une hashmap qui permet d'acceder aux variables ?
     *
     * La simultanéité des voitures sera resolue par une variable de rage. Les rageux passent en premier. Certains
     * décideraient plutôt de la nommer initiative mais avoir des rageux c'est plus marrant. Mais dans le code de la
     * route on en a pas vraiment besoin.
     */

}

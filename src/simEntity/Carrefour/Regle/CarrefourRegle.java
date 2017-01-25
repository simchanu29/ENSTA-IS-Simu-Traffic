package simEntity.Carrefour.Regle;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourNames;
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
    private Carrefour carrefour;

    public CarrefourRegle(SimEngine engine){
        super(engine,"CarrefourRegle");

        this.authorizationEnterCarrefour = new HashMap<>();

        //Le carrefour est initialisé comme un point
        authorizationEnterCarrefour.put(QueueNames.Nord,null);
        authorizationEnterCarrefour.put(QueueNames.Sud,null);
        authorizationEnterCarrefour.put(QueueNames.Est,null);
        authorizationEnterCarrefour.put(QueueNames.Ouest,null);
    }

    /**
     * Dit si la voiture peut entrer dans le carrefour.
     * @param voiture
     * @param carrefour
     * @return
     */
    public boolean voitureEntre(Voiture voiture, Carrefour carrefour){
        QueueNames queue = carrefour.getQueueNameOfVoiture(voiture);
        return getAuthorizationEnterCarrefour().get(queue);
    };

    /**
     * La voiture peut sortir
     * si elle ne coupe pas de voie
     * OU
     * si elle coupe une voie que celle-ci soit vide ou bloquee
     * @param voiture
     * @param carrefour
     * @return
     */
    public boolean voitureSort(Voiture voiture, Carrefour carrefour) {
        // Une voiture coupe une voie si elle tourne à gauche uniquement
        CarrefourNames nomPrevious = voiture.getChemin().getPrevious();
        CarrefourNames nomNext = voiture.getChemin().getNextOfnext();
        // On utilise pas la hashMap ici car ajouter le quartier en variable d'instance des feux rouges ne parait pas
        // cohérent
        QueueNames next = carrefour.getQueueByCarrefourName(nomNext);
        QueueNames prev = carrefour.getQueueByCarrefourName(nomPrevious);

        // Normalement les next et les previous ne sont pas le carrefour courrant sauf si c'est le dernier.
        if(next.isLeftOf(prev)){
            //La voiture tourne a gauche, c'est la que les choses intéressantes commencent

            if(carrefour.getQueueByName(next).size()==0){
                // Si la voie est vide on passe
                return true;
            }
            else if(authorizationEnterCarrefour.get(next)){
                // Si les voitures d'en face peuvent passer, on ne passe pas
                return false;
            }
            else{
                // Sinon on passe
                return true;
            }
        }
        else {
            // Pour tout autre direction on passe
            return true;
        }
    }

    public void triggerUpdate(){
        carrefour.updateCarrefour();
    }

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

    public HashMap<QueueNames, Boolean> getAuthorizationEnterCarrefour() {
        return authorizationEnterCarrefour;
    }
    public void setAuthorizationEnterCarrefour(HashMap<QueueNames, Boolean> authorizationEnterCarrefour) {
        this.authorizationEnterCarrefour = authorizationEnterCarrefour;
    }
    public Carrefour getCarrefour() {
        return carrefour;
    }
    public void setCarrefour(Carrefour carrefour) {
        this.carrefour = carrefour;
    }
}

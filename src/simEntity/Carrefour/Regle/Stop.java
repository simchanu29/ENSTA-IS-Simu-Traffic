package simEntity.Carrefour.Regle;

import enstabretagne.base.time.LogicalDuration;
import fr.ensta.lerouxlu.simu.SimEngine;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.QueueNames;
import simEntity.Voiture.Voiture;

import java.util.HashMap;

/**
 * Created by Tag on 18/01/2017.
 */
public class Stop extends CarrefourRegle {

    /**
     * Duree de l'arret recommandé au STOP
     * Cette duree est en int pour rendre le constructeur moins long à l'ecriture
     */
    private int dureeArretStop;
    private HashMap<QueueNames,Boolean> tabStopExist;

    public Stop(SimEngine engine, int dureeArretStop, HashMap<QueueNames,Boolean> tabStopExist) {
		super(engine);
		this.tabStopExist = tabStopExist;
		this.dureeArretStop = dureeArretStop;

        for (QueueNames queueNames:QueueNames.values()) {
            getAuthorizationEnterCarrefour().put(queueNames, !tabStopExist.get(queueNames));
        }
    }

    /**
     * Les voitures peuvent toujours sortir du carrefour car lors d'un stop il faut dégager le carrefour le plus
     * vite possible. Sauf si on est pas sur la voir qui a le stop. Auquel cas les regles de priorité normale
     * s'appliquent
     * @param voiture
     * @param carrefour
     * @return autorisationPassage
     */
    @Override
    public boolean voitureSort(Voiture voiture, Carrefour carrefour) {
        QueueNames queueNameOfVoiture = carrefour.getQueueNameOfVoiture(voiture);
        if(tabStopExist.get(queueNameOfVoiture)){
            return true;
        }else {
            return prioDroite(voiture, carrefour);
        }
    }

    /**
     * Dès qu'une voiture demande l'autorisation de rentrer, l'état des regles du carrefour se met à jour.
     * On regarde l'état du carrefour et on regarde qui peut passer.
     */
    @Override
    public void triggerRule(Voiture voiture, Carrefour carrefour) {
        for (QueueNames queueName:QueueNames.values()) {
            if(tabStopExist.get(queueName)){

                /*
                 * Le stop est passant si :
                 *  - La première voiture de la file considérée peut passer sans couper la route à une autre voiture
                 *    prioritaire ou plus rageuse
                 * Puis lorsqu'il est passant il faut quand même attendre 3 secondes. Donc il faut checker depuis quand
                 * la voiture attend en 1ère, pour vérifier que 3 secondes se sont bien passés.
                 *
                 * Donc
                 *
                 * Etape 1 : On regarde si elle a attendu assez
                 */

                Voiture voitureOnCurrentQueue = (Voiture) carrefour.getQueueByName(queueName).peek();
                // Si la voiture, si elle existe, a assez attendu
                if(voitureOnCurrentQueue!=null && voitureOnCurrentQueue.getTimeArrivalFirstInQueue().soustract(
                                                  getEngine().SimulationDate()).compareTo(
                                                  LogicalDuration.ofSeconds(dureeArretStop)) == 1){

                    /*
                     * On est sur une voie avec un stop
                     * Etape 2 : Si le carrefour est libre (Buffer libre) et que les voies prioritaires qui peuvent
                     * couper ma voie sont vide
                     */
                    QueueNames frontDir = queueName.getFrontQueue();
                    QueueNames leftDir = queueName.getLeftQueue();
                    QueueNames rightDir = queueName.getRightQueue();

                    // Si le buffer n'est pas vide on passe pas
                    if(carrefour.getBufferFromQueue(voitureOnCurrentQueue)==null){
                        QueueNames dirVoiture = carrefour.getQueueByCarrefourName(voiture.getChemin().getNextOfnext());
                        if(dirVoiture.equals(leftDir)){
                            // Prio : Face, Gauche, Droite

                            boolean stopPassage = true;

                            // Si la direction considérée n'est pas un stop et que ,si elle existe, n'est pas vide
                            if(!tabStopExist.get(leftDir) && carrefour.getQueueByName(leftDir)!=null && carrefour.getQueueByName(leftDir).size()!=0){
                                stopPassage = false;
                            }
                            if(!tabStopExist.get(frontDir) && carrefour.getQueueByName(frontDir)!=null && carrefour.getQueueByName(frontDir).size()!=0){
                                stopPassage = false;
                            }
                            if(!tabStopExist.get(rightDir) && carrefour.getQueueByName(rightDir)!=null && carrefour.getQueueByName(rightDir).size()!=0){
                                stopPassage = false;
                            }

                            getAuthorizationEnterCarrefour().put(queueName,stopPassage);

                        }else if(dirVoiture.equals(rightDir)){
                            // Prio : Face, Gauche

                            boolean stopPassage = true;

                            // Si la direction considérée n'est pas un stop et que ,si elle existe, n'est pas vide
                            if(!tabStopExist.get(leftDir) && carrefour.getQueueByName(leftDir)!=null && carrefour.getQueueByName(leftDir).size()!=0){
                                stopPassage = false;
                            }
                            if(!tabStopExist.get(frontDir) && carrefour.getQueueByName(frontDir)!=null && carrefour.getQueueByName(frontDir).size()!=0){
                                stopPassage = false;
                            }

                            getAuthorizationEnterCarrefour().put(queueName,stopPassage);

                        }else if(dirVoiture.equals(frontDir)){
                            // Prio : Gauche, Droite


                            boolean stopPassage = true;

                            // Si la direction considérée n'est pas un stop et que ,si elle existe, n'est pas vide
                            if(!tabStopExist.get(leftDir) && carrefour.getQueueByName(leftDir)!=null && carrefour.getQueueByName(leftDir).size()!=0){
                                stopPassage = false;
                            }
                            if(!tabStopExist.get(rightDir) && carrefour.getQueueByName(rightDir)!=null && carrefour.getQueueByName(rightDir).size()!=0){
                                stopPassage = false;
                            }

                            getAuthorizationEnterCarrefour().put(queueName,stopPassage);

                        }else {
                            System.out.println("[ERROR](triggerRule) queueName not a queue in "+carrefour.getNom());
                        }
                    }
                    // Le buffer n'est pas vide
                    else {
                        getAuthorizationEnterCarrefour().put(queueName,false);
                    }
                }
                // La voiture n'a pas assez attendu
                else {
                    getAuthorizationEnterCarrefour().put(queueName,false);
                }
            }
        }
    }

    /*
     * Le Stop en soi modifie le droit d'entrée.
     *  On peut entrer quand chacune des files qui te coupent la priorité sont vides, cad null ou size(0)
     *  Les files qui coupent la priorité sont
     *      Si on tourne à gauche : Face, Gauche
     *      Si on tourne à droite : Gauche
     *      Si on tourne à face : Gauche, Droite
     * Le droit de sortie est automatique
     *
     * Il faudrait plutôt que lorsque la voiture le demande, les autorisations sont activée ou modifiées lorsqu'une
     * voiture demande si elle peut passer.
     */
}

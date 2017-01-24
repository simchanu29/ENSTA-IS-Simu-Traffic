# ENSTA-IS-Simu-Traffic


####Notes de developpeurs
___
######Simon
 - L'ensemble des carrefours sont accédés par une table de hachage.
___
######Camille
 - Attention méthode getHour à créer dans classe LogicalDateTime dans simu_base_common:
```
    public int getHour() {  
        return logicalDate.getHour();        
    }
```
 - 
___
######Romain
# ENSTA-IS-Simu-Traffic


####Notes de developpeurs  

- Des problèmes d'encodages peuvent apparaître, ainsi nous conseillons aux utilisateurs de vérifier qu'ils utilisent le même encodage que celui utilisé par les développeurs : l' UTF-8

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
 - Attention methode soustract à créer dans la classe LogicalDuration dans simu_base_common:
``` 
   public LogicalDuration soustract(LogicalDuration value) {
	if(logicalDuration==null || value.logicalDuration==null)
		return LogicalDuration.POSITIVE_INFINITY;
	return new LogicalDuration(logicalDuration.minus(value.logicalDuration));
   }
 ```
___  

######Romain
 - Attention Getter & Setter pour la variable L dans La classe SortedList du projet simu_base_common (package enstabretagne.simulation.core)
``` 
	public List<T> getL() {
		return l;
	}

	public void setL(List<T> l) {
		this.l = l;
	}
```

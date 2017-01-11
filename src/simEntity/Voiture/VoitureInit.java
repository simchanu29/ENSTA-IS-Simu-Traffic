
package simEntity.Voiture;

import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimInitParameters;

public class VoitureInit extends SimInitParameters {
	
	CategoriesGenerator tempsAttenteGenerator;

	public VoitureInit(CategoriesGenerator tempsAttenteGenerator) {
		super();
		this.tempsAttenteGenerator = tempsAttenteGenerator;
	}
	

}


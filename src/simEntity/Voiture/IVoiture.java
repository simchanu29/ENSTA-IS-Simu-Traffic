package simEntity.Voiture;

import simEntity.Carrefour.CarrefoursNames;

public interface IVoiture {
	public CarrefoursNames allerNextCarrefour();	
	public EtatVoiture getEtatVoiture();
	public void setEtatVoiture(EtatVoiture etat);
	void quitterQuartier();
}


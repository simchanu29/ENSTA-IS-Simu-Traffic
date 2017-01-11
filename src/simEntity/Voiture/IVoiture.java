package simEntity.Voiture;

import simEntity.Carrefour.CarrefourNames;

public interface IVoiture {
	public CarrefourNames allerNextCarrefour();
	public EtatVoiture getEtatVoiture();
	public void setEtatVoiture(EtatVoiture etat);
	void quitterQuartier();
}


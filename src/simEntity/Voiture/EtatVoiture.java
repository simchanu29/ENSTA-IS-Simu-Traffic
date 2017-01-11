
package simEntity.Voiture;

public enum EtatVoiture {

	EnRoute("En route"),
	EnAttente("En attente"),
	TraverseCarrefour("TraverseCarrefour");
	
	private String etatVoiture;
	private EtatVoiture(String etat){
		etatVoiture=etat;
	}
	
	@Override
	public String toString() {
		return etatVoiture;
	}
}


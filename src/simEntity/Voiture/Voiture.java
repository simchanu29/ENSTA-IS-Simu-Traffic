
package simEntity.Voiture;

import java.util.LinkedList;
import base.Message.Messages;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import simEntity.Carrefour.CarrefourNames;

public class Voiture extends SimEntity implements IVoiture,IRecordable {
	CarrefourNames nextCarrefour;
	int currentIndex=0;
	EtatVoiture etatVoiture;
	LogicalDateTime heureEntreeFileAttente;
	LogicalDateTime heureDebutMvt;	
	LogicalDuration dureeAttente;

	public Voiture(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);				
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		allerNextCarrefour();
		setEtatVoiture(EtatVoiture.EnRoute);
	}

	LinkedList<CarrefourNames> path;
	public void addToPath(CarrefourNames CarrefourId){
		path.add(CarrefourId);
	}
	
	
	public CarrefourNames allerNextCarrefour() {
		if(this.path.get(currentIndex+1)!=null) {
			nextCarrefour=path.get(currentIndex+1);
			currentIndex+=1;
			return path.get(currentIndex);
		}
		else
		{
			quitterQuartier();
			return null;
		}
	}

	@Override
	public void quitterQuartier() {
		Logger.Information(this,"quitterQuartier",Messages.VoitureQuitteQuartier,this.getName());		
		Logger.Data(this);
		deactivate();
	}


	public EtatVoiture getEtatVoiture() {
		return etatVoiture;
	}

	@Override
	public void setEtatVoiture(EtatVoiture  etatVoiture) {
		if(etatVoiture.equals(EtatVoiture.EnAttente)) heureEntreeFileAttente=(LogicalDateTime) getCurrentLogicalDate().getCopy();
		if(etatVoiture.equals(EtatVoiture.TraverseCarrefour)) {
			heureDebutMvt=(LogicalDateTime) getCurrentLogicalDate().getCopy();
			dureeAttente=dureeAttente.add(LogicalDuration.soustract(heureDebutMvt, heureEntreeFileAttente));					
		}
		this.etatVoiture=etatVoiture;
	}

	@Override
	public String[] getTitles() {
		String[] titles={"Durée d'Attente","Classe Attente"};
		return titles;
	}

	@Override
	public String[] getRecords() {
		String[] rec;
		rec=new String[]{Integer.toString(dureeAttente.getMinutes()),((VoitureInit)getInitParameters()).tempsAttenteGenerator.getSegmentOf(dureeAttente.getMinutes()).toString()};
		return rec;
	}

	@Override
	public String getClassement() {
		return "Voiture";
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}


}


package simEntity.Carrefour;


import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Quartier.Quartier;
import simEntity.Voiture.Voiture;

import java.util.LinkedList;
import java.util.Queue;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;

import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;


public class Route {
	private LinkedList<Voiture> ListVoiture;
	private int nbVoiture;
	
	
	public Route(){
		ListVoiture=new LinkedList<Voiture>();
		this.nbVoiture=0;
		
	}
	public void ajouterVoiture (Voiture car){
		this.ListVoiture.add(car);
		this.nbVoiture=this.nbVoiture+1;
	}
	public void retirerVoiture(){
		this.ListVoiture.remove();
		this.nbVoiture=this.nbVoiture-1;
	}
	public void ajouterTempsEnMasse(){
		for(int i=0;i<this.nbVoiture;i++){
			ajouterTemps(this.ListVoiture.get(i));
		}
	}
	
	public void ajouterTemps(Voiture car){
		int nbEvent=car.getEngine().getEcheancier().getL().size();
		LogicalDuration offset =LogicalDuration.ofMillis(360);
		for (int i=0;i<nbEvent;i++){
			if(car.getEngine().getEcheancier().getL().get(i).getV()==car){
				car.getEngine().getEcheancier().getL().get(i).scheduleDate().add(offset);
			}
			
		}
	}
	public LinkedList<Voiture> getListVoiture() {
		return ListVoiture;
	}
	public void setListVoiture(LinkedList<Voiture> listVoiture) {
		ListVoiture = listVoiture;
	}
	public int getNbVoiture() {
		return nbVoiture;
	}
	public void setNbVoiture(int nbVoiture) {
		this.nbVoiture = nbVoiture;
	}
	
	

}

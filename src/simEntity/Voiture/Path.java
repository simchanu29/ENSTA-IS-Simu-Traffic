package simEntity.Voiture;

import java.util.LinkedList;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import fr.ensta.lerouxlu.simu.SimEvent;
import simEntity.Carrefour.CarrefourNames;
import de.vogella.algorithms.dijkstra.test.DijkstraRoutier;


public class Path {
	private DijkstraRoutier viamichelin;
	private CarrefourNames start;
	private CarrefourNames end;
	private LinkedList<CarrefourNames> path;
	private CarrefourNames last;
	private CarrefourNames next;
	private int compteur;
	
	private LinkedList<Double> trajet;
	private LogicalDuration Time2next;

	public Path(CarrefourNames start, CarrefourNames end) {
		super();
		this.start = start;
		this.end = end;
		this.viamichelin=new DijkstraRoutier();
		int indexL=CarrefourNames.valueOf(start.toString()).ordinal()+1;
		int indexD=CarrefourNames.valueOf(end.toString()).ordinal()+1;
		this.path =viamichelin.chemin(indexL, indexD); ;
		this.last = start;
		this.next =path.get(1);
		this.trajet =viamichelin.temps();
		this.compteur=1;
		this.Time2next=LogicalDuration.ofSeconds(trajet.getFirst());
	}
	
	
	public void etape(){
		if (this.end==this.next){
			this.last=this.next;}
		else{
			this.Time2next=LogicalDuration.ofSeconds(trajet.get(compteur));
			compteur++;
			this.last=this.next;
			this.next=this.path.get(compteur);
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public CarrefourNames getStart() {
		return start;
	}

	public void setStart(CarrefourNames start) {
		this.start = start;
	}

	public CarrefourNames getEnd() {
		return end;
	}

	public void setEnd(CarrefourNames end) {
		this.end = end;
	}

	public LinkedList<CarrefourNames> getPath() {
		return path;
	}

	public void setPath(LinkedList<CarrefourNames> path) {
		this.path = path;
	}

	public CarrefourNames getLast() {
		return last;
	}

	public void setLast(CarrefourNames last) {
		this.last = last;
	}

	public CarrefourNames getNext() {
		return next;
	}

	public void setNext(CarrefourNames next) {
		this.next = next;
	}


	public LogicalDuration getTime2next() {
		return Time2next;
	}


	public void setTime2next(LogicalDuration time2next) {
		Time2next = time2next;
	}


	public LinkedList<Double> getTrajet() {
		return trajet;
	}


	public void setTrajet(LinkedList<Double> trajet) {
		this.trajet = trajet;
	}
	
	
	
	
	
}

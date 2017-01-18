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
	
	private LogicalDuration trajet;

	public Path(CarrefourNames start, CarrefourNames end) {
		super();
		this.start = start;
		this.end = end;
		this.viamichelin=new DijkstraRoutier();
		int indexL=CarrefourNames.valueOf(start.toString()).ordinal()+1;
		int indexD=CarrefourNames.valueOf(end.toString()).ordinal()+1;
		this.path =viamichelin.chemin(indexL, indexD); ;
		this.last = start;
		this.next = end;
		this.trajet = LogicalDuration.ofSeconds((long)viamichelin.temps());
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

	public LogicalDuration getTrajet() {
		return trajet;
	}

	public void setTrajet(LogicalDuration trajet) {
		this.trajet = trajet;
	}
	
	
	
}

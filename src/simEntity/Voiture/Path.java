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
	private CarrefourNames previous;
	private CarrefourNames next;
	private CarrefourNames nextOfnext;
	private int compteur;
	
	private LinkedList<Double> trajet;
	private Double Time2next;

	public Path(CarrefourNames start, CarrefourNames end) {
		super();
		this.start = start;
		this.end = end;
		this.viamichelin=new DijkstraRoutier();
		int indexL=CarrefourNames.valueOf(start.toString()).ordinal()+1;
		int indexD=CarrefourNames.valueOf(end.toString()).ordinal()+1;
		this.path =viamichelin.chemin(indexL, indexD); ;
		this.previous = start;
		this.next =path.get(1);

		if(path.size()<3){this.nextOfnext = this.next;}
		else{this.nextOfnext = path.get(2);}

		this.trajet =viamichelin.temps();
		this.compteur=1;
		this.Time2next=trajet.getFirst();
	}

    /**
     * Les next et compagnies s'accumulent à la fin de la liste quand on arrive en fin de trajet
     */
	public void etape(){
		if (this.end==this.next){
		    //On ne change que previous
			this.previous=this.next;

		}
		else if(this.end==this.nextOfnext){
            //On ne change pas nextOfnext
		    this.Time2next=(trajet.get(compteur));
            compteur++;

            this.previous=this.next;
            this.next=this.path.get(compteur);

        }
		else{
		    //On change tout

			this.Time2next=(trajet.get(compteur));
			compteur++;

			this.previous=this.next;
			this.next=this.path.get(compteur);

			this.nextOfnext = this.path.get(compteur+1);
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

	public CarrefourNames getPrevious() {
		return previous;
	}

	public void setLast(CarrefourNames previous) {
		this.previous = previous;
	}

	public CarrefourNames getNext() {
		return next;
	}

	public void setNext(CarrefourNames next) {
		this.next = next;
	}


	public Double getTime2next() {
		return Time2next;
	}


	public void setTime2next(Double time2next) {
		Time2next = time2next;
	}


	public LinkedList<Double> getTrajet() {
		return trajet;
	}


	public void setTrajet(LinkedList<Double> trajet) {
		this.trajet = trajet;
	}

    public CarrefourNames getNextOfnext() {
        return nextOfnext;
    }

    @Override
	public String toString() {
		return "[" + path + "]";
	}
	
	
	
	
}

package de.vogella.algorithms.dijkstra.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import de.vogella.algorithms.dijkstra.engine.DijkstraAlgorithm;
import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Graph;
import de.vogella.algorithms.dijkstra.model.IEdge;
import de.vogella.algorithms.dijkstra.model.IVertex;
import de.vogella.algorithms.dijkstra.model.Vertex;
import simEntity.Carrefour.CarrefourNames;


public class DijkstraRoutier {

  private List<IVertex> nodes;
  private List<IEdge> edges;
  private Graph graph;
  private DijkstraAlgorithm dijkstra;
  
  
  
  public DijkstraRoutier() {
	    nodes = new ArrayList<IVertex>();
	    edges = new ArrayList<IEdge>();
	    Vertex location = new Vertex("Node_" + 0,CarrefourNames.I2 );
	      nodes.add(location);
	      LinkedList<CarrefourNames> P=new LinkedList<CarrefourNames>();
	      P.add(CarrefourNames.I1);
	      P.add(CarrefourNames.P1);
	      P.add(CarrefourNames.P2);
	      P.add(CarrefourNames.P3);
	      P.add(CarrefourNames.P4);
	      P.add(CarrefourNames.P5);
	      P.add(CarrefourNames.P6);
	      P.add(CarrefourNames.P7);
	    for (int i = 1; i < 8; i++) {
	      location = new Vertex("Node_" + i, P.get(i));
	      nodes.add(location);
	    }
	    LinkedList<CarrefourNames> numero=new LinkedList<CarrefourNames>();
	    numero.add(CarrefourNames.I4);
	    numero.add(CarrefourNames.I3);
	    numero.add(CarrefourNames.I1);
	    int j=0;
	    for (int i= 8; i < 11; i++) {
		      location = new Vertex("Node_" + i,numero.get(j));
		      nodes.add(location);
		      j++;
		    }

	    addLane("Edge_0", 1, 10, 1);
	    addLane("Edge_1", 10, 0, 1);
	    addLane("Edge_2", 0, 3, 1);
	    addLane("Edge_3", 2, 0, 1);
	    addLane("Edge_4", 10, 8, 1);
	    addLane("Edge_5", 5, 8, 1);
	    addLane("Edge_6", 6, 8, 1);
	    addLane("Edge_7", 8, 9, 1);
	    addLane("Edge_8", 7, 9, 1);
	    addLane("Edge_9", 4, 9, 1 );;

	    graph = new Graph(nodes, edges);
	    dijkstra = new DijkstraAlgorithm(graph);
}

public void chemin(int NodeDepart,int NodeArrive) {
    dijkstra.execute(nodes.get(NodeDepart));
    LinkedList<IVertex> path = dijkstra.getNodePath(nodes.get(NodeArrive));
    LinkedList<CarrefourNames> gps = new LinkedList<CarrefourNames>();
    for (IVertex vertex : path) {
        gps.add(vertex.getName());
      }
     System.out.println(gps);
//     return gps;
    }

  private void addLane(String laneId, int sourceLocNo, int destLocNo,
      int duration) {
	    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
	    edges.add(lane);

	    lane = new Edge(laneId+"rev",nodes.get(destLocNo), nodes.get(sourceLocNo),duration);
	    edges.add(lane);
  }
}
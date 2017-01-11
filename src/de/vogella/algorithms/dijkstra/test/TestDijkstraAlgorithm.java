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


public class TestDijkstraAlgorithm {

  private List<IVertex> nodes;
  private List<IEdge> edges;
  
  public static void main(String[] args) {
	TestDijkstraAlgorithm tda = new TestDijkstraAlgorithm();
	tda.testExcute();
}

  public void testExcute() {
    nodes = new ArrayList<IVertex>();
    edges = new ArrayList<IEdge>();
    for (int i = 0; i < 11; i++) {
      Vertex location = new Vertex("Node_" + i, "Node_" + i);
      nodes.add(location);
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

    Graph graph = new Graph(nodes, edges);
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
    dijkstra.execute(nodes.get(2));
    LinkedList<IVertex> path = dijkstra.getNodePath(nodes.get(4));
        
    for (IVertex vertex : path) {
      System.out.println(vertex);
    }
    
  }

  private void addLane(String laneId, int sourceLocNo, int destLocNo,
      int duration) {
	    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
	    edges.add(lane);

	    lane = new Edge(laneId+"rev",nodes.get(destLocNo), nodes.get(sourceLocNo),duration);
	    edges.add(lane);
  }
}
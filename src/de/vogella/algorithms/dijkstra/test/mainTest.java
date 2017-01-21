package de.vogella.algorithms.dijkstra.test;

import java.util.LinkedList;

import simEntity.Carrefour.CarrefourNames;

public class mainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DijkstraRoutier map=new DijkstraRoutier();
		LinkedList<CarrefourNames> gps =map.chemin(1,9);
		System.out.println(gps);

	}

}

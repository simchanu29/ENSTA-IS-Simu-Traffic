package Main;

import java.util.HashMap;
import java.util.LinkedList;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.simulation.core.ISimulationDateProvider;
import fr.ensta.lerouxlu.simu.SimEngine;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Monitor.SimMonitor;

public class Main {
	public static void main(String [] args) {
		
		//Premier d'entre eux: le logger qui écrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());
		
		//Premier d'entre eux: le logger qui écrit dans un fichier excel
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir"));
		params.put(LoggerParamsNames.FileName.toString(), "Traffic.xlsx");
		loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),params);	
		LogicalDateTime begin = LogicalDateTime.Zero;

		
		SimEngine engine = new SimEngine(1,begin,LogicalDuration.ofHours(24));
		//Initialisation de l'ensemble des loggers
		Logger.Init((ISimulationDateProvider) engine, loggersNames, true);
		SimMonitor sm=new SimMonitor(engine, "Monitor");
		LinkedList<Integer> freqPopVoitureP1=createListeFreq(40,300,20,100,20);
		LinkedList<Integer> freqPopVoitureP2=createListeFreq(50,200,30,150,30);
		LinkedList<Integer> freqPopVoitureP3=createListeFreq(30,100,20,300,15);
		LinkedList<Integer> freqPopVoitureP4=createListeFreq(20,100,30,200,50);
		LinkedList<Integer> freqPopVoitureP5=createListeFreq(30,400,20,150,20);
		LinkedList<Integer> freqPopVoitureP6=createListeFreq(20,50,30,100,10);
		LinkedList<Integer> freqPopVoitureP7=createListeFreq(30,30,10,100,10);
		
		Carrefour p1=new Carrefour(engine, CarrefourNames.P1, freqPopVoitureP1);
		Carrefour p2=new Carrefour(engine, CarrefourNames.P2, freqPopVoitureP2);
		Carrefour p3=new Carrefour(engine, CarrefourNames.P3, freqPopVoitureP3);
		Carrefour p4=new Carrefour(engine, CarrefourNames.P4, freqPopVoitureP4);
		Carrefour p5=new Carrefour(engine, CarrefourNames.P5, freqPopVoitureP5);
		Carrefour p6=new Carrefour(engine, CarrefourNames.P6, freqPopVoitureP6);
		Carrefour p7=new Carrefour(engine, CarrefourNames.P7, freqPopVoitureP7);
		
		engine.initialize();	
		engine.resume();
		while (engine.triggerNextEvent()) {			
		}
		
		Logger.Terminate();
	}
	
	public static LinkedList<Integer> createListeFreq(int a, int b, int c, int d, int e) {
		LinkedList<Integer> freqPopVoiture=new LinkedList<Integer>();
		freqPopVoiture.add(a);
		freqPopVoiture.add(b);
		freqPopVoiture.add(c);
		freqPopVoiture.add(d);
		freqPopVoiture.add(e);
		return freqPopVoiture;
	}
}

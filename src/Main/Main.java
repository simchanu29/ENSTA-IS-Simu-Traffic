package Main;

import java.lang.reflect.Array;
import java.util.*;

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
import simEntity.Carrefour.QueueNames;
import simEntity.Carrefour.Regle.CarrefourRegle;
import simEntity.Carrefour.Regle.FeuRougeCroises;
import simEntity.Carrefour.Regle.FeuRougeIndiv;
import simEntity.Carrefour.Regle.Stop;
import simEntity.Quartier.Quartier;

public class Main {
	public static void main(String [] args) {

	    //=== LOGGER ===

		//Premier d'entre eux: le logger qui écrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());
		
		//Premier d'entre eux: le logger qui écrit dans un fichier excel
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir"));
		params.put(LoggerParamsNames.FileName.toString(), "Traffic.xlsx");
		loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),params);	
		LogicalDateTime begin = LogicalDateTime.Zero;


		SimEngine engine = new SimEngine(1,begin,LogicalDuration.ofHours(8));

		//Initialisation de l'ensemble des loggers
		Logger.Init((ISimulationDateProvider) engine, loggersNames, true);
		
		//Initialisation des fréquences de pop des carrefours
		LinkedList<Integer> freqPopVoitureP1=createListeFreq(40,300,20,100,20);
		LinkedList<Integer> freqPopVoitureP2=createListeFreq(50,200,30,150,30);
		LinkedList<Integer> freqPopVoitureP3=createListeFreq(30,100,20,300,15);
		LinkedList<Integer> freqPopVoitureP4=createListeFreq(20,100,30,200,50);
		LinkedList<Integer> freqPopVoitureP5=createListeFreq(30,400,20,150,20);
		LinkedList<Integer> freqPopVoitureP6=createListeFreq(20,50,30,100,10);
		LinkedList<Integer> freqPopVoitureP7=createListeFreq(30,30,10,100,10);

		//=== INIT SIM OBJECTS ===

        // initialisation quartier
        Quartier  githubCity = new Quartier(engine, "githubCity");

        // initialisation des carrefours
        Carrefour p1=new Carrefour(engine, githubCity, CarrefourNames.P1, freqPopVoitureP1);
        Carrefour p2=new Carrefour(engine, githubCity, CarrefourNames.P2, freqPopVoitureP2);
        Carrefour p3=new Carrefour(engine, githubCity, CarrefourNames.P3, freqPopVoitureP3);
        Carrefour p4=new Carrefour(engine, githubCity, CarrefourNames.P4, freqPopVoitureP4);
        Carrefour p5=new Carrefour(engine, githubCity, CarrefourNames.P5, freqPopVoitureP5);
        Carrefour p6=new Carrefour(engine, githubCity, CarrefourNames.P6, freqPopVoitureP6);
        Carrefour p7=new Carrefour(engine, githubCity, CarrefourNames.P7, freqPopVoitureP7);

        HashMap<QueueNames,Boolean> stopHPI1 = new HashMap<>();
        stopHPI1.put(QueueNames.Nord,true); stopHPI1.put(QueueNames.Sud,true);
        stopHPI1.put(QueueNames.Ouest,false); stopHPI1.put(QueueNames.Est,false);
        Carrefour i1=new Carrefour(engine, githubCity, CarrefourNames.I1, new Stop(engine,3,stopHPI1));

        HashMap<QueueNames,Boolean> stopHPI2 = new HashMap<>();
        stopHPI2.put(QueueNames.Nord,false); stopHPI2.put(QueueNames.Sud,true);
        stopHPI2.put(QueueNames.Ouest,false); stopHPI2.put(QueueNames.Est,false);
        Carrefour i2=new Carrefour(engine, githubCity, CarrefourNames.I2, new Stop(engine,3,stopHPI2));
        
        HashMap<QueueNames,Boolean> stopHPI4 = new HashMap<>();
        stopHPI4.put(QueueNames.Nord,true); stopHPI4.put(QueueNames.Sud,true);
        stopHPI4.put(QueueNames.Ouest,false); stopHPI4.put(QueueNames.Est,false);
        Carrefour i4=new Carrefour(engine, githubCity, CarrefourNames.I4, new Stop(engine,3,stopHPI4));
        



//        HashMap<QueueNames,Boolean> stopHPI3 = new HashMap<>();
//        stopHPI3.put(QueueNames.Nord,true); stopHPI3.put(QueueNames.Sud,false);
//        stopHPI3.put(QueueNames.Ouest,true); stopHPI3.put(QueueNames.Est,true);
//        Carrefour i3=new Carrefour(engine, githubCity, CarrefourNames.I3, new Stop(engine,3,stopHPI3));
        Carrefour i3=new Carrefour(engine, githubCity, CarrefourNames.I3, new FeuRougeIndiv(engine,30,30,0,30));

        // relier les carrefours entre eux
        p1.setCarrefourEst(i1);
        p2.setCarrefourNord(i2);
        p3.setCarrefourOuest(i2);
        p4.setCarrefourOuest(i3);
        p5.setCarrefourEst(i4);
        p6.setCarrefourSud(i4);
        p7.setCarrefourSud(i3);
        i1.setCarrefourOuest(p1);
        i1.setCarrefourEst(i2);
        i1.setCarrefourNord(i4);
        i2.setCarrefourOuest(i1);
        i2.setCarrefourEst(p3);
        i2.setCarrefourSud(p2);
        i3.setCarrefourOuest(i4);
        i3.setCarrefourEst(p4);
        i3.setCarrefourNord(p7);
        i4.setCarrefourOuest(p5);
        i4.setCarrefourEst(i3);
        i4.setCarrefourNord(p6);
        i4.setCarrefourSud(i1);

        // initialisons une liste de carrefour qui sera le quartier.

        // Mise en place d'une liste pour une iteration facile et une modularité des carrefours
        List<Carrefour> listCarrefour = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,i1,i2,i3,i4);
        // Mise en place de la table de hachage pour les carrefours
        HashMap<CarrefourNames,Carrefour> dicCarrefour = new HashMap<>();
        for (Carrefour carrefour:listCarrefour) {
            dicCarrefour.put(carrefour.getNom(),carrefour);
        }
        githubCity.setDicCarrefour(dicCarrefour);

		engine.initialize();	
		engine.resume();
		while (engine.triggerNextEvent()) {}
		
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

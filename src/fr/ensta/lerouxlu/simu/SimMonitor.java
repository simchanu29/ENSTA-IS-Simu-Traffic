package fr.ensta.lerouxlu.simu;

import java.util.HashMap;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.simulation.core.ISimulationDateProvider;
import fr.ensta.lerouxlu.DemoSimple.Sim;

public class SimMonitor {
		

	
	public static void main(String [] args) {
		
		//Premier d'entre eux: le logger qui écrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());
		
		//Premier d'entre eux: le logger qui écrit dans un fichier excel
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir"));
		params.put(LoggerParamsNames.FileName.toString(), "LoggerAndProba.xlsx");
		loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),params);
		
		LogicalDateTime begin = LogicalDateTime.Zero;

		
		SimEngine engine = new SimEngine(1,begin,LogicalDuration.ofHours(30));
		//Initialisation de l'ensemble des loggers
		Logger.Init((ISimulationDateProvider) engine, loggersNames, true);

		Sim alice = new Sim(engine);
		alice.setName("Alice");
		Sim bob = new Sim(engine);
		bob.setName("Bob");
		
		engine.initialize();
		engine.resume();
		while (engine.triggerNextEvent()) {}
	}
	
}

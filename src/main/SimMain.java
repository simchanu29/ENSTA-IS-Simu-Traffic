package main;

/**
 * Created by Tag on 11/01/2017.
 */

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Scenarios.SimTrafficScenario;
import Scenarios.SimTrafficScenarioFeatures;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.monitors.MonteCarloMonitor;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.*;

import simEntity.Carrefour.CarrefourFeatures;
import simEntity.Carrefour.CarrefourInit;
import simEntity.Carrefour.CarrefourNames;
import simEntity.Quartier.QuartierFeatures;
import simEntity.Quartier.QuartierInit;

public class SimMain extends MonteCarloMonitor implements IMonitor {

    public SimMain(HashMap<String, HashMap<String, Object>> loggersNames) {
        super(loggersNames);
    }

    public static void main(String[] args) {

        System.out.println("Debut =" + Instant.now());

        // Configuration requise pour le logger
        HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
        loggersNames.put(SysOutLogger.class.getCanonicalName(),new HashMap<String,Object>());

        HashMap<String,Object> xlsxParams=new HashMap<String,Object>();
        xlsxParams.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir") + "\\log");
        xlsxParams.put(LoggerParamsNames.FileName.toString(), "SimuCircuit.xlsx");

        // Mise en oeuvre du SXLSExcelDatalogger: celui ci fonctionne par buffer ce qui permet de gérer des tables excel de grande taille sans avoir à les mettre intégralement en mémoire.
        loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),xlsxParams);

        // Création du moniteur
        SimMain sm = new SimMain(loggersNames);

        // Déclaration des données qui serviront à l'initialisation du scénario
        LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
        int nbJoursDeSimulation = 3;
        int repliqueNumber = 5;
//        String heureDebutOuvertureSalon="10:00";
//        String heureFermetureSalon="20:00";
//        String heureDebutArriveeClient="09:00";
//        String heureFinArriveeClient="20:00";
        int nbVoitureMaxEnQuartier = 10000;
        double vitesseVoiture = 19;
        double periodeArriveeVoituresEnMinutes;
//        List<DayOfWeek> joursFermeture = new ArrayList<DayOfWeek>();
//        joursFermeture.add(DayOfWeek.SUNDAY);
//        joursFermeture.add(DayOfWeek.MONDAY);

        List<SimScenario> listeScenario = new ArrayList<SimScenario>();

        //=================================== Mise en place scenario ===================================================

        /*
        Scenario :
        Liste de carrefour
        Caractéritiques des carrefour avec la List<>

        Il faut regérer la gestion de l'arrivee des clients. Celle ci est gérée dans le scenario.

         */


        //déclaration des variables qui nous servirons à chaque run

        SimTrafficScenarioFeatures scsf;
        List<CarrefourFeatures> l;
        HashMap<CarrefourNames,CarrefourInit> i;

        //Création des Scénarios
        periodeArriveeVoituresEnMinutes=12;

        ///Famille Scénario 1: scénario de base proposé dans l'énoncé et correspondant à la situation actuelle
        l = new ArrayList<CarrefourFeatures>();
//        l.add(new CoiffeurFeature(CoiffeursNames.Flaky,vitesseDeCoupe,StatutCoiffeur.Employe,30));
//        l.add(new CoiffeurFeature(CoiffeursNames.Lumpy,vitesseDeCoupe,StatutCoiffeur.Employe,10));
//        l.add(new CoiffeurFeature(CoiffeursNames.Petunia,vitesseDeCoupe,StatutCoiffeur.Patron,0));

        i = new HashMap<CarrefourNames,CarrefourInit>();
//        i.put(CoiffeursNames.Flaky,new CoiffeurInit());
//        i.put(CoiffeursNames.Lumpy,new CoiffeurInit());
//        i.put(CoiffeursNames.Petunia,new CoiffeurInit());

        scsf = new SimTrafficScenarioFeatures(
                "test1",
                60.0,
                new QuartierFeatures("Salon Petunia", nbVoitureMaxEnQuartier, l),
                new QuartierInit(i),
                new CategoriesGenerator(0, periodeArriveeVoituresEnMinutes*10, 10, 3, 2),
                new CategoriesGenerator(0, vitesseVoiture*5, 50, 3, 2)
        );

        listeScenario.add(new SimTrafficScenario(
                sm.getEngine(),
                new ScenarioId("Scenario1"),
                scsf,
                start,
                start.add(LogicalDuration.ofDay(nbJoursDeSimulation))
        ));

        //================================ Fin mise en place scénario ==================================================

        sm.run(listeScenario, repliqueNumber);
        sm.terminate(false);

        System.out.println("Fin : " + Instant.now());
    }


}

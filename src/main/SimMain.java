package main;

/**
 * Created by Tag on 11/01/2017.
 */

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.base.utility.loggerimpl.XLSXExcelDataloggerImpl;
import enstabretagne.monitors.MonteCarloMonitor;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.components.SimScenarioInit;
import enstabretagne.simulation.core.*;

/*
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.Scenarios.SalonCoiffureScenario;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.Scenarios.SalonCoiffureScenarioFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurFeature;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurInit;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.StatutCoiffeur;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.SalonFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.SalonInit;
*/

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
        xlsxParams.put(LoggerParamsNames.FileName.toString(), "SalonCoiffure.xlsx");

        // Mise en oeuvre du SXLSExcelDatalogger: celui ci fonctionne par buffer ce qui permet de gérer des tables excel de grande taille sans avoir à les mettre intégralement en mémoire.
        loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),xlsxParams);

        // Création du moniteur
        SimMain sm = new SimMain(loggersNames);

        // Déclaration des données qui serviront à l'initialisation du scénario
        LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
        int nbJoursDeSimulation = 3;
        int repliqueNumber = 5;
        String heureDebutOuvertureSalon="10:00";
        String heureFermetureSalon="20:00";
        String heureDebutArriveeClient="09:00";
        String heureFinArriveeClient="20:00";
        int nbClientMaxEnSalle = 10;
        double vitesseDeCoupe=19;
        double periodeArriveeClientsEnMinutes;
        List<DayOfWeek> joursFermeture = new ArrayList<DayOfWeek>();
        joursFermeture.add(DayOfWeek.SUNDAY);
        joursFermeture.add(DayOfWeek.MONDAY);

        List<SimScenario> listeScenario = new ArrayList<SimScenario>();

        //=================================== Mise en place scenario ===================================================

        //déclaration des variables qui nous servirons à chaque run
//        SalonCoiffureScenarioFeatures scsf;
//        List<CoiffeurFeature> l;
//        HashMap<CoiffeursNames,CoiffeurInit> i;

        //Création des Scénarios
//        periodeArriveeClientsEnMinutes=12;
        ///Famille Scénario 1: scénario de base proposé dans l'énoncé et correspondant à la situation actuelle
//        l = new ArrayList<CoiffeurFeature>();
//        l.add(new CoiffeurFeature(CoiffeursNames.Flaky,vitesseDeCoupe,StatutCoiffeur.Employe,30));
//        l.add(new CoiffeurFeature(CoiffeursNames.Lumpy,vitesseDeCoupe,StatutCoiffeur.Employe,10));
//        l.add(new CoiffeurFeature(CoiffeursNames.Petunia,vitesseDeCoupe,StatutCoiffeur.Patron,0));

//        i = new HashMap<CoiffeursNames,CoiffeurInit>();
//        i.put(CoiffeursNames.Flaky,new CoiffeurInit());
//        i.put(CoiffeursNames.Lumpy,new CoiffeurInit());
//        i.put(CoiffeursNames.Petunia,new CoiffeurInit());

//        scsf = new SalonCoiffureScenarioFeatures(
//                "ScenarioSalonPetunia1",
//                60.0/periodeArriveeClientsEnMinutes,//arrivée toute les 12 minutes des clients
//                heureDebutArriveeClient,
//                heureFinArriveeClient,
//                new SalonFeatures("Salon Petunia", 10, 4, nbClientMaxEnSalle, heureDebutOuvertureSalon, heureFermetureSalon,joursFermeture, l),
//                new SalonInit(i),
//                new CategoriesGenerator(0, periodeArriveeClientsEnMinutes*10, 10, 3, 2),
//                new CategoriesGenerator(0, vitesseDeCoupe*5, 50, 3, 2)
//        );

//        listeScenario.add(new SalonCoiffureScenario(
//                sm.getEngine(),
//                new ScenarioId("Scenario1"),
//                scsf,
//                start,
//                start.add(LogicalDuration.ofDay(nbJoursDeSimulation))
//        ));

        sm.run(listeScenario, repliqueNumber);
        sm.terminate(false);

        System.out.println("Fin : " + Instant.now());
    }


}

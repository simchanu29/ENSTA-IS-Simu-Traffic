package simEntity.Quartier;

import enstabretagne.base.utility.Logger;
import fr.ensta.lerouxlu.simu.SimEngine;
import fr.ensta.lerouxlu.simu.SimEntity;
import org.python.antlr.ast.Str;
import simEntity.Carrefour.Carrefour;
import simEntity.Carrefour.CarrefourNames;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tag on 23/01/2017.
 */
public class Quartier extends SimEntity {

    private HashMap<CarrefourNames,Carrefour> dicCarrefour;
    private String name;

    public Quartier(SimEngine engine, String name) {
        super(engine, "Quartier");
        this.name = name;
    }

    public Quartier(SimEngine engine, String name, HashMap<CarrefourNames,Carrefour> dicCarrefour) {
        super(engine, "Quartier");
        this.name = name;
        this.dicCarrefour = dicCarrefour;
    }

    public HashMap<CarrefourNames,Carrefour> getDicCarrefour() {
        return dicCarrefour;
    }

    @Override
    public void initialize() {
        super.activate();
        Logger.Information(this.name, "activate", "Monitor se reveille");
    }

    public void setDicCarrefour(HashMap<CarrefourNames,Carrefour> dicCarrefour) {
        this.dicCarrefour = dicCarrefour;
    }
}

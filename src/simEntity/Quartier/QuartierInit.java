package simEntity.Quartier;

import java.util.HashMap;
import simEntity.Carrefour.CarrefourInit;
import simEntity.Carrefour.CarrefourNames;

public class QuartierInit {

		HashMap<CarrefourNames, CarrefourInit> carrefourInit;
		public QuartierInit(HashMap<CarrefourNames, CarrefourInit> carrefoursInits) {
			this.carrefourInit=carrefoursInits;
		}
		public HashMap<CarrefourNames, CarrefourInit> getCarrefoursInits() {
			return carrefourInit;
		}




}

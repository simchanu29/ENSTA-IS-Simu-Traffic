package simEntity.Carrefour;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import enstabretagne.base.math.MoreRandom;

public enum CarrefourNames {
	P1("P1"),
	P2("P2"),
	P3("P3"),
	P4("P4"),
	P5("P5"),
	P6("P6"),
	P7("P7"),
	I1("I1"),
	I2("I2"),
	I3("I3"),
	I4("I4");

	
	private final String name;
	
	private static final List<CarrefourNames> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = 7;
	private static final Random RANDOM = new MoreRandom();

	public static CarrefourNames randomCarrefour()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	private CarrefourNames(String name){
		this.name=name;
	}
		
	@Override
	public String toString() {
		return name;
	}

	
	
}

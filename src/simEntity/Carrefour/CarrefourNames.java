package simEntity.Carrefour;

public enum CarrefourNames {
	I1("I1"),
	I2("I2"),
	I3("I3"),
	I4("I4"),
	P1("P1"),
	P2("P2"),
	P3("P3"),
	P4("P4"),
	P5("P5"),
	P6("P6"),
	P7("P7");
	
	private final String name;
	
	private CarrefourNames(String name){
		this.name=name;
	}
		
	@Override
	public String toString() {
		return name;
	}

}

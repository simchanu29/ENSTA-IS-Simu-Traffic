package simEntity.Carrefour;

public enum CarrefoursNames {
	I1("I1"),
	I2("I2"),
	I3("I3"),
	I4("I4");
	
	private final String name;
	
	private CarrefoursNames(String name){
		this.name=name;
	}
		
	@Override
	public String toString() {
		return name;
	}

}

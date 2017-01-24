package simEntity.Carrefour;

/**
 * Created by Tag on 18/01/2017.
 */
public enum QueueNames {
    Nord("Nord"),
    Est("Est"),
    Ouest("Ouest"),
    Sud("Sud"),
    Not_a_queue("-1");

    private String name;

    private QueueNames(String name){
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}
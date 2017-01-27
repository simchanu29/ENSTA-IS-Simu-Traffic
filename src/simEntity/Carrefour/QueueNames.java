package simEntity.Carrefour;

/**
 * Created by Tag on 18/01/2017.
 */
public enum QueueNames {
    Nord("Nord",0),
    Est("Est",1),
    Ouest("Ouest",3),
    Sud("Sud",2),
    Not_a_queue("-1",-1);

    private String name;
    private int num;

    private QueueNames(String name,int num){
        this.name=name;
        this.num=num;
    }

    public boolean isLeftOf(QueueNames dir){
        int base = dir.getNum();
        int comp = this.num;
        int newDir = Math.floorMod(comp-base,4);
        return newDir == 1;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getNum() {
        return num;
    }
}
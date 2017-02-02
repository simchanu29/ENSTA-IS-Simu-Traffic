package simEntity.Carrefour;

/**
 * Created by Tag on 18/01/2017.
 * On tourne dans le sens horaire donc
 * -1 = droite
 * +1 = gauche
 * -2 ou -2 = en face
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
    public QueueNames getQueueNameByNum(int num){
        switch (num){
            case 0:
                return Nord;
            case 1:
                return Est;
            case 2:
                return Sud;
            case 3:
                return Ouest;
        }
        return Not_a_queue;
    }
    public QueueNames getLeftQueue() {
        return getQueueNameByNum(Math.floorMod(num+1,4));
    }
    public QueueNames getRightQueue() {
        return getQueueNameByNum(Math.floorMod(num-1,4));
    }
    public QueueNames getFrontQueue() {
        return getQueueNameByNum(Math.floorMod(num+2,4));
    }
}
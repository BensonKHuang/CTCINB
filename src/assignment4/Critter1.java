package assignment4;
/*
*   Virus critter
*
*
*
 */

public class Critter1 extends Critter {

    private int dir;
    private int type;

    public Critter1() {

        dir = getRandomInt(8);
        type = getRandomInt(2);
    }

    /**
     * Does time step for Critter1
     * Walks in direction of dir, and sets new distinct direction
     */

    public void doTimeStep() {


        int new_dir = getRandomInt(8);

        walk(dir);


        if(getEnergy() >= 80){

            createOffspring();
            if(type == 1){
                createOffspring();
            }
        }

        while (new_dir != dir) {
            new_dir = getRandomInt(8);
        }

        dir = new_dir;
    }

    /**
     * Critter1 always fights
     *
     * @param irrelevant is irrelevant
     * @return true every time
     */
    public boolean fight(String irrelevant) {

        return true;
    }

    public int getType(){
        return type;
    }

    private void createOffspring() {

        Critter1 child = new Critter1();
        reproduce(child, getRandomInt(8));
    }

    public String toString() {

        return "V";
    }

    public static void runStats(java.util.List<Critter> viruses){


        int numA = 0;
        int numB = 0;

        for(Object o : viruses){

            Critter1 c = (Critter1) o;
            if(c.type == 0){
                ++numA;
            }
            else{
                ++numB;
            }
        }

        System.out.println(viruses.size() + " total Viruses");
        System.out.println(numA + " type A viruses");
        System.out.println(numB + " type B viruses");
    }
}




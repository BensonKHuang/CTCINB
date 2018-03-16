package assignment4;
/*
*   Virus critter
*
*
*
 */

public class Critter1 extends Critter {

    private int dir;
    private static int max_litter_size = 2;

    public Critter1(){

        dir = getRandomInt(8);
    }

    /**
     * Does time step for Critter1
     * Walks in direction of dir, and sets new distinct direction
     */

    public void doTimeStep() {


        int new_dir = getRandomInt(8);
        walk(dir);

        //int litter_size = getRandomInt(max_litter_size) + 1;

        if(getEnergy() >= 80){
            createOffspring();
        }

        while(new_dir != dir){
            new_dir = getRandomInt(8);
        }

        dir = new_dir;
    }

    /**
     * Critter1 always fights
     * @param irrelevant is irrelevant
     * @return true every time
     */
    public boolean fight(String irrelevant) {

        return true;
    }

    private void createOffspring(){

        Critter1 child = new Critter1();
        reproduce(child, getRandomInt(8));
    }

    public String toString(){

        return "V";
    }
}

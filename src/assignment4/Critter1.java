package assignment4;

public class Critter1 extends Critter {

    private int dir;

    public Critter1(){

        dir = getRandomInt(8);
    }

    /**
     * Does time step for Critter1
     * Walks in direction of dir, and sets new distinct direction
     * Reproduces between 1 and max_litter_size offspring
     * 1/5 chance to increase max_litter_size, caps off at 8
     */

    public void doTimeStep() {


        int new_dir = getRandomInt(8);
        walk(dir);

        if(getEnergy() > 70){
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

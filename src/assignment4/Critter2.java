package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Nimay Kumar
 * nrk472
 * 15470
 * Benson Huang
 * bkh642
 * 15470
 * Slip days used: <0>
 * Spring 2018
 */

//Herbivore critter
public class Critter2 extends Critter{

    private int dir;


    /**
     * Returns new Critter2
     * sets direction randomly
     */
    public Critter2(){

        dir = getRandomInt(8);
    }

    /**
     * Does 1 time step of action for Critter2 objects
     * 1/2 chance to walk. 1/2 chance to rest
     * Reproduces at 120 energy
     * Sets new direction that is different from current direction
     */
    public void doTimeStep(){

        int new_dir = getRandomInt(8);

        if(getRandomInt(2) == 1){
            walk(dir);
        }

        if(getEnergy() > 120){
            Critter2 child = new Critter2();
            reproduce(child, getRandomInt(8));
        }

        while(new_dir == dir){
            new_dir = getRandomInt(8);
        }

        dir = new_dir;
    }

    /**
     * Only fights against algae
     * @param opponent String of opponent
     * @return true if opponent is algae, false else
     */

    public boolean fight(String opponent){

        if(opponent.equals("@")){
            return true;
        }

        return false;
    }

    /**
     * Returns M to represent Herbivore
     */
    public String toString(){

        return "2";
    }

    /**
     * Prints out number of Critter2 on map
     * @param herbivores list of all Critter2 alive
     */
    public static void runStats(java.util.List<Critter> herbivores ){

        System.out.println(herbivores.size() + " total Herbivores");
    }
}

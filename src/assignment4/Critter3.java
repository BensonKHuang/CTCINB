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

//Carnivore critter
public class Critter3 extends Critter{


    private int dir;

    /**
     * Returns new Critter3
     * sets direction randomly
     */
    public Critter3(){

        dir = getRandomInt(8);
    }

    /**
     * Does 1 step of action for Critter3
     * 2/3 chance to run, 1/2 chance to walk
     * Reproduces at 140 energy
     * Sets new direction randomly
     */
    public void doTimeStep(){

        int action = getRandomInt(3);

        if(action <= 1){
            run(dir);
        }
        else{
            walk(dir);
        }

        if(getEnergy() >= 140){
            Critter3 child = new Critter3();
            reproduce(child, getRandomInt(8));
        }

        dir = getRandomInt(8);
    }

    /**
     * Returns boolean whether or not Critter3 will fight
     * @param opponent String representing opponenet
     * @return true if opponent is another critter, false if opponent is algae
     */
    public boolean fight(String opponent) {

        if(opponent.equals("@")){
            return false;
        }

        return true;
    }

    /**
     *returns R to represent carnivoRe
     */
    public String toString(){
        return "R";
    }

    /**
     * Prints out number of carnivores on map
     * @param carnivores list of all living carnivores
     */
    public static void runStats(java.util.List<Critter> carnivores ){

        System.out.println(carnivores.size() + " total Carnivores");
    }
}

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

//Cannibal critter
public class Critter4 extends Critter{


    private int dir;

    /**
     * Sets direction randomly
     * Returns new Cannibal critter (Critter4)
     */
    public Critter4(){

        dir = getRandomInt(8);
    }

    /**
     * 1/3 chance to run, 1/3 chance to walk. 1/3 chance to rest
     * Reproduces at 125 energy
     * Changes direction
     */
    public void doTimeStep(){

        int action = getRandomInt(3);

        if(action == 2){
            run(dir);
        }
        else if (action == 1){
            walk(dir);
        }

        if(getEnergy() >= 125){

            Critter4 child = new Critter4();
            reproduce(child, getRandomInt(8));
        }

        dir = getRandomInt(8);
    }

    /**
     * Only fights itself, hence Cannibal
     * @param opponent Critter string of opponent
     * @return true if opponent is a Critter4, else false
     */
    public boolean fight(String opponent){

        if(opponent.equals("L")){
            return true;
        }

        return false;
    }

    /**
     * String representation of Critter4
     * @return L for cannibaL
     */
    public String toString(){

        return "L";
    }

    /**
     * Prints out how many cannibals currently on map
     * @param cannibals list of all cannibals in population
     */
    public static void runStats(java.util.List<Critter> cannibals){

        System.out.println(cannibals.size() + " total Cannibals");
    }

}

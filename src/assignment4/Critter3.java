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

//The Hindu, reincarnates after it dies after a grace period
//Has chance to reproduce any Critter
public class Critter3 extends Critter{

    private int dir;
    private boolean isDead;
    private int respawn_time;

    /**
     * Returns new Critter3
     * sets direction randomly
     */
    public Critter3(){
        isDead = false;
        respawn_time = 3;
        dir = getRandomInt(8);
    }

    /**
     * Does 1 step of action for Critter3
     * 2/3 chance to run, 1/2 chance to walk
     * Reproduces at 140 energy
     * Sets new direction randomly
     */
    public void doTimeStep(){
        if(isDead){
            if(respawn_time == 0){
                int reincarnation = getRandomInt(10);
                try{
                    switch(reincarnation){
                        case 0:
                            makeCritter("Algae");
                            break;
                        case 1:
                            makeCritter("Critter1");
                            break;
                        case 2:
                            makeCritter("Critter2");
                            break;
                        case 3:
                            makeCritter("Critter4");
                            break;
                        default:
                            makeCritter("Critter3");
                            break;
                    }
                    isDead = false;
                }
                catch (InvalidCritterException e){
                    e.printStackTrace(); //do something with exception
                }
            }
            else{
                --respawn_time;
            }
        }
        else{
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
    }

    /**
     * Returns boolean whether or not Critter3 will fight
     * @param opponent String representing opponent
     * @return true if opponent is another critter, false if opponent is algae
     */
    public boolean fight(String opponent) {

        if(opponent.toString().equals("H")){
            return false;
        }
        return true;
    }

    /**
     *returns H to represent hindu
     */
    public String toString(){
        return "H";
    }

    /**
     * Prints out number of carnivores on map
     * @param hindus list of all living hindus
     */
    public static void runStats(java.util.List<Critter> hindus){

        System.out.println(hindus.size() + " total Hindus");
    }

    /**
     * sets dead as true for reincarnation logic
     */
    public void setDead(){
        isDead = true;
    }
    public boolean getDead(){
        return isDead;
    }
}

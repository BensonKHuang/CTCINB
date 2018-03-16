package assignment4;

/*
*
* Herbivore Critter
*
*
 */

public class Critter2 extends Critter{

    private int dir;


    public Critter2(){

        dir = getRandomInt(8);
    }

    public void doTimeStep(){

        if(getRandomInt(2) == 1){
            walk(dir);
        }

        if(getEnergy() > 120){
            Critter2 child = new Critter2();
            reproduce(child, getRandomInt(8));
        }

        dir = getRandomInt(8);
    }


    public boolean fight(String opponent){

        if(opponent.equals("@")){
            return true;
        }

        return false;
    }

    public String toString(){

        return "H";
    }
}

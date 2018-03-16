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

        int new_dir = getRandomInt(8);

        if(getRandomInt(2) == 1){
            walk(dir);
        }

        if(getEnergy() > 120){
            Critter2 child = new Critter2();
            reproduce(child, getRandomInt(8));
        }

        while(new_dir != dir){
            new_dir = getRandomInt(8);
        }

        dir = new_dir;
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

package assignment4;
/*
*
*
* Carnivore critter
*
 */

public class Critter3 extends Critter{


    private int dir;

    public Critter3(){

        dir = getRandomInt(8);
    }

    public void doTimeStep(){

        int action = getRandomInt(3);

        if(action <= 1){
            run(dir);
        }
        else{
            walk(dir);
        }

        if(getEnergy() > 140){
            Critter3 child = new Critter3();
            reproduce(child, getRandomInt(8));
        }

        dir = getRandomInt(8);
    }

    public boolean fight(String opponent) {

        if(opponent.equals("@")){
            return false;
        }

        return true;
    }

    public String toString(){
        return "R";
    }
}

package assignment4;

/*
*
* Cannibal critter
*
 */

public class Critter4 extends Critter{


    private int dir;

    public Critter4(){

        dir = getRandomInt(8);
    }

    public void doTimeStep(){

        int action = getRandomInt(3);
        

        if(action == 2){
            run(dir);
        }
        else if (action == 1){
            walk(dir);
        }

        if(getEnergy() > 125){

            Critter4 child = new Critter4();
            reproduce(child, getRandomInt(8));
        }

        dir = getRandomInt(8);
    }

    public boolean fight(String opponent){

        if(opponent.equals("L")){
            return true;
        }

        return false;
    }

    public String toString(){

        return "L";
    }

}

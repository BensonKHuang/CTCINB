package assignment4;

import java.util.*;

public class MyCritter1 extends Critter.TestCritter {


    private int dir;
    private int strength;
    private static int max_litter_size= 4;

    public MyCritter1(){

        this.dir = getRandomInt(8);
        this.strength = getRandomInt(5);
    }

	@Override
	public void doTimeStep() {

		walk(dir);

		if(getEnergy() > 120){

		    int litter_size = getRandomInt(max_litter_size);

        }
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {

		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
}

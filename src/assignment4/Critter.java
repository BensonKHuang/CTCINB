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



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private static List<List<List<Critter>>> map = new ArrayList<List<List<Critter>>>();
	private static List<Critter3> reincarnation = new ArrayList<Critter3>();
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

    /**
     * Initializes map
     */
	private static void initializeMap(){
		map.clear();
		for (int row = 0; row < Params.world_height; ++row) {
			map.add(row, new ArrayList<>());
			for (int col = 0; col < Params.world_width; ++col) {
				map.get(row).add(col, new ArrayList<Critter>()); //sets every cell to empty
			}
		}
	}

	private boolean alive;
	private boolean moved;

	private boolean isAlive(){
		return alive;
	}
	private boolean hasMoved(){
		return moved;
	}

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {

	    return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {

	    rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() {

	    return "";
	}
	
	private int energy = 0;

    /**
     * Getter for energy
     * @return int representing amoutn of energy
     */
	protected int getEnergy() {

		return energy;
	}
	
	private int x_coord;
	private int y_coord;

    /**
     * Moves 1 in direction
     * @param direction direction to walk
     */
	protected final void walk(int direction) {

		if(!moved){
			map.get(y_coord).get(x_coord).remove(this);
			move(direction, 1);
            map.get(y_coord).get(x_coord).add(this);
			moved = true;
		}
        energy -= Params.walk_energy_cost;
	}

    /**
     * Moves 2 in direction
     * @param direction direction to run
     */
	protected final void run(int direction) {

		if(!moved){
			map.get(y_coord).get(x_coord).remove(this);
			move(direction, 2);
            map.get(y_coord).get(x_coord).add(this);
			moved = true;
		}
	    energy -= Params.run_energy_cost;
	}

    /**
     * Creates new child if enough energy
     * Sets child coordinates, energy, updates parent energy, moves child, and adds child to babies
     * @param offspring child critter
     * @param direction direction in which offspring will move
     */
	protected final void reproduce(Critter offspring, int direction) {

		if(this.getEnergy() < Params.min_reproduce_energy){
			return;
		}

		offspring.alive = true;
		offspring.moved = false;
		offspring.energy = (this.energy / 2);
		this.energy = (this.energy + 1) / 2; //pseudo CEIL formula
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.move(direction, 1);
		babies.add(offspring);
	}

    /**
     * Moves a critter in a direction by dist amount
     * @param direction direction to move
     * @param dist amount to move in direction
     */
	private void move(int direction, int dist){

        if(direction == 0 || direction == 1 || direction == 7){
            x_coord += dist;
        }
        else if(direction == 3 || direction ==4 || direction == 5){
            x_coord -= dist;
        }

        if(direction == 1 || direction == 2 || direction == 3){
            y_coord -= dist;
        }
        else if(direction == 5 || direction == 6 || direction == 7) {
            y_coord += dist;
        }
        fixCoord();
    }

    /**
     * Wraps coordinates around the map
     */
    private void fixCoord(){

        if(x_coord >= Params.world_width){
            x_coord %= Params.world_width;
        }
        else if(x_coord < 0){
            x_coord += Params.world_width ;
        }

        if(y_coord >= Params.world_height){
            y_coord %= Params.world_height;
        }
        else if(y_coord < 0){
            y_coord += Params.world_height;
        }
    }

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name name of critter subclass to be created
	 * @throws InvalidCritterException if critter_class_name is not a valid critter subclass
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

	    try{
	        Class c = Class.forName(myPackage + "." + critter_class_name);
	        Critter cr = (Critter) c.getDeclaredConstructor().newInstance();
	        cr.energy = Params.start_energy;
	        cr.x_coord = getRandomInt(Params.world_width);
	        cr.y_coord = getRandomInt(Params.world_height);
	        cr.alive = true;
	        cr.moved = false;
	        population.add(cr);
			map.get(cr.y_coord).get(cr.x_coord).add(cr);

        }
        catch(ClassNotFoundException | InvocationTargetException| IllegalAccessException | InstantiationException | NoSuchMethodException | NoClassDefFoundError e){
	        throw new InvalidCritterException(critter_class_name);
		}

	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException if critter_class_name is not valid Critter subclass
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try{
			for(Critter c : population){
				if(Class.forName(myPackage + "." + critter_class_name).isInstance(c)){
					result.add(c);
				}
			}
		}
		catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {

			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			map.get(super.y_coord).get(super.x_coord).remove(this);
			super.x_coord = new_x_coord;
			map.get(super.y_coord).get(super.x_coord).add(this);
		}
		
		protected void setY_coord(int new_y_coord) {
			map.get(super.y_coord).get(super.x_coord).remove(this);
			super.y_coord = new_y_coord;
			map.get(super.y_coord).get(super.x_coord).add(this);
		}
		
		protected int getX_coord() {

			return super.x_coord;
		}
		
		protected int getY_coord() {

			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {

			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {

			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {

		population.clear();
		babies.clear();
		initializeMap();
	}

    /**
     * Does timestep for every critter in the world, and calls other timestep functions.
     */
	public static void worldTimeStep() {

		individualTimeStep();
		removeDeadCritters();
		resolveEncounters();
		postEncounterTimeStep();
        removeDeadCritters();
		respawnAlgae();
		babyPopulate();
	}

    /**
     * calls doTimeStep for every critter in world
     */
	private static void individualTimeStep(){
		for(Critter c : population){
			c.doTimeStep();
		}
        List<Critter3> reincarnated = new ArrayList<>();
		for(Critter3 hindu : reincarnation){
		    hindu.doTimeStep();
            if(!hindu.getDead()){
                reincarnated.add(hindu);
            }
        }
        reincarnation.removeAll(reincarnated);
		reincarnated.clear();
	}

    /**
     * Updates statuses of critters following fights
     */
	private static void postEncounterTimeStep(){
		for(Critter c: population){
			c.energy -= Params.rest_energy_cost;
			if(c.getEnergy() <= 0){
				c.alive = false;
			}
			else{
				c.moved = false;
			}
		}
	}

    /**
     * Handles all possible fights in map.
     * Loser is set to dead
     */
	private static void resolveEncounters() {
		int luckA, luckB;
		Critter critterA, critterB, cr;
		boolean fightA, fightB;


		for (int row = 0; row < Params.world_height; ++row) {
			for (int col = 0; col < Params.world_width; ++col) {

			    //Critter dies naturally
			    if(map.get(row).get(col).size() == 1){

			        cr = (Critter) map.get(row).get(col).get(0);

			        if(cr.getEnergy() <= 0){
			            map.get(row).get(col).remove(cr);
                    }
                }

                //encounters to be resolved
				while(map.get(row).get(col).size() > 1){
					critterA = (Critter) map.get(row).get(col).get(0);
					critterB = (Critter) map.get(row).get(col).get(1);

					if(critterA.getEnergy() <= 0 ){
						map.get(row).get(col).remove(critterA);
					}

					if(critterB.getEnergy() <= 0 ){
						map.get(row).get(col).remove(critterA);
					}

					if(critterA.getEnergy() <= 0 || critterB.getEnergy() <= 0){
						continue;
					}


					fightA = critterA.fight(critterB.toString());
					if(!fightA && !(critterA instanceof TestCritter)){
						critterA.flee();
					}
					fightB = critterB.fight(critterA.toString());
					if(!fightB && !(critterB instanceof TestCritter)){
						critterA.flee();
					}

					if(sameLocation(critterA, critterB) && critterA.getEnergy() > 0 && critterB.getEnergy() > 0){

						if(fightA){
							luckA = getRandomInt(critterA.getEnergy());
						}
						else{
							luckA = 0;
						}

						if(fightB){
							luckB = getRandomInt(critterB.getEnergy());
						}
						else{
							luckB = 0;
						}

						if(luckA >= luckB){
							critterA.energy += (critterB.energy/2);
							critterB.alive = false;
							map.get(row).get(col).remove(critterB);
						}
						else{
							critterB.energy += (critterA.energy/2);
							critterA.alive = false;
							map.get(row).get(col).remove(critterA);
						}

					}
				}
			}
		}
	}

    /**
     * Determines whether two critters occupy the same spot
     * @param a first critter
     * @param b second critter
     * @return true if a and b occupy the same map coordinate, false otherwise
     */
	private static boolean sameLocation(Critter a, Critter b){
		return (a.x_coord == b.x_coord && a.y_coord == b.y_coord);
	}

    /**
     * Removes all dead critters from population
     */
	private static void removeDeadCritters(){
		List<Critter> deadCritters = new java.util.ArrayList<>();
		for(Critter c : population){
			if(!c.alive || c.getEnergy() <= 0){
				map.get(c.y_coord).get(c.x_coord).remove(c);
				deadCritters.add(c);
				if(c instanceof Critter3){
				    ((Critter3) c).setDead();
				    reincarnation.add((Critter3) c);
                }
			}
		}
		population.removeAll(deadCritters);
	}

    /**
     * Spawns refresh_algae_count amount of algae per timestep
     */
	private static void respawnAlgae(){
		for(int i = 0; i < Params.refresh_algae_count; ++i){
			try{
				Critter.makeCritter("Algae");
			}
			catch(InvalidCritterException e){
				e.printStackTrace(); //do something with exception
			}
		}
	}


	/**
	 * Adds all new babies to the map, population, and clears babies
	 */
	private static void babyPopulate(){

	    for(Critter c : babies){

	        map.get(c.y_coord).get(c.x_coord).add(c);
        }

		population.addAll(babies);
		babies.clear();
	}

    /**
     * Prints out current world map, and all its critters
     */
	public static void displayWorld() {

		printBorder();
		for(int row = 0; row < Params.world_height; ++row){
			System.out.print("|");
			for(int col = 0; col < Params.world_width; ++col){
				List<Critter> cell = map.get(row).get(col);
				if(cell.isEmpty()){
					System.out.print(" ");
				}
				else{
					System.out.print(cell.get(0).toString()); //prints first Critter in arraylist (if multiple existing from spawn)
				}
			}
			System.out.println("|");
		}
		printBorder();
	}

    /**
     * Helper function for displayWorld
     * Prints border of the world
     */
	private static void printBorder() {
        String s = "";
        s += "+";
        for (int i = 0; i < Params.world_width; ++i) {
            s += "-";
        }
        s += "+";
        System.out.println(s);

    }


    /**
     * Sees if a Critter can flee into a spot.
     * Critter moves if it can flee, otherwise it stays put.
     */
    private void flee(){

        if(moved){
        	energy -= Params.walk_energy_cost;
            return;
        }

        int new_dir = getRandomInt(8);
        if(isEmpty(new_dir)){
            walk(new_dir);
        }
    }

    /**
     *
     * Checks if the spot denoted by dir is empty or not
     * @param dir direction to check
     * @return true if the spot in dir is empty
     */
    private boolean isEmpty(int dir){

        int new_x = x_coord;
        int new_y = y_coord;

        if(dir == 0 || dir == 1 || dir == 7){
            ++new_x;
        }
        else if(dir == 3 || dir == 4 || dir ==5){
            --new_x;
        }

        if(dir == 1 || dir == 2 || dir ==3){
            --new_y;
        }
        else if(dir == 5 || dir == 6 || dir == 7){
            ++new_y;
        }

        if(new_x >= Params.world_width){
            new_x %= Params.world_width;
        }
        else if(new_x < 0){
            new_x += Params.world_width ;
        }

        if(new_y >= Params.world_height){
            new_y %= Params.world_height;
        }
        else if(new_y < 0){
            new_y += Params.world_height;
        }

        if(map.get(new_y).get(new_x).size() > 0){
            return false;
        }

        return true;
    }
}
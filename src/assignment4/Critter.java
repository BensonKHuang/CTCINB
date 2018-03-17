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



import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private static List<List<List<Critter>>> map = new ArrayList<List<List<Critter>>>();
	//private static ArrayList[][] map = new ArrayList[Params.world_height][Params.world_width];
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	public static void initializeMap(){

		for (int row = 0; row < Params.world_height; ++row) {
			map.add(row, new ArrayList<>());
			for (int col = 0; col < Params.world_width; ++col) {
				map.get(row).add(col, new ArrayList<Critter>()); //sets every cell to empty
			}
		}
//		try{
//			for(int i = 0; i < 25; ++i){
//				Critter.makeCritter("Craig");
//			}
//			for(int i = 0; i < 100; ++i){
//				Critter.makeCritter("Algae");
//			}
//		}
//		catch(InvalidCritterException e){
//			e.printStackTrace(); //do something with exception
//		}
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
	protected int getEnergy() {

		return energy;
	}
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {

		if(!moved){
			move(direction, 1);
            map.get(y_coord).get(x_coord).add(this);
			moved = true;
		}
        energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {

		if(!moved){
			move(direction, 2);
            map.get(y_coord).get(x_coord).add(this);
			moved = true;
		}
	    energy -= Params.run_energy_cost;
	}
	
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



	private void move(int direction, int dist){
		int prevX = x_coord;
		int prevY = y_coord;
		map.get(prevY).get(prevX).remove(this);

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
		//map.get(y_coord).get(x_coord).add(this);
    }

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
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

	    try{
	        Class c = Class.forName(myPackage + "." + critter_class_name);
	        Critter cr = (Critter) c.newInstance();
	        cr.energy = Params.start_energy;
	        cr.x_coord = getRandomInt(Params.world_width);
	        cr.y_coord = getRandomInt(Params.world_height);
	        cr.alive = true;
	        cr.moved = false;
	        population.add(cr);
			map.get(cr.y_coord).get(cr.x_coord).add(cr);

        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
	        throw new InvalidCritterException(critter_class_name);
        }

	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
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

			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {

			super.y_coord = new_y_coord;
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
	
	public static void worldTimeStep() {

		individualTimeStep();
		resolveEncounters();
		postEncounterTimeStep();
        removeDeadCritters();
		respawnAlgae();
		babyPopulate();
	}

	private static void individualTimeStep(){
		for(Critter c : population){
			c.doTimeStep();
			c.energy -= Params.rest_energy_cost;
		}
	}

	private static void postEncounterTimeStep(){
		for(Critter c: population){
			if(c.getEnergy() <= 0){
				c.alive = false;
			}
			else{
				c.moved = false;
			}
		}
	}

	private static void resolveEncounters() {
		int luckA, luckB;
		Critter critterA, critterB, cr;
		boolean fightA, fightB;

		for (int row = 0; row < Params.world_height; ++row) {
			for (int col = 0; col < Params.world_width; ++col) {


			    if(map.get(row).get(col).size() == 1){

			        cr = (Critter) map.get(row).get(col).get(0);

			        if(cr.getEnergy() <= 0){
			            map.get(row).get(col).remove(cr);
                    }
                }



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
					fightB = critterB.fight(critterA.toString());


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

	private static boolean sameLocation(Critter a, Critter b){
		return (a.x_coord == b.x_coord && a.y_coord == b.y_coord);
	}

	private static void removeDeadCritters(){
		List<Critter> deadCritters = new java.util.ArrayList<>();
		for(Critter c : population){
			if(!c.alive || c.getEnergy() <= 0){
				deadCritters.add(c);
			}
		}
		population.removeAll(deadCritters);
	}

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
	 * Adds all new babies to the map
	 */
	private static void babyPopulate(){

	    for(Critter c : babies){

	        map.get(c.y_coord).get(c.x_coord).add(c);
        }

		population.addAll(babies);
		babies.clear();
	}
	
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
	private static void printBorder(){
		String s = "";
		s += "+";
		for(int i = 0; i < Params.world_width; ++i){
			s+= "-";
		}
		s += "+";
		System.out.println(s);
	}
}
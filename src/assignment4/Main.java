package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Nimay Kumar
 * nrk472
 * 15470
 * Benson Huang
 * bkh642
 * 15470
 * Slip days used: <0>
 * Spring 2018
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        Critter.initializeMap();
        boolean quit = false;
        while(!quit){
            String in = kb.nextLine();
            String[] inArray = in.split(" ");

            switch(inArray[0]){
                case "quit":
                    if(inArray.length != 1){
                        System.out.println("error processing: " + in);
                    }
                    else{
                        quit = true;
                    }
                    break;

                case "show":
                    if(inArray.length != 1){
                        System.out.println("error processing: " + in);
                    }
                    else{
                        Critter.displayWorld();
                    }
                    break;

                case "step":
                    if(inArray.length > 2){
                        System.out.println("error processing: " + in);
                    }
                    else{
                        try{
                            int numStep = Integer.parseInt(inArray[1]);
                            for(int i = 0; i < numStep; ++i){
                                Critter.worldTimeStep();
                            }
                        }
                        catch(IndexOutOfBoundsException e){
                            Critter.worldTimeStep();
                        }
                        catch(NumberFormatException e){
                            System.out.println("error processing: " + in);
                        }
                    }
                    break;

                case "seed":
                    if(inArray.length > 2){
                        System.out.println("error processing: " + in);
                    }
                    else{
                        try{
                            int numStep = Integer.parseInt(inArray[1]);
                            Critter.setSeed(numStep);
                        }
                        catch(NumberFormatException | IndexOutOfBoundsException e){
                            System.out.println("error processing: " + in);
                        }
                    }
                    break;

                case "make":
                    if(inArray.length > 3 || inArray.length <= 1){
                        System.out.println("error processing: " + in);
                    }
                    else if(inArray.length == 3){
                        try{
                            int count = Integer.parseInt(inArray[2]);
                            for(int i = 0; i < count; ++i){
                                Critter.makeCritter(inArray[1]);
                            }
                        }
                        catch(NumberFormatException | InvalidCritterException e){
                            System.out.println("error processing: " + in);
                        }
                    }
                    else{
                        try{
                            Critter.makeCritter(inArray[1]);
                        }
                        catch(InvalidCritterException e){
                            System.out.println("error processing: " + in);
                        }
                    }
                    break;

                case "stats":
                    if(inArray.length != 2){
                        System.out.println("error processing: " + in);
                    }
                    else if(inArray[1].equals("Critter")){
                        System.out.println("error processing: " + in);
                    }
                    else{
                        String critterName = inArray[1];
                        try{
                            List<Critter> list = Critter.getInstances(critterName);
                            Class critterClass = Class.forName(myPackage + "." + critterName); //Reflection
                            Method methodcall = critterClass.getMethod("runStats", List.class);
                            Critter critterInstance = (Critter) critterClass.newInstance();
                            methodcall.invoke(critterInstance, list);
                        }
                        catch(InvalidCritterException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                            System.out.println("error processing: " + in);
                        }
                    }
                    break;

                default:
                    System.out.println("invalid command: " + in);
                    break;
            }
        }

        /* Write your code above */
        System.out.flush();

    }
}

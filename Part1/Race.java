import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Sahin Halil
 * @version 1.0
 */
public class Race
{
    private Horse[] horseList;
    private int raceLength;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int laneNumber)
    {
        // initialise instance variables
        if (distance <= 0)
        {
            System.err.println("not enough distance");
            return;
        }
        if (laneNumber <= 0)
        {
            System.err.println("not enough lanes");
            return;
        }
        raceLength = distance;
        horseList = new Horse[laneNumber];
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber <= horseList.length && laneNumber > 0)
        {
            horseList[laneNumber-1] = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        for (Horse horse : horseList) {
            if (horse != null){
                System.out.println(horse.getName());
            }
            else{
                System.out.println("Empty lane");
            }
        }
        int counter = 0;
        for (Horse horse : horseList) {
            if (horse != null) {
                counter ++;
            }
        }
        if (counter < 2)
        {
            System.out.println("Inadequate number of horses to start the race");
            return;
        }
        
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : horseList) {
            if (horse != null){
                horse.goBackToStart();
            }
        }
                      
        while (!finished)
        {
            //check if all horses have fallen
            boolean allHorsesFallen = true;
            for (Horse horse : horseList) {
                if (horse != null && !horse.hasFallen()) {
                    allHorsesFallen = false;
                    break;
                }
            }
            //move each horse
            for (Horse horse : horseList) {
                if (horse != null){
                    moveHorse(horse);
                }
            }

            printRace();
            
            //if any of the three horses has won the race is finished
            for (Horse horse : horseList) {
                if (horse != null && raceWonBy(horse)){
                    finished = true;
                }
            }

            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}

            //end program if all horses have fallen
            if (allHorsesFallen) {
                System.out.println("All horses have fallen");
                break;
            }
        }

    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            System.out.println("Horse " + theHorse.getName() + " has won and travelled " + theHorse.getDistanceTravelled() + " metres");

            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        for (Horse horse : horseList) {
            printLane(horse);
            System.out.println();
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = raceLength;
        int spacesAfter = 1;
        if (theHorse != null)
        {
            spacesBefore = theHorse.getDistanceTravelled();
            spacesAfter = raceLength - theHorse.getDistanceTravelled();
        }
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if (theHorse != null){
            if(theHorse.hasFallen())
            {
                System.out.print('\u2322');
            }
            else
            {
                System.out.print(theHorse.getSymbol());
            }
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        for (int i = 0; i < times; i++)
        {
            System.out.print(aChar);
        }
    }
}
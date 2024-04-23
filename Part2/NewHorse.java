/**
 * Write a description of class Horse here.
 * The class Horse represents a horse in the race
 * 
 * @author Sahin Halil
 * @version 1.0
 */
public class NewHorse
{
    //Fields of class Horse
    final private String horseName;
    private double horseConfidence;
    private int distanceTravelled;
    private boolean horseFallen;
    private char horseSymbol;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public NewHorse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseName = horseName;
        if (horseConfidence <= 0 || horseConfidence > 1)
        {
            this.horseConfidence = 0.05;
        }
        else{
            this.horseConfidence = horseConfidence;
        }
        this.distanceTravelled = 0;
        this.horseFallen = false;
        this.horseSymbol = horseSymbol;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        horseFallen = true; 
    }
    
    public double getConfidence()
    {
        return horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return horseName;
    }
    
    public char getSymbol()
    {
        return horseSymbol;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
        horseFallen = false;
    }
    
    public boolean hasFallen()
    {
        return horseFallen;
    }

    public void moveForward()
    {
        distanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence <= 0)
        {
            horseConfidence = 0.05;
        }
        else if (newConfidence > 1)
        {
            horseConfidence = 1;
        }
        else{
            horseConfidence = newConfidence;
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }
    
}
/**
 * Write a description of class Horse here.
 * The class Horse represents a horse in the race
 * 
 * @author Sahin Halil
 * @version 1.0
 */
public class Horse
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
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        horseName = horseName;
        horseConfidence = horseConfidence;
        distanceTravelled = 0;
        horseFallen = false;
        horseSymbol = horseSymbol;
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
        
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
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
        horseConfidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        
    }
    
}
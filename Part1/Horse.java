
/**
 * Write a description of class Horse here.
 * This is a class for a horse which will die during the races
 * 
 * @author Sahin Halil 
 * @version 1.0
 */
public class Horse
{
    //Fields of class Horse
    private char horseSymbol;
    final private String horseName;
    private double horseConfidence;
    private int distanceTravelled;
    private boolean dead;

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.horseSymbol = horseSymbol;
       this.horseName = horseName;    
       this.horseConfidence = horseConfidence;
       this.distanceTravelled = 0;    
       this.dead = false;     
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        dead = true;
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
    }
    
    public boolean hasFallen()
    {
        return dead;
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
        horseSymbol = newSymbol;
    }
}

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

    //GUI components
    private String breed;
    private String coatColour;
    private String equipment;
    private String accessory;
    private int horseNumber;
    private int winNumber;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public NewHorse(char horseSymbol, String horseName, double horseConfidence, String breed, String coatColour, String equipment, String accessory, int horseNumber)
    {
        this.horseName = horseName;
        if (horseConfidence <= 0)
        {
            this.horseConfidence = 0.05;
        }
        else if (horseConfidence > 1)
        {
            this.horseConfidence = 1;
        }
        else{
            this.horseConfidence = horseConfidence;
        }
        this.distanceTravelled = 0;
        this.horseFallen = false;
        this.horseSymbol = horseSymbol;

        //GUI components
        this.breed = breed;
        this.coatColour = coatColour;
        this.equipment = equipment;
        this.accessory = accessory;
        this.horseNumber = horseNumber;
        this.winNumber = 0;
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

    //GUI components
    public String getBreed(){
        return breed;
    }

    public String getCoatColour(){
        return coatColour;
    }

    public String getEquipment(){
        return equipment;
    }

    public String getAccessory(){
        return accessory;
    }

    public int getHorseNumber(){
        return horseNumber;
    }

    public int getWinNumber(){
        return winNumber;
    }

    public void setWinNumber(int winNumber){
        this.winNumber = winNumber;
    }
}
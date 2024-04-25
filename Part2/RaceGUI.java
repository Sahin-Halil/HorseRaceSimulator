import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
/**
 * A horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Sahin Halil
 * @version 1.0
 */
public class RaceGUI
{
    private NewHorse[] horseList;
    private int raceLength;
    private int laneNumbers;

    // GUI components as fields
    private JFrame mainFrame;
    private JPanel trackPanel;
    private JLabel[] horseLabels;

    // Objects for the customisation dialogs
    private TrackCustomisationDialog trackDialog;
    private HorseCustomisationDialog horseDialog;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    // Method to initialise and show the GUI
    public RaceGUI() {
        
        // Create the main frame
        mainFrame = new JFrame("Horse Racing Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        
        // New JDialog for track customisation
        showTrackCustomisation();
        
        // Initialise panels
        mainFrame.add(new JButton("Start Button"), BorderLayout.SOUTH);
    
        // Set the size of the main frame, make it visible, and prevent resizing
        mainFrame.setSize(raceLength * 50 + 100, 600);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(NewHorse theHorse, int laneNumber)
    {
        boolean added = false;
        if (laneNumber <= horseList.length && laneNumber > 0)
        {
            for (NewHorse horse : horseList) {
                if (horse != null && (horse.getName().equals(theHorse.getName()) || horse.getSymbol() == theHorse.getSymbol()))
                {
                    System.out.println("Cannot add horse to lane " + laneNumber + " because horse " + horse.getName() + " is already in");
                    added = true;
                }
            }
            if (added == false)
            {
                horseList[laneNumber - 1] = theHorse;
            }
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    // Method to show the track customisation dialog
    public void showTrackCustomisation() {
        
        trackDialog = new TrackCustomisationDialog(mainFrame);
        trackDialog.setVisible(true);
        
        // After the dialog is closed, you can retrieve the values
        raceLength = trackDialog.getRaceLength();
        laneNumbers = trackDialog.getLaneNumbers();
        
        // Customise the mainframe with the selected color as a parameter
        customiseMainFrame();
    }
    
    // Method to show the horse customisation dialog
    public void showHorseCustomisation() {
        
        horseDialog = new HorseCustomisationDialog(mainFrame, laneNumbers);
            
        //After the dialog is closed, you can retrieve the values
        horseList = horseDialog.getHorses();
    }

    // Method to customise the main frame
    public void customiseMainFrame() {
        
        showHorseCustomisation();
        //NewHorse horse1 = new NewHorse('1', "Horse1", 0.5);
        //NewHorse horse2 = new NewHorse('2', "Horse2", 0.5);
        //NewHorse horse3 = new NewHorse('3', "Horse3", 0.5);
        //NewHorse horse4 = new NewHorse('4', "Horse4", 0.5);
        //NewHorse horse5 = new NewHorse('5', "Horse5", 0.5);
        //NewHorse horse6 = new NewHorse('6', "Horse6", 0.5);
        //NewHorse horse7 = new NewHorse('7', "Horse7", 0.5);
        //NewHorse horse8 = new NewHorse('8', "Horse8", 0.5);
        //NewHorse horse9 = new NewHorse('9', "Horse9", 0.5);
        //NewHorse horse10 = new NewHorse('0', "Horse10", 0.5);

        //NewHorse[] horses = {horse1, horse2, horse3, horse4, horse5, horse6, horse7, horse8, horse9, horse10};

        for (NewHorse horse : horseList) {
            if (horse != null){
                //addHorse(horse, horse.getHorseNumber());
            }

        }
        trackPanel = new JPanel();
        trackPanel.setLayout(new GridLayout(horseList.length, 1, 0, 5));

        // Create a border and set it to the track panel
        Border border = BorderFactory.createLineBorder(Color.BLACK, 10); 
        trackPanel.setBorder(border);
        mainFrame.add(trackPanel, BorderLayout.CENTER);
        horseLabels = new JLabel[horseList.length]; 

        // Add the lanes to the track panel
        for (int i = 0; i < horseList.length; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BorderLayout());
            rowPanel.setBackground(trackDialog.getTrackColour());
            
            // Check if the horse at this index is not null
            if (horseList[i] != null) {
                // Create a label with the horse's symbol
                JLabel laneLabel = new JLabel(String.valueOf(horseList[i].getSymbol()));
                laneLabel.setForeground(Color.WHITE);  
                
                // Add the label to the row panel
                rowPanel.add(laneLabel, BorderLayout.CENTER);
                horseLabels[i] = laneLabel; 
            } 
            else {
                // Create a placeholder label for empty lanes
                JLabel laneLabel = new JLabel("");
                laneLabel.setForeground(Color.WHITE);  
                
                // Add the placeholder label to the row panel
                rowPanel.add(laneLabel, BorderLayout.CENTER);
                horseLabels[i] = laneLabel; 
            }
            trackPanel.add(rowPanel);
        }

    }

    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRaceGUI()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (NewHorse horse : horseList) {
            if (horse != null){
                horse.goBackToStart();
            }
        }

        while (!finished)
        {
            //check if all horses have fallen
            boolean allHorsesFallen = true;
            for (NewHorse horse : horseList) {
                if (horse != null && !horse.hasFallen()) {
                    allHorsesFallen = false;
                    break;
                }
            }

            //move each horse
            moveHorse();

            //printRace();
            
            //if any of the horses has won the race is finished
            for (NewHorse horse : horseList) {
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
    private void moveHorse()
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        for (int i = 0; i < horseList.length; i++) {
            NewHorse horse = horseList[i];
            if (!horse.hasFallen()) {
                if (Math.random() < horse.getConfidence()) {
                    horse.moveForward();
                }
                //the probability that the horse will fall is very small (max is 0.1)
                //but will also will depends exponentially on confidence 
                //so if you double the confidence, the probability that it will fall is *2
                if (Math.random() < (0.1 * horse.getConfidence() * horse.getConfidence())) {
                    horse.setConfidence(horse.getConfidence() - 0.05);
                    horse.fall();
                    horseLabels[i].setForeground(Color.RED);
                }
                // Update the label's position
                horseLabels[i].setLocation(horse.getDistanceTravelled() * 50, horseLabels[i].getY());
            } 
        }
    }
    
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(NewHorse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            System.out.println("Horse " + theHorse.getName() + " has won and travelled " + theHorse.getDistanceTravelled() + " metres");

            theHorse.setConfidence(theHorse.getConfidence() + 0.05);

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
        
        for (NewHorse horse : horseList) {
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
    private void printLane(NewHorse theHorse)
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

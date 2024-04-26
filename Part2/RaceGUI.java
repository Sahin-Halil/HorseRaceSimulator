import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.border.Border;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
    private int numberOfGames = 0;

    // GUI components as fields
    private JFrame mainFrame;
    private JPanel trackPanel;
    private JLabel[] horseLabels;
    private JLabel[] horseLabelsCopy;

    // Objects for the customisation dialogs
    private TrackCustomisationDialog trackDialog;
    private HorseCustomisationDialog horseDialog;
    private BettingSystemDialog bettingDialog;

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
        showBettings();

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
         // Initialise button to start
        JButton startButton = new JButton("Start Race");
        buttonPanel.add(startButton);
        
        // Initialise button for horse metrics
        JButton metricsButton = new JButton("Horse Metrics");
        buttonPanel.add(metricsButton);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        // Perform long-running task here (outside the EDT)
                        startRaceGUI();
                        return null;
                    }
                };
                worker.execute();
            }
        });
        

        // Add action listener to the metrics button
        metricsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        showHorseMetrics();
                        return null;
                    }
                };
                worker.execute();
            }
        });

        // View past horse metrics button
        JButton pastHButton = new JButton("Past Horse Metrics");
        buttonPanel.add(pastHButton);
        
        // Add action listener to the past horse metrics button
        pastHButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        readRaceData();
                        return null;
                    }
                };
                worker.execute();
            }
        });
        
        // Add the button panel to the main frame
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        // Set the size of the main frame, make it visible, and prevent resizing
        mainFrame.setSize(raceLength * 50 + 100, 600);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
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

    public void addCustomisationtoHorse(JLabel laneLabel, NewHorse horse) {
        Random random = new Random();

        // Set the horse's confidence
        double[] horseConfidence = {0.1, 0.2, 0.3};
        
        // Get coat colour, equipment, and accessory using getter methods
        String coatColour = horse.getCoatColour();
        String equipment = horse.getEquipment();
        String accessory = horse.getAccessory();
        
        // Set confidence based on equipment
        double confidence = horseConfidence[random.nextInt(horseConfidence.length)];
        if (equipment.equals("Shoes")) {
            confidence += 0.1;
        } 
        else if (equipment.equals("Saddle")) {
            confidence += 0.2;
        } 
        else if (equipment.equals("Reins")) {
            confidence += 0.3;
        }
        
        // Set the horse's confidence
        horse.setConfidence(confidence);
        
        // Determine text style based on accessory
        String textStyle = "";
        if (accessory.equals("Strike-through")) {
            textStyle = "text-decoration:line-through;";
        } 
        else if (accessory.equals("Italics")) {
            textStyle = "font-style:italic;";
        } 
        else if (accessory.equals("Underline")) {
            textStyle = "text-decoration:underline;";
        }
        
        // Apply customisation to the label
        laneLabel.setText("<html><div style='color:" + coatColour + "; background-color:white; " + textStyle + "'>" + horse.getSymbol() + "</div></html>");
    }

    // Method to customise the main frame
    public void customiseMainFrame() {
        
        showHorseCustomisation();

        trackPanel = new JPanel();
        trackPanel.setLayout(new GridLayout(horseList.length, 1, 0, 5));

        // Create a border and set it to the track panel
        Border border = BorderFactory.createLineBorder(Color.BLACK, 10); 
        trackPanel.setBorder(border);
        mainFrame.add(trackPanel, BorderLayout.CENTER);
        horseLabels = new JLabel[horseList.length]; 
        horseLabelsCopy = new JLabel[horseList.length];

        // Add the lanes to the track panel
        for (int i = 0; i < horseList.length; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BorderLayout());
            rowPanel.setBackground(trackDialog.getTrackColour());
            
            // Check if the horse at this index is not null
            if (horseList[i] != null) {
                // Create a label with the horse's symbol
                JLabel laneLabel = new JLabel(String.valueOf(horseList[i].getSymbol())); 

                addCustomisationtoHorse(laneLabel, horseList[i]);
                
                // Add the label to the row panel
                rowPanel.add(laneLabel, BorderLayout.CENTER);
                horseLabels[i] = laneLabel; 

                // Add the label to the copy array
                horseLabelsCopy[i] = new JLabel(horseLabels[i].getText());
            } 
            else {
                // Create a placeholder label for empty lanes
                JLabel laneLabel = new JLabel("");
                laneLabel.setForeground(Color.WHITE);  
                
                // Add the placeholder label to the row panel
                rowPanel.add(laneLabel, BorderLayout.CENTER);
                horseLabels[i] = laneLabel; 

                // Add the placeholder label to the copy array
                horseLabelsCopy[i] = new JLabel(horseLabels[i].getText());
            }
            trackPanel.add(rowPanel);
        }
    }

    public void showBettings() {
        bettingDialog = new BettingSystemDialog(mainFrame, horseList);
    }

    public void showHorseMetrics() {
        JFrame metricsFrame = new JFrame("Horse Metrics");
        metricsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        metricsFrame.setLayout(new GridLayout(horseList.length, 1, 0, 5));
        
        // Add the lanes to the metrics frame
        for (int i = 0; i < horseList.length; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BorderLayout());
            rowPanel.setBackground(trackDialog.getTrackColour());
            
            // Check if the horse at this index is not null
            if (horseList[i] != null) {
    
                // Create a label with the horse's metrics
                JLabel metricsLabel = new JLabel(horseList[i].getName() + ", Confidence: " + String.format("%.2f", horseList[i].getConfidence()) + ", Odds: " + String.format("%.2f", horseList[i].getHorseOdds() * 100) + "%, Win Ratio: " + String.format("%.2f", horseList[i].getWinRatio()) + ", Distance: " + horseList[i].getDistanceTravelled() + "m");
                
                // Add the metrics label to the row panel
                rowPanel.add(metricsLabel, BorderLayout.EAST);

                // Add the horse label copy to the row panel at the start of its lane
                rowPanel.add(new JLabel(horseLabelsCopy[i].getText()), BorderLayout.WEST);
            } 
        
            metricsFrame.add(rowPanel);
        }

        // Add a WindowListener to handle the window closing event
        metricsFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Terminate the program
                metricsFrame.dispose();
            }
        });
        
        // Set the size of the metrics frame, make it visible, and prevent resizing
        metricsFrame.setSize(raceLength * 50 + 100, 600);
        metricsFrame.setResizable(false);
        metricsFrame.setVisible(true);
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

        //increment the number of games
        numberOfGames++;

        // Update win ratio for all horses
        for (NewHorse horse : horseList) {
            if (horse != null) {
                horse.setWinRatio((double) horse.getWinNumber() / numberOfGames);
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
                JOptionPane.showMessageDialog(null, "All horses have fallen.");
                break;
            }
        }

        // Apply horse labels to the track panel
        for (int i = 0; i < horseLabels.length; i++) {    
                horseLabels[i].setText(horseLabelsCopy[i].getText());
        }

        // After the race is finished
        for (NewHorse horse : horseList) {
            if (horse != null) {
                // Create a string with the horse's data
                String horseData = horse.getName() + ", Confidence: " + String.format("%.2f", horse.getConfidence()) + ", Odds: " + String.format("%.2f", horse.getHorseOdds() * 100) + "%, Win Ratio: " + String.format("%.2f", horse.getWinRatio()) + ", Distance: " + horse.getDistanceTravelled() + "metres" + "\n";

                // Write the data to a file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Part2/race_results.txt", true))) {
                    writer.write(horseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Write a separator line to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Part2/race_results.txt", true))) {
            writer.write("--------------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readRaceData() {
        JTextArea textArea = new JTextArea();
        JDialog dialog = new JDialog(mainFrame, "Past Horse Metrics", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);
        dialog.setSize(500, 500);
    
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            protected Void doInBackground() throws Exception {
                try (BufferedReader reader = new BufferedReader(new FileReader("Part2/race_results.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        publish(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
    
            protected void process(List<String> chunks) {
                for (String line : chunks) {
                    textArea.append(line + "\n");
                }
            }
    
            protected void done() {
                dialog.setVisible(true);
            }
        };
        worker.execute();
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
            if (horse != null && !horse.hasFallen()) {
                if (Math.random() < horse.getConfidence()) {
                    horse.moveForward();
                }
                //the probability that the horse will fall is very small (max is 0.05)
                //but will also will depends exponentially on confidence 
                //so if you double the confidence, the probability that it will fall is *2
                if (Math.random() < (0.05 * horse.getConfidence() * horse.getConfidence())) {
                    horse.setConfidence(horse.getConfidence() - 0.05);
                    horse.setHorseOdds(horse.getHorseOdds() - 0.025);
                    horse.fall();
                    horseLabels[i].setText("X");
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

            JOptionPane.showMessageDialog(null, theHorse.getName() + " has won and travelled " + theHorse.getDistanceTravelled() + " metres.");

            // Update the horse's metrics
            theHorse.setConfidence(theHorse.getConfidence() + 0.05);
            theHorse.setWinNumber(theHorse.getWinNumber() + 1);
            theHorse.setWinRatio( (double) theHorse.getWinNumber() / numberOfGames);
            theHorse.setHorseOdds(theHorse.getHorseOdds() + 0.025);

            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static void main(String[] args) {
        
        RaceGUI race = new RaceGUI();
        
    }
}

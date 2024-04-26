import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class BettingSystemDialog extends JDialog {
    private JTextField betField;
    private JComboBox<String> horseBox;
    private NewHorse[] horseList;
    private JLabel betStatusLabel;
    private Map<String, Integer> betAmounts = new HashMap<>();
    private Map<String, JLabel> betLabels = new HashMap<>();
    private JLabel totalBetLabel;
    private int amountLeft = 100;
    private HashMap<String, Double> horseOdds = new HashMap<>();

    public BettingSystemDialog(JFrame parentFrame, NewHorse[] horseList) {
        super(parentFrame, "Betting System", true);
        this.horseList = horseList;
        for (NewHorse horse : horseList) {
            if (horse != null) {
                // Initialise the bet amounts for each horse to 0
                betAmounts.put(horse.getName(), 0);
            }
        }
        initialiseComponents();
    }

    private void initialiseComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Dimension preferredSize = new Dimension(300, 100);

        // Bets Placed
        constraints.gridy = 0;
        this.add(new JLabel("Bets Placed:"), constraints);  
        for (NewHorse horse : horseList) {
            if (horse != null) {
                constraints.gridy++;
                JLabel betLabel = new JLabel(horse.getName() + ": " + betAmounts.get(horse.getName()) + " " + "odds: " + Double.parseDouble(String.format("%.2f", horse.getConfidence() * 100)) + "%");
                this.add(betLabel, constraints);
                betLabels.put(horse.getName(), betLabel);
            }
        }

        // Bet Amount left
        constraints.gridy++;
        totalBetLabel = new JLabel("Total Bet Amount: " + amountLeft);
        this.add(totalBetLabel, constraints);

        // Dropdown for selecting a horse
        constraints.gridy ++;
        this.add(new JLabel("Select a Horse:"), constraints);
        ArrayList<String> horseNames = new ArrayList<>();
        for (NewHorse horse : horseList) {
            if (horse != null) {
            horseNames.add(horse.getName());
            }
        }
        String[] horseNamesArray = horseNames.toArray(new String[0]);
        horseBox = new JComboBox<>(horseNamesArray);
        horseBox.setPreferredSize(preferredSize);
        constraints.gridy ++;
        this.add(horseBox, constraints);

        // Field for entering the bet amount
        constraints.gridy ++;
        this.add(new JLabel("Enter Bet Amount:"), constraints);
        betField = new JTextField("100", 5);
        betField.setPreferredSize(preferredSize);
        constraints.gridy ++;
        this.add(betField, constraints);

        // Label to show the bet status
        betStatusLabel = new JLabel("");
        constraints.gridy ++;
        this.add(betStatusLabel, constraints);

        // Button to place the bet
        JButton betButton = new JButton("Place Bet");
        constraints.gridy ++;
        this.add(betButton, constraints);

        // Add an ActionListener to the "Place Bet" button
        betButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save the values from the dropdown and text field
                NewHorse selectedHorse = null;
                for (NewHorse horse : horseList) {
                    if (horse != null && horse.getName().equals(horseBox.getSelectedItem())) {
                        selectedHorse = horse;
                    }
                }
                
                String betAmountStr = betField.getText();

                // Validate the bet amount
                try {
                    int betAmount = Integer.parseInt(betAmountStr);
                    if (betAmount <= 0) {
                        JOptionPane.showMessageDialog(null, "Bet amount must be greater than 0.");
                        return;
                    }
                    if (amountLeft - betAmount < 0) {
                        JOptionPane.showMessageDialog(null, "Total bet amount cannot exceed 100.");
                        return;
                    }

                    // Update the horse's confidence based on the bet amount
                    selectedHorse.setConfidence(selectedHorse.getConfidence() + betAmount * 0.001);
                    double rating = Double.parseDouble(String.format("%.2f", selectedHorse.getConfidence() * 100));
                    
                    // Update the bet amounts for the selected horse
                    betAmounts.put(selectedHorse.getName(), betAmounts.get(selectedHorse.getName()) + betAmount);
                    
                    // Update the total bet amount
                    amountLeft -= betAmount;

                    // Update the bet amounts label
                    betLabels.get(selectedHorse.getName()).setText(selectedHorse.getName() + ": " + betAmounts.get(selectedHorse.getName()) + " " + "odds: " + rating + "%");

                    // Update the total bet amount label
                    totalBetLabel.setText("Total Bet Amount: " + amountLeft);

                    // Close the betting dialog once all the money has been used.
                    if (amountLeft == 0){
                        JOptionPane.showMessageDialog(null, "You have used all your money.");
                        dispose();
                    }
                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid bet amount. Please enter a number that is an integer.");
                }
            }
        });

        // Add the horse odds to a map
        for (NewHorse horse : horseList) {
            if (horse != null) {
                horseOdds.put(horse.getName(), (double) Math.round(horse.getConfidence() * 100));
            }
        }

        // Add a WindowListener to handle the window closing event
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Terminate the program
                System.exit(0);
            }
        });

        // Set the size of the dialog and make it visible
        this.pack();
        this.setVisible(true);
    }

    public HashMap<String, Double> getHorseOdds() {
        return horseOdds;
    }

    public void setHorseOdds(HashMap<String, Double> horseOdds) {
        this.horseOdds = horseOdds;
    }
}
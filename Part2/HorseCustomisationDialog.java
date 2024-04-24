import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HorseCustomisationDialog extends JDialog {
    private JComboBox<Character> SymbolComboBox;
    private JComboBox<String> breedComboBox;
    private JComboBox<String> coatColourComboBox;
    private JComboBox<String> equipmentComboBox;
    private JComboBox<String> accessoriesComboBox;
    int horseListLength;
    private List<NewHorse> horses;
    private double[] horseConfidence = {0.1, 0.2, 0.3};

    // fields to return
    private String breed;
    private String coatColour;
    private String equipment;

    public HorseCustomisationDialog(JFrame parentFrame, int  horseListLength) {
        super(parentFrame, "Horse Customisation", true);
        horses = new ArrayList<>();
        this.horseListLength = horseListLength;
        initialiseComponents();
    }

    public void initialiseComponents(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Dimension preferredSize = new Dimension(300, 100);
    
        // Symbol ComboBox
        JLabel symbolLabel = new JLabel("Symbol:");
        constraints.gridy = 0;
        this.add(symbolLabel, constraints);
        Character[] symbols = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        SymbolComboBox = new JComboBox<>(symbols);
        SymbolComboBox.setPreferredSize(preferredSize);
        constraints.gridy = 1;
        this.add(SymbolComboBox, constraints);
    
        // Breed ComboBox
        JLabel breedLabel = new JLabel("Breed:");
        constraints.gridy = 2;
        this.add(breedLabel, constraints);
        String[] breeds = {"Arabian", "Thoroughbred", "Quarter Horse", "Morgan", "Paint", "Appaloosa", "Mustang", "Pony", "Warmblood", "Draft"};
        breedComboBox = new JComboBox<>(breeds);
        breedComboBox.setPreferredSize(preferredSize);
        constraints.gridy = 3;
        this.add(breedComboBox, constraints);
    
        // coatColour ComboBox
        JLabel colourLabel = new JLabel("Coat Colour:");
        constraints.gridy = 4;
        this.add(colourLabel, constraints);
        String[] colours = {"Black", "White", "Brown", "Grey", "Chestnut", "Bay", "Palomino", "Buckskin", "Dun", "Roan"};
        coatColourComboBox = new JComboBox<>(colours);
        coatColourComboBox.setPreferredSize(preferredSize);
        constraints.gridy = 5;
        this.add(coatColourComboBox, constraints);
    
        // Equipment ComboBox
        JLabel equipmentLabel = new JLabel("Equipment:");
        constraints.gridy = 6;
        this.add(equipmentLabel, constraints);
        String[] equipments = {"Shoes", "Saddle", "Reins"};
        equipmentComboBox = new JComboBox<>(equipments);
        equipmentComboBox.setPreferredSize(preferredSize);
        constraints.gridy = 7;
        this.add(equipmentComboBox, constraints);
    
        // Accessories ComboBox
        JLabel accessoriesLabel = new JLabel("Accessories:");
        constraints.gridy = 8;
        this.add(accessoriesLabel, constraints);
        String[] accessories = {"boldSymbol", "italicSymbol", "underlineSymbol"};
        accessoriesComboBox = new JComboBox<>(accessories);
        accessoriesComboBox.setPreferredSize(preferredSize);
        constraints.gridy = 9;
        this.add(accessoriesComboBox, constraints);
        
        JButton addHorseButton = new JButton("Add Horse");
        constraints.gridy = 11;
        this.add(addHorseButton, constraints);

        addHorseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(horses.size() < horseListLength){
                    NewHorse horse = new NewHorse(
                        (char) SymbolComboBox.getSelectedItem(),
                        "Horse" + (horses.size() + 1),
                        0.1
                    );
                    horses.add(horse);
                    JOptionPane.showMessageDialog(null, "Horse added. Total horses: " + horses.size());
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot add more than " + horses.size() + " horses.");
                }
            }
        });
        
        // Add a "Save" button to the dialog
        JButton saveButton = new JButton("Save");
        constraints.gridy = 12;
        this.add(saveButton, constraints);

        // Add an ActionListener to the "Save" button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save the values from the sliders and color chooser
                breed = (String) breedComboBox.getSelectedItem();
                coatColour = (String) coatColourComboBox.getSelectedItem();
                equipment = (String) equipmentComboBox.getSelectedItem();

                // Close the customisation dialog
                dispose();
            }
        });

        // Add a WindowListener to handle the window closing event
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Terminate the program
                System.exit(0);
            }
        });
        
        // Set the size of the dialog 
        this.pack();
        this.setVisible(true);
    }

    public List<NewHorse> getHorses(){
        return horses;
    }

    public String getBreed(){
        return breed;
    }

    public String getCoatColour(){
        return coatColour;
    }

    public String getEquipment(){
        return equipment;
    }
}
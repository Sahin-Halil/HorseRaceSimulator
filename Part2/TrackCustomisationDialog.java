import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackCustomisationDialog extends JDialog {
    private JSlider numLanesSlider;
    private JSlider trackLengthSlider;
    private JColorChooser trackColourChooser;
    private int raceLength;
    private NewHorse[] horseList;
    private Color trackColour;

    public TrackCustomisationDialog(JFrame parentFrame) {
        super(parentFrame, "Track Customisation", true);
        initialiseComponents();
    }

    private void initialiseComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Dimension preferredSize = new Dimension(700, 225);

        // Add slider for the number of tracks
        constraints.gridy = 0;
        this.add(new JLabel("Number of Lanes:"), constraints);
        numLanesSlider = new JSlider(JSlider.HORIZONTAL, 2, 10, 5);
        numLanesSlider.setMajorTickSpacing(1);
        numLanesSlider.setPaintTicks(true);
        numLanesSlider.setPaintLabels(true);
        numLanesSlider.setPreferredSize(preferredSize);
        constraints.gridy = 1;
        this.add(numLanesSlider, constraints);

        // Add slider for the track length
        constraints.gridy = 2;
        this.add(new JLabel("Track Length:"), constraints);
        trackLengthSlider = new JSlider(JSlider.HORIZONTAL, 10, 40, 25);
        trackLengthSlider.setMajorTickSpacing(10);
        trackLengthSlider.setPaintTicks(true);
        trackLengthSlider.setPaintLabels(true);
        trackLengthSlider.setPreferredSize(preferredSize);
        constraints.gridy = 3;
        this.add(trackLengthSlider, constraints);

        // Add color chooser for the track color
        constraints.gridy = 4;
        this.add(new JLabel("Track Colour:"), constraints);
        trackColourChooser = new JColorChooser();
        trackColourChooser.setPreferredSize(preferredSize);
        constraints.gridy = 5;
        this.add(trackColourChooser, constraints);

        // Add a "Save" button to the dialog
        JButton saveButton = new JButton("Save");
        constraints.gridy = 6;
        this.add(saveButton, constraints);

        // Add an ActionListener to the "Save" button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save the values from the sliders and color chooser
                raceLength = trackLengthSlider.getValue();
                horseList = new NewHorse[numLanesSlider.getValue()];
                trackColour = trackColourChooser.getColor();

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
    }

    public int getRaceLength() {
        return raceLength;
    }

    public NewHorse[] getHorseList() {
        return horseList;
    }

    public Color getTrackColour() {
        return trackColour;
    }
}

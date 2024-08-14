import javax.swing.*;
import java.awt.*;

public class Settings extends AbstractScreen {

    public Settings(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Configurations", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.NORTH);

        JPanel configurationPanel = getjPanel();
        mainPanel.add(configurationPanel, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Draco Zhang");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel getjPanel() {
        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridLayout(1, 3, 10, 10));

        // Column 1: Labels
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(7, 1, 10, 10));

        labelsPanel.add(new JLabel("Field Width (No of cells):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Field Height (No of cells):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Game Level:", JLabel.CENTER));
        labelsPanel.add(new JLabel("Music (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Sound Effect (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("AI Play (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Extend Mode (On/Off):", JLabel.CENTER));


        // Column 2: Sliders and Checkboxes
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(7, 1, 10, 10));
        JSlider fieldWidthSlider = new JSlider(5, 15, 10);
        JSlider fieldHeightSlider = new JSlider(15, 30, 20);
        JSlider gameLevelSlider = new JSlider(1, 10, 4);
        JCheckBox musicCheckBox = new JCheckBox("",true);
        JCheckBox soundEffectCheckBox = new JCheckBox("",true);
        JCheckBox aiPlayCheckBox = new JCheckBox();
        JCheckBox extendModeCheckBox = new JCheckBox();
        configureSlider(fieldWidthSlider,fieldHeightSlider,gameLevelSlider);
        addComponentsToPanel(controlsPanel,fieldWidthSlider,fieldHeightSlider,gameLevelSlider,musicCheckBox,soundEffectCheckBox,aiPlayCheckBox,extendModeCheckBox);

        // Column 3: Values
        JPanel valuesPanel = new JPanel();
        valuesPanel.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel fieldWidthValue = new JLabel(getStringFromComponent(fieldWidthSlider));
        JLabel fieldHeightValue = new JLabel(getStringFromComponent(fieldHeightSlider));
        JLabel gameLevelValue = new JLabel(getStringFromComponent(gameLevelSlider));
        JLabel musicValue = new JLabel(getStringFromComponent(musicCheckBox));
        JLabel soundEffectValue = new JLabel(getStringFromComponent(soundEffectCheckBox));
        JLabel aiPlayValue = new JLabel(getStringFromComponent(aiPlayCheckBox));
        JLabel extendModeValue = new JLabel(getStringFromComponent(extendModeCheckBox));
        addListenerEvent(fieldWidthValue, fieldWidthSlider);
        addListenerEvent(fieldHeightValue, fieldHeightSlider);
        addListenerEvent(gameLevelValue, gameLevelSlider);
        addListenerEvent(musicValue, musicCheckBox);
        addListenerEvent(soundEffectValue, soundEffectCheckBox);
        addListenerEvent(aiPlayValue, aiPlayCheckBox);
        addListenerEvent(extendModeValue, extendModeCheckBox);
        addComponentsToPanel(valuesPanel,fieldWidthValue,fieldHeightValue,gameLevelValue,musicValue,soundEffectValue,aiPlayValue,extendModeValue);


        // Add all columns to the main panel
        configurationPanel.add(labelsPanel);
        configurationPanel.add(controlsPanel);
        configurationPanel.add(valuesPanel);

        return configurationPanel;
    }

    // Private static method to display tick labels on JSlider
    private static void configureSlider(JSlider... sliders){
        for (JSlider slider : sliders){
            slider.setMajorTickSpacing(1);

            //  Enable tick marks and labels
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
        }
    }

    // Private static method to add JComponents to a JPanel
    private static void addComponentsToPanel(JPanel panel, JComponent... components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

    // Private static method to add a ChangeListener to a JComponent and update a JLabel
    private static void addListenerEvent(JLabel jLabel, JComponent jComponent) {
        if (jComponent instanceof JSlider jSlider) {
            jSlider.addChangeListener(e -> jLabel.setText(getStringFromComponent(jSlider)));
        } else if (jComponent instanceof JCheckBox jCheckBox) {
            jCheckBox.addItemListener(e -> jLabel.setText(getStringFromComponent(jCheckBox)));
        }
    }



    // Private static method to get the string representation of a component's value
    private static String getStringFromComponent(JComponent component) {
        if (component instanceof JSlider slider) {
            return String.valueOf(slider.getValue());
        } else if (component instanceof JCheckBox checkBox) {
            return checkBox.isSelected() ? "On" : "Off";
        }
        return "";
    }
}

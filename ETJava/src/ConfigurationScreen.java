import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends AbstractScreen {

    private Configurations configurations;

    public ConfigurationScreen(GameController gameController, Configurations configurations) {
        super(gameController);
        this.configurations = configurations;

        mainPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Configurations", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.NORTH);

        JPanel configurationPanel = getConfigurationPanel();
        mainPanel.add(configurationPanel, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Draco Zhang");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel getConfigurationPanel() {
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

        JSlider fieldWidthSlider = new JSlider(5, 15, configurations.getFieldWidth());
        JSlider fieldHeightSlider = new JSlider(15, 30, configurations.getFieldHeight());
        JSlider gameLevelSlider = new JSlider(1, 10, configurations.getGameLevel());
        JCheckBox musicCheckBox = new JCheckBox("", configurations.isMusicOn());
        JCheckBox soundEffectCheckBox = new JCheckBox("", configurations.isSoundEffectsOn());
        JCheckBox aiPlayCheckBox = new JCheckBox("", configurations.isAiPlayOn());
        JCheckBox extendModeCheckBox = new JCheckBox("", configurations.isExtendModeOn());

        configureSlider(fieldWidthSlider, fieldHeightSlider, gameLevelSlider);
        addComponentsToPanel(controlsPanel, fieldWidthSlider, fieldHeightSlider, gameLevelSlider, musicCheckBox, soundEffectCheckBox, aiPlayCheckBox, extendModeCheckBox);

        // Column 3: Values (not needed in this case, but for visual confirmation if desired)
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

        addComponentsToPanel(valuesPanel, fieldWidthValue, fieldHeightValue, gameLevelValue, musicValue, soundEffectValue, aiPlayValue, extendModeValue);

        configurationPanel.add(labelsPanel);
        configurationPanel.add(controlsPanel);
        configurationPanel.add(valuesPanel);

        return configurationPanel;
    }

    // Handle JSlider and JCheckBox changes by updating configurations
    private static void configureSlider(JSlider... sliders) {
        for (JSlider slider : sliders) {
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
        }
    }

    private static void addComponentsToPanel(JPanel panel, JComponent... components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

    private static void addListenerEvent(JLabel label, JComponent component) {
        if (component instanceof JSlider slider) {
            slider.addChangeListener(e -> label.setText(getStringFromComponent(slider)));
        } else if (component instanceof JCheckBox checkBox) {
            checkBox.addItemListener(e -> label.setText(getStringFromComponent(checkBox)));
        }
    }

    private static String getStringFromComponent(JComponent component) {
        if (component instanceof JSlider slider) {
            return String.valueOf(slider.getValue());
        } else if (component instanceof JCheckBox checkBox) {
            return checkBox.isSelected() ? "On" : "Off";
        }
        return "";
    }
}

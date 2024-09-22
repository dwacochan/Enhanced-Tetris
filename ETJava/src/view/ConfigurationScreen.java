package view;

import controller.GameController;
import model.Configurations;
import model.AbstractScreen;
import model.GameLoop;
import model.PlayerType;

import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends AbstractScreen {

    private Configurations configurations;
    private JSlider fieldWidthSlider, fieldHeightSlider, gameLevelSlider;
    private JCheckBox musicCheckBox, soundEffectCheckBox, aiPlayCheckBox, extendModeCheckBox;
    private JComboBox<PlayerType> player1TypeComboBox, player2TypeComboBox;
    private GameLoop gameLoop;


    public ConfigurationScreen(GameController gameController, Configurations configurations) {
        super(gameController);
        this.configurations = configurations;
        this.gameLoop = gameController.getGameLoop();

        mainPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Configurations", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.NORTH);

        JPanel configurationPanel = getConfigurationPanel();
        mainPanel.add(configurationPanel, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Draco Zhang", e -> {
            saveConfigurations();
            gameController.showMainMenu();
        });
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel getConfigurationPanel() {
        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridLayout(1, 3, 10, 10));

        // Column 1: Labels
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(9, 1, 10, 10));

        labelsPanel.add(new JLabel("Field Width (No of cells):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Field Height (No of cells):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Game Level:", JLabel.CENTER));
        labelsPanel.add(new JLabel("Music (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Sound Effect (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("AI Play (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Extend Mode (On/Off):", JLabel.CENTER));
        labelsPanel.add(new JLabel("Player 1 Type:", JLabel.CENTER));
        labelsPanel.add(new JLabel("Player 2 Type:", JLabel.CENTER));

        // Column 2: Sliders and Checkboxes
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(9, 1, 10, 10));

        fieldWidthSlider = new JSlider(5, 15, configurations.getFieldWidth());
        fieldHeightSlider = new JSlider(15, 30, configurations.getFieldHeight());
        gameLevelSlider = new JSlider(1, 10, configurations.getGameLevel());
        musicCheckBox = new JCheckBox("", configurations.isMusicOn());
        soundEffectCheckBox = new JCheckBox("", configurations.isSoundEffectsOn());
        aiPlayCheckBox = new JCheckBox("", configurations.isAiPlayOn());
        extendModeCheckBox = new JCheckBox("", configurations.isExtendModeOn());

        player1TypeComboBox = new JComboBox<>(PlayerType.values());
        player1TypeComboBox.setSelectedItem(configurations.getPlayer1Type());

        player2TypeComboBox = new JComboBox<>(PlayerType.values());
        player2TypeComboBox.setSelectedItem(configurations.getPlayer2Type());
        player2TypeComboBox.setEnabled(configurations.isExtendModeOn()); // Disable player 2 combo box by default

        configureSlider(fieldWidthSlider, fieldHeightSlider, gameLevelSlider);
        addComponentsToPanel(controlsPanel, fieldWidthSlider, fieldHeightSlider, gameLevelSlider, musicCheckBox, soundEffectCheckBox, aiPlayCheckBox, extendModeCheckBox, player1TypeComboBox, player2TypeComboBox);

        // Column 3: Values (for visual confirmation)
        JPanel valuesPanel = new JPanel();
        valuesPanel.setLayout(new GridLayout(9, 1, 10, 10));

        JLabel fieldWidthValue = new JLabel(getStringFromComponent(fieldWidthSlider));
        JLabel fieldHeightValue = new JLabel(getStringFromComponent(fieldHeightSlider));
        JLabel gameLevelValue = new JLabel(getStringFromComponent(gameLevelSlider));
        JLabel musicValue = new JLabel(getStringFromComponent(musicCheckBox));
        JLabel soundEffectValue = new JLabel(getStringFromComponent(soundEffectCheckBox));
        JLabel aiPlayValue = new JLabel(getStringFromComponent(aiPlayCheckBox));
        JLabel extendModeValue = new JLabel(getStringFromComponent(extendModeCheckBox));
        JLabel player1TypeValue = new JLabel(player1TypeComboBox.getSelectedItem().toString());
        JLabel player2TypeValue = new JLabel(player2TypeComboBox.getSelectedItem().toString());

        addListenerEvent(fieldWidthValue, fieldWidthSlider);
        addListenerEvent(fieldHeightValue, fieldHeightSlider);
        addListenerEvent(gameLevelValue, gameLevelSlider);
        addListenerEvent(musicValue, musicCheckBox);
        addListenerEvent(soundEffectValue, soundEffectCheckBox);
        addListenerEvent(aiPlayValue, aiPlayCheckBox);
        addListenerEvent(extendModeValue, extendModeCheckBox);
        addListenerEvent(player1TypeValue, player1TypeComboBox);
        addListenerEvent(player2TypeValue, player2TypeComboBox);

        addComponentsToPanel(valuesPanel, fieldWidthValue, fieldHeightValue, gameLevelValue, musicValue, soundEffectValue, aiPlayValue, extendModeValue, player1TypeValue, player2TypeValue);

        extendModeCheckBox.addItemListener(e -> player2TypeComboBox.setEnabled(extendModeCheckBox.isSelected()));

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
        } else if (component instanceof JComboBox comboBox) {
            comboBox.addItemListener(e -> label.setText(comboBox.getSelectedItem().toString()));
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

    // Save the current configuration to JSON
    private void saveConfigurations() {
        configurations.setFieldWidth(fieldWidthSlider.getValue());
        configurations.setFieldHeight(fieldHeightSlider.getValue());
        configurations.setGameLevel(gameLevelSlider.getValue());
        configurations.setMusicOn(musicCheckBox.isSelected());
        configurations.setSoundEffectsOn(soundEffectCheckBox.isSelected());
        configurations.setAiPlayOn(aiPlayCheckBox.isSelected());
        configurations.setExtendModeOn(extendModeCheckBox.isSelected());
        configurations.setPlayer1Type((PlayerType) player1TypeComboBox.getSelectedItem());
        if (extendModeCheckBox.isSelected()) {
            configurations.setPlayer2Type((PlayerType) player2TypeComboBox.getSelectedItem());
        }

        // Save to JSON
        configurations.saveToFile();
    }
}

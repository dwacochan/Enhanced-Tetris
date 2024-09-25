package view;

import controller.GameController;
import model.Configurations;
import model.PlayerType;
import util.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ConfigurationScreen extends AbstractScreen {

    private final Configurations configurations;
    private JSlider fieldWidthSlider, fieldHeightSlider, gameLevelSlider;
    private JCheckBox musicCheckBox, soundEffectCheckBox, extendModeCheckBox;
    private JComboBox<PlayerType> player1TypeComboBox, player2TypeComboBox;

    public ConfigurationScreen(GameController gameController, Configurations configurations) {
        super(gameController);
        this.configurations = configurations;

        mainPanel.setLayout(new BorderLayout());

        OutlinedLabel label = new OutlinedLabel("Configurations", JLabel.CENTER, Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.NORTH);

        JPanel configurationPanel = getConfigurationPanel();
        mainPanel.add(configurationPanel, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Draco Zhang", e -> {
            saveConfigurations();
            AudioManager.getInstance().pauseMusic();
            AudioManager.getInstance().playMusic("/resources/mainmenu.wav"); // Play main menu music
            gameController.showMainMenu();
        });

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel getConfigurationPanel() {
        JPanel configurationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        configurationPanel.setOpaque(true);
        configurationPanel.setBackground(new Color(0, 0, 0, 150));

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(9, 1, 10, 10));
        labelsPanel.setOpaque(false);
        labelsPanel.setBackground(new Color(0, 0, 0, 150));

        labelsPanel.add(createOutlinedLabel("Field Width (No of cells):"));
        labelsPanel.add(createOutlinedLabel("Field Height (No of cells):"));
        labelsPanel.add(createOutlinedLabel("Game Level:"));
        labelsPanel.add(createOutlinedLabel("Music (On/Off):"));
        labelsPanel.add(createOutlinedLabel("Sound Effect (On/Off):"));
        labelsPanel.add(createOutlinedLabel("Extend Mode (On/Off):"));
        labelsPanel.add(createOutlinedLabel("Player 1 Type:"));
        labelsPanel.add(createOutlinedLabel("Player 2 Type:"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        configurationPanel.add(labelsPanel, gbc);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(9, 1, 10, 10));
        controlsPanel.setOpaque(false);
        controlsPanel.setBackground(new Color(0, 0, 0, 150));

        fieldWidthSlider = new JSlider(5, 15, configurations.getFieldWidth());
        fieldHeightSlider = new JSlider(15, 30, configurations.getFieldHeight());
        gameLevelSlider = new JSlider(1, 10, configurations.getGameLevel());
        musicCheckBox = new JCheckBox("", configurations.isMusicOn());
        soundEffectCheckBox = new JCheckBox("", configurations.isSoundEffectsOn());
        extendModeCheckBox = new JCheckBox("", configurations.isExtendModeOn());

        player1TypeComboBox = new JComboBox<>(PlayerType.values());
        player1TypeComboBox.setSelectedItem(configurations.getPlayer1Type());

        player2TypeComboBox = new JComboBox<>(PlayerType.values());
        player2TypeComboBox.setSelectedItem(configurations.getPlayer2Type());
        player2TypeComboBox.setEnabled(configurations.isExtendModeOn());

        configureSlider(fieldWidthSlider, fieldHeightSlider, gameLevelSlider);
        addComponentsToPanel(controlsPanel, fieldWidthSlider, fieldHeightSlider, gameLevelSlider, musicCheckBox, soundEffectCheckBox, extendModeCheckBox, player1TypeComboBox, player2TypeComboBox);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        gbc.fill = GridBagConstraints.BOTH;
        configurationPanel.add(controlsPanel, gbc);


        JPanel valuesPanel = new JPanel();
        valuesPanel.setLayout(new GridLayout(9, 1, 10, 10));
        valuesPanel.setOpaque(false);
        valuesPanel.setBackground(new Color(0, 0, 0, 150));

        valuesPanel.add(createOutlinedLabel(fieldWidthSlider));
        valuesPanel.add(createOutlinedLabel(fieldHeightSlider));
        valuesPanel.add(createOutlinedLabel(gameLevelSlider));
        valuesPanel.add(createOutlinedLabel(musicCheckBox));
        valuesPanel.add(createOutlinedLabel(soundEffectCheckBox));
        valuesPanel.add(createOutlinedLabel(extendModeCheckBox));
        valuesPanel.add(createOutlinedLabel(player1TypeComboBox));
        valuesPanel.add(createOutlinedLabel(player2TypeComboBox));

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        configurationPanel.add(valuesPanel, gbc);

        return configurationPanel;
    }


    // Overloaded methods to create an OutlinedLabel for both static and dynamic text

    // Static text
    private OutlinedLabel createOutlinedLabel(String text) {
        OutlinedLabel label = new OutlinedLabel(text, JLabel.CENTER, Color.BLACK);
        label.setFont(new Font("Courier New", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    // Dynamic values (e.g., sliders, checkboxes, combo boxes)
    private OutlinedLabel createOutlinedLabel(JComponent component) {
        String valueText = getStringFromComponent(component);
        OutlinedLabel label = new OutlinedLabel(valueText, JLabel.CENTER, Color.BLACK);
        label.setFont(new Font("Courier New", Font.BOLD, 16));
        label.setForeground(Color.WHITE);

        // Add listeners for dynamic updates
        if (component instanceof JSlider slider) {
            slider.addChangeListener(e -> {
                label.setText(getStringFromComponent(slider));
                reloadScreen(); // Reload the screen when slider is changed
            });
        } else if (component instanceof JCheckBox checkBox) {
            checkBox.addItemListener(e -> {
                label.setText(getStringFromComponent(checkBox));
                reloadScreen(); // Reload the screen when checkbox is changed
            });
        } else if (component instanceof JComboBox<?> comboBox) {
            comboBox.addItemListener(e -> {
                label.setText(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
                reloadScreen(); // Reload the screen when combobox is changed
            });
        }
        return label;
    }


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

    private static String getStringFromComponent(JComponent component) {
        if (component instanceof JSlider slider) {
            return String.valueOf(slider.getValue());
        } else if (component instanceof JCheckBox checkBox) {
            return checkBox.isSelected() ? "On" : "Off";
        }
        return "";
    }

    private void saveConfigurations() {
        configurations.setFieldWidth(fieldWidthSlider.getValue());
        configurations.setFieldHeight(fieldHeightSlider.getValue());
        configurations.setGameLevel(gameLevelSlider.getValue());
        configurations.setMusicOn(musicCheckBox.isSelected());
        configurations.setSoundEffectsOn(soundEffectCheckBox.isSelected());
        configurations.setExtendModeOn(extendModeCheckBox.isSelected());
        configurations.setPlayer1Type((PlayerType) player1TypeComboBox.getSelectedItem());
        if (extendModeCheckBox.isSelected()) {
            configurations.setPlayer2Type((PlayerType) player2TypeComboBox.getSelectedItem());
        }

        configurations.saveToFile();
    }

    private void reloadScreen() {
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}

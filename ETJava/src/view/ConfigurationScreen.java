package view;

import controller.GameController;
import model.Configurations;
import model.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import javax.swing.plaf.basic.BasicSliderUI;

public class ConfigurationScreen extends AbstractScreen {

    private final Configurations configurations;
    private JSlider fieldWidthSlider, fieldHeightSlider, gameLevelSlider;
    private JCheckBox musicCheckBox, soundEffectCheckBox, extendModeCheckBox;
    private JComboBox<PlayerType> player1TypeComboBox, player2TypeComboBox;

    public ConfigurationScreen(GameController gameController, Configurations configurations) {
        super(gameController, "/resources/SettingsScreen.jpg");
        this.configurations = configurations;

        mainPanel.setLayout(new BorderLayout());

        // Centering the title label
        OutlinedLabel label = new OutlinedLabel("Configurations", JLabel.CENTER, Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(label);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel configurationPanel = getConfigurationPanel();
        mainPanel.add(configurationPanel, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Draco Zhang", e -> {
            saveConfigurations();
            gameController.showMainMenu();
        });
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel getConfigurationPanel() {
        JPanel configurationPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        configurationPanel.setOpaque(false);

        // Increased vertical gaps from 10 to 20
        JPanel labelsPanel = new JPanel(new GridLayout(8, 1, 10, 20));
        labelsPanel.setOpaque(false);
        labelsPanel.add(createStyledLabel("Field Width:"));
        labelsPanel.add(createStyledLabel("Field Height:"));
        labelsPanel.add(createStyledLabel("Game Level:"));
        labelsPanel.add(createStyledLabel("Music (On/Off):"));
        labelsPanel.add(createStyledLabel("Sound Effect (On/Off):"));
        labelsPanel.add(createStyledLabel("Extend Mode (On/Off):"));
        labelsPanel.add(createStyledLabel("Player 1 Type:"));
        labelsPanel.add(createStyledLabel("Player 2 Type:"));

        // Increased vertical gaps from 10 to 20
        JPanel controlsPanel = new JPanel(new GridLayout(8, 1, 10, 20));
        controlsPanel.setOpaque(false);

        fieldWidthSlider = styleSlider(new JSlider(5, 15, configurations.getFieldWidth()));
        fieldHeightSlider = styleSlider(new JSlider(15, 30, configurations.getFieldHeight()));
        gameLevelSlider = styleSlider(new JSlider(1, 10, configurations.getGameLevel()));

        musicCheckBox = styleCheckBox(new JCheckBox("", configurations.isMusicOn()));
        soundEffectCheckBox = styleCheckBox(new JCheckBox("", configurations.isSoundEffectsOn()));
        extendModeCheckBox = styleCheckBox(new JCheckBox("", configurations.isExtendModeOn()));
        player1TypeComboBox = styleComboBox(new JComboBox<>(PlayerType.values()));
        player1TypeComboBox.setSelectedItem(configurations.getPlayer1Type());

        player2TypeComboBox = styleComboBox(new JComboBox<>(PlayerType.values()));
        player2TypeComboBox.setSelectedItem(configurations.getPlayer2Type());
        player2TypeComboBox.setEnabled(configurations.isExtendModeOn());
        extendModeCheckBox.addItemListener(e -> player2TypeComboBox.setEnabled(extendModeCheckBox.isSelected()));

        addComponentsToPanel(controlsPanel, fieldWidthSlider, fieldHeightSlider, gameLevelSlider, musicCheckBox,
                soundEffectCheckBox, extendModeCheckBox, player1TypeComboBox, player2TypeComboBox);

        // Increased vertical gaps from 10 to 20
        JPanel valuesPanel = new JPanel(new GridLayout(8, 1, 10, 20));
        valuesPanel.setOpaque(false);

        valuesPanel.add(createStyledValueLabel(fieldWidthSlider));
        valuesPanel.add(createStyledValueLabel(fieldHeightSlider));
        valuesPanel.add(createStyledValueLabel(gameLevelSlider));
        valuesPanel.add(createStyledValueLabel(musicCheckBox));
        valuesPanel.add(createStyledValueLabel(soundEffectCheckBox));
        valuesPanel.add(createStyledValueLabel(extendModeCheckBox));
        valuesPanel.add(createStyledValueLabel(player1TypeComboBox));
        valuesPanel.add(createStyledValueLabel(player2TypeComboBox));

        configurationPanel.add(labelsPanel);
        configurationPanel.add(controlsPanel);
        configurationPanel.add(valuesPanel);

        return configurationPanel;
    }

    // Method to style the slider
    private JSlider styleSlider(JSlider slider) {
        slider.setFont(new Font("Courier New", Font.BOLD, 12));
        slider.setMajorTickSpacing((slider.getMaximum() - slider.getMinimum()) / 5);
        slider.setPaintLabels(true);
        slider.setForeground(Color.BLACK);
        slider.setBackground(Color.WHITE);

        // Apply custom UI
        slider.setUI(new CustomSliderUI(slider));

        return slider;
    }

    // Custom UI class for the slider
    public static class CustomSliderUI extends BasicSliderUI {

        public CustomSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paintThumb(Graphics g) {
            if (thumbRect == null) {
                return;
            }

            // Get the thumb bounds
            Rectangle knobBounds = thumbRect;
            int w = knobBounds.width;
            int h = knobBounds.height;

            // Draw the thumb
            Graphics2D g2d = (Graphics2D) g.create();

            // Enable anti-aliasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Translate to the position of thumbRect
            g2d.translate(knobBounds.x, knobBounds.y);

            // Set the thumb color (white)
            g2d.setColor(Color.WHITE);
            g2d.fillOval(0, 0, w, h);

            // Draw the outline (black)
            g2d.setColor(Color.BLACK);
            g2d.drawOval(0, 0, w - 1, h - 1); // Subtract 1 to fit inside the rectangle

            g2d.dispose();
        }

        @Override
        public void paintTrack(Graphics g) {
            super.paintTrack(g);
            // You can also customize the track here if needed
        }
    }

    private JCheckBox styleCheckBox(JCheckBox checkBox) {
        checkBox.setFont(new Font("Courier New", Font.BOLD, 12));
        checkBox.setForeground(Color.DARK_GRAY);
        checkBox.setBackground(Color.LIGHT_GRAY);
        checkBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return checkBox;
    }

    private JComboBox<PlayerType> styleComboBox(JComboBox<PlayerType> comboBox) {
        comboBox.setFont(new Font("Courier New", Font.BOLD, 12));
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setBackground(Color.LIGHT_GRAY);
        return comboBox;
    }

    private OutlinedLabel createStyledLabel(String text) {
        OutlinedLabel label = new OutlinedLabel(text, JLabel.LEFT, Color.BLACK);
        label.setFont(new Font("Courier New", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return label;
    }

    private JLabel createStyledValueLabel(JComponent component) {
        String valueText = getStringFromComponent(component);
        OutlinedLabel label = new OutlinedLabel(valueText, JLabel.LEFT, Color.BLACK);
        label.setFont(new Font("Courier New", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        if (component instanceof JSlider slider) {
            slider.addChangeListener(e -> label.setText(getStringFromComponent(slider)));
        } else if (component instanceof JCheckBox checkBox) {
            checkBox.addItemListener(e -> label.setText(getStringFromComponent(checkBox)));
        } else if (component instanceof JComboBox<?> comboBox) {
            comboBox.addItemListener(e -> label.setText(Objects.requireNonNull(comboBox.getSelectedItem()).toString()));
        }
        return label;
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
        } else if (component instanceof JComboBox<?> comboBox) {
            return Objects.requireNonNull(comboBox.getSelectedItem()).toString();
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
}

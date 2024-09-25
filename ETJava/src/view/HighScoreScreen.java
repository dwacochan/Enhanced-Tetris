package view;

import controller.GameController;
import model.HighScores;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoreScreen extends AbstractScreen {

    private static final int MAX_SCORES = 10;

    public HighScoreScreen(GameController gameController, HighScores highScores) {
        super(gameController,"/resources/LeaderboardScreen.jpg");

        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JButton clearButton = getClearButton(gameController, highScores);
        topPanel.add(clearButton, BorderLayout.EAST);

        OutlinedLabel titleLabel = new OutlinedLabel("High Scores", JLabel.CENTER, Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel scoresPanel = getScoresPanel(highScores);
//        JScrollPane scrollPane = new JScrollPane(scoresPanel);
//        scrollPane.setOpaque(false);
//        scrollPane.getViewport().setOpaque(false);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scoresPanel, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Elijah De Calmer");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel getScoresPanel(HighScores highScores) {
        JPanel scoresPanel = new JPanel();
        scoresPanel.setOpaque(true);
        scoresPanel.setBackground(new Color(0, 0, 0, 150));
        scoresPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        OutlinedLabel rankHeader = createOutlinedLabel("#");
        scoresPanel.add(rankHeader, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        OutlinedLabel nameHeader = createOutlinedLabel("Name");
        scoresPanel.add(nameHeader, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1.0;
        OutlinedLabel scoreHeader = createOutlinedLabel("Score");
        scoresPanel.add(scoreHeader, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        OutlinedLabel configHeader = createOutlinedLabel("Config");
        scoresPanel.add(configHeader, gbc);

        List<HighScores.Score> scoreList = highScores.getScores();

        for (int i = 0; i < MAX_SCORES; i++) {
            gbc.gridy = i + 1;

            gbc.gridx = 0;
            String rank = String.valueOf(i + 1);
            OutlinedLabel rankLabel = createOutlinedLabel(rank, Color.CYAN);
            scoresPanel.add(rankLabel, gbc);

            gbc.gridx = 1;
            String name = i < scoreList.size() ? scoreList.get(i).name() : "---";
            OutlinedLabel nameLabel = createOutlinedLabel(name, Color.WHITE);
            scoresPanel.add(nameLabel, gbc);

            gbc.gridx = 2;
            String score = i < scoreList.size() ? String.valueOf(scoreList.get(i).score()) : "---";
            OutlinedLabel scoreLabel = createOutlinedLabel(score, Color.GREEN);
            scoresPanel.add(scoreLabel, gbc);

            gbc.gridx = 3;
            String config = i < scoreList.size() ? scoreList.get(i).config() : "---";
            OutlinedLabel configLabel = createOutlinedLabel(config, Color.ORANGE);
            scoresPanel.add(configLabel, gbc);
        }

        return scoresPanel;
    }

    private JPanel centerPanel(JComponent component) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(component);
        return panel;
    }

    private OutlinedLabel createOutlinedLabel(String text) {
        OutlinedLabel label = new OutlinedLabel(text, JLabel.CENTER, Color.BLACK, 1);
        label.setFont(new Font("Courier New", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        return label;
    }

    private OutlinedLabel createOutlinedLabel(String text, Color color) {
        OutlinedLabel label = new OutlinedLabel(text, JLabel.CENTER, Color.BLACK,1);
        label.setFont(new Font("Courier New", Font.BOLD, 20));
        label.setForeground(color);
        return label;
    }

    private JButton getClearButton(GameController gameController, HighScores highScores) {
        JButton clearButton = new JButton("Clear High Scores");
        clearButton.setFont(new Font("Courier New", Font.BOLD, 18));
        clearButton.setPreferredSize(new Dimension(200, 50));
        clearButton.setOpaque(true);
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.RED);
        clearButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        clearButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to clear all high scores?",
                    "Confirm Clear High Scores",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                highScores.clearHighScore();
                gameController.showHighScores();
            }
        });
        return clearButton;
    }
}

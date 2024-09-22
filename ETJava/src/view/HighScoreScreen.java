package view;

import controller.GameController;
import model.AbstractScreen;
import model.HighScores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HighScoreScreen extends AbstractScreen {

    private static final int MAX_SCORES = 10;

    public HighScoreScreen(GameController gameController, HighScores highScores) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton clearButton = getClearButton(gameController, highScores);


        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(clearButton, BorderLayout.EAST);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel scoresPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 30, 5, 30);

        List<HighScores.Score> scoreList = highScores.getScores();

        JLabel rankHeader = new JLabel("#", JLabel.CENTER);
        JLabel nameHeader = new JLabel("Name", JLabel.CENTER);
        JLabel scoreHeader = new JLabel("Score", JLabel.CENTER);
        JLabel configHeader = new JLabel("Config", JLabel.CENTER);

        rankHeader.setFont(new Font("Arial", Font.BOLD, 18));
        nameHeader.setFont(new Font("Arial", Font.BOLD, 18));
        scoreHeader.setFont(new Font("Arial", Font.BOLD, 18));
        configHeader.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        scoresPanel.add(rankHeader, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        scoresPanel.add(nameHeader, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        scoresPanel.add(scoreHeader, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        scoresPanel.add(configHeader, gbc);

        for (int i = 0; i < MAX_SCORES; i++) {
            String rank = String.valueOf(i + 1);
            String name = i < scoreList.size() ? scoreList.get(i).name() : "---";
            String score = i < scoreList.size() ? String.valueOf(scoreList.get(i).score()) : "---";
            String config = i < scoreList.size() ? String.valueOf(scoreList.get(i).config()) : "---";

            JLabel rankLabel = new JLabel(rank, JLabel.CENTER);
            JLabel nameLabel = new JLabel(name, JLabel.CENTER);
            JLabel scoreLabel = new JLabel(score, JLabel.CENTER);
            JLabel configLabel = new JLabel(config, JLabel.CENTER);

            gbc.gridy = i + 1;

            gbc.gridx = 0;
            scoresPanel.add(rankLabel, gbc);

            gbc.gridx = 1;
            scoresPanel.add(nameLabel, gbc);

            gbc.gridx = 2;
            scoresPanel.add(scoreLabel, gbc);

            gbc.gridx = 3;
            scoresPanel.add(configLabel, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(scoresPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Elijah De Calmer");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private static JButton getClearButton(GameController gameController, HighScores highScores) {
        JButton clearButton = new JButton("Clear Highscore");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to clear all high scores?",
                        "Confirm Clear High Scores",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    highScores.clearHighScore();
                    gameController.showHighScores(); // Refresh screen
                }
            }
        });
        return clearButton;
    }
}

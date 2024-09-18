import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoreScreen extends AbstractScreen {


    public HighScoreScreen(GameController gameController, HighScores highScores) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel scoresPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 30, 5, 30);

        List<HighScores.Score> scoreList = highScores.getScores();

        JLabel nameHeader = new JLabel("Name", JLabel.CENTER);
        JLabel scoreHeader = new JLabel("Score", JLabel.CENTER);
        nameHeader.setFont(new Font("Arial", Font.BOLD, 18));
        scoreHeader.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        scoresPanel.add(nameHeader, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        scoresPanel.add(scoreHeader, gbc);

        int i = 0;
        for (HighScores.Score score : scoreList) {
            JLabel nameLabel = new JLabel(score.name(), JLabel.CENTER);
            JLabel scoreLabel = new JLabel(String.valueOf(score.score()), JLabel.CENTER);

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            scoresPanel.add(nameLabel, gbc);

            gbc.gridx = 1;
            scoresPanel.add(scoreLabel, gbc);

            i++;
        }

        JScrollPane scrollPane = new JScrollPane(scoresPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Elijah De Calmer");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}

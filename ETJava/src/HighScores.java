import javax.swing.*;
import java.awt.*;

public class HighScores extends AbstractScreen {

    public HighScores(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel scoresPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 30, 5, 30); // Padding between rows

        String[] names = {
                "John", "Emily", "Michael", "Sophia", "Daniel",
                "Olivia", "James", "Isabella", "David", "Mia",
                "Benjamin", "Charlotte", "Matthew", "Amelia", "Joseph",
                "Evelyn", "Henry", "Abigail", "Samuel", "Harper",
                "William", "Ava", "Alexander", "Lily", "Elijah",
                "Grace", "Lucas", "Chloe", "Jackson", "Ella"
        };

        int[] scores = {
                2000, 1950, 1900, 1850, 1800,
                1750, 1700, 1650, 1600, 1550,
                1500, 1450, 1400, 1350, 1300,
                1250, 1200, 1150, 1100, 1050,
                1000, 950, 900, 850, 800,
                750, 700, 650, 600, 550
        };

        JLabel nameHeader = new JLabel("Name", JLabel.CENTER);
        JLabel scoreHeader = new JLabel("Score", JLabel.CENTER);
        nameHeader.setFont(new Font("Arial", Font.BOLD, 18));
        scoreHeader.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        scoresPanel.add(nameHeader, gbc);

        gbc.gridx = 1;
        scoresPanel.add(scoreHeader, gbc);

        for (int i = 0; i < names.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1; // Start after the header
            JLabel nameLabel = new JLabel(names[i], JLabel.CENTER);
            scoresPanel.add(nameLabel, gbc);

            gbc.gridx = 1;
            JLabel scoreLabel = new JLabel(String.valueOf(scores[i]), JLabel.CENTER);
            scoresPanel.add(scoreLabel, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(scoresPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Elijah De Calmer");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}
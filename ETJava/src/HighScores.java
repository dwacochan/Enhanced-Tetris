import java.util.ArrayList;
import java.util.List;

public class HighScores {
    private List<Score> scores;
    

    public HighScores() {
        scores = new ArrayList<>();
        loadScores();
    }

    public void addScore(int score, String name) {
        scores.add(new Score(score, name));
    }

    public List<Score> getScores() {
        scores.sort((s1, s2) -> Integer.compare(s2.score(), s1.score()));
        return scores;
    }


    public record Score(int score, String name) {
    }


    private void loadScores() {
        scores.add(new Score(2000, "John"));
        scores.add(new Score(1950, "Emily"));
        scores.add(new Score(1900, "Michael"));
        scores.add(new Score(1850, "Sophia"));
        scores.add(new Score(1800, "Daniel"));
        scores.add(new Score(1750, "Olivia"));
        scores.add(new Score(1700, "James"));
        scores.add(new Score(1650, "Isabella"));
        scores.add(new Score(1600, "David"));
        scores.add(new Score(1550, "Mia"));
    }
}





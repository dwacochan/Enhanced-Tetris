import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HighScores {
    private List<Score> scores;
    private static final String HIGH_SCORES_FILE_PATH = "highscores.json";

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


    // Save high scores to JSON
    public void saveToFile() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(HIGH_SCORES_FILE_PATH)) {
            System.out.println("Highscore Saved");
            gson.toJson(scores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load high scores from JSON
    public void loadFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(HIGH_SCORES_FILE_PATH)) {
            Type scoreListType = new TypeToken<List<Score>>() {}.getType();
            System.out.println("Highscore Read");
            scores = gson.fromJson(reader, scoreListType);
        } catch (IOException e) {
            e.printStackTrace();
            loadScores(); // Load default scores if there's an issue
        }
    }
}





package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighScores {
    private List<Score> scores;
    private static final String HIGH_SCORES_FILE_PATH = "highscores.json";
    private static final Logger logger = Logger.getLogger(HighScores.class.getName());

    public HighScores() {
        this.scores = new ArrayList<>();
    }

    // Add a score to the list
    public void addScore(int score, String name, String config) {
        scores.add(new Score(score, name, config));
    }

    // Return the list of scores sorted in descending order
    public List<Score> getScores() {
        scores.sort(null);
        return scores;
    }

    // Record class to hold score data and implement Comparable interface
    public record Score(int score, String name, String config) implements Comparable<Score> {
        @Override
        public int compareTo(Score other) {
            // Sorting in descending order of score
            return Integer.compare(other.score, this.score);
        }
    }

    // Save high scores to JSON
    public void saveToFile() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(HIGH_SCORES_FILE_PATH)) {
            gson.toJson(scores, writer);
            logger.info("Highscore saved successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save high scores.", e);
        }
    }

    // Load high scores from JSON
    public void loadFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(HIGH_SCORES_FILE_PATH)) {
            Type scoreListType = new TypeToken<List<Score>>() {}.getType();
            scores = gson.fromJson(reader, scoreListType);
            logger.info("Highscore loaded successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load high scores.", e);
            scores = new ArrayList<>();
        }
    }

    // Check if a score qualifies for the top 10
    public boolean isTopTen(int score) {
        scores.sort(null);
        if (scores.size() < 10) {
            return true;
        }
        return score > scores.get(9).score();
    }

    // Clear the high scores list and save to file
    public void clearHighScore() {
        scores.clear();
        saveToFile();
        logger.info("Highscore list cleared.");
    }
}

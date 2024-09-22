package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HighScores {
    private List<Score> scores = new ArrayList<Score>();
    private static final String HIGH_SCORES_FILE_PATH = "highscores.json";

    public HighScores() {
        this.scores = new ArrayList<Score>();
    }

    // Add a score to the list
    public void addScore(int score, String name, String config) {
        scores.add(new Score(score, name, config));
    }

    // Return the list of scores sorted in descending order
    public List<Score> getScores() {
        return scores;
    }

    // Record class to hold score data
    public record Score(int score, String name, String config) {}

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
            e.printStackTrace(); // Load default scores if there's an issue
        }
    }

    // Check if a score qualifies for the top 10
    public boolean isTopTen(int score) {
        scores.sort((s1, s2) -> Integer.compare(s2.score(), s1.score()));
        if (scores.size() < 10) {
            return true;
        }
        return score > scores.get(9).score();
    }

    // Clear the high scores list and save to file
    public void clearHighScore() {
        scores.clear();
        saveToFile();
    }
}

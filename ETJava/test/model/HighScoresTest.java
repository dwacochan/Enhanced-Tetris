package model;
import model.HighScores.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HighScoresTest {

    private HighScores highScores;
    private static final String TEST_HIGH_SCORES_FILE = "test_highscores.json";

    @BeforeEach
    public void setUp() {
        highScores = new HighScores();
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test file
        File file = new File(TEST_HIGH_SCORES_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddScore() {
        highScores.addScore(100, "Player1", "Config1");
        List<Score> scores = highScores.getScores();
        assertEquals(1, scores.size(), "Score list should have one entry");
        assertEquals(100, scores.getFirst().score(), "Score value should match the added score");
        assertEquals("Player1", scores.getFirst().name(), "Name should match the added score");
    }

    @Test
    public void testIsTopTen() {
        for (int i = 0; i < 10; i++) {
            highScores.addScore(100 + i, "Player" + i, "Config" + i);
        }
        assertTrue(highScores.isTopTen(110), "110 should be in the top 10");
        assertFalse(highScores.isTopTen(90), "90 should not be in the top 10");

        // Test when there are less than 10 scores
        highScores.clearHighScore();
        highScores.addScore(100, "Player1", "Config1");
        assertTrue(highScores.isTopTen(50), "Any score should be in top 10 if less than 10 scores exist");
    }

    @Test
    public void testSaveToFileAndLoadFromFile() {
        highScores.addScore(150, "Player1", "Config1");
        highScores.addScore(200, "Player2", "Config2");
        highScores.saveToFile();

        HighScores loadedHighScores = new HighScores();
        loadedHighScores.loadFromFile();

        List<Score> loadedScores = loadedHighScores.getScores();
        assertEquals(2, loadedScores.size(), "Loaded scores should have two entries");

        assertEquals(150, loadedScores.get(0).score(), "First score should be 150");
        assertEquals("Player1", loadedScores.get(0).name(), "First player should be Player1");

        assertEquals(200, loadedScores.get(1).score(), "Second score should be 200");
        assertEquals("Player2", loadedScores.get(1).name(), "Second player should be Player2");
    }

    @Test
    public void testClearHighScore() {
        highScores.addScore(150, "Player1", "Config1");
        highScores.addScore(200, "Player2", "Config2");
        highScores.clearHighScore();

        assertEquals(0, highScores.getScores().size(), "Score list should be empty after clearing");
    }
}

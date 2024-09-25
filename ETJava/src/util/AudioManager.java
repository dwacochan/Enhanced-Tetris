package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance; // Singleton instance
    private Clip clip;
    private String currentFilePath;
    private boolean isPaused = false; // Track if the clip is paused

    // Map to store the positions of different audio files
    private Map<String, Long> clipPositions = new HashMap<>();

    private AudioManager() {
        // Private constructor to prevent instantiation from outside
    }

    // Get the single instance of AudioManager
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playMusic(String filepath) {
        try {
            // If it's the same music and is paused, just resume
            if (clip != null && filepath.equals(currentFilePath) && isPaused) {
                resumeMusic(filepath); // Resume if paused
                return;
            }

            // If it's the same music that's already playing, do nothing
            if (clip != null && clip.isRunning() && filepath.equals(currentFilePath)) {
                return; // Music is already playing, so no need to restart
            }

            // Stop current music if a different track is playing
            if (clip != null) {
                clip.stop();
                clip.close();
            }

            // Load and play the new music
            URL soundURL = getClass().getResource(filepath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            currentFilePath = filepath;

            // Get the last saved position for this file if available
            Long savedPosition = clipPositions.getOrDefault(filepath, 0L);
            clip.setMicrosecondPosition(savedPosition);

            clip.loop(Clip.LOOP_CONTINUOUSLY); // Set loop
            clip.start(); // Start playing
            isPaused = false; // Reset paused state

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            long currentClipPosition = clip.getMicrosecondPosition(); // Save current position
            clipPositions.put(currentFilePath, currentClipPosition); // Save the position of the current file
            clip.stop();
            isPaused = true; // Mark as paused
        }
    }

    public void resumeMusic(String filepath) {
        if (clip != null && isPaused && filepath.equals(currentFilePath)) {
            Long savedPosition = clipPositions.getOrDefault(filepath, 0L);
            clip.setMicrosecondPosition(savedPosition); // Resume from the saved position
            clip.start();
            isPaused = false; // Reset paused state
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clipPositions.put(currentFilePath, 0L); // Reset the position for the current file
            currentFilePath = null; // Reset the file path
            isPaused = false; // Reset paused state
        }
    }

    public boolean isMusicPaused() {
        return isPaused;
    }

    // Clear all saved positions (if necessary)
    public void resetAllPositions() {
        clipPositions.clear();
    }
}

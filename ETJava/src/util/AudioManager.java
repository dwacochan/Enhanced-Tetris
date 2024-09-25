package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioManager {
    private static AudioManager instance; // Singleton instance
    private Clip clip;
    private long clipPosition = 0; // To resume the music from the last position
    private String currentFilePath;

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
            // Avoid restarting the music if it's already playing the same file
            if (clip != null && clip.isRunning() && filepath.equals(currentFilePath)) {
                return; // Music is already playing, so no need to restart
            }

            if (clip == null || !filepath.equals(currentFilePath)) {
                // If it's a new clip or a different file, load the new music
                if (clip != null && clip.isOpen()) {
                    clip.close();
                }
                URL soundURL = getClass().getResource(filepath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                currentFilePath = filepath;
            }

            // Resume from the last position
            clip.setMicrosecondPosition(clipPosition);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Set loop
            clip.start(); // Start playing

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition(); // Save current position
            clip.stop();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clipPosition = 0; // Reset position for the next play
            currentFilePath = null; // Reset the file path
        }
    }
}

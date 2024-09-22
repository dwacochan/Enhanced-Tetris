package model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Configurations {
    private int fieldWidth;
    private int fieldHeight;
    private int gameLevel;
    private boolean musicOn;
    private boolean soundEffectsOn;
    private boolean aiPlayOn;
    private boolean extendModeOn;

    private static final String CONFIG_FILE_PATH = "configurations.json";

    public Configurations() {
        // Default configurations
        this.fieldWidth = 10;
        this.fieldHeight = 20;
        this.gameLevel = 4;
        this.musicOn = true;
        this.soundEffectsOn = true;
        this.aiPlayOn = false;
        this.extendModeOn = false;
    }

    // Getters and setters
    public int getFieldWidth() { return fieldWidth; }
    public void setFieldWidth(int fieldWidth) { this.fieldWidth = fieldWidth; }

    public int getFieldHeight() { return fieldHeight; }
    public void setFieldHeight(int fieldHeight) { this.fieldHeight = fieldHeight; }

    public int getGameLevel() { return gameLevel; }
    public void setGameLevel(int gameLevel) { this.gameLevel = gameLevel; }

    public boolean isMusicOn() { return musicOn; }
    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }

    public boolean isSoundEffectsOn() { return soundEffectsOn; }
    public void setSoundEffectsOn(boolean soundEffectsOn) { this.soundEffectsOn = soundEffectsOn; }

    public boolean isAiPlayOn() { return aiPlayOn; }
    public void setAiPlayOn(boolean aiPlayOn) { this.aiPlayOn = aiPlayOn; }

    public boolean isExtendModeOn() { return extendModeOn; }
    public void setExtendModeOn(boolean extendModeOn) { this.extendModeOn = extendModeOn; }


    // Save configuration to JSON
    public void saveToFile() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(this, writer);
            System.out.println("Config Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load configuration from JSON
    public static Configurations loadFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            System.out.println("Config Read");
            return gson.fromJson(reader, Configurations.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Configurations(); // Return default if there's an issue
        }
    }

    @Override
    public String toString() {
        return "Configurations{" +
                "fieldWidth=" + fieldWidth +
                ", fieldHeight=" + fieldHeight +
                ", gameLevel=" + gameLevel +
                ", musicOn=" + musicOn +
                ", soundEffectsOn=" + soundEffectsOn +
                ", aiPlayOn=" + aiPlayOn +
                ", extendModeOn=" + extendModeOn +
                '}';
    }
}

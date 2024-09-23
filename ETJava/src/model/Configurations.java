package model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Configurations {
    private int fieldWidth;
    private int fieldHeight;
    private int gameLevel;
    private boolean musicOn;
    private boolean soundEffectsOn;
    private boolean extendModeOn;
    private PlayerType player1Type;
    private PlayerType player2Type;

    private String configFilePath;
    private static final Logger logger = Logger.getLogger(Configurations.class.getName());



    public Configurations() {
        this("configurations.json");  // Default path
    }

    public Configurations(String configFilePath) {
        this.configFilePath = configFilePath;
        this.fieldWidth = 10;
        this.fieldHeight = 20;
        this.gameLevel = 4;
        this.musicOn = true;
        this.soundEffectsOn = true;
        this.extendModeOn = false;
        this.player1Type = PlayerType.HUMAN;
        this.player2Type = PlayerType.HUMAN;
    }

    // Getters and setters
    public int getFieldWidth() { return fieldWidth; }
    public void setFieldWidth(int fieldWidth) { this.fieldWidth = fieldWidth; }

    public int getFieldHeight() { return fieldHeight; }
    public void setFieldHeight(int fieldHeight) { this.fieldHeight = fieldHeight; }

    public int getGameLevel() { return gameLevel; }
    public void setGameLevel(int gameLevel) { this.gameLevel = gameLevel; }

    public PlayerType getPlayer1Type() { return player1Type; }
    public void setPlayer1Type(PlayerType player1Type) { this.player1Type = player1Type; }

    public PlayerType getPlayer2Type() { return player2Type; }
    public void setPlayer2Type(PlayerType player2Type) { this.player2Type = player2Type; }

    public boolean isMusicOn() { return musicOn; }
    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }

    public boolean isSoundEffectsOn() { return soundEffectsOn; }
    public void setSoundEffectsOn(boolean soundEffectsOn) { this.soundEffectsOn = soundEffectsOn; }


    public boolean isExtendModeOn() { return extendModeOn; }
    public void setExtendModeOn(boolean extendModeOn) { this.extendModeOn = extendModeOn; }

    public boolean isSamePlayerType() {return this.player1Type == this.player2Type; }



    // Save configuration to JSON
    public void saveToFile() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(configFilePath)) {
            gson.toJson(this, writer);
            logger.info("Config saved successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save configuration", e);
        }
    }

    // Load configuration from JSON
    public static Configurations loadFromFile(String configFilePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(configFilePath)) {
            logger.info("Config read successfully.");
            return gson.fromJson(reader, Configurations.class);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load configuration", e);
            return new Configurations(configFilePath);
        }
    }


    public static Configurations loadFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("configurations.json")) {
            logger.info("Config read successfully.");
            return gson.fromJson(reader, Configurations.class);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load configuration", e);
            return new Configurations("configurations.json");
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
                ", extendModeOn=" + extendModeOn +
                ", player1Type=" + player1Type +
                ", player2Type=" + player2Type +
                '}';
    }


}

public class Configurations {
    private int fieldWidth;
    private int fieldHeight;
    private int gameLevel;
    private boolean musicOn;
    private boolean soundEffectsOn;
    private boolean aiPlayOn;
    private boolean extendModeOn;

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
}

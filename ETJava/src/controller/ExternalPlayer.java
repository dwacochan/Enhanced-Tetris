package controller;

import model.PureGame;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ExternalPlayer {
    protected GameController gameController;



    private int gameNumber;
    protected Timer timer;
    protected Timer downTimer;

    public ExternalPlayer(int gameNumber) {
        gameController = GameController.getInstance();
        this.gameNumber = gameNumber;
        this.downTimer = new Timer();  // Separate timer for downward movement
        this.timer = new Timer();  // Create the timer here
        System.out.println("ExternalPlayer constructor");
    }

    public abstract void decideAndMakeBestMove(PureGame pureGame);

    // Movement control methods for External Player 1 or 2 based on gameNumber
    protected void moveLeft() {
        if (gameNumber == 1) {
            Controls.setExt_1_left(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_left(true);
        }
    }

    protected void moveRight() {
        if (gameNumber == 1) {
            Controls.setExt_1_right(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_right(true);
        }
    }

    protected void moveUp() {
        if (gameNumber == 1) {
            Controls.setExt_1_up(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_up(true);
        }
    }

    protected void moveDown() {
        if (gameNumber == 1) {
            Controls.setExt_1_down(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_down(true);
        }
    }

    // Start downward movement
    protected void startDownwardMovement() {
        TimerTask moveDownTask = new TimerTask() {
            @Override
            public void run() {
                moveDown();  // Continuously move down
            }
        };
        downTimer.schedule(moveDownTask, 0, 500);  // Moves down every 100ms (adjust if needed)
    }

    public void stop() {
        System.out.println("Stopping " + this + " number : " + gameNumber);
        if (timer != null) {
            timer.cancel();  // Cancel the timer to stop task execution
        }
        resetControls();  // Optionally reset controls when stopping
    }

    // Reset external player controls
    protected void resetControls() {
        Timer resetTimer = new Timer();
        resetTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (gameNumber == 1) {
                    Controls.setExt_1_left(false);
                    Controls.setExt_1_right(false);
                    Controls.setExt_1_up(false);
                    Controls.setExt_1_down(false);
                } else if (gameNumber == 2) {
                    Controls.setExt_2_left(false);
                    Controls.setExt_2_right(false);
                    Controls.setExt_2_up(false);
                    Controls.setExt_2_down(false);
                }
            }
        }, 50);  // Reset after 50ms to simulate key release
    }

    public int getGameNumber() {
        return gameNumber;
    }
}

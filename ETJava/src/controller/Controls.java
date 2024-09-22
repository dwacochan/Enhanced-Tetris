package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Controls {
    // Player 1 controls
    public static boolean up;         // Arrow Up
    public static boolean right;      // Arrow Right
    public static boolean down;       // Arrow Down
    public static boolean left;       // Arrow Left

    // Player 2 controls (WASD)
    public static boolean alt_up;     // W
    public static boolean alt_right;  // D
    public static boolean alt_down;   // S
    public static boolean alt_left;   // A

    // External player 1 controls
    public static boolean ext_1_up;
    public static boolean ext_1_right;
    public static boolean ext_1_down;
    public static boolean ext_1_left;

    // External player 2 controls
    public static boolean ext_2_up;
    public static boolean ext_2_right;
    public static boolean ext_2_down;
    public static boolean ext_2_left;

    public static boolean pause;      // Pause for both players

    // Timer to simulate external player controls
    private static Timer externalPlayerTimer;

    // Simulate Player 1 being externally controlled
    public static void startExternalPlayer1() {
        // Stop any previous simulation if it is running
        if (externalPlayerTimer != null) {
            externalPlayerTimer.cancel();
        }

        // Create a new Timer
        externalPlayerTimer = new Timer();

        // Schedule repeated tasks to simulate key presses in a loop
        externalPlayerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Simulate external player 1 movement pattern
                ext_1_up = true;
                delay(100);  // Wait 0.1 second
                ext_1_up = false;
                ext_1_right = true;
                delay(100);
                ext_1_right = false;
                ext_1_down = true;
                delay(100);
                ext_1_down = false;
                ext_1_left = true;
                delay(100);
                ext_1_left = false;
            }
        }, 0, 500); // Run every second (adjust as necessary)
    }

    // Helper method to create delays between key events
    private static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }


    public static void bindKeys(JComponent comp) {
        if (comp == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }

        // Player 1 controls
        bindKey(comp, "UP", "up");
        bindKey(comp, "RIGHT", "right");
        bindKey(comp, "DOWN", "down");
        bindKey(comp, "LEFT", "left");

        // Player 2 controls (WASD)
        bindKey(comp, "W", "alt_up");
        bindKey(comp, "D", "alt_right");
        bindKey(comp, "S", "alt_down");
        bindKey(comp, "A", "alt_left");

        // Bind PAUSE key for both players
        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "pause");
        comp.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause = !pause;
                if (pause) {
                    resetControls();
                }
            }
        });
    }

    private static void bindKey(JComponent comp, String key, String action) {
        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), action);
        comp.getActionMap().put(action, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (action) {
                    case "up":
                        up = true;
                        break;
                    case "right":
                        right = true;
                        break;
                    case "down":
                        down = true;
                        break;
                    case "left":
                        left = true;
                        break;
                    case "alt_up":
                        alt_up = true;
                        break;
                    case "alt_right":
                        alt_right = true;
                        break;
                    case "alt_down":
                        alt_down = true;
                        break;
                    case "alt_left":
                        alt_left = true;
                        break;
                }
            }
        });
    }

    private static void resetControls() {
        up = right = down = left = false;
        alt_up = alt_right = alt_down = alt_left = false; // Reset both players' controls
        ext_1_right = ext_1_left = ext_1_up = ext_1_down = false;
        ext_2_left = ext_2_right = ext_2_up = ext_2_down = false;
    }
}

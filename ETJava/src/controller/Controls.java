package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

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

    public static boolean pause;      // Pause for both players

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
    }
}

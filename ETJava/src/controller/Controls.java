package controller;

import model.ControlsSet;
import model.PlayerType;

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

    public static void setExt_1_up(boolean ext_1_up) {
        Controls.ext_1_up = ext_1_up;
    }

    public static void setExt_1_right(boolean ext_1_right) {
        Controls.ext_1_right = ext_1_right;
    }

    public static void setExt_1_down(boolean ext_1_down) {
        Controls.ext_1_down = ext_1_down;
    }

    public static void setExt_1_left(boolean ext_1_left) {
        Controls.ext_1_left = ext_1_left;
    }

    public static void setExt_2_up(boolean ext_2_up) {
        Controls.ext_2_up = ext_2_up;
    }

    public static void setExt_2_right(boolean ext_2_right) {
        Controls.ext_2_right = ext_2_right;
    }

    public static void setExt_2_down(boolean ext_2_down) {
        Controls.ext_2_down = ext_2_down;
    }

    public static void setExt_2_left(boolean ext_2_left) {
        Controls.ext_2_left = ext_2_left;
    }

    public static ControlsSet getCurrentControls(int gameNumber, PlayerType playerType) {
        switch(gameNumber){
            case 1 -> {
                if (playerType == PlayerType.HUMAN) {
                    return ControlsSet.DEFAULT;
                } else if (playerType == PlayerType.EXTERNAL) {
                    return ControlsSet.EXTERNAL_1;
                } else {
                    return ControlsSet.DEFAULT;
                }
            }
            case 2 -> {
                if (playerType == PlayerType.HUMAN) {
                    return ControlsSet.ALTERNATE;
                } else if (playerType == PlayerType.EXTERNAL) {
                    return ControlsSet.EXTERNAL_2;
                } else {
                    return ControlsSet.ALTERNATE;
                }
            }

            default -> {
                return ControlsSet.DEFAULT;
            }
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

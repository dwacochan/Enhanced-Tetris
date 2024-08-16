package game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controls {
    public static boolean up;
    public static boolean right;
    public static boolean down;
    public static boolean left;

    public static void bindKeys(JComponent comp){
        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
        comp.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = true;
            }
        });

        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
        comp.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = true;
            }
        });

        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
        comp.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down = true;
            }
        });

        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");
        comp.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = true;
            }
        });
    }

}

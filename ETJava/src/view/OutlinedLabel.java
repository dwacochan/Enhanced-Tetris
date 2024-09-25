package view;

import javax.swing.*;
import java.awt.*;

public class OutlinedLabel extends JLabel {

    private Color outlineColor;

    public OutlinedLabel(String text, int horizontalAlignment, Color outlineColor) {
        super(text, horizontalAlignment);
        this.outlineColor = outlineColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw thicker outline (increase the loop range for thicker effect)
        g2.setColor(outlineColor);
        int outlineThickness = 2; // Set the thickness of the outline (increase for thicker)
        for (int i = -outlineThickness; i <= outlineThickness; i++) {
            for (int j = -outlineThickness; j <= outlineThickness; j++) {
                if (i != 0 || j != 0) { // Avoid drawing at the center (where the text will be)
                    g2.drawString(getText(), getInsets().left + i, getInsets().top + j + g2.getFontMetrics().getAscent());
                }
            }
        }

        // Draw the actual text (on top of the outline)
        g2.setColor(getForeground());
        g2.drawString(getText(), getInsets().left, getInsets().top + g2.getFontMetrics().getAscent());

        g2.dispose();
    }
}

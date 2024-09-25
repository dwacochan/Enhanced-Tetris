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

        // Draw outline (slightly offset around the text)
        g2.setColor(outlineColor);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                g2.drawString(getText(), getInsets().left + i, getInsets().top + j + g2.getFontMetrics().getAscent());
            }
        }

        // Draw the actual text (on top of the outline)
        g2.setColor(getForeground());
        g2.drawString(getText(), getInsets().left, getInsets().top + g2.getFontMetrics().getAscent());

        g2.dispose();
    }
}

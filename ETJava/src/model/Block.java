package model;

import java.awt.*;

public class Block {
    private int x;
    private int y;
    public static final int SIZE = 20;
    private Color color;

    public Block(Color color) {
        this.setColor(color);
    }

    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the main block
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), SIZE, SIZE);

        // Add a border to the block
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(getX(), getY(), SIZE, SIZE);

        // Create a simple 3D effect using shading
        Color brighter = getColor().brighter();
        Color darker = getColor().darker();

        // Highlight: Top and left sides
        g2d.setColor(brighter);
        g2d.drawLine(getX(), getY(), getX() + SIZE, getY());  // Top side
        g2d.drawLine(getX(), getY(), getX(), getY() + SIZE);  // Left side

        // Shadow: Bottom and right sides
        g2d.setColor(darker);
        g2d.drawLine(getX(), getY() + SIZE, getX() + SIZE, getY() + SIZE);  // Bottom side
        g2d.drawLine(getX() + SIZE, getY(), getX() + SIZE, getY() + SIZE);  // Right side

        // Add a subtle inner shadow effect for more depth
        g2d.setColor(darker.darker());
        g2d.drawRect(getX() + 2, getY() + 2, SIZE - 4, SIZE - 4);  // Inner shadow
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

package model;

import java.awt.*;

public class Block {
    private int x;
    private int y;
    public static final int SIZE = 20;
    private Color color;

    public Block(Color color){
        this.setColor(color);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), SIZE, SIZE);
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
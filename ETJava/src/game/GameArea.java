package game;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    final int FPS = 60;
    Thread gameThread;
    Gameplay gameplay;

    public GameArea(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        gameplay = new Gameplay();
    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update(){
        gameplay.update();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta --;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameplay.draw(g2d);
    }
}
package model;

import controller.facade.GameFacade;
import controller.Controls;

import javax.swing.*;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    final int FPS = 60;
    private Thread gameThread;
    private boolean running = false;
    private boolean paused = false;

    // Use the facade instead of direct access to Gameplay
    private GameFacade gameFacade;

    public GameLoop() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        // Initialize the GameFacade with custom width and height
        gameFacade = new GameFacade(200, 400);
    }

    public void startGame() {
        if (gameThread == null || !running) {
            gameThread = new Thread(this);
            gameThread.start();
            running = true;
            paused = false;
        }
    }

    public void pauseGame() {
        if (running && !paused) {
            paused = true;
        }
    }

    public void resumeGame() {
        if (running && paused) {
            paused = false;
        }
    }

    public void stopGame() {
        if (running) {
            running = false;
            paused = false;

            // Interrupt the game thread to exit the wait state if it's paused
            if (gameThread != null) {
                gameThread.interrupt();
            }

            try {
                // Ensure the game thread finishes execution
                gameThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve the interrupt status
                System.out.println("Stopping game");
            } finally {
                gameThread = null; // Clean up the reference to the game thread
            }
        }
    }

    public void resetGame() {
        stopGame();
        gameFacade.startNewGame(); // Reset the game using the facade
    }

    private void update() {
        if (!paused && !Controls.pause) {
            gameFacade.updateGame();  // Use the facade to update the game
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameFacade.renderGame(g2d);
    }

    public void adjustGameDimensions(int width, int height) {
        gameFacade.setGameDimensions(width, height);
    }

    public boolean isGameOver(){
        return gameFacade.isGameOver();
    }
}

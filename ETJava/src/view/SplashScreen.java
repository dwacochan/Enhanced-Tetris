package view;

import controller.GameController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.InputStream;

public class SplashScreen extends AbstractScreen {
    private BufferedImage splashImage;
    private BufferedImage mainMenuImage;
    private boolean showingSplash = true;  // Track whether splash or main menu image is showing
    private float alpha = 0f; // Transparency level for fading effect
    private Timer fadeOutTimer;
    private Timer fadeInTimer;
    private Timer showSplashTimer;  // Timer to show splash screen for 2 seconds

    public SplashScreen(GameController gameController) {
        super(gameController);

        // Load the splash screen image
        try {
            InputStream splashInputStream = getClass().getClassLoader().getResourceAsStream("resources/newSplashScreen.png");
            if (splashInputStream == null) {
                System.out.println("Splash Image not found!");
            } else {
                splashImage = ImageIO.read(splashInputStream);
            }

            // Load the static main menu screenshot
            InputStream mainMenuInputStream = getClass().getClassLoader().getResourceAsStream("resources/MainMenuScreenshot.png");
            if (mainMenuInputStream == null) {
                System.out.println("Main Menu Screenshot not found!");
            } else {
                mainMenuImage = ImageIO.read(mainMenuInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Add the custom ImagePanel to display the images and handle fading
        ImagePanel imagePanel = new ImagePanel();
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        // Start showing the splash screen for 2 seconds, then fade out
        showSplashTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSplashTimer.stop();
                startFadeOut(); // After 2 seconds, start fade-out
            }
        });
        showSplashTimer.setRepeats(false);
        showSplashTimer.start();

        setVisible(true);
    }

    /**
     * Starts the fade-out effect by gradually increasing the alpha value to fade to black.
     */
    private void startFadeOut() {
        int fadeDuration = 2000; // 2 seconds
        int fadeInterval = 5;   // Update every 5 milliseconds
        int steps = fadeDuration / fadeInterval; // Total number of steps

        fadeOutTimer = new Timer(fadeInterval, new ActionListener() {
            int currentStep = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep++;
                alpha = (float) currentStep / (float) steps;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fadeOutTimer.stop();
                    switchToMainMenuImage(); // After fade out, switch to main menu image
                }
                mainPanel.repaint(); // Redraw to show fade effect
            }
        });
        fadeOutTimer.start();
    }

    /**
     * Switch to the main menu image after the fade-out completes.
     */
    private void switchToMainMenuImage() {
        showingSplash = false; // Now show the main menu image
        alpha = 1f;  // Reset alpha to fully black before starting the fade-in
        startFadeIn(); // Begin the fade-in process
    }

    /**
     * Starts the fade-in effect by gradually decreasing the alpha value to reveal the main menu screenshot.
     */
    private void startFadeIn() {
        int fadeDuration = 500; // 0.5 second
        int fadeInterval = 5;   // Update every 5 milliseconds
        int steps = fadeDuration / fadeInterval; // Total number of steps

        fadeInTimer = new Timer(fadeInterval, new ActionListener() {
            int currentStep = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep++;
                alpha = 1f - ((float) currentStep / (float) steps); // Fade in from black
                if (alpha <= 0f) {
                    alpha = 0f;
                    fadeInTimer.stop();
                    transitionToMainMenu(); // After fade-in, transition to the actual main menu
                }
                mainPanel.repaint(); // Redraw to show fade effect
            }
        });
        fadeInTimer.start();
    }

    /**
     * Transition to the actual Main Menu screen after the fade-in completes.
     */
    private void transitionToMainMenu() {
        GameController.getInstance().showMainMenu(); // Switch to actual main menu with buttons
    }

    /**
     * Custom JPanel to display the images and handle the fade effect.
     */
    private class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Display the current image based on the splash or main menu state
            BufferedImage currentImage = showingSplash ? splashImage : mainMenuImage;
            if (currentImage != null) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = currentImage.getWidth();
                int imageHeight = currentImage.getHeight();

                // Calculate the scaling factor to fit the image within the panel
                double widthScale = (double) panelWidth / imageWidth;
                double heightScale = (double) panelHeight / imageHeight;
                double scale = Math.max(widthScale, heightScale); // Scale to fill the panel

                // Calculate the new dimensions for the image
                int newImageWidth = (int) (scale * imageWidth);
                int newImageHeight = (int) (scale * imageHeight);

                // Calculate the x and y coordinates to crop the image to fit
                int x = (panelWidth - newImageWidth) / 2;
                int y = (panelHeight - newImageHeight) / 2;

                // Draw the cropped and scaled image
                g.drawImage(currentImage, x, y, newImageWidth, newImageHeight, this);
            }

            // Apply the fade effect (black overlay with the current alpha transparency)
            if (alpha > 0f) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight()); // Draw black overlay
                g2d.dispose();
            }
        }
    }
}

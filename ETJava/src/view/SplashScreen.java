package view;

import controller.GameController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class SplashScreen extends AbstractScreen {

    private BufferedImage splashImage;

    public SplashScreen(GameController gameController) {
        super(gameController);

        // Load the PNG image
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resources/newSplashScreen.png");
            if (inputStream == null) {
                System.out.println("Image not found!");
            } else {
                splashImage = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(new ImagePanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private class ImagePanel extends Panel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (splashImage != null) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = splashImage.getWidth();
                int imageHeight = splashImage.getHeight();

                // Calculate the scaling factor to fit the image within the panel
                double widthScale = (double) panelWidth / imageWidth;
                double heightScale = (double) panelHeight / imageHeight;
                double scale = Math.max(widthScale, heightScale); // Scale to fill panel

                // Calculate the new dimensions for the image
                int newImageWidth = (int) (scale * imageWidth);
                int newImageHeight = (int) (scale * imageHeight);

                // Calculate the x and y coordinates to crop the image
                int x = (newImageWidth - panelWidth) / 2;
                int y = (newImageHeight - panelHeight) / 2;

                // Draw the cropped and scaled image
                g.drawImage(splashImage, -x, -y, newImageWidth, newImageHeight, this);
            }
        }
    }
}

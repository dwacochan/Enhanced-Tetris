package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel(String resourcePath) {
        try {
            // Load image using getResourceAsStream
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(resourcePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int imgWidth = backgroundImage.getWidth();
            int imgHeight = backgroundImage.getHeight();
            float imgAspect = (float) imgWidth / imgHeight;

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            float panelAspect = (float) panelWidth / panelHeight;

            int newWidth, newHeight;

            if (panelAspect > imgAspect) {
                // Panel is wider than image: fit by width and crop height
                newWidth = panelWidth;
                newHeight = (int) (newWidth / imgAspect);
            } else {
                // Panel is taller than image: fit by height and crop width
                newHeight = panelHeight;
                newWidth = (int) (newHeight * imgAspect);
            }

            // Center the cropped image
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            // Draw the image with cropping to fill the entire window
            g.drawImage(backgroundImage, x, y, newWidth, newHeight, this);
        }
    }
}

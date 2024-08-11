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
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resources/splashScreen.png");
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

        // Add the custom panel to draw the image
        mainPanel.add(new ImagePanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private class ImagePanel extends Panel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (splashImage != null) {
                // Get the dimensions of the panel and the image
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = splashImage.getWidth();
                int imageHeight = splashImage.getHeight();

                // Calculate the best fit position
                int x = (panelWidth - imageWidth) / 2;
                int y = (panelHeight - imageHeight) / 2;

                // Draw the image
                g.drawImage(splashImage, x, y, this);
            }
        }
    }
}

import controller.GameController;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class MainTest {

    @Test
    public void testGameControllerSingletonInitialization() {
        // Get the instance of GameController and check it's not null
        GameController gameController = GameController.getInstance();
        assertNotNull(gameController, "GameController instance should not be null");
    }

    @Test
    public void testSwingUtilitiesInvokeLater() throws InterruptedException, InvocationTargetException {
        // This will simulate the effect of SwingUtilities.invokeLater, ensuring it runs on the EDT.
        SwingUtilities.invokeAndWait(() -> {
            try {
                // Here we simulate the SplashScreen initialization by calling main directly.
                Main.main(new String[]{});

                // Check if the GameController was initialized and the SplashScreen invoked (implicitly by not throwing any errors)
                GameController gameController = GameController.getInstance();
                assertNotNull(gameController, "GameController instance should not be null after main execution");

                // No actual SplashScreen instance is verified, but the idea is that invoking Main.main doesn't throw errors.
            } catch (Exception e) {
                fail("Main should run without exceptions: " + e.getMessage());
            }
        });
    }
}

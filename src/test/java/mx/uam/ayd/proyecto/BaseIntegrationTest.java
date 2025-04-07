package mx.uam.ayd.proyecto;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest extends ApplicationTest {

    @BeforeAll
    public static void setupJavaFX() {
        // Ensure JavaFX is initialized
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @Override
    public void start(Stage stage) {
        // This method is required by ApplicationTest but we don't need to do anything here
        // since Spring Boot will handle the application startup
    }
} 
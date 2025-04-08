package mx.uam.ayd.proyecto;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {
    // Base class for integration tests without JavaFX dependencies
} 
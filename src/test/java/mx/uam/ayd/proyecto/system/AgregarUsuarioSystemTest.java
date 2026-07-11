package mx.uam.ayd.proyecto.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import mx.uam.ayd.proyecto.ProyectoApplication;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;

/**
 * Test de sistema para probar el flujo completo de agregar usuario
 * usando TestFX para interactuar con la interfaz gráfica JavaFX
 * 
 * @author humbertocervantes
 */
@SpringBootTest(classes = ProyectoApplication.class)
@ActiveProfiles("test")
public class AgregarUsuarioSystemTest extends ApplicationTest {

    @Autowired
    private ControlPrincipal controlPrincipal;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void start(Stage stage) throws Exception {
        // Esperar a que Spring haya inyectado las dependencias
        // La inicialización de la aplicación se hará después de setUp
    }

    @BeforeEach
    @Transactional
    public void setUp() throws Exception {
        // Limpiar la base de datos antes de cada test
        usuarioRepository.deleteAll();
        grupoRepository.deleteAll();

        // Crear grupos de prueba
        Grupo grupoAdmin = new Grupo();
        grupoAdmin.setNombre("Administradores");
        grupoRepository.save(grupoAdmin);

        Grupo grupoOps = new Grupo();
        grupoOps.setNombre("Operadores");
        grupoRepository.save(grupoOps);

        // Inicializar la aplicación después de preparar los datos
        controlPrincipal.inicia();
        
        // Esperar a que la ventana principal aparezca
        sleep(500);
    }

    @Test
    public void testAgregarUsuarioExitosamente() {
        // Given: Datos del usuario a agregar
        String nombre = "Juan";
        String apellido = "Pérez";
        String nombreGrupo = "Administradores";

        // Verificar que el usuario no existe previamente
        Usuario usuarioExistente = usuarioRepository.findByNombreAndApellido(nombre, apellido);
        assertTrue(usuarioExistente == null, "El usuario no debería existir antes del test");

        // When: Interactuar con la interfaz gráfica
        // 1. Hacer clic en el botón "Agregar usuario" de la ventana principal
        Button btnAgregarUsuario = lookup(button -> button instanceof Button && 
            ((Button) button).getText().equals("Agregar usuario")).query();
        clickOn(btnAgregarUsuario);

        // Esperar a que aparezca la ventana de agregar usuario y esté completamente cargada
        // La ventana se crea en un Stage separado, así que necesitamos esperar más tiempo
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1000);

        // 2. Llenar el campo de nombre - esperar a que el elemento esté disponible
        // Buscar en todas las ventanas abiertas
        TextField textFieldNombre = waitForNode(() -> {
            try {
                return lookup("#textFieldNombre").query();
            } catch (Exception e) {
                return null;
            }
        }, 10);
        assertNotNull(textFieldNombre, "El campo de nombre debería estar visible");
        clickOn(textFieldNombre);
        write(nombre);

        // 3. Llenar el campo de apellido
        TextField textFieldApellido = waitForNode(() -> {
            try {
                return lookup("#textFieldApellido").query();
            } catch (Exception e) {
                return null;
            }
        }, 10);
        assertNotNull(textFieldApellido, "El campo de apellido debería estar visible");
        clickOn(textFieldApellido);
        write(apellido);

        // 4. Seleccionar el grupo del ComboBox
        ComboBox<String> comboBoxGrupo = waitForNode(() -> {
            try {
                return lookup("#comboBoxGrupo").query();
            } catch (Exception e) {
                return null;
            }
        }, 10);
        assertNotNull(comboBoxGrupo, "El ComboBox debería estar visible");
        // El ComboBox ya tiene un valor por defecto (el primero), pero lo seleccionamos explícitamente
        // Usar el método setValue directamente en lugar de hacer clic en el dropdown
        Platform.runLater(() -> {
            comboBoxGrupo.setValue(nombreGrupo);
        });
        sleep(300);

        // 5. Hacer clic en el botón "Agregar"
        Button btnAgregar = waitForNode(() -> {
            try {
                return lookup(button -> button instanceof Button && 
            ((Button) button).getText().equals("Agregar")).query();
            } catch (Exception e) {
                return null;
            }
        }, 10);
        assertNotNull(btnAgregar, "El botón Agregar debería estar visible");
        clickOn(btnAgregar);

        // Esperar a que se procese la acción y aparezca el diálogo
        // El diálogo usa showAndWait() que bloquea, así que necesitamos esperar más tiempo
        sleep(2000);

        // Then: Verificar que se muestra el diálogo de éxito
        // Buscar el botón OK del diálogo de alerta
        Button okButton = lookup(button -> button instanceof Button && 
            ((Button) button).getText().equals("OK")).query();
        assertNotNull(okButton, "Debería mostrarse un diálogo con botón OK");
        
        // Cerrar el diálogo
        clickOn(okButton);

        // Esperar a que se cierre el diálogo y se complete la transacción
        sleep(1000);

        // Verificar en la base de datos que el usuario fue guardado
        // Necesitamos hacer flush para asegurar que los cambios se persistan
        Usuario usuarioGuardado = usuarioRepository.findByNombreAndApellido(nombre, apellido);
        assertNotNull(usuarioGuardado, "El usuario debería haberse guardado en la base de datos");
        assertEquals(nombre, usuarioGuardado.getNombre(), "El nombre debería coincidir");
        assertEquals(apellido, usuarioGuardado.getApellido(), "El apellido debería coincidir");

        // Verificar que el usuario está asociado al grupo correcto
        Grupo grupoGuardado = grupoRepository.findByNombre(nombreGrupo);
        assertNotNull(grupoGuardado, "El grupo debería existir");
        assertTrue(grupoGuardado.getUsuarios().contains(usuarioGuardado), 
            "El usuario debería estar asociado al grupo");
    }

    /**
     * Método helper para esperar a que un nodo esté disponible en el scene graph
     */
    private <T extends Node> T waitForNode(java.util.function.Supplier<T> supplier, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                WaitForAsyncUtils.waitForFxEvents();
                T node = supplier.get();
                if (node != null && node.isVisible()) {
                    return node;
                }
            } catch (Exception e) {
                // Continuar intentando
            }
            sleep(200);
        }
        return null;
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
        super.stop();
    }
}


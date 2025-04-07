package mx.uam.ayd.proyecto.presentacion.principal;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

/**
 * Ventana principal usando JavaFX
 * 
 */
@Component
public class VentanaPrincipal {

	private Stage stage;
	private ControlPrincipal control;
	private boolean initialized = false;

	/**
	 * Constructor without UI initialization
	 */
	public VentanaPrincipal() {
		// Don't initialize JavaFX components in constructor
	}
	
	/**
	 * Initialize UI components on the JavaFX application thread
	 */
	private void initializeUI() {
		if (initialized) {
			return;
		}
		
		// Create UI only if we're on JavaFX thread
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}
		
		stage = new Stage();
		stage.setTitle("Mi Aplicación");
		
		// Create UI components
		Label lblMiAplicacion = new Label("Mi Aplicación");
		
		Button btnAgregarUsuario = new Button("Agregar usuario");
		btnAgregarUsuario.setOnAction(e -> {
			if (control != null) {
				control.agregarUsuario();
			}
		});
		
		Button btnListarUsuarios = new Button("Listar usuarios");
		btnListarUsuarios.setOnAction(e -> {
			if (control != null) {
				control.listarUsuarios();
			}
		});

		Button btnListarGrupos = new Button("Listar grupos");
		btnListarGrupos.setOnAction(e -> {
			if (control != null) {
				control.listarGrupos();
			}
		});
		
		// Layout
		VBox layout = new VBox(10); // 10px spacing
		layout.getChildren().addAll(lblMiAplicacion, btnAgregarUsuario, btnListarUsuarios, btnListarGrupos);
		layout.setStyle("-fx-padding: 15px;");
		
		// Create scene
		Scene scene = new Scene(layout, 450, 300);
		stage.setScene(scene);
		
		initialized = true;
	}
	
	/**
	 * Muestra la ventana y establece el control
	 * 
	 * @param control El controlador asociado a esta ventana
	 */
	public void muestra(ControlPrincipal control) {
		this.control = control;
		
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(control));
			return;
		}
		
		initializeUI();
		stage.show();
	}
}

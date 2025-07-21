package mx.uam.ayd.proyecto.presentacion.principal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Ventana principal usando JavaFX con FXML
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
		
		try {
			stage = new Stage();
			stage.setTitle("Mi Aplicación");
			
			// Load FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-principal.fxml"));
			loader.setController(this);
			Scene scene = new Scene(loader.load(), 450, 300);
			stage.setScene(scene);
			
			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setControlPrincipal(ControlPrincipal control) {
		this.control = control;
	}
	/**
	 * Muestra la ventana y establece el control
	 * 
	 * @param control El controlador asociado a esta ventana
	 */
	public void muestra() {
		//this.control = control;
		
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra());
			return;
		}
		
		initializeUI();
		stage.show();
	}
	
	// FXML Event Handlers
	
	@FXML
	private void handleAgregarUsuario() {
		if (control != null) {
			control.agregarUsuario();
		}
	}
	
	@FXML
	private void handleListarUsuarios() {
		if (control != null) {
			control.listarUsuarios();
		}
	}
	
	@FXML
	private void handleListarGrupos() {
		if (control != null) {
			control.listarGrupos();
		}
	}
}

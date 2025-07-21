package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@Component
public class VentanaAgregarUsuario {

	private Stage stage;
	private ControlAgregarUsuario control;
	
	@FXML
	private TextField textFieldNombre;
	
	@FXML
	private TextField textFieldApellido;
	
	@FXML
	private ComboBox<String> comboBoxGrupo;
	
	private boolean initialized = false;

	/**
	 * Constructor without UI initialization
	 */
	public VentanaAgregarUsuario() {
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
			stage.setTitle("Agregar Usuario");
			
			// Load FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-agregar-usuario.fxml"));
			loader.setController(this);
			Scene scene = new Scene(loader.load(), 300, 220);
			stage.setScene(scene);
			
			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Establece el controlador asociado a esta ventana
	 * 
	 * @param control El controlador asociado
	 */
	public void setControlAgregarUsuario(ControlAgregarUsuario control) {
		this.control = control;
	}
	
	/**
	 * Muestra la ventana y establece los datos
	 * 
	 * @param grupos La lista de grupos disponibles
	 */
	public void muestra(List<Grupo> grupos) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(grupos));
			return;
		}
		
		initializeUI();
		
		textFieldNombre.setText("");
		textFieldApellido.setText("");
		
		comboBoxGrupo.getItems().clear();
		for(Grupo grupo : grupos) {
			comboBoxGrupo.getItems().add(grupo.getNombre());
		}
		
		if(!comboBoxGrupo.getItems().isEmpty()) {
			comboBoxGrupo.setValue(comboBoxGrupo.getItems().get(0));
		}
		
		stage.show();
	}
	
	/**
	 * Muestra un diálogo con un mensaje
	 * 
	 * @param mensaje El mensaje a mostrar
	 */
	public void muestraDialogoConMensaje(String mensaje) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestraDialogoConMensaje(mensaje));
			return;
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
	/**
	 * Oculta la ventana
	 */
	public void setVisible(boolean visible) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.setVisible(visible));
			return;
		}
		
		if (!initialized) {
			if (visible) {
				initializeUI();
			} else {
				return;
			}
		}
		
		if (visible) {
			stage.show();
		} else {
			stage.hide();
		}
	}
	
	// FXML Event Handlers
	
	@FXML
	private void handleAgregar() {
		if(textFieldNombre.getText().isEmpty() || textFieldApellido.getText().isEmpty()) {
			muestraDialogoConMensaje("El nombre y el apellido no deben estar vacios");
		} else {
			control.agregaUsuario(textFieldNombre.getText(), textFieldApellido.getText(), comboBoxGrupo.getValue());
		}
	}
	
	@FXML
	private void handleCancelar() {
		control.termina();
	}
}

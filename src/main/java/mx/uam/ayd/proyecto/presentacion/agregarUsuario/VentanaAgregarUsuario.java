package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@Component
public class VentanaAgregarUsuario {

	private Stage stage;
	private ControlAgregarUsuario control;
	private TextField textFieldNombre;
	private TextField textFieldApellido;
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
		
		stage = new Stage();
		stage.setTitle("Agregar Usuario");
		
		// Create UI components
		Label lblNombre = new Label("Nombre:");
		Label lblApellido = new Label("Apellido:");
		Label lblGrupo = new Label("Grupo:");
		
		textFieldNombre = new TextField();
		textFieldApellido = new TextField();
		comboBoxGrupo = new ComboBox<>();
		
		Button btnAgregar = new Button("Agregar");
		Button btnCancelar = new Button("Cancelar");
		
		// Set actions
		btnAgregar.setOnAction(e -> {
			if(textFieldNombre.getText().isEmpty() || textFieldApellido.getText().isEmpty()) {
				muestraDialogoConMensaje("El nombre y el apellido no deben estar vacios");
			} else {
				control.agregaUsuario(textFieldNombre.getText(), textFieldApellido.getText(), comboBoxGrupo.getValue());
			}
		});
		
		btnCancelar.setOnAction(e -> {
			control.termina();
		});
		
		// Layout
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		// Add components to grid
		gridPane.add(lblNombre, 0, 0);
		gridPane.add(textFieldNombre, 1, 0);
		gridPane.add(lblApellido, 0, 1);
		gridPane.add(textFieldApellido, 1, 1);
		gridPane.add(lblGrupo, 0, 2);
		gridPane.add(comboBoxGrupo, 1, 2);
		
		// Buttons in HBox
		HBox buttonBox = new HBox(10);
		buttonBox.setPadding(new Insets(10, 0, 0, 0));
		buttonBox.getChildren().addAll(btnAgregar, btnCancelar);
		
		// Main layout
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.getChildren().addAll(gridPane, buttonBox);
		
		// Create scene
		Scene scene = new Scene(vbox, 300, 220);
		stage.setScene(scene);
		
		initialized = true;
	}
	
	/**
	 * Muestra la ventana y establece los datos
	 * 
	 * @param control El controlador asociado
	 * @param grupos La lista de grupos disponibles
	 */
	public void muestra(ControlAgregarUsuario control, List<Grupo> grupos) {
		this.control = control;
		
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(control, grupos));
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
}

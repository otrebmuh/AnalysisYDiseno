package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.io.IOException;

import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

/**
 * Ventana para listar usuarios usando JavaFX con FXML
 */
@Component
public class VentanaListarUsuarios {

	private Stage stage;
	
	@FXML
	private TableView<Usuario> tableUsuarios;
	
	@FXML
	private TableColumn<Usuario, Long> idColumn;
	
	@FXML
	private TableColumn<Usuario, String> nombreColumn;
	
	@FXML
	private TableColumn<Usuario, String> apellidoColumn;
	
	@FXML
	private TableColumn<Usuario, Integer> edadColumn;
	
	private ControlListarUsuarios control;
	private boolean initialized = false;

	/**
	 * Constructor without UI initialization
	 */
	public VentanaListarUsuarios() {
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
			stage.setTitle("Lista de Usuarios");
			
			// Load FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-listar-usuarios.fxml"));
			loader.setController(this);
			Scene scene = new Scene(loader.load(), 450, 400);
			stage.setScene(scene);
			
			// Configure columns after FXML is loaded
			idColumn.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
			nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
			edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
			
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
	public void setControlListarUsuarios(ControlListarUsuarios control) {
		this.control = control;
	}
	
	/**
	 * Muestra la ventana y carga los usuarios
	 * 
	 * @param usuarios La lista de usuarios a mostrar
	 */
	public void muestra(List<Usuario> usuarios) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(usuarios));
			return;
		}
		
		initializeUI();
		
		ObservableList<Usuario> data = FXCollections.observableArrayList(usuarios);
		tableUsuarios.setItems(data);
		
		stage.show();
	}
	
	// FXML Event Handlers
	
	@FXML
	private void handleCerrar() {
		stage.close();
	}
}

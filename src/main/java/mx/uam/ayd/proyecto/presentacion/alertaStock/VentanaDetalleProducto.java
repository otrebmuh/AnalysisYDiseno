package mx.uam.ayd.proyecto.presentacion.alertaStock;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.io.IOException;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * @author KEVIND DYDIER
 */
@Component
public class VentanaDetalleProducto {

	private Stage stage;
	
	@SuppressWarnings("unused")
	private ControlRevisarExistencia control;
	
	@FXML
	private Label lblClave;
	
	@FXML
	private Label lblNombre;
	
	@FXML
	private Label lblStockActual;
	
	@FXML
	private Label lblStockMinimo;
	
	private boolean initialized = false;
//////////////////////////////////////////////////////////////////////77 CONSTRUCRTOR
	public VentanaDetalleProducto() {
	}
	
	/**
	 * Inicializa los componentes de la interfaz en el hilo de JavaFX
	 */
	private void initializeUI() {
		if (initialized) {
			return;
		}
		
		// Crear la UI solo si estamos en el hilo de JavaFX
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}
		
		try {
			stage = new Stage();
			stage.setTitle("Detalle de Existencias");
			// Configurado como modal para que el usuario deba cerrarlo antes de volver a la lista [7]
			stage.initModality(Modality.APPLICATION_MODAL);
			
			// Carga del FXML del diálogo
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogo-detalle-producto.fxml"));
			loader.setController(this);
			Scene scene = new Scene(loader.load(), 400, 350);
			stage.setScene(scene);
			
			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Establece el controlador asociado
	 */
	public void setControl(ControlRevisarExistencia control) {
		this.control = control;
	}
	
	/**
	 * Muestra el diálogo y establece los datos del producto seleccionado
	 * 
	 * @param producto El producto con bajo stock
	 * @param stockMinimo El límite mínimo configurado en el inventario [2]
	 */


/////////////////////////////////////////////////////////////////////// FXML Event Handlers
	public void muestra(Producto producto, int stockMinimo) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(producto, stockMinimo));
			return;
		}
		
		initializeUI();
		
		// Llenado de información
		lblClave.setText(producto.getClave());
		lblNombre.setText(producto.getNombre());
		lblStockActual.setText(String.valueOf(producto.getExistenciaActual()));
		lblStockMinimo.setText(String.valueOf(stockMinimo));
		
		stage.showAndWait();
	}
	
	@FXML
	private void handleCerrar() {
		stage.close();
	}
}
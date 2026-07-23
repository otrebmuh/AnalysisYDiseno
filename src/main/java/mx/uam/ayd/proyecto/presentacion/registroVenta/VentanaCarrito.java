package mx.uam.ayd.proyecto.presentacion.registroVenta;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.uam.ayd.proyecto.negocio.modelo.DescripcionVenta;


@Component
public class VentanaCarrito {

	private Stage stage;
	private ControlRegistroVenta control;
	private boolean initialized = false;

	// Elementos FXML
	@FXML private TableView<DescripcionVenta> tablaCarrito;
	@FXML private TableColumn<DescripcionVenta, String> colNombre;
	@FXML private TableColumn<DescripcionVenta, Double> colPrecio;
	@FXML private TableColumn<DescripcionVenta, Integer> colCantidad;
	@FXML private TableColumn<DescripcionVenta, Double> colSubtotal;
	@FXML private Label lblTotal;

	public VentanaCarrito() {
	}

	public void setControl(ControlRegistroVenta control) {
		this.control = control;
	}

	private void initializeUI() {
		if (initialized) return;
		
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}

		try {
			// Carga el FXMl
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-carrito.fxml"));
			loader.setController(this);
			Parent root = loader.load();
			
			stage = new Stage();
			stage.setTitle("Confirmación de Venta - Resumen");
			stage.setScene(new Scene(root));
			
			// Configuración de columnas
			colNombre.setCellValueFactory(new PropertyValueFactory<>("productoNombre")); 
			colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
			colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
			colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//recibe directamente el carrito lleno y el total calculado.

	public void muestra(List<DescripcionVenta> detalles, double total) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(detalles, total));
			return;
		}

		initializeUI();
		
		// Llena la tabla con los productos que el propietario agregó en la HU-05 [5]
		tablaCarrito.setItems(FXCollections.observableArrayList(detalles));
		lblTotal.setText(String.format("$%.2f", total));
		
		stage.show();
	}

	public void muestraDialogoConMensaje(String mensaje) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestraDialogoConMensaje(mensaje));
			return;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Validación");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public void setVisible(boolean visible) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.setVisible(visible));
			return;
		}
		if (visible) stage.show();
		else stage.hide();
	}


	@FXML
	private void onConfirmarVenta() {
		// Avisa al control que el propietario verificó el total y desea cobrar
		control.procesarConfirmacionVenta();
	}

	@FXML
	private void onCancelarVenta() {
		// Limpia la interfaz sin alterar el sistema
		control.termina();
	}
}
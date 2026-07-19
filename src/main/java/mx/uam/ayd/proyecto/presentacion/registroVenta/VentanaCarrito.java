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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.uam.ayd.proyecto.negocio.modelo.DescripcionVenta;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Vista para el Carrito de Compras de la HU-04.
 * Gestiona la selección de productos y la visualización de la lista de venta.
 */
@Component
public class VentanaCarrito {

	private Stage stage;
	private ControlRegistroVenta control;
	private boolean initialized = false;

	// Elementos FXML vinculados por id (Fuente 5)
	@FXML private ComboBox<Producto> comboProductos;
	@FXML private TextField txtCantidad;
	@FXML private TableView<DescripcionVenta> tablaCarrito;
	@FXML private TableColumn<DescripcionVenta, String> colNombre;
	@FXML private TableColumn<DescripcionVenta, Double> colPrecio;
	@FXML private TableColumn<DescripcionVenta, Integer> colCantidad;
	@FXML private TableColumn<DescripcionVenta, Double> colSubtotal;
	@FXML private Label lblTotal;

	public VentanaCarrito() {
		// Constructor sin inicialización de UI (Estilo del profesor)
	}

	/**
	 * Establece la conexión con el controlador orquestador
	 */
	public void setControl(ControlRegistroVenta control) {
		this.control = control;
	}

	/**
	 * Inicializa la interfaz cargando el archivo FXML
	 */
	private void initializeUI() {
		if (initialized) {
			return;
		}
		
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-carrito.fxml"));
			loader.setController(this); // La ventana es el controlador de sus propios eventos FXML
			Parent root = loader.load();
			
			stage = new Stage();
			stage.setTitle("Registro de Ventas - Carrito");
			stage.setScene(new Scene(root));
			
			// Configurar las columnas de la tabla para mostrar los atributos (RN-10)
			colNombre.setCellValueFactory(new PropertyValueFactory<>("productoNombre")); 
			colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
			colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
			colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muestra la ventana y carga el catálogo de productos
	 */
	public void muestra(Iterable<Producto> productos) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(productos));
			return;
		}

		initializeUI();
		limpiaCampos();
		
		comboProductos.getItems().clear();
		for (Producto p : productos) {
			comboProductos.getItems().add(p);
		}
		
		stage.show();
	}

	/**
	 * Actualiza visualmente la tabla de productos y el total acumulado
	 */
	public void actualizaCarrito(List<DescripcionVenta> detalles, double total) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.actualizaCarrito(detalles, total));
			return;
		}
		tablaCarrito.setItems(FXCollections.observableArrayList(detalles));
		lblTotal.setText(String.format("$%.2f", total));
	}

	/**
	 * Restablece la interfaz a su estado inicial (RN-12 / Escenario 5)
	 */
	public void limpiaCampos() {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::limpiaCampos);
			return;
		}
		comboProductos.setValue(null);
		txtCantidad.setText("1");
		tablaCarrito.getItems().clear();
		lblTotal.setText("$0.00");
	}

	/**
	 * Muestra un diálogo informativo al usuario (Criterio de aceptación)
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

	public void setVisible(boolean visible) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.setVisible(visible));
			return;
		}
		if (visible) stage.show();
		else stage.hide();
	}

	// --- Manejadores de Eventos FXML (Fuente 5) ---

	@FXML
	private void onAgregarProducto() {
		Producto seleccionado = comboProductos.getValue();
		if (seleccionado == null || txtCantidad.getText().isEmpty()) {
			muestraDialogoConMensaje("Seleccione un producto e ingrese la cantidad.");
			return;
		}
		try {
			int cantidad = Integer.parseInt(txtCantidad.getText());
			// Delega la validación de stock (RN-09) y el cálculo al control
			control.agregarProductoAlCarrito(seleccionado, cantidad);
		} catch (NumberFormatException e) {
			muestraDialogoConMensaje("La cantidad debe ser un número entero.");
		}
	}

	@FXML
	private void onConfirmarVenta() {
		// Delega la validación de precios (RN-04) y apertura de cobro al control
		control.procesarConfirmacionVenta();
	}

	@FXML
	private void onCancelarVenta() {
		// Ejecuta la limpieza y cierre (RN-12)
		control.termina();
	}
}
package mx.uam.ayd.proyecto.presentacion.agregarProductos;

import java.io.IOException;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import mx.uam.ayd.proyecto.negocio.modelo.Venta;

/**
 * @author Tu Nombre
 */
@Component
public class VistaAgregarProductos {

    private Stage stage;
    private ControlAgregarProductos control;

    @FXML
    private ComboBox<Producto> comboProductos;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TableView<DescripcionVenta> tablaCarrito;

    @FXML
    private TableColumn<DescripcionVenta, String> colNombre;

    @FXML
    private TableColumn<DescripcionVenta, Double> colPrecio;

    @FXML
    private TableColumn<DescripcionVenta, Integer> colCantidad;

    @FXML
    private TableColumn<DescripcionVenta, Double> colSubtotal;

    @FXML
    private Label lblTotal;

    private boolean initialized = false;

    /////////////////////////////////////////////////////////////////////// CONSTRUCTOR
    public VistaAgregarProductos() {
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
            stage.setTitle("Agregar Productos a la Venta");

            // Carga del FXML de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vista-agregar-productos.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), 700, 500);
            stage.setScene(scene);

            // Mapeo de columnas con las propiedades de DescripcionVenta
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
     * Establece el controlador asociado
     */
    public void setControl(ControlAgregarProductos control) {
        this.control = control;
    }

    /**
     * Muestra la ventana y establece el catálogo de productos inicial.
     * Corresponde a mostrarVentanaVenta en el diagrama de secuencia.
     */
    public void mostrarVentanaVenta(ControlAgregarProductos control, Iterable<Producto> productos) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.mostrarVentanaVenta(control, productos));
            return;
        }

        this.setControl(control);
        initializeUI();

        // Carga de productos en el ComboBox
        comboProductos.getItems().clear();
        for (Producto p : productos) {
            comboProductos.getItems().add(p);
        }

        // Se inicia la venta y se limpia la tabla
        control.iniciarVenta();
        tablaCarrito.getItems().clear();
        lblTotal.setText("$0.00");

        stage.show();
    }

    /**
     * Refresca la tabla del carrito y el total según la Venta actualizada.
     * Corresponde a mostrarVenta en el diagrama de secuencia.
     */
  public void mostrarVenta(Venta venta) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.mostrarVenta(venta));
            return;
        }

        // Usa getProductos() que es el getter real de tu entidad Venta
        if (venta != null && venta.getProductos() != null) {
            tablaCarrito.setItems(FXCollections.observableArrayList(venta.getProductos()));
            lblTotal.setText(String.format("$%.2f", venta.getTotal()));
        }
    }

    /**
     * Muestra un mensaje de advertencia/error al usuario
     */
    public void muestraMensajeError(String mensaje) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestraMensajeError(mensaje));
            return;
        }

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /////////////////////////////////////////////////////////////////////// FXML Event Handlers

    @FXML
    private void handleAgregarProducto() {
        Producto productoSeleccionado = comboProductos.getValue();

        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (productoSeleccionado == null) {
                muestraMensajeError("Por favor selecciona un producto.");
                return;
            }

            // Llamada al controlador para procesar la adición
            control.agregarProducto(productoSeleccionado, cantidad);

        } catch (NumberFormatException e) {
            muestraMensajeError("Ingresa un número entero válido en la cantidad.");
        }
    }

    @FXML
    private void handleNuevaVenta() {
        if (control != null) {
            control.iniciarVenta();
            tablaCarrito.getItems().clear();
            lblTotal.setText("$0.00");
        }
    }

    @FXML
    private void handleCerrar() {
        stage.close();
    }
}
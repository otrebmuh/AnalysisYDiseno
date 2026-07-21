package mx.uam.ayd.proyecto.presentacion.ControlHistorialMovimientos;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uam.ayd.proyecto.negocio.modelo.MovimientoInventario;

/**
 * Ventana para consultar el historial de movimientos de inventario.
 *
 * @author Yamelin, Guillermo, Dydier, Yael, Sheyla
 */
@Component
public class VentanaHistorialMovimientos {

    private Stage escenario;

    private ControlHistorialMovimientos control;

    @FXML
    private TableView<MovimientoInventario> tblMovimientos;

    @FXML
    private TableColumn<MovimientoInventario, Long> colId;

    @FXML
    private TableColumn<MovimientoInventario, String> colFecha;

    @FXML
    private TableColumn<MovimientoInventario, String> colTipo;

    @FXML
    private TableColumn<MovimientoInventario, String> colProducto;

    @FXML
    private TableColumn<MovimientoInventario, Integer> colCantidad;

    @FXML
    private TableColumn<MovimientoInventario, Integer> colExistenciaAnterior;

    @FXML
    private TableColumn<MovimientoInventario, Integer> colExistenciaActual;

    @FXML
    private TextField txtBuscar;

    /**
     * Formato utilizado para mostrar la fecha y hora.
     */
    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Asigna el controlador de la historia de usuario.
     *
     * @param control controlador de historial de movimientos
     */
    public void setControl(ControlHistorialMovimientos control) {
        this.control = control;
    }

    /**
     * Carga y muestra la ventana.
     */
    public void muestra() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/historial-movimientos.fxml"));

            loader.setController(this);

            Parent root = loader.load();

            escenario = new Stage();
            escenario.setTitle("Historial de Movimientos");
            escenario.setScene(new Scene(root));

            inicializaTabla();

            escenario.show();

        } catch (IOException e) {

            System.err.println(
                    "No se pudo cargar la ventana de historial de movimientos.");

            e.printStackTrace();
        }
    }

    /**
     * Configura las columnas de la tabla.
     */
    private void inicializaTabla() {

        colId.setCellValueFactory(data ->
                new SimpleLongProperty(
                        data.getValue().getIdMovimiento()).asObject());

        colFecha.setCellValueFactory(data -> {

            MovimientoInventario movimiento = data.getValue();

            if (movimiento.getFecha() == null) {
                return new SimpleStringProperty("");
            }

            return new SimpleStringProperty(
                    movimiento.getFecha().format(formatoFecha));
        });

        colTipo.setCellValueFactory(data -> {

            String tipoMovimiento =
                    data.getValue().getTipoMovimiento();

            if (tipoMovimiento == null) {
                tipoMovimiento = "";
            }

            return new SimpleStringProperty(tipoMovimiento);
        });

        colProducto.setCellValueFactory(data -> {

            MovimientoInventario movimiento = data.getValue();

            if (movimiento.getProducto() == null
                    || movimiento.getProducto().getNombre() == null) {

                return new SimpleStringProperty(
                        "Producto no especificado");
            }

            return new SimpleStringProperty(
                    movimiento.getProducto().getNombre());
        });

        colCantidad.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getCantidad()).asObject());

        colExistenciaAnterior.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getExistenciaAnterior()).asObject());

        colExistenciaActual.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getExistenciaActual()).asObject());
    }

    /**
     * Coloca los movimientos recibidos en la tabla.
     *
     * @param movimientos lista de movimientos
     */
    public void muestraMovimientos(
            List<MovimientoInventario> movimientos) {

        if (movimientos == null) {
            tblMovimientos.setItems(FXCollections.observableArrayList());
            return;
        }

        tblMovimientos.setItems(
                FXCollections.observableArrayList(movimientos));
    }

    /**
     * Busca movimientos utilizando el texto escrito por el usuario.
     */
    @FXML
    private void buscar() {

        if (control == null) {
            return;
        }

        control.buscarMovimiento(txtBuscar.getText());
    }

    /**
     * Limpia el filtro y vuelve a cargar todos los movimientos.
     */
    @FXML
    private void actualizar() {

        txtBuscar.clear();

        if (control == null) {
            return;
        }

        control.cargarMovimientos();
    }

    /**
     * Consulta el detalle del movimiento seleccionado.
     */
    @FXML
    private void verDetalle() {

        MovimientoInventario movimientoSeleccionado =
                tblMovimientos.getSelectionModel().getSelectedItem();

        if (movimientoSeleccionado == null || control == null) {
            return;
        }

        control.consultarDetalleMovimiento(
                movimientoSeleccionado.getIdMovimiento());
    }

    /**
     * Cierra la ventana.
     */
    @FXML
    private void cerrar() {

        if (escenario != null) {
            escenario.close();
        }
    }
}
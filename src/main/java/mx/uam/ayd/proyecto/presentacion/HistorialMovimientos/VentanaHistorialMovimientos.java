package mx.uam.ayd.proyecto.presentacion.HistorialMovimientos;

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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uam.ayd.proyecto.negocio.modelo.MovimientoInventario;

/**
 * Ventana para consultar el historial de movimientos de inventario.
 *
 * @author Yael Mora Simón
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
    private TableColumn<MovimientoInventario, Integer>
            colExistenciaAnterior;

    @FXML
    private TableColumn<MovimientoInventario, Integer>
            colExistenciaActual;

    @FXML
    private TextField txtBuscar;

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Asigna el controlador de la historia de usuario.
     *
     * @param control controlador del historial
     */
    public void setControl(ControlHistorialMovimientos control) {
        this.control = control;
    }

    /**
     * Carga el archivo FXML y muestra la ventana.
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
            escenario.show();

        } catch (IOException e) {

            System.err.println(
                    "No se pudo cargar la ventana "
                    + "de historial de movimientos.");

            e.printStackTrace();
        }
    }

    /**
     * Se ejecuta automáticamente después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        inicializaTabla();
    }

    /**
     * Configura las columnas de la tabla.
     */
    private void inicializaTabla() {

        colId.setCellValueFactory(data ->
                new SimpleLongProperty(
                        data.getValue()
                                .getIdMovimiento())
                        .asObject());

        colFecha.setCellValueFactory(data -> {

            MovimientoInventario movimiento =
                    data.getValue();

            if (movimiento.getFecha() == null) {
                return new SimpleStringProperty("");
            }

            return new SimpleStringProperty(
                    movimiento.getFecha()
                            .format(formatoFecha));
        });

        colTipo.setCellValueFactory(data -> {

            String tipoMovimiento =
                    data.getValue()
                            .getTipoMovimiento();

            if (tipoMovimiento == null) {
                tipoMovimiento = "";
            }

            return new SimpleStringProperty(
                    tipoMovimiento);
        });

        colProducto.setCellValueFactory(data -> {

            MovimientoInventario movimiento =
                    data.getValue();

            if (movimiento.getProducto() == null
                    || movimiento.getProducto()
                            .getNombre() == null) {

                return new SimpleStringProperty(
                        "Producto no especificado");
            }

            return new SimpleStringProperty(
                    movimiento.getProducto()
                            .getNombre());
        });

        colCantidad.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue()
                                .getCantidad())
                        .asObject());

        colExistenciaAnterior.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue()
                                .getExistenciaAnterior())
                        .asObject());

        colExistenciaActual.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue()
                                .getExistenciaActual())
                        .asObject());
    }

    /**
     * Muestra los movimientos recibidos en la tabla.
     *
     * @param movimientos lista de movimientos
     */
    public void muestraMovimientos(
            List<MovimientoInventario> movimientos) {

        if (tblMovimientos == null) {
            return;
        }

        if (movimientos == null) {

            tblMovimientos.setItems(
                    FXCollections.observableArrayList());

            return;
        }

        tblMovimientos.setItems(
                FXCollections.observableArrayList(
                        movimientos));
    }

    /**
     * Busca movimientos utilizando el texto escrito.
     */
    @FXML
    private void buscar() {

        if (control == null) {
            return;
        }

        control.buscarMovimiento(
                txtBuscar.getText());
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
     * Muestra el detalle del movimiento seleccionado.
     */
    @FXML
    private void verDetalle() {

        MovimientoInventario movimientoSeleccionado =
                tblMovimientos
                        .getSelectionModel()
                        .getSelectedItem();

        if (movimientoSeleccionado == null) {

            muestraAlerta(
                    Alert.AlertType.WARNING,
                    "Movimiento no seleccionado",
                    "Selecciona un movimiento "
                    + "para consultar su detalle.");

            return;
        }

        if (control == null) {
            return;
        }

        MovimientoInventario movimiento =
                control.consultarDetalleMovimiento(
                        movimientoSeleccionado
                                .getIdMovimiento());

        if (movimiento == null) {

            muestraAlerta(
                    Alert.AlertType.ERROR,
                    "Movimiento no encontrado",
                    "No fue posible recuperar "
                    + "el movimiento seleccionado.");

            return;
        }

        muestraDetalleMovimiento(movimiento);
    }

    /**
     * Muestra los datos completos de un movimiento.
     *
     * @param movimiento movimiento consultado
     */
    private void muestraDetalleMovimiento(
            MovimientoInventario movimiento) {

        String fecha;

        if (movimiento.getFecha() == null) {
            fecha = "No especificada";
        } else {
            fecha = movimiento.getFecha()
                    .format(formatoFecha);
        }

        String producto;

        if (movimiento.getProducto() == null
                || movimiento.getProducto()
                        .getNombre() == null) {

            producto = "No especificado";

        } else {

            producto = movimiento.getProducto()
                    .getNombre();
        }

        String tipo = movimiento.getTipoMovimiento();

        if (tipo == null || tipo.trim().isEmpty()) {
            tipo = "No especificado";
        }

        String observacion =
                movimiento.getObservacion();

        if (observacion == null
                || observacion.trim().isEmpty()) {

            observacion = "Sin observaciones";
        }

        String detalle =
                "ID: "
                + movimiento.getIdMovimiento()
                + "\nFecha: "
                + fecha
                + "\nProducto: "
                + producto
                + "\nTipo de movimiento: "
                + tipo
                + "\nCantidad: "
                + movimiento.getCantidad()
                + "\nExistencia anterior: "
                + movimiento.getExistenciaAnterior()
                + "\nExistencia actual: "
                + movimiento.getExistenciaActual()
                + "\nObservación: "
                + observacion;

        Alert alerta =
                new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Detalle del movimiento");
        alerta.setHeaderText(
                "Información del movimiento seleccionado");
        alerta.setContentText(detalle);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta.
     *
     * @param tipo tipo de alerta
     * @param titulo título de la alerta
     * @param mensaje mensaje de la alerta
     */
    private void muestraAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
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
package mx.uam.ayd.proyecto.presentacion.registrarMercancia;

import java.io.IOException;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

@Component
public class VentanaRegistrarMercancia {

    private Stage stage;

    private ControlRegistrarMercancia control;

    private boolean initialized = false;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblExistencia;

    private Producto productoActual;

    public VentanaRegistrarMercancia() {

    }

    private void initializeUI() {

        if (initialized) {
            return;
        }

        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::initializeUI);
            return;
        }

        try {

            stage = new Stage();

            stage.setTitle("Registrar mercancía");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/vista-registrar-mercancia.fxml"));

            loader.setController(this);

            Scene scene = new Scene(loader.load(), 550, 300);

            stage.setScene(scene);

            initialized = true;

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void setControlRegistrarMercancia(ControlRegistrarMercancia control) {
        this.control = control;
    }

    public void muestra() {

        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::muestra);
            return;
        }

        initializeUI();

        stage.show();

    }

    @FXML
    private void handleBuscar() {

        productoActual = control.buscarProducto(txtClave.getText());

        if (productoActual != null) {

            lblNombre.setText(productoActual.getNombre());

            lblExistencia.setText(String.valueOf(productoActual.getExistenciaActual()));

        } else {

            lblNombre.setText("No encontrado");

            lblExistencia.setText("-");

        }

    }

    @FXML
    private void handleRegistrar() {

        if (productoActual == null) {
            return;
        }

        int cantidad = Integer.parseInt(txtCantidad.getText());

        boolean exito = control.registrarMercancia(
                productoActual.getClave(),
                cantidad);

        if (exito) {

              productoActual = control.buscarProducto(productoActual.getClave());

              lblExistencia.setText(
                   String.valueOf(productoActual.getExistenciaActual()));


        }

    }

    @FXML
    private void handleCerrar() {

        stage.close();

    }

}
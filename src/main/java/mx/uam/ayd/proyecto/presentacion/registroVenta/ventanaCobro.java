
package mx.uam.ayd.proyecto.presentacion.registroVenta;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class VentanaCobro {

    @Autowired
    @Lazy
    private ControlRegistroVenta control;
    
    private Stage stage;
    private boolean initialized = false;

    // Elementos FXML vinculados
    @FXML private Label lblTotalCobro;
    @FXML private TextField txtPago;
    @FXML private Label lblCambio;

    // Constructor sin inicialización
    public VentanaCobro() {
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
            // Carga del archivo FXML de la pantalla de cobro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-cobro.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            
            stage = new Stage();
            stage.setTitle("Proceso de Cobro");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal

            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra la ventana y prepara los campos para el cobro
     */
    public void muestra(double total) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestra(total));
            return;
        }

        initializeUI();
        
        // Limpieza y preparación de etiquetas
        txtPago.setText("");
        lblCambio.setText("$0.00");
        lblTotalCobro.setText(String.format("$%.2f", total));
        
        stage.show();
    }

    /**
     * Actualiza el cambio en pantalla
     */
    public void actualizaCambio(double cambio, String mensaje) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.actualizaCambio(cambio, mensaje));
            return;
        }
        if (mensaje != null) {
            lblCambio.setText(mensaje);
        } else {
            lblCambio.setText(String.format("$%.2f", cambio));
        }
    }

    public void muestraDialogoConMensaje(String mensaje) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestraDialogoConMensaje(mensaje));
            return;
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sistema de Ventas");
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
    private void onCalcularCambio() {
        try {
            if (!txtPago.getText().isEmpty()) {
                double efectivo = Double.parseDouble(txtPago.getText());
                // Delega al control para validar y calcular [3]
                control.calcularCambio(efectivo);
            }
        } catch (NumberFormatException e) {
            actualizaCambio(0, "Monto inválido");
        }
    }

    @FXML
    private void onRegistrarVenta() {
        if (txtPago.getText().isEmpty()) {
            muestraDialogoConMensaje("Ingrese el monto recibido.");
            return;
        }
        try {
            double efectivo = Double.parseDouble(txtPago.getText());
            // El controlador finalizará la compra y actualizará el inventario
            control.finalizarCompra(efectivo);
        } catch (NumberFormatException e) {
            muestraDialogoConMensaje("Ingrese un número válido.");
        }
    }
}
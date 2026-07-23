package mx.uam.ayd.proyecto.presentacion.devolucionProducto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.io.IOException;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;

/**
 * Ventana para la devolución de productos dañados (HU-10).
 * 
 * @author Yamelin Larios Nepomuseno
 */
@Component
public class VentanaDevolucionProducto {

    private Stage stage;
    private ControlDevolucionProducto control;
    
    @FXML
    private TextField textFieldIdProducto;
    
    @FXML
    private TextField textFieldNombreProducto;
    
    @FXML
    private TextField textFieldCantidad;
    
    @FXML
    private TextArea textAreaMotivo;
    
    private boolean initialized = false;

    /**
     * Constructor sin inicialización de UI para evitar conflictos en hilos de JavaFX.
     */
    public VentanaDevolucionProducto() {
        // No inicializar la UI en el constructor
    }
    
    /**
     * Inicializa los componentes de la interfaz de usuario en el hilo de JavaFX.
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
            stage = new Stage();
            stage.setTitle("Devolución de Producto Dañado");
            
            // Carga del archivo FXML correspondiente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-devolucion-producto.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), 400, 350);
            stage.setScene(scene);
            
            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Establece el controlador asociado a esta ventana.
     * 
     * @param control El controlador de la vista
     */
    public void setControl(ControlDevolucionProducto control) {
        this.control = control;
    }
    
    /**
     * Muestra la ventana y resetea los campos.
     */
    public void muestra() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::muestra);
            return;
        }
        
        initializeUI();
        limpiarCampos();
        stage.show();
    }
    
    /**
     * Muestra en la interfaz los datos del producto encontrado.
     * 
     * @param producto Producto obtenido de la base de datos
     */
    public void muestraProductoEncontrado(Producto producto) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestraProductoEncontrado(producto));
            return;
        }
        
        if (producto != null) {
            if (textFieldNombreProducto != null) {
                textFieldNombreProducto.setText(producto.getNombre());
            }
        } else {
            muestraError("No se encontró ningún producto con el ID especificado.");
            if (textFieldNombreProducto != null) {
                textFieldNombreProducto.setText("");
            }
        }
    }
    
    /**
     * Notifica un resultado exitoso al usuario y limpia el formulario.
     * 
     * @param mensaje Mensaje explicativo
     */
    public void muestraDevolucionExitosa(String mensaje) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestraDevolucionExitosa(mensaje));
            return;
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        
        limpiarCampos();
    }
    
    /**
     * Muestra una alerta de error al usuario.
     * 
     * @param mensaje Mensaje de error
     */
    public void muestraError(String mensaje) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestraError(mensaje));
            return;
        }
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    /**
     * Oculta o muestra la ventana.
     * 
     * @param visible true para mostrar, false para ocultar
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
    
    private void limpiarCampos() {
        if (textFieldIdProducto != null) textFieldIdProducto.setText("");
        if (textFieldNombreProducto != null) textFieldNombreProducto.setText("");
        if (textFieldCantidad != null) textFieldCantidad.setText("");
        if (textAreaMotivo != null) textAreaMotivo.setText("");
    }
    
    // Manejadores de eventos FXML
    
    @FXML
    private void handleBuscarProducto() {
        String idText = textFieldIdProducto.getText();
        if (idText == null || idText.trim().isEmpty()) {
            muestraError("Ingresa un ID de producto válido.");
            return;
        }
        
        try {
            long idProducto = Long.parseLong(idText.trim());
            control.buscarProducto(idProducto);
        } catch (NumberFormatException e) {
            muestraError("El ID del producto debe ser un número entero.");
        }
    }
    
    @FXML
    private void handleRegistrarDevolucion() {
        String idText = textFieldIdProducto.getText();
        String cantText = textFieldCantidad.getText();
        String motivo = textAreaMotivo != null ? textAreaMotivo.getText() : "";
        
        if (idText == null || idText.trim().isEmpty() || cantText == null || cantText.trim().isEmpty()) {
            muestraError("Por favor completa los campos de ID y Cantidad.");
            return;
        }
        
        try {
            long idProducto = Long.parseLong(idText.trim());
            int cantidad = Integer.parseInt(cantText.trim());
            
            if (cantidad <= 0) {
                muestraError("La cantidad a devolver debe ser mayor a 0.");
                return;
            }
            
            control.registrarDevolucion(idProducto, cantidad, motivo);
            
        } catch (NumberFormatException e) {
            muestraError("Asegúrate de ingresar números válidos en los campos de ID y Cantidad.");
        }
    }
    
    @FXML
    private void handleCancelar() {
        setVisible(false);
    }
}
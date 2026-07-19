package mx.uam.ayd.proyecto.presentacion.registroVenta;

import java.io.IOException;

import org.springframework.stereotype.Component;

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

/**
 * Vista secundaria para el proceso de cobro (HU-04).
 * Implementa el Escenario 1: Calcular el cambio en pago con efectivo.
 */
@Component
public class VentanaCobro {

	private Stage stage;
	private ControlRegistroVenta control;
	private boolean initialized = false;

	// Elementos FXML vinculados por id (Fuente 5)
	@FXML private Label lblTotalCobro;
	@FXML private TextField txtPago;
	@FXML private Label lblCambio;

	public VentanaCobro() {
		// Constructor sin inicialización de UI para seguir el estándar del profesor
	}

	/**
	 * Establece el controlador asociado a esta ventana para la comunicación bidireccional
	 */
	public void setControl(ControlRegistroVenta control) {
		this.control = control;
	}

	/**
	 * Inicializa los componentes de la interfaz en el hilo de la aplicación JavaFX
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
			// Carga del archivo FXML específico de cobro (Fuente 5, Código 2)
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-cobro.fxml"));
			loader.setController(this);
			Parent root = loader.load();
			
			stage = new Stage();
			stage.setTitle("Proceso de Cobro");
			stage.setScene(new Scene(root));
			
			// Se configura como modalidad de aplicación para bloquear la ventana principal
			stage.initModality(Modality.APPLICATION_MODAL);

			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muestra la ventana de cobro y establece el total a pagar
	 * @param total El monto total calculado en el carrito
	 */
	public void muestra(double total) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(total));
			return;
		}

		initializeUI();
		
		// Limpiar campos para una nueva operación (Escenario 2)
		txtPago.setText("");
		lblCambio.setText("$0.00");
		
		// Establecer el total a pagar en la etiqueta correspondiente
		lblTotalCobro.setText(String.format("$%.2f", total));
		
		stage.show();
	}

	/**
	 * Actualiza visualmente el cambio a entregar al cliente (Escenario 1)
	 * @param cambio El monto a regresar
	 * @param mensaje Opcional, mensaje de error si el efectivo es insuficiente
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

	/**
	 * Muestra un diálogo de información (Usado para el mensaje de venta exitosa)
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

	/**
	 * Controla la visibilidad de la ventana
	 */
	public void setVisible(boolean visible) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.setVisible(visible));
			return;
		}
		if (visible) stage.show();
		else stage.hide();
	}

	// --- Manejadores de Eventos FXML (Fuente 5) ---

	/**
	 * Se activa cada vez que el usuario escribe en el campo de pago.
	 * Delega el cálculo de la diferencia al controlador (Escenario 1).
	 */
	@FXML
	private void onCalcularCambio() {
		try {
			if (!txtPago.getText().isEmpty()) {
				double efectivo = Double.parseDouble(txtPago.getText());
				control.calcularCambio(efectivo);
			}
		} catch (NumberFormatException e) {
			actualizaCambio(0, "Monto inválido");
		}
	}

	/**
	 * Se activa al presionar el botón de finalizar venta.
	 * Delega el registro definitivo y actualización de stock al controlador (RN-10).
	 */
	@FXML
	private void onRegistrarVenta() {
		if (txtPago.getText().isEmpty()) {
			muestraDialogoConMensaje("Debe ingresar la cantidad con la que pagó el cliente.");
			return;
		}
		try {
			double efectivo = Double.parseDouble(txtPago.getText());
			control.finalizarCompra(efectivo);
		} catch (NumberFormatException e) {
			muestraDialogoConMensaje("Por favor, ingrese un monto numérico válido.");
		}
	}
}
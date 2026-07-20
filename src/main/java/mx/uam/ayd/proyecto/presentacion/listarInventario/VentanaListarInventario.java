package mx.uam.ayd.proyecto.presentacion.listarInventario;

import java.util.List;
import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Inventario;

/**
 * Ventana para listar inventario usando JavaFX con FXML
 */
@Component
public class VentanaListarInventario {

    private Stage stage;

    @FXML
    private TableView<Inventario> tableInventario;

    @FXML
    private TableColumn<Inventario, Long> idColumn;

    @FXML
    private TableColumn<Inventario, Integer> existenciaActualColumn;

    @FXML
    private TableColumn<Inventario, Integer> existenciaDisponibleColumn;

    @FXML
    private TableColumn<Inventario, Integer> stockMinimoColumn;

    // Relación con el controlador
    private ControlListarInventario control;

    private boolean initialized = false;

    /**
     * Constructor sin inicializar la interfaz.
     */
    public VentanaListarInventario() {
        // No inicializar componentes JavaFX en el constructor
    }

    /**
     * Inicializa la interfaz gráfica.
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
            stage.setTitle("Lista de Inventario");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/ventana-listar-inventario.fxml"));

            loader.setController(this);

            Scene scene = new Scene(loader.load(), 600, 400);

            stage.setScene(scene);

            idColumn.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
            existenciaActualColumn.setCellValueFactory(new PropertyValueFactory<>("existenciaActual"));
            existenciaDisponibleColumn.setCellValueFactory(new PropertyValueFactory<>("existenciaDisponible"));
            stockMinimoColumn.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));

            initialized = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asocia el controlador con la ventana.
     *
     * @param control controlador de la HU
     */
    public void setControlListarInventario(ControlListarInventario control) {
        this.control = control;
    }

    /**
     * Muestra la ventana con los productos de bajo stock.
     *
     * @param inventarios Lista de inventarios.
     */
    public void muestra(List<Inventario> inventarios) {

        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestra(inventarios));
            return;
        }

        initializeUI();

        ObservableList<Inventario> data = FXCollections.observableArrayList(inventarios);

        tableInventario.setItems(data);

        stage.show();
    }

    /**
     * Cierra la ventana.
     */
    @FXML
    private void handleCerrar() {
        stage.close();
    }
}
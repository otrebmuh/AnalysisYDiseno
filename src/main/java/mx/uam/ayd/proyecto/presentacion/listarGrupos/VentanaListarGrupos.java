package mx.uam.ayd.proyecto.presentacion.listarGrupos;

import java.util.List;

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
import javafx.beans.property.SimpleIntegerProperty;

import org.springframework.stereotype.Component;

import java.io.IOException;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@Component
public class VentanaListarGrupos {

    private Stage stage;
    
    @FXML
    private TableView<Grupo> tableGrupos;
    
    @FXML
    private TableColumn<Grupo, Long> idColumn;
    
    @FXML
    private TableColumn<Grupo, String> nombreColumn;
    
    @FXML
    private TableColumn<Grupo, Integer> usuariosColumn;
    
    private ControlListarGrupos control;
    private ObservableList<Grupo> gruposData;
    private boolean initialized = false;

    /**
     * Constructor without UI initialization
     */
    public VentanaListarGrupos() {
        // Don't initialize JavaFX components in constructor
        gruposData = FXCollections.observableArrayList();
    }
    
    /**
     * Initialize UI components on the JavaFX application thread
     */
    private void initializeUI() {
        if (initialized) {
            return;
        }
        
        // Create UI only if we're on JavaFX thread
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::initializeUI);
            return;
        }
        
        try {
            stage = new Stage();
            stage.setTitle("Lista de Grupos");
            
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-listar-grupos.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), 450, 400);
            stage.setScene(scene);
            
            // Configure columns after FXML is loaded
            idColumn.setCellValueFactory(new PropertyValueFactory<>("idGrupo"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            usuariosColumn.setCellValueFactory(cellData -> {
                int numUsuarios = cellData.getValue().getUsuarios().size();
                return new SimpleIntegerProperty(numUsuarios).asObject();
            });
            
            tableGrupos.setItems(gruposData);
            
            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Establece el controlador asociado a esta ventana
     * 
     * @param control El controlador asociado
     */
    public void setControlListarGrupos(ControlListarGrupos control) {
        this.control = control;
    }
    
    /**
     * Muestra la ventana y carga los grupos
     * 
     * @param grupos La lista de grupos a mostrar
     */
    public void muestra(List<Grupo> grupos) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestra(grupos));
            return;
        }
        
        if (!initialized) {
            initializeUI();
        }
        
        // Update data
        Platform.runLater(() -> {
            gruposData.clear();
            gruposData.addAll(grupos);
        });
        
        if (!stage.isShowing()) {
            stage.show();
        }
    }
    
    // FXML Event Handlers
    
    @FXML
    private void handleCerrar() {
        stage.close();
    }
} 
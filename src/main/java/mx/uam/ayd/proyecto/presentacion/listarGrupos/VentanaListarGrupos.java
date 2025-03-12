package mx.uam.ayd.proyecto.presentacion.listarGrupos;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@Component
public class VentanaListarGrupos {

    private Stage stage;
    private TableView<Grupo> tableGrupos;
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
        
        stage = new Stage();
        stage.setTitle("Lista de Grupos");
        
        // Create UI components
        Label lblTitulo = new Label("Grupos Registrados");
        
        // Create table
        tableGrupos = new TableView<>();
        
        // Configure columns
        TableColumn<Grupo, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idGrupo"));
        
        TableColumn<Grupo, String> nombreColumn = new TableColumn<>("Nombre");
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<Grupo, Integer> usuariosColumn = new TableColumn<>("Número de Usuarios");
        usuariosColumn.setCellValueFactory(cellData -> {
            int numUsuarios = cellData.getValue().getUsuarios().size();
            return new SimpleIntegerProperty(numUsuarios).asObject();
        });
        
        tableGrupos.getColumns().addAll(idColumn, nombreColumn, usuariosColumn);
        tableGrupos.setItems(gruposData);
        
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(e -> stage.close());
        
        // Layout
        VBox vboxTop = new VBox(10);
        vboxTop.setPadding(new Insets(10));
        vboxTop.getChildren().add(lblTitulo);
        
        VBox vboxBottom = new VBox(10);
        vboxBottom.setPadding(new Insets(10));
        vboxBottom.getChildren().add(btnCerrar);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vboxTop);
        borderPane.setCenter(tableGrupos);
        borderPane.setBottom(vboxBottom);
        
        Scene scene = new Scene(borderPane, 450, 400);
        stage.setScene(scene);
        
        initialized = true;
    }
    
    /**
     * Muestra la ventana y carga los grupos
     * 
     * @param control El controlador asociado
     * @param grupos La lista de grupos a mostrar
     */
    public void muestra(ControlListarGrupos control, List<Grupo> grupos) {
        this.control = control;
        
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.muestra(control, grupos));
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
} 
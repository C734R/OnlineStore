package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaArticulos;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public abstract class VistaArticulosJavaFX extends VistaBaseJavaFX implements IVistaArticulos {

    private ControlArticulos cArticulos;

    @FXML
    private Button btnAddArticulo;
    @FXML
    private Button btnModArticulo;
    @FXML
    private Button btnDeleteArticulo;
    @FXML
    private Button btnListArticulo;

    @FXML
    private TableView<ArticuloDTO> tablaArticulos;
    @FXML
    private TableColumn<ClienteDTO, String> columnaID;
    @FXML
    private TableColumn<ClienteDTO, String> columnaCodigo;
    @FXML
    private TableColumn<ClienteDTO, String> columnaDescripcion;
    @FXML
    private TableColumn<ClienteDTO, String> columnaPrecio;
    @FXML
    private TableColumn<ClienteDTO, String> columnaPreparacion;

    @FXML
    private TextField txtIDArticulo;
    @FXML
    private TextField txtCodigoArticulo;
    @FXML
    private TextField txtDescripcionArticulo;
    @FXML
    private TextField txtPrecioArticulo;
    @FXML
    private TextField txtPreparacionArticulo;
    @FXML
    private TextField txtStockArticulo;


    public VistaArticulosJavaFX() {
        this.cArticulos = cArticulos;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Suscripciones botones
        btnAddArticulo.setOnAction(event -> addArticulo());
        btnModArticulo.setOnAction(event -> removeArticulo());
        btnDeleteArticulo.setOnAction(event -> listarArticulo());
        btnListArticulo.setOnAction(event -> modArticulo());
    }

    private void listarArticulo() {
        cArticulos.showListArticulos();
        
    }

    private void addArticulo() {
        cArticulos.addArticulo();
    }

    private void removeArticulo() {
        cArticulos.removeArticulo();
    }

    private void modArticulo() {
        cArticulos.updateArticulo();
    }

    @Override
    public float askPrecio(float min, float max) {
        return 0;
    }

    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        tablaArticulos.getItems().clear();
        tablaArticulos.getItems().addAll(articulosDTO);
    }
}

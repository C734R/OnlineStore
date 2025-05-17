package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
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
    private TableColumn<ArticuloDTO, String> columnaID;
    @FXML
    private TableColumn<ArticuloDTO, String> columnaCodigo;
    @FXML
    private TableColumn<ArticuloDTO, String> columnaDescripcion;
    @FXML
    private TableColumn<ArticuloDTO, String> columnaPrecio;
    @FXML
    private TableColumn<ArticuloDTO, String> columnaPreparacion;

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


    public VistaArticulosJavaFX(ControlArticulos cArticulos) {
        this.cArticulos = cArticulos;
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Suscripciones botones
        btnAddArticulo.setOnAction(event -> addArticulo());
        btnModArticulo.setOnAction(event -> modArticulo());
        btnDeleteArticulo.setOnAction(event -> removeArticulo());
        btnListArticulo.setOnAction(event -> listarArticulo());
    }

    private void listarArticulo() {
        tablaArticulos.getItems().clear();
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
// Getters y Setters
@Override
public float askPrecio(float min, float max) {
    String input = txtPrecioArticulo.getText().trim();

    if (input.isEmpty()) {
        showError("El campo de precio está vacío.");
        return -99999f;
    }

    try {
        float precio = Float.parseFloat(input);
        if (precio >= min && precio <= max) {
            return precio;
        } else {
            showError("Precio fuera de rango (" + min + " - " + max + ").");
        }
    } catch (NumberFormatException e) {
        showError("Formato inválido para el precio. Usa solo números.");
    }

    return -99999f;
}


    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        tablaArticulos.getItems().clear();
        tablaArticulos.getItems().addAll(articulosDTO);
    }

    public TableView<ArticuloDTO> getTablaArticulos() {
        return tablaArticulos;
    }

    public TextField getTxtIDArticulo() {
        return txtIDArticulo;
    }

    public TextField getTxtCodigoArticulo() {
        return txtCodigoArticulo;
    }

    public TextField getTxtDescripcionArticulo() {
        return txtDescripcionArticulo;
    }

    public TextField getTxtPrecioArticulo() {
        return txtPrecioArticulo;
    }

    public TextField getTxtPreparacionArticulo() {
        return txtPreparacionArticulo;
    }

    public TextField getTxtStockArticulo() {
        return txtStockArticulo;
    }

    public Button getBtnAddArticulo() {
        return btnAddArticulo;
    }

    public Button getBtnModArticulo() {
        return btnModArticulo;
    }

    public Button getBtnDeleteArticulo() {
        return btnDeleteArticulo;
    }

    public Button getBtnListArticulo() {
        return btnListArticulo;
    }

    public ControlArticulos getControlArticulos() {
        return cArticulos;
    }

}

package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaArticulos;
import java.util.List;
import java.util.Map;

public class VistaArticulosJavaFX extends VistaBaseJavaFX implements IVistaArticulos {

    @FXML private Button btnVolver;
    @FXML private Button btnAddArticulo;
    @FXML private Button btnModArticulo;
    @FXML private Button btnDeleteArticulo;
    @FXML private Button btnListArticulo;

    @FXML private TableView<ArticuloDTO> tblArticulos;
    @FXML private TableColumn<ArticuloDTO, Integer> columnaID;
    @FXML private TableColumn<ArticuloDTO, String> columnaCodigo;
    @FXML private TableColumn<ArticuloDTO, String> columnaDescripcion;
    @FXML private TableColumn<ArticuloDTO, Float> columnaPrecio;
    @FXML private TableColumn<ArticuloDTO, Integer> columnaPreparacion;

    @FXML
    public void initialize() {
        columnaID.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<Integer>());
        columnaCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo()));
        columnaDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        columnaPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrecio()));
        columnaPreparacion.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMinutosPreparacion()));
    }

    @Override
    public float askPrecio(float min, float max) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Introducir Precio");
        dialog.setHeaderText("Introduce un precio entre " + min + " y " + max);
        dialog.setContentText("Precio:");

        while (true) {
            var result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    float value = Float.parseFloat(result.get());
                    if (value >= min && value <= max) return value;
                } catch (NumberFormatException ignored) {}
                dialog.setHeaderText("Valor inválido. Introduce un número entre " + min + " y " + max);
            } else {
                return -1f; // Cancelado
            }
        }
    }

    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        tblArticulos.getItems().setAll(articulosDTO);
    }

    @Override
    public void showListArticulosStock(Map<ArticuloDTO, Integer> articuloStockMap) {
        StringBuilder builder = new StringBuilder();
        articuloStockMap.forEach((articulo, stock) -> {
            builder.append(articulo.getCodigo()).append(" - ")
                    .append(articulo.getDescripcion()).append(" : ")
                    .append("Stock: ").append(stock).append("\n");
        });

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stock Artículos");
        alert.setHeaderText("Listado de artículos con stock:");
        alert.setContentText(builder.toString());
        alert.showAndWait();
    }

    @Override
    public void showListArticulosNumerada(List<ArticuloDTO> articulosDTO) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < articulosDTO.size(); i++) {
            builder.append(i + 1).append(". ").append(articulosDTO.get(i).getDescripcion()).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lista Numerada");
        alert.setHeaderText("Artículos:");
        alert.setContentText(builder.toString());
        alert.showAndWait();
    }

    @Override
    public void showArticulo(ArticuloDTO articuloDTO) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles del Artículo");
        alert.setHeaderText("Información del artículo seleccionado:");
        alert.setContentText(
                "ID: " + articuloDTO.getId() + "\n" +
                        "Código: " + articuloDTO.getCodigo() + "\n" +
                        "Descripción: " + articuloDTO.getDescripcion() + "\n" +
                        "Precio: " + articuloDTO.getPrecio() + "\n" +
                        "Preparación: " + articuloDTO.getMinutosPreparacion() + " min"
        );
        alert.showAndWait();
    }

    @Override
    public void showStockArticulos(Map<ArticuloDTO, Integer> articuloStockMap) {
        showListArticulosStock(articuloStockMap);
    }
    @FXML
    private void onBtnAddArticuloClick() {
        System.out.println("Añadir artículo clicado");
    }

    @FXML
    private void onBtnModArticuloClick() {
        System.out.println("Modificar artículo clicado");
    }

    @FXML
    private void onBtnDeleteArticuloClick() {
        System.out.println("Eliminar artículo clicado");
    }

    @FXML
    private void onBtnListArticuloClick() {
        System.out.println("Mostrar artículos clicado");
    }

    @FXML
    private void onBtnVolverClick() {
        System.out.println("Volver clicado");
    }

}
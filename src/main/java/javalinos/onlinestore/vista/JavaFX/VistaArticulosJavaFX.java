package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaArticulos;

import java.net.URL;
import java.util.*;

import static javalinos.onlinestore.OnlineStore.cArticulos;

public class VistaArticulosJavaFX extends VistaBaseJavaFX implements IVistaArticulos {

    @FXML private Button btnVolver;
    @FXML private Button btnAddArticulo;
    @FXML private Button btnModArticulo;
    @FXML private Button btnDeleteArticulo;
    @FXML private Button btnListArticulo;
    @FXML private Button btnListArticuloStock;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAddArticulo.setOnAction(event -> cArticulos.addArticulo());
        btnDeleteArticulo.setOnAction(event -> cArticulos.removeArticulo());
        btnModArticulo.setOnAction(event -> cArticulos.updateArticulo());
        btnListArticulo.setOnAction(event -> cArticulos.showListArticulos());
        btnListArticuloStock.setOnAction(event -> cArticulos.showStockArticulos());
        btnVolver.setOnAction(event -> GestorEscenas.cerrarVentana("GestionArticulos"));
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
                return -1f;
            }
        }
    }

    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        showListGenerica(articulosDTO,"ARTÍCULOS", true, false, false);
    }

    public String askCodigo() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Introducir Código");
        dialog.setHeaderText("Introduce el código del artículo:");
        dialog.setContentText("Código:");

        var result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void showListArticulosStock(Map<ArticuloDTO, Integer> articuloStockMap) {
        StringBuilder builder = new StringBuilder();
        articuloStockMap.forEach((articulo, stock)
                -> {
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
        showListGenerica(articulosDTO,"ARTÍCULOS NUMERADOS", true, true, true);
    }


    public void showArticulo(ArticuloDTO articuloDTO) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles del Artículo");
        alert.setHeaderText("Información del artículo seleccionado:");
        alert.setContentText(articuloDTO.toString());
        alert.showAndWait();
    }

    @Override
    public void showStockArticulos(Map<ArticuloDTO, Integer> articuloStockMap) {
        showListArticulosStock(articuloStockMap);
    }

    @Override
    public int askRemoveArticulo(List<ArticuloDTO> articulosDTO) {

        Map<String, Integer> mapa = new LinkedHashMap<>();
        int index = 1;
        for (ArticuloDTO articulo : articulosDTO) {
            mapa.put(articulo.getCodigo() + " - " + articulo.getDescripcion(), index);
            index++;
        }

        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int seleccion = 0;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el artículo a eliminar");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            seleccion = mapa.get(respuesta);
        }
        else return -99999;
        if (seleccion == 0) {
            showMensajePausa("Volviendo atrás...", true);
            return -99999;
        }
        return seleccion;

    }
    @Override
    public int askUpdateArticulo(List<ArticuloDTO> articulosDTO) {

        LinkedHashMap<String, Integer> mapa = new LinkedHashMap<>();
        int index = 1;
        for (ArticuloDTO articulo : articulosDTO) {
            mapa.put(articulo.getCodigo() + " - " + articulo.getDescripcion(), index);
            index++;
        }

        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int seleccion = 0;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el artículo a modificar");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            if(respuesta.isEmpty()) return -99999;
            seleccion = mapa.get(respuesta);
        }
        else return -99999;
        if (seleccion == 0) {
            showMensajePausa("Volviendo atrás...", true);
            return -99999;
        }
        return seleccion;
    }
}



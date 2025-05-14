package javalinos.onlinestore.vista.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javalinos.onlinestore.vista.Interfaces.IVistaBase;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class VistaBaseJavaFX extends Application implements IVistaBase {

    @FXML private Label lblTitulo;
    @FXML private StackPane cntCentral;

    @Override
    public void setCabecera(String cabecera) {
        lblTitulo.setText(cabecera);
    }

    @Override
    public void showCabecera() {
        lblTitulo.setDisable(false);
    }

    public void cargarVistaCentral(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node vista = loader.load();
            cntCentral.getChildren().setAll(vista);
        } catch (IOException e) {
            showError("Error al cargar vista central: " + e.getMessage());
        }
    }

    @Override
    public void showMenu(int retorno) {

    }

    @Override
    public void setListaMenu(List<String> listaMenu) {

    }

    @Override
    public int askInt(String mensaje, int min, int max, boolean reintentar, boolean sinFin, boolean error) {
        return 0;
    }

    @Override
    public Float askFloat(String mensaje, float min, float max, boolean reintentar, boolean sinFin) {
        return 0f;
    }

    @Override
    public String askString(String mensaje, int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error) {
        return "";
    }

    @Override
    public Boolean askBoolean(String mensaje, boolean reintentar, boolean maxIntentos) {
        return null;
    }

    @Override
    public void showMensaje(String mensaje, boolean salto) {
        Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
        alertaInfo.setTitle("Información");
        alertaInfo.setHeaderText(null);
        alertaInfo.setContentText(mensaje);
        alertaInfo.showAndWait();
    }

    @Override
    public void showOptions(List<String> lista, int tipoRetorno, Boolean encuadre, Boolean numeracion, Boolean opcion) {

    }

    @Override
    public <T> void showListGenerica(List<T> lista, String titulo, boolean encuadre, boolean numeracion) {

    }

    @Override
    public LocalDate askFecha(String mensaje) {
        return null;
    }

    @Override
    public void showMensajePausa(String mensaje, boolean salto) {
        Alert alertaError = new Alert(Alert.AlertType.WARNING);
        alertaError.setTitle("Advertencia");
        alertaError.setHeaderText("Atención");
        alertaError.setContentText(mensaje);
        alertaError.showAndWait();
    }

    public void showError(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("Error");
        alertaError.setHeaderText("Se ha producido un error");
        alertaError.setContentText(mensaje);
        alertaError.showAndWait();
    }

    @Override
    public String askStringOpcional(String mensaje, int maxLongitud) {
        return "";
    }

    @Override
    public Float askFloatOpcional(String mensaje, float min, float max) {
        return 0f;
    }

    @Override
    public Float askPrecioOpcional(String mensaje, float min, float max) {
        return 0f;
    }

    @Override
    public Integer askIntOpcional(String mensaje, int min, int max) {
        return 0;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}

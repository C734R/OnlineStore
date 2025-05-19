package javalinos.onlinestore.vista.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javalinos.onlinestore.vista.Interfaces.IVistaBase;

import javax.swing.text.html.parser.Parser;
import java.net.URL;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javalinos.onlinestore.OnlineStore.vFactory;
import static javalinos.onlinestore.vista.JavaFX.GestorEscenas.crearVentana;

public class VistaBaseJavaFX extends Application implements IVistaBase, Initializable {

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


    @Override
    public void showMenu(int retorno) {
        switch (retorno) {
            case 1:
                crearVentana(TipoVentana.GestionClientes, vFactory.getVClientesRaiz());
                break;
            case 2:
                crearVentana(TipoVentana.GestionArticulos, vFactory.getVArticulosRaiz());
                break;
            case 3:
                crearVentana(TipoVentana.GestionPedidos, vFactory.getVPedidosRaiz());
                break;
            default:
                break;
        }

    }

    @Override
    public void setListaMenu(List<String> listaMenu) {

    }

    @Override
    public int askInt(String mensaje, int min, int max, boolean reintentar, boolean sinFin, boolean error) {
        int integer, intentos = 0;
        if(!reintentar) intentos = 2;

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Introducción de enteros");
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText("Número entero: ");

        while(intentos < 3) {
            try {
                Optional<String> resultado = dialogo.showAndWait();

                // Comprobar si es entero
                if (resultado.isPresent()) {
                    integer = Integer.parseInt(resultado.get());
                }
                else return -99999;

                if (integer >= min && integer <= max) return integer;
                else if (error) showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
            catch (InputMismatchException e) {
                if (error)
                    showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
        }
        if (reintentar && error) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

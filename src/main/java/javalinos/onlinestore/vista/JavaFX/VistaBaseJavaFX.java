package javalinos.onlinestore.vista.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javalinos.onlinestore.vista.Interfaces.IVistaBase;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
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
        dialogo.setTitle("Introduzca un número entero");
        dialogo.setHeaderText(mensaje + " (entre " + min + " y " + max + "): ");
        dialogo.setContentText("Número entero: ");

        while(intentos < 3) {
            try {
                Optional<String> resultado = dialogo.showAndWait();
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
        float _float;
        int intentos = 0;
        if(!reintentar) intentos = 2;

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Introduzca un número decimal");
        dialogo.setHeaderText(mensaje + " (entre " + min + " y " + max + "): ");
        dialogo.setContentText("Número decimal: ");

        while(intentos < 3) {
            try {
                Optional<String> resultado = dialogo.showAndWait();
                if (resultado.isPresent()) {
                    _float = Float.parseFloat(resultado.get());
                }
                else return -99999f;
                if (_float >= min && _float <= max) return _float;
                else showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
            catch (InputMismatchException e) {
                showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
                //scanner.next();
            }
        }
        if (reintentar) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999f;    }

    @Override
    public String askString(String mensaje, int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error) {
        int intentos = 0;
        if(!reintentar) intentos = 2;
        String entrada;

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Introduzca una cadena de caracteres");
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText("Cadena de caracteres: ");
        dialogo.getDialogPane().setPrefWidth(Region.USE_COMPUTED_SIZE);
        dialogo.getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);

        while(intentos < 3) {
            try {
                Optional<String> resultado = dialogo.showAndWait();
                if (resultado.isPresent()) {
                    entrada = resultado.get();
                }
                else return null;
                if (entrada.length() <= longitudMax && entrada.length() >= longitudMin) return entrada;
                else if (entrada.length() > longitudMax) {
                    if (error) showMensajePausa("Error. Entrada excedida. No puedes sobrepasar los " + longitudMax + " carácteres. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                    if (!sinFin) intentos++;
                }
                else {
                    if (error) showMensajePausa("Error. Entrada insuficiente. La longitud de la entrada no puede ser inferior a " + longitudMin + " caracteres. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                    if (!sinFin) intentos++;
                }
            }
            catch (Exception e) {
                if (error) showMensajePausa("Error. Entrada inválida. " + (reintentar ? "Introduce una cadena de texto." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
        }
        if (reintentar && error) showMensajePausa("Error. Demasiados intentos fallidos. Volviendo...", true);
        return null;
    }

    @Override
    public Boolean askBoolean(String mensaje, boolean reintentar, boolean maxIntentos) {
        Map<String, Boolean> mapa = Map.of(
                "Sí", true,
                "No", false
        );
        String respuesta = null;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        ChoiceDialog<String> dialogo = new ChoiceDialog<>("Sí", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
        }
        else return null;
        if (respuesta.equals("Sí")) {
            return mapa.get("Sí");
        }
        else return mapa.get("No");
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
        Alert alertaAdvertencia = new Alert(Alert.AlertType.WARNING);
        alertaAdvertencia.setTitle("Advertencia");
        alertaAdvertencia.setHeaderText("Atención");
        alertaAdvertencia.setContentText(mensaje);
        alertaAdvertencia.showAndWait();
    }

//    public void showError(String mensaje) {
//        Alert alertaError = new Alert(Alert.AlertType.ERROR);
//        alertaError.setTitle("Error");
//        alertaError.setHeaderText("Se ha producido un error");
//        alertaError.setContentText(mensaje);
//        alertaError.showAndWait();
//    }

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

package javalinos.onlinestore.vista.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GestorEscenas extends Application {

    private static Stage ventanaPrincipal;
    private static final Map<String, Stage> ventanasSecundarias = new HashMap<>();
    private static final String ID_MENU_PRINCIPAL = "MenuPrincipal";

    @Override
    public void start(Stage primaryStage) {
        ventanaPrincipal = primaryStage;
    }

    public static void crearVentana(TipoVentana tipoVentana, Stage... stage) {
        // Si es secundaria y ya está abierta, se enfoca en vez de crearla nueva
        if (!tipoVentana.nombre.equals(ID_MENU_PRINCIPAL) && ventanasSecundarias.containsKey(tipoVentana.nombre)) {
            ventanasSecundarias.get(tipoVentana.nombre).toFront();
            return;
        }

        try {
            // Definir raíz ventanas
            Parent raiz = FXMLLoader.load(Objects.requireNonNull(GestorEscenas.class.getResource(tipoVentana.ruta)));
            // Si se pasa un stage (menu principal) cargar stage, si no, nuevo
            Stage nuevaVentana = (stage.length > 0) ? stage[0] : new Stage();

            // Definir propiedades ventana
            nuevaVentana.setTitle(tipoVentana.titulo);
            nuevaVentana.setScene(new Scene(raiz));
            nuevaVentana.setResizable(false);
            nuevaVentana.show();

            // Si el identificador es MenuPrincipal
            if (tipoVentana.nombre.equals(ID_MENU_PRINCIPAL)) {
                // Crear ventana principal con propiedades definidas
                ventanaPrincipal = nuevaVentana;
                // Definir evento para cierre de todas las ventanas
                ventanaPrincipal.setOnCloseRequest(event -> {
                    // Cerrar todas las ventanas creadas
                    for (Stage v : ventanasSecundarias.values()) {
                        if (v != null) v.close();
                    }
                    // Limpiar registro ventanas
                    ventanasSecundarias.clear();
                });
            }
            else
            {
                // Registrar nueva ventana
                ventanasSecundarias.put(tipoVentana.nombre, nuevaVentana);
                // Definir evento para eliminar ventana
                nuevaVentana.setOnCloseRequest(e -> ventanasSecundarias.remove(tipoVentana.nombre));
            }

        }
        catch (IOException e)
        {
            // Lanzar excepción si falla carga ventana
            throw new RuntimeException("Error al cargar la ventana: " + tipoVentana.ruta);
        }
    }

    public static void cerrarVentana(String idVentana) {
        Stage ventana = ventanasSecundarias.remove(idVentana);
        if (ventana != null) ventana.close();
    }

    public static void iniciarVentanaPrincipal() {
        if (ventanaPrincipal != null && ventanaPrincipal.getScene() == null) {
            crearVentana(TipoVentana.MenuPrincipal, ventanaPrincipal);
        }
    }


}

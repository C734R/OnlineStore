package javalinos.onlinestore.vista.JavaFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

import static javalinos.onlinestore.OnlineStore.loadMVC;
import static javalinos.onlinestore.OnlineStore.vFactory;

public class GestorEscenas extends Application {

    private static Stage ventanaPrincipal;
    private static final Map<String, Stage> ventanasSecundarias = new HashMap<>();
    private static final String ID_MENU_PRINCIPAL = "MenuPrincipal";

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            loadMVC();
            ventanaPrincipal = primaryStage;

            // Obtener la vista raíz desde FactoryVista
            Parent raiz = vFactory.getVMenuPrincipalRaiz();
            if (raiz == null) throw new IllegalStateException("Vista principal no cargada desde el FXML.");

            // Configurar escena y propiedades de la ventana principal
            ventanaPrincipal.setScene(new Scene(raiz));
            ventanaPrincipal.setTitle("Menú Principal");
            ventanaPrincipal.setResizable(false);

            // Cierre de todas las ventanas abiertas si se cierra la principal
            ventanaPrincipal.setOnCloseRequest(event -> {
                ventanasSecundarias.values().forEach(Stage::close);
                ventanasSecundarias.clear();
                Platform.exit();
            });

            ventanaPrincipal.show();
        }
        catch (Exception e) {
            System.out.println("Error al iniciar ventana:" + e);
        }
    }

    public static void crearVentana(TipoVentana tipoVentana, Parent raiz, Stage... stage) {
        // Si es secundaria y ya está abierta, se enfoca en vez de crearla nueva
        if (!tipoVentana.nombre.equals(ID_MENU_PRINCIPAL) && ventanasSecundarias.containsKey(tipoVentana.nombre)) {
            Stage ventanaExistente = ventanasSecundarias.get(tipoVentana.nombre);
            ventanaExistente.show();
            ventanaExistente.toFront();
            return;
        }

        try {
            // Definir raíz ventanas
            //Parent raiz = FXMLLoader.load(Objects.requireNonNull(GestorEscenas.class.getResource(tipoVentana.ruta)));
            // Si se pasa un stage (menu principal) cargar stage, si no, nuevo
            Stage nuevaVentana = (stage.length > 0) ? stage[0] : new Stage();

            // Definir propiedades ventana
            nuevaVentana.setScene(new Scene(raiz));
            nuevaVentana.setTitle(tipoVentana.titulo);
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
                nuevaVentana.setOnCloseRequest(e -> {
                    e.consume();
                    nuevaVentana.hide();
                });
            }

        }
        catch (Exception e)
        {
            // Lanzar excepción si falla carga ventana
            throw new RuntimeException("Error al cargar la ventana: " + tipoVentana.nombre + " (" + tipoVentana.ruta + ")", e);
        }
    }

    public static void cerrarVentana(String idVentana) {
        if (ID_MENU_PRINCIPAL.equals(idVentana)) {
            if (ventanaPrincipal != null) {
                ventanaPrincipal.close();
                ventanaPrincipal = null;
                Platform.exit();
            }
        } else {
            Stage ventana = ventanasSecundarias.remove(idVentana);
            if (ventana != null) ventana.close();
        }
    }

}

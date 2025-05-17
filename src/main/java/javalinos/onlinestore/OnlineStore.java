package javalinos.onlinestore;
// Dependencias

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Window;
import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.controlador.ControlClientes;
import javalinos.onlinestore.controlador.ControlMenuPrincipal;
import javalinos.onlinestore.controlador.ControlPedidos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;
import javalinos.onlinestore.modelo.gestores.FactoryModelo;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.utils.Conexiones.FactoryConexionBBDD;
import javalinos.onlinestore.utils.Conexiones.IConexionBBDD;
import javalinos.onlinestore.utils.GestoresEntidades.ProveedorEntityManagerJPA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

import javalinos.onlinestore.vista.FactoryVista;
import javalinos.onlinestore.vista.Interfaces.*;
import javalinos.onlinestore.vista.JavaFX.GestorEscenas;
import javalinos.onlinestore.vista.JavaFX.TipoVentana;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static javalinos.onlinestore.vista.JavaFX.GestorEscenas.crearVentana;

/**
 * Clase principal de la aplicación OnlineStore.
 * - Inicializa modelos, vistas y controladores (patrón MVC)
 * - Carga los datos iniciales
 * - Gestiona el flujo principal de ejecución
 */
public class OnlineStore {

    /** Valor de configuración para carga de datos. */
    public static Configuracion configuracion;

    /** Modelo general de la tienda (agrega clientes, artículos y pedidos). */
    public static ModeloStore mStore;
    public static IModeloClientes mClientes;
    public static IModeloArticulos mArticulos;
    public static IModeloPedidos mPedidos;
    public static FactoryModelo mFactory;

    /** Vistas principales del sistema. */
    public static IVistaMenuPrincipal vMenuPrincipal;
    public static IVistaArticulos vArticulos;
    public static IVistaClientes vClientes;
    public static IVistaPedidos vPedidos;
    public static FactoryVista vFactory;

    /** Controladores que gestionan la lógica de las vistas. */
    public static ControlMenuPrincipal cMenuPrincipal;
    public static ControlClientes cClientes;
    public static ControlArticulos cArticulos;
    public static ControlPedidos cPedidos;

    /**
     * Punto de entrada de la aplicación.
     * @param args argumentos opcionales (modo de configuración).
     */
    public static void main(String[] args){

        // Forzar componentes logs
        java.util.logging.LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        // Procesar argumento de configuración
        if (args.length != 0) {
            switch (args[0]) {
                case "0" -> configuracion = Configuracion.LOCAL;
                case "1" -> configuracion = Configuracion.JDBC_MYSQL;
                case "2" -> configuracion = Configuracion.JPA_HIBERNATE_MYSQL;
                case "3" -> configuracion = Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL;
            }
        }
        else configuracion = Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL;

        // Si se selecciona opción con JAVAFX, iniciar ciclo de vida servicio
        if(configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) GestorEscenas.launch(GestorEscenas.class, args);

        // Aplicar autocierre al uso de la conexión por parte de ModeloFactory
        try (FactoryModelo factory = new FactoryModelo()) {
            mFactory = factory;
            loadMVC();
            //if (!precargaDatos(configuracion)) return;
            iniciarPrograma();
        }
        catch (Exception e) {
            System.err.println("Error crítico: " + e);
        }
        finally {
            if(configuracion == Configuracion.JPA_HIBERNATE_MYSQL) ProveedorEntityManagerJPA.cerrarEMF();
        }
    }

    /**
     * Precarga los datos necesarios para la aplicación según la configuración.
     * Reintenta en caso de error hasta que el usuario decida salir.
     */
    public static boolean precargaDatos()  {
        while(true) {
            if(configuracion != Configuracion.LOCAL) {
                if(!ejecutarScript()) return false;
            }
            try
            {
                cClientes.loadClientes();
                cArticulos.loadArticulos();
                cPedidos.loadPedidos();
                break;
            }
            catch (Exception e){
                if(!cMenuPrincipal.errorPrecarga(e)) return false;
            }
        }
        return true;
    }

    private static boolean ejecutarScript() {
        try (IConexionBBDD conexion = FactoryConexionBBDD.crearConexion(Configuracion.JDBC_MYSQL, "localhost", "root", "1234", "onlinestore")) {
            ejecutarScriptSQL("src/main/java/javalinos/onlinestore/ScriptBBDD.sql", (Connection) conexion.getConexion());
        } catch (Exception e) {
            vMenuPrincipal.showMensajePausa("Error al ejecutar el script SQL:" + e, true);
            return false;
        }
        return true;
    }

    public static void ejecutarScriptSQL(String ruta, Connection conexion) throws Exception {
        String script = Files.readString(Path.of(ruta));
        String[] sentencias = script.split(";");
        try (Statement stmt = conexion.createStatement()) {
            for (String sentencia : sentencias) {
                sentencia = sentencia.trim();
                if (!sentencia.isEmpty()) {
                    stmt.execute(sentencia);
                }
            }
        }
        vMenuPrincipal.showMensaje("Script SQL ejecutado correctamente.", true);
    }


    /**
     * Inicia el menú principal y controla el flujo del programa.
     */
    private static void iniciarPrograma(){
        if(configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) iniciarJavaFX();
        else iniciarConsola();
    }

    private static void iniciarJavaFX() {
        javafx.application.Application.launch(GestorEscenas.class);
        int opcion;
        while(true) {
            opcion = cMenuPrincipal.iniciar();
            switch (opcion) {
                case 1:
                    vClientes.showMenu(1);
                    break;
                case 2:
                    vArticulos.showMenu(2);
                    break;
                case 3:
                    vPedidos.showMenu(3);
                    break;
                case 0:
                    cMenuPrincipal.salir();
                    return;
            }
        }
    }

    private static void iniciarConsola() {
        int opcion;
        while(true) {
            opcion = cMenuPrincipal.iniciar();
            switch (opcion) {
                case 1:
                    cClientes.iniciar();
                    break;
                case 2:
                    cArticulos.iniciar();
                    break;
                case 3:
                    cPedidos.iniciar();
                    break;
                case 0:
                    cMenuPrincipal.salir();
                    return;
            }
        }
    }

    /**
     * Carga el modelo-vista-controlador (MVC) según el modo de configuración.
     */
    private static void loadMVC() throws Exception {

            mFactory = new FactoryModelo();
            vFactory = new FactoryVista();

            mClientes = mFactory.getModeloClientes();
            mArticulos = mFactory.getModeloArticulos();
            mPedidos = mFactory.getModeloPedidos();
            mStore = new ModeloStore(mClientes, mArticulos, mPedidos);

            vMenuPrincipal = vFactory.getVistaMenuPrincipal();
            cMenuPrincipal = new ControlMenuPrincipal(mStore, vMenuPrincipal);

            vClientes = vFactory.getVistaClientes();
            cClientes = new ControlClientes(mStore, vClientes);

            vArticulos = vFactory.getVistaArticulos();
            cArticulos = new ControlArticulos(mStore, vArticulos);

            vPedidos = vFactory.getVistaPedidos();
            cPedidos = new ControlPedidos(mStore, vPedidos);

    }

}

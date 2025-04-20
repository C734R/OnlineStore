package javalinos.onlinestore;
// Dependencias

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
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.vista.VistaClientes;
import javalinos.onlinestore.vista.VistaMenuPrincipal;
import javalinos.onlinestore.vista.VistaPedidos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase principal de la aplicación OnlineStore.
 * - Inicializa modelos, vistas y controladores (patrón MVC)
 * - Carga los datos iniciales
 * - Gestiona el flujo principal de ejecución
 */
public class OnlineStore {

    /** Valor de configuración para carga de datos. */
    public static Configuracion configuracion = Configuracion.JDBC_MYSQL;

    /** Modelo general de la tienda (agrega clientes, artículos y pedidos). */
    public static ModeloStore mStore;
    public static IModeloClientes mClientes;
    public static IModeloArticulos mArticulos;
    public static IModeloPedidos mPedidos;
    public static FactoryModelo mFactory;

    /** Vistas principales del sistema. */
    public static VistaMenuPrincipal vMenuPrincipal;
    public static VistaArticulos vArticulos;
    public static VistaClientes vClientes;
    public static VistaPedidos vPedidos;

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
        if (args.length != 0) {
            switch (args[0]) {
                case "0" -> configuracion = Configuracion.LOCAL;
                case "1" -> configuracion = Configuracion.JDBC_MYSQL;
                case "2" -> configuracion = Configuracion.HIBERNATE_MYSQL;
            }
        }
        else configuracion = Configuracion.JDBC_MYSQL;

        // Aplicar autocierre al uso de la conexión por parte de ModeloFactory
        try (FactoryModelo factory = new FactoryModelo(configuracion)) {
            mFactory = factory;
            loadMVC(configuracion);
            //if (!precargaDatos(configuracion)) return;
            iniciarPrograma();
        } catch (Exception e) {
            System.err.println("Error crítico: " + e);
        }
    }

    /**
     * Precarga los datos necesarios para la aplicación según la configuración.
     * Reintenta en caso de error hasta que el usuario decida salir.
     */
    public static boolean precargaDatos()  {
        while(true) {
            try (IConexionBBDD conexion = FactoryConexionBBDD.crearConexion(configuracion, "localhost", "root", "1234", "onlinestore")) {
                assert conexion != null;
                ejecutarScriptSQL("src/main/java/javalinos/onlinestore/modelo/ScriptBBDD.sql", (Connection) conexion.getConexion());
            } catch (Exception e) {
                vMenuPrincipal.showMensajePausa("Error al ejecutar el script SQL:" + e, true);
                return false;
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
     * @param configuracion modo de carga (0 = Local, 1 = con BBDD, 2 = con ORM, etc.).
     */
    private static void loadMVC(Configuracion configuracion) throws Exception {

            mFactory = new FactoryModelo(configuracion);

            mClientes = mFactory.getModeloClientes();
            mArticulos = mFactory.getModeloArticulos();
            mPedidos = mFactory.getModeloPedidos();
            mStore = new ModeloStore(mClientes, mArticulos, mPedidos);

            vMenuPrincipal = new VistaMenuPrincipal();
            cMenuPrincipal = new ControlMenuPrincipal(mStore, vMenuPrincipal);

            vClientes = new VistaClientes();
            cClientes = new ControlClientes(mStore, vClientes);

            vArticulos = new VistaArticulos();
            cArticulos = new ControlArticulos(mStore, vArticulos);

            vPedidos = new VistaPedidos();
            cPedidos = new ControlPedidos(mStore, vPedidos);

    }

}

package javalinos.onlinestore;
// Dependencias

import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.controlador.ControlClientes;
import javalinos.onlinestore.controlador.ControlMenuPrincipal;
import javalinos.onlinestore.controlador.ControlPedidos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloFactory;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.vista.VistaClientes;
import javalinos.onlinestore.vista.VistaMenuPrincipal;
import javalinos.onlinestore.vista.VistaPedidos;

import java.sql.SQLException;

/**
 * Clase principal de la aplicación OnlineStore.
 * - Inicializa modelos, vistas y controladores (patrón MVC)
 * - Carga los datos iniciales
 * - Gestiona el flujo principal de ejecución
 */
public class OnlineStore {

    /** Valor de configuración para carga de datos. */
    public static int configuracion = 1;

    /** Modelo general de la tienda (agrega clientes, artículos y pedidos). */
    public static ModeloStore mStore;
    public static IModeloClientes mClientes;
    public static IModeloArticulos mArticulos;
    public static IModeloPedidos mPedidos;
    public static ModeloFactory mFactory;

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
        if (args.length != 0) configuracion = Integer.parseInt(args[1]);
        else configuracion = 1;

        // Aplicar autocierre al uso de la conexión por parte de ModeloFactory
        try (ModeloFactory factory = new ModeloFactory(configuracion)) {
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
            try
            {
                cClientes.loadClientes();
                cArticulos.loadArticulos();
                cPedidos.loadPedidos();
                break;
            }
            catch (Exception e){
                if(!cMenuPrincipal.errorPrecarga()) return false;
            }
        }
        return true;
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
    private static void loadMVC(int configuracion) throws SQLException {

            mFactory = new ModeloFactory(configuracion);

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

package javalinos.onlinestore;

import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.controlador.ControlClientes;
import javalinos.onlinestore.controlador.ControlMenuPrincipal;
import javalinos.onlinestore.controlador.ControlPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.vista.VistaClientes;
import javalinos.onlinestore.vista.VistaMenuPrincipal;
import javalinos.onlinestore.vista.VistaPedidos;

/**
 * Clase principal de la aplicación.
 */
public class OnlineStore {

    public static int configuracion = 0;
    public static ModeloStore mStore;
    public static ModeloClientes mClientes;
    public static ModeloArticulos mArticulos;
    public static ModeloPedidos mPedidos;
    public static VistaMenuPrincipal vMenuPrincipal;
    public static VistaArticulos vArticulos;
    public static VistaClientes vClientes;
    public static VistaPedidos vPedidos;
    public static ControlMenuPrincipal cMenuPrincipal;
    public static ControlClientes cClientes;
    public static ControlArticulos cArticulos;
    public static ControlPedidos cPedidos;

    /**
     * Método principal de la aplicación.
     * @param args recibe los argumentos.
     */
    public static void main(String[] args) {
        if (!args[1].isEmpty()) configuracion = Integer.parseInt(args[1]);
        precargaDatos();
        iniciarPrograma();
    }

    /**
     * Método que precarga los datos.
     */
    private static void precargaDatos() {
        loadMVC();
        cClientes.loadClientes();
        cArticulos.loadArticulos();
        cPedidos.loadPedidos();
    }

    /**
     * Método que inicia el programa, cargando el menú principal.
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
     * Método que carga el MVC.
     */
    private static void loadMVC(){
        mClientes = new ModeloClientes();
        mArticulos = new ModeloArticulos();
        mPedidos = new ModeloPedidos();
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

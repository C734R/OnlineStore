package javalinos.onlinestore;
// Dependencias

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
     * Main de la aplicación.
     * @param args recibe los argumentos.
     */
    public static void main(String[] args) {
        if (args.length != 0) configuracion = Integer.parseInt(args[1]);
        else configuracion = 0;
        precargaDatos(configuracion);
        iniciarPrograma();
    }

    /**
     * Precarga los datos.
     */
    private static void precargaDatos(int configuracion) {
        loadMVC(configuracion);
        while (true) {
            if (    !cClientes.loadClientes(configuracion)||!cArticulos.loadArticulos(configuracion)||!cPedidos.loadPedidos(configuracion)) {
                if (!cMenuPrincipal.errorPrecarga()) break;
            }
            else break;
        }

    }

    /**
     * Inicia el programa, cargando el menú principal.
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
     * Carga el MVC.
     */
    private static void loadMVC(int configuracion){

        if (configuracion == 0) {
            mClientes = new ModeloClientes();
            mArticulos = ModeloArticulos.getInstancia();
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
        else if (configuracion == 1) {
            //TODO Implementar ORM
        }
        else {
            //TODO necesario?
        }
    }


}

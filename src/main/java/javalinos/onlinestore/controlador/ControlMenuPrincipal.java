package javalinos.onlinestore.controlador;

import javalinos.onlinestore.Configuracion;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.Consola.VistaMenuPrincipal;
import javalinos.onlinestore.vista.Interfaces.IVistaMenuPrincipal;
import javalinos.onlinestore.vista.JavaFX.GestorEscenas;

import static javalinos.onlinestore.OnlineStore.configuracion;
import static javalinos.onlinestore.OnlineStore.precargaDatos;

/**
 * Controlador del menú principal de la aplicación.
 */
public class ControlMenuPrincipal extends ControlBase {

    private IVistaMenuPrincipal vMenuPrincipal;
    boolean iniciado = false;
    /**
     * Constructor principal de ControlMenuPrincipal.
     * @param mStore el ModelStore que va a utilizar
     * @param vMenuPrincipal la vista que va a utilizar
     */
    public ControlMenuPrincipal(ModeloStore mStore, IVistaMenuPrincipal vMenuPrincipal) {
        super(mStore);
        this.vMenuPrincipal = vMenuPrincipal;
    }
    /**
     * Constructor vacío.
     */
    public ControlMenuPrincipal() {
        super();
    }

    //*************************** Getters & Setters ***************************//
    /**
     * Devuelve la vista del menú principal.
     * @return Vista del menú principal
     */
    public IVistaMenuPrincipal getVistaMenuPrincipal() {
        return vMenuPrincipal;
    }
    /**
     * Asigna una nueva vista para el menú principal.
     * @param vMenuPrincipal vista a asignar
     */
    public void setVistaMenuPrincipal(VistaMenuPrincipal vMenuPrincipal) {
        this.vMenuPrincipal = vMenuPrincipal;
    }

    //*************************** Menu principal ***************************//

    public int iniciar()
    {
        if (configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) iniciarVentana();
        else return iniciarConsola();
        return 0;
    }

    private void iniciarVentana() {
        Boolean realizar = null;
        vMenuPrincipal.showMensaje(" #### Ejecutando aplicación en modo: " + configuracion.name() + " ####",true);
        while(!iniciado) {
            realizar = vMenuPrincipal.askBoolean("¿Deseas realizar la precarga de datos?", true, false);
            if (realizar == null) realizar = false;
            if (realizar) {
                if (!precargaDatos()) {
                    vMenuPrincipal.showMensajePausa("Error al realizar la precarga de datos", true);
                    continue;
                }
                iniciado = true;
            } else break;
        }
    }

    /**
     * Inicia el menú principal y permite seleccionar una opción.
     * @return número entero según la opción seleccionada
     */
    public int iniciarConsola() {
        int opcion;
        while(true) {
            vMenuPrincipal.showCabecera();
            vMenuPrincipal.showMensaje(" #### Ejecutando aplicación en modo: " + configuracion.name() + " ####",true);
            if(!iniciado && vMenuPrincipal.askBoolean("¿Deseas realizar la precarga de datos?", true, false)){
                if(!precargaDatos()) return 0;
            }
            iniciado = true;
            vMenuPrincipal.showMenu(1);
            opcion = vMenuPrincipal.askInt("Seleccione una opción", 0, 3, false, false, true);
            switch (opcion) {
                case 1:
                    vMenuPrincipal.showMensaje("*** Accediendo al menú de gestión de clientes... ***\n", true);
                    return 1;
                case 2:
                    vMenuPrincipal.showMensaje("*** Accediendo al menú de gestión de artículos... ***\n", true);
                    return 2;
                case 3:
                    vMenuPrincipal.showMensaje("*** Accediendo al menú de gestión de pedidos... ***\n", true);
                    return 3;
                case 0:
                    vMenuPrincipal.showMensaje("*** Saliendo del programa... ***", true);
                    return 0;
                default:
                    vMenuPrincipal.showMensajePausa("Error. Opción invalida. Introduce alguna de las opciones indicadas.", true);
            }
        }
    }

    public void iniciarVentana(int opcion) {
        switch (opcion) {
            case 1:
                vMenuPrincipal.showMenu(1);
                break;
            case 2:
                vMenuPrincipal.showMenu(2);
                break;
            case 3:
                vMenuPrincipal.showMenu(3);
        }
    }

    //*************************** Error precarga ***************************//

    /**
     * Muestra un mensaje de error al fallar la precarga de datos.
     * @return true si el usuario desea volver a intentarlo, false en caso contrario
     */
    public boolean errorPrecarga(Exception e) {
        vMenuPrincipal.showMensajePausa("Error. Se ha producido un error en la precarga de datos: " + e, true);
        return vMenuPrincipal.askBoolean("¿Deseas volver a intentarlo?", true, false);
    }

    public void exitoPrecarga() {
        vMenuPrincipal.showMensaje("*** Precarga realizada con éxito ***", true);
    }

    /**
     * Muestra un mensaje de salida del programa.
     */
    public void salir() {
        if (configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) cerrarVentana();
        else vMenuPrincipal.showMensaje("********* ¡¡Hasta pronto!! *********",true);
    }

    private void cerrarVentana() {
        GestorEscenas.cerrarVentana("MenuPrincipal");
    }


}

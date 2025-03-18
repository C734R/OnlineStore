package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaMenuPrincipal;

import static javalinos.onlinestore.OnlineStore.vMenuPrincipal;

public class ControlMenuPrincipal extends ControlBase {

    private VistaMenuPrincipal vMenuPrincipal;

    public ControlMenuPrincipal(ModeloStore mStore, VistaMenuPrincipal vMenuPrincipal) {
        super(mStore);
        this.vMenuPrincipal = vMenuPrincipal;
    }

    public ControlMenuPrincipal() {
        super();
    }

    public VistaMenuPrincipal getVistaMenuPrincipal() {
        return vMenuPrincipal;
    }

    public void setVistaMenuPrincipal(VistaMenuPrincipal vistaMenuPrincipal) {
        this.vMenuPrincipal = vMenuPrincipal;
    }

    public int iniciar() {
        int opcion;
        while(true) {
            vMenuPrincipal.showCabecera();
            vMenuPrincipal.showMenu(1);
            opcion = vMenuPrincipal.askInt("Seleccione una opción", 0, 3, false, false);
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

    public boolean errorPrecarga() {
        vMenuPrincipal.showMensajePausa("Error. Se ha producido un error en la precarga de datos.", true);
        return vMenuPrincipal.askBoolean("¿Deseas volver a intentarlo?", true, false);
    }
    public void salir() {
        vMenuPrincipal.showMensaje("********* ¡¡Hasta pronto!! *********",true);
    }


}

package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaMenuPrincipal;

public class ControlMenuPrincipal extends ControlBase {

    private VistaMenuPrincipal vistaMenuPrincipal;

    public ControlMenuPrincipal(ModeloStore mStore, VistaMenuPrincipal vistaMenuPrincipal) {
        super(mStore);
        this.vistaMenuPrincipal = vistaMenuPrincipal;
    }

    public ControlMenuPrincipal() {
        super();
    }

    public VistaMenuPrincipal getVistaMenuPrincipal() {
        return vistaMenuPrincipal;
    }

    public void setVistaMenuPrincipal(VistaMenuPrincipal vistaMenuPrincipal) {
        this.vistaMenuPrincipal = vistaMenuPrincipal;
    }

    public int iniciar() {
        return 0;
    }

    public void salir() {}


}

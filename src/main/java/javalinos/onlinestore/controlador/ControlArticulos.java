package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;

public class ControlArticulos extends ControlBase{

    private VistaArticulos vArticulos;

    public ControlArticulos(ModeloStore mStore, VistaArticulos vistaArticulos) {
        super(mStore);
        this.vArticulos = vistaArticulos;
    }

    public ControlArticulos() {
        super();
    }

    public VistaArticulos getVistaArticulos() {
        return vArticulos;
    }

    public void setVistaArticulos(VistaArticulos vistaArticulos) {
        this.vArticulos = vistaArticulos;
    }

    public void iniciar() {
        int opcion;
        while(true){
            vArticulos.showCabecera();
            vArticulos.showMenu(2);
            opcion = vArticulos.askInt("Introduce una opción",0,3, false, false);
            switch(opcion){
                case 1:
                    addArticulo();
                    break;
                case 2:
                    removeArticulo();
                    break;
                case 3:
                    listArticulos();
                    break;
                case 0:
                    vArticulos.showMensajePausa("Volviendo al menú principal... ", true);
                    return;
                default:
                    vArticulos.showMensajePausa("Error. Opción invalida. Introduce alguna de las opciones indicadas.",true);
            }
        }
    }

    public void addArticulo() {

    }

    public void removeArticulo() {

    }

    public void listArticulos() {

    }

    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            return this.getmStore().getModeloArticulos().loadArticulos(configuracion);
        }
        else {
            return false;
        }
    }
}

package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;

public class ControlArticulos extends ControlBase{

    private VistaArticulos vistaArticulos;

    public ControlArticulos(ModeloStore mStore, VistaArticulos vistaArticulos) {
        super(mStore);
        this.vistaArticulos = vistaArticulos;
    }

    public ControlArticulos() {
        super();
    }

    public VistaArticulos getVistaArticulos() {
        return vistaArticulos;
    }

    public void setVistaArticulos(VistaArticulos vistaArticulos) {
        this.vistaArticulos = vistaArticulos;
    }

    public void iniciar() {}

    public void addArticulo() {}

    public void removeArticulo() {}

    public void listArticulos() {}

    public void loadArticulos() {}
}

package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Articulo;

import java.util.List;

public class VistaArticulos extends VistaBase {

    public VistaArticulos() {
        super();
    }

    public VistaArticulos(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

    public float askPrecio(float min, float max) {
        return 0f;
    }

    public String askArticulo(List<Articulo> articulos) {
        return "";
    }
}

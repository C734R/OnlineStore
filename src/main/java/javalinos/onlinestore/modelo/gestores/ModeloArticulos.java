package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import java.util.ArrayList;
import java.util.List;

public class ModeloArticulos {

    private static ModeloArticulos instancia; // Única instancia
    private List<Articulo> articulos;

    // Constructor privado para Singleton
    private ModeloArticulos() {
        this.articulos = new ArrayList<>();
    }

    // Método estático para obtener la única instancia. Recordar que es un metodo de unica instancia y no puede ser null
    public static ModeloArticulos getInstancia() {
        if (instancia == null) {
            instancia = new ModeloArticulos();
        }
        return instancia;
    }
// Array para listar el metodo de articulos
    public List<Articulo> getArticulos() {
        return articulos;
    }
// donde guardaremos los articulos ya creados
    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
// como hacer el articulo a partir del codigo
    public Articulo makeArticulo(String descripcion, Float precio, Float preparacion, Integer stock) {
        String codigo;

        if (articulos.isEmpty()) {
            codigo = "ART000";
        } else {
            String lastCodigo = articulos.get(articulos.size() - 1).getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(lastCodigo.substring(3)) + 1);
        }
        return new Articulo(codigo, descripcion, precio, preparacion);
    }

    public boolean addArticulo(Articulo articulo) {
        return this.articulos.add(articulo);
    }

    public boolean removeArticulo(Articulo articulo) {
        return this.articulos.remove(articulo);
    }

    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            try {
                this.articulos.clear();
                addArticulo(makeArticulo("Guitarra española de juguete.", 6f, 0.05f,5));
                addArticulo(makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 0.08f,9));
                addArticulo(makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 0.10f,14));
                addArticulo(makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 0.07f,2));
                addArticulo(makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 0.06f,100));
                addArticulo(makeArticulo("Muñeca Nancy - Famosa.", 20f, 0.06f,45));
                addArticulo(makeArticulo("Madelman - Figura de acción articulada.", 15f, 0.05f,83));
                addArticulo(makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 0.04f,32));
                addArticulo(makeArticulo("Simon - Juego electrónico de memoria.", 14f, 0.08f,67));
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkArticulo(String codigo) {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}

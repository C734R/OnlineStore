package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import java.util.ArrayList;
import java.util.List;

public class ModeloArticulos {

    private List<Articulo> articulos;

    public ModeloArticulos() {
        this.articulos = new ArrayList<>();
    }

    /**
     * Devuelve una lista con todos los artículos
     *
     * @return Lista con todos los articulos
     */
    public List<Articulo> getArticulos() {
        return articulos;
    }

    // Donde guardaremos los articulos ya creados
    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    /**
     * Devuelve un solo artículo
     *
     * @param id El id del artículo que se quiere devolver
     * @return Articulo deseado
     */
    public Articulo getArticulo(int id) {
        List<Articulo> articulos = getArticulos();
        return articulos.get(id);
    }

    /**
     * Crear un nuevo artículo
     *
     * @param descripcion Descripción del artículo
     * @param precio Precio del artículo
     * @param preparacion Tiempo de preparación del artículo
     * @param stock Strock del artículo
     * @return Articulo nuevo
     */
    public Articulo makeArticulo(String descripcion, Float precio, Float preparacion, Integer stock) {
        String codigo;

        if (articulos.isEmpty()) {
            codigo = "ART000";
        } else {
            String lastCodigo = articulos.get(articulos.size() - 1).getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(lastCodigo.substring(3)) + 1);
        }
        return new Articulo(codigo, descripcion, precio, preparacion, stock);
    }

    /**
     * Añadir artículo a la lista de artículos
     *
     * @param articulo El artículo que se va a añadir
     * @return Boolean si se ha añadido bien o no
     */
    public boolean addArticulo(Articulo articulo) {
        return this.articulos.add(articulo);
    }

    /**
     * Borrar un artículo
     *
     * @param articulo El artículo que se va a eliminar
     * @return Boolean si se borra bien o no
     */
    public boolean removeArticulo(Articulo articulo) {
        return this.articulos.remove(articulo);
    }

    /**
     * Carga de todos los artículos
     *
     * @param configuracion
     * @return Boolena si se cargan bien o no
     */
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

    /**
     * Comprueba si existe un artículo
     *
     * @param codigo el codigo artículo del artículo que se va a comprobar
     * @return Boolean si existe o no
     */
    public boolean checkArticulo(String codigo) {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}

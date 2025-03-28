package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.*;

public class ModeloArticulos {

    private List<Articulo> articulos;
    private Map<Articulo, Integer> stockArticulos;

    public ModeloArticulos() {
        this.articulos = new ArrayList<>();
        this.stockArticulos = new LinkedHashMap<>();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve una lista con todos los artículos
     * @return Lista con todos los articulos
     */
    public List<Articulo> getArticulos() {
        return articulos;
    }

    public Articulo getArticuloIndex(int index) {
        if (index < 0 || index >= articulos.size()) return null;
        return articulos.get(index);
    }

    public Map<Articulo, Integer> getStockArticulos() {
        return stockArticulos;
    }

    public Integer getStockArticulo(Articulo articulo) {
        if (stockArticulos.isEmpty()) return null;
        return stockArticulos.get(articulo);
    }

    /**
     * Establece la lista de artículos.
     * @param articulos Lista de artículos.
     */
    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public void setStockArticulos(Map<Articulo, Integer> stockArticulos) {
        this.stockArticulos = stockArticulos;
    }

    public void setStockArticulo(Articulo articulo, Integer stock) {
        this.stockArticulos.put(articulo, stock);
    }

    //*************************** CRUD ***************************//

    /**
     * Añadir artículo a la lista de artículos
     * @param articulo El artículo que se va a añadir
     */
    public void addArticulo(Articulo articulo) {
        articulos.add(articulo);
    }

    public void addStockArticulo(Articulo articulo, int stock) {
        stockArticulos.put(articulo, stock);
    }

    /**
     * Borrar un artículo
     * @param articulo El artículo que se va a eliminar
     */
    public void removeArticulo(Articulo articulo) {
        articulos.remove(articulo);
    }

    public void removeStockArticulo(Articulo articulo) {
        stockArticulos.remove(articulo);
    }

    /**
     * Modificar a un artículo
     * @param articuloOld Articulo antiguo
     * @param articuloNew Nuevo artículo
     */
    public void updateArticulo(Articulo articuloOld, Articulo articuloNew) {
        int index = articulos.indexOf(articuloOld);
        if (index != -1) {
            articulos.set(index, articuloNew);
        }
    }

    public void updateStockArticulo(Articulo articulo, int stockNew) {
        stockArticulos.put(articulo, stockNew);
    }

    //*************************** Crear datos ***************************//

    /**
     * Crear un nuevo artículo
     * @param descripcion Descripción del artículo
     * @param precio Precio del artículo
     * @param preparacion Tiempo de preparación del artículo
     * @return Articulo - Artículo nuevo
     */
    public Articulo makeArticulo(String descripcion, Float precio, Float preparacion) {
        String codigo;
        if (articulos.isEmpty()) codigo = "ART000";
        else {
            String lastCodigo = articulos.getLast().getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(lastCodigo.substring(3)) + 1);
        }
        return new Articulo(codigo, descripcion, precio, preparacion);
    }

    /**
     * Carga de todos los artículos
     * @param configuracion define la configuración seleccionada.
     * @return Boolena si se cargan bien o no
     */
    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            try {
                articulos.clear();
                Articulo articuloTemp = new Articulo();
                articuloTemp = makeArticulo("Guitarra española de juguete.", 6f, 0.05f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 5);
                articuloTemp = makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 0.08f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 9);
                articuloTemp = makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 0.10f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 14);
                articuloTemp = makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 0.07f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 20);
                articuloTemp = makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 0.06f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 8);
                articuloTemp = makeArticulo("Muñeca Nancy - Famosa.", 20f, 0.06f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 10);
                articuloTemp = makeArticulo("Madelman - Figura de acción articulada.", 15f, 0.05f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 12);
                articuloTemp = makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 0.04f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 14);
                articuloTemp = makeArticulo("Simon - Juego electrónico de memoria.", 14f, 0.08f);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 20);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
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

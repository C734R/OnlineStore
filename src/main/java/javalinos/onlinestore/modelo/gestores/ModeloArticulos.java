package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.*;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los artículos y su stock.
 */
public class ModeloArticulos {

    private List<Articulo> articulos;
    private Map<Articulo, Integer> stockArticulos;
    /**
     * Constructor por defecto. Inicializa las listas de artículos y su stock.
     */
    public ModeloArticulos() {
        this.articulos = new ArrayList<>();
        this.stockArticulos = new LinkedHashMap<>();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve una lista con todos los artículos registrados.
     * @return lista de artículos.
     */
    public List<Articulo> getArticulos() {
        return articulos;
    }
    /**
     * Devuelve un artículo según su posición en la lista.
     * @param index posición en la lista.
     * @return artículo encontrado o null si está fuera de rango.
     */
    public Articulo getArticuloIndex(int index) {
        if (index < 0 || index >= articulos.size()) return null;
        return articulos.get(index);
    }
    /**
     * Devuelve el mapa de stock de todos los artículos.
     * @return mapa con artículos y cantidades disponibles.
     */
    public Map<Articulo, Integer> getStockArticulos() {
        return stockArticulos;
    }
    /**
     * Devuelve el stock de un artículo concreto.
     * @param articulo artículo a consultar.
     * @return unidades disponibles o null si no está en el mapa.
     */
    public Integer getStockArticulo(Articulo articulo) {
        if (stockArticulos.isEmpty()) return null;
        return stockArticulos.get(articulo);
    }

    /**
     * Establece una nueva lista de artículos.
     * @param articulos nueva lista de artículos.
     */
    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
    /**
     * Establece un nuevo mapa de stock.
     * @param stockArticulos mapa de artículos con su stock.
     */
    public void setStockArticulos(Map<Articulo, Integer> stockArticulos) {
        this.stockArticulos = stockArticulos;
    }
    /**
     * Establece el stock de un artículo específico.
     * @param articulo artículo a actualizar.
     * @param stock nueva cantidad disponible.
     */
    public void setStockArticulo(Articulo articulo, Integer stock) {
        this.stockArticulos.put(articulo, stock);
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un artículo a la lista de artículos.
     * @param articulo artículo a añadir.
     */
    public void addArticulo(Articulo articulo) {
        articulos.add(articulo);
    }
    /**
     * Añade stock para un artículo determinado.
     * @param articulo artículo a actualizar.
     * @param stock cantidad a añadir.
     */
    public void addStockArticulo(Articulo articulo, int stock) {
        stockArticulos.put(articulo, stock);
    }

    /**
     * Elimina un artículo de la lista.
     * @param articulo artículo a eliminar.
     */
    public void removeArticulo(Articulo articulo) {
        articulos.remove(articulo);
    }
    /**
     * Elimina el stock de un artículo.
     * @param articulo artículo del cual eliminar el stock.
     */
    public void removeStockArticulo(Articulo articulo) {
        stockArticulos.remove(articulo);
    }

    /**
     * Reemplaza un artículo por uno nuevo.
     * @param articuloOld artículo original.
     * @param articuloNew nuevo artículo.
     */
    public void updateArticulo(Articulo articuloOld, Articulo articuloNew) {
        int index = articulos.indexOf(articuloOld);
        if (index != -1) {
            articulos.set(index, articuloNew);
        }
    }
    /**
     * Actualiza el stock de un artículo.
     * @param articulo artículo a actualizar.
     * @param stockNew nueva cantidad de stock.
     */
    public void updateStockArticulo(Articulo articulo, int stockNew) {
        stockArticulos.put(articulo, stockNew);
    }

    //*************************** Crear datos ***************************//

    /**
     * Crea un nuevo artículo con un código generado automáticamente.
     * @param descripcion descripción del artículo.
     * @param precio precio del artículo.
     * @param preparacion tiempo de preparación en minutos.
     * @return instancia del nuevo artículo.
     */
    public Articulo makeArticulo(String descripcion, Float precio, Integer preparacion) {
        String codigo;
        if (articulos.isEmpty()) codigo = "ART000";
        else {
            String lastCodigo = articulos.getLast().getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(lastCodigo.substring(3)) + 1);
        }
        return new Articulo(codigo, descripcion, precio, preparacion);
    }

    /**
     * Precarga una lista de artículos con stock para pruebas.
     * @param configuracion define si se carga el modo por defecto.
     * @return true si se cargó correctamente, false si hubo error.
     */
    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            try {
                articulos.clear();
                Articulo articuloTemp = new Articulo();
                articuloTemp = makeArticulo("Guitarra española de juguete.", 6f, 50);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 5);
                articuloTemp = makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 100);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 9);
                articuloTemp = makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 70);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 14);
                articuloTemp = makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 30);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 20);
                articuloTemp = makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 200);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 8);
                articuloTemp = makeArticulo("Muñeca Nancy - Famosa.", 20f, 25);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 10);
                articuloTemp = makeArticulo("Madelman - Figura de acción articulada.", 15f, 1000);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 12);
                articuloTemp = makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 450);
                addArticulo(articuloTemp);
                addStockArticulo(articuloTemp, 14);
                articuloTemp = makeArticulo("Simon - Juego electrónico de memoria.", 14f, 245);
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
     * Verifica si existe un artículo con un código dado.
     * @param codigo código a comprobar.
     * @return true si existe un artículo con ese código, false en caso contrario.
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

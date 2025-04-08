package javalinos.onlinestore.modelo.gestores.Local;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;

import java.util.*;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los artículos y su stock.
 */
public class ModeloArticulosLocal implements IModeloArticulos {

    private List<ArticuloDTO> articulosDTO;
    private Map<ArticuloDTO, Integer> stockArticulos;
    /**
     * Constructor por defecto. Inicializa las listas de artículos y su stock.
     */
    public ModeloArticulosLocal() {
        this.articulosDTO = new ArrayList<>();
        this.stockArticulos = new LinkedHashMap<>();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve una lista con todos los artículos registrados.
     * @return lista de artículos.
     */
    public List<ArticuloDTO> getArticulos() {
        return articulosDTO;
    }
    /**
     * Devuelve un artículo según su posición en la lista.
     * @param index posición en la lista.
     * @return artículo encontrado o null si está fuera de rango.
     */
    public ArticuloDTO getArticuloIndex(int index) {
        if (index < 0 || index >= articulosDTO.size()) return null;
        return articulosDTO.get(index);
    }
    /**
     * Devuelve el mapa de stock de todos los artículos.
     * @return mapa con artículos y cantidades disponibles.
     */
    public Map<ArticuloDTO, Integer> getStockArticulos() {
        return stockArticulos;
    }
    /**
     * Devuelve el stock de un artículo concreto.
     * @param articuloDTO artículo a consultar.
     * @return unidades disponibles o null si no está en el mapa.
     */
    public Integer getStockArticulo(ArticuloDTO articuloDTO) {
        if (stockArticulos.isEmpty()) return null;
        return stockArticulos.get(articuloDTO);
    }

    /**
     * Establece una nueva lista de artículos.
     * @param articulosDTO nueva lista de artículos.
     */
    public void setArticulos(List<ArticuloDTO> articulosDTO) {
        this.articulosDTO = articulosDTO;
    }
    /**
     * Establece un nuevo mapa de stock.
     * @param stockArticulos mapa de artículos con su stock.
     */
    public void setStockArticulos(Map<ArticuloDTO, Integer> stockArticulos) {
        this.stockArticulos = stockArticulos;
    }
    /**
     * Establece el stock de un artículo específico.
     * @param articuloDTO artículo a actualizar.
     * @param stock nueva cantidad disponible.
     */
    public void setStockArticulo(ArticuloDTO articuloDTO, Integer stock) {
        this.stockArticulos.put(articuloDTO, stock);
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un artículo a la lista de artículos.
     * @param articuloDTO artículo a añadir.
     */
    public void addArticulo(ArticuloDTO articuloDTO) {
        articulosDTO.add(articuloDTO);
    }
    /**
     * Añade stock para un artículo determinado.
     * @param articuloDTO artículo a actualizar.
     * @param stock cantidad a añadir.
     */
    public void addStockArticulo(ArticuloDTO articuloDTO, int stock) {
        stockArticulos.put(articuloDTO, stock);
    }

    /**
     * Elimina un artículo de la lista.
     * @param articuloDTO artículo a eliminar.
     */
    public void removeArticulo(ArticuloDTO articuloDTO) {
        articulosDTO.remove(articuloDTO);
    }
    /**
     * Elimina el stock de un artículo.
     * @param articuloDTO artículo del cual eliminar el stock.
     */
    public void removeStockArticulo(ArticuloDTO articuloDTO) {
        stockArticulos.remove(articuloDTO);
    }

    /**
     * Reemplaza un artículo por uno nuevo.
     * @param articuloDTOOld artículo original.
     * @param articuloDTONew nuevo artículo.
     */
    public void updateArticulo(ArticuloDTO articuloDTOOld, ArticuloDTO articuloDTONew) {
        int index = articulosDTO.indexOf(articuloDTOOld);
        if (index != -1) {
            articulosDTO.set(index, articuloDTONew);
        }
    }
    /**
     * Actualiza el stock de un artículo.
     * @param articuloDTO artículo a actualizar.
     * @param stockNew nueva cantidad de stock.
     */
    public void updateStockArticulo(ArticuloDTO articuloDTO, int stockNew) {
        stockArticulos.put(articuloDTO, stockNew);
    }

    //*************************** Crear datos ***************************//

    /**
     * Crea un nuevo artículo con un código generado automáticamente.
     * @param descripcion descripción del artículo.
     * @param precio precio del artículo.
     * @param preparacion tiempo de preparación en minutos.
     * @return instancia del nuevo artículo.
     */
    public ArticuloDTO makeArticulo(String descripcion, Float precio, Integer preparacion) {
        String codigo;
        if (articulosDTO.isEmpty()) codigo = "ART000";
        else {
            String lastCodigo = articulosDTO.getLast().getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(lastCodigo.substring(3)) + 1);
        }
        return new ArticuloDTO(codigo, descripcion, precio, preparacion);
    }

    /**
     * Precarga una lista de artículos con stock para pruebas.
     */
    public void loadArticulos() {
        if (configuracion == 0) {
            try {
                articulosDTO.clear();
                ArticuloDTO articuloDTOTemp = new ArticuloDTO();
                articuloDTOTemp = makeArticulo("Guitarra española de juguete.", 6f, 50);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 5);
                articuloDTOTemp = makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 100);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 9);
                articuloDTOTemp = makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 70);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 14);
                articuloDTOTemp = makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 30);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 20);
                articuloDTOTemp = makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 200);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 8);
                articuloDTOTemp = makeArticulo("Muñeca Nancy - Famosa.", 20f, 25);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 10);
                articuloDTOTemp = makeArticulo("Madelman - Figura de acción articulada.", 15f, 1000);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 12);
                articuloDTOTemp = makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 450);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 14);
                articuloDTOTemp = makeArticulo("Simon - Juego electrónico de memoria.", 14f, 245);
                addArticulo(articuloDTOTemp);
                addStockArticulo(articuloDTOTemp, 20);
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
        for (ArticuloDTO articuloDTO : articulosDTO) {
            if (articuloDTO.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}

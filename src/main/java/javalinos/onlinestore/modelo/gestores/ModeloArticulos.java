package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;

import java.util.*;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los artículos y su stock.
 */
public class ModeloArticulos {

    private List<ArticuloDTO> ArticuloDTOS;
    private Map<ArticuloDTO, Integer> stockArticulos;
    /**
     * Constructor por defecto. Inicializa las listas de artículos y su stock.
     */
    public ModeloArticulos() {
        this.ArticuloDTOS = new ArrayList<>();
        this.stockArticulos = new LinkedHashMap<>();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve una lista con todos los artículos registrados.
     * @return lista de artículos.
     */
    public List<ArticuloDTO> getArticulos() {
        return ArticuloDTOS;
    }
    /**
     * Devuelve un artículo según su posición en la lista.
     * @param index posición en la lista.
     * @return artículo encontrado o null si está fuera de rango.
     */
    public ArticuloDTO getArticuloIndex(int index) {
        if (index < 0 || index >= ArticuloDTOS.size()) return null;
        return ArticuloDTOS.get(index);
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
     * @param ArticuloDTO artículo a consultar.
     * @return unidades disponibles o null si no está en el mapa.
     */
    public Integer getStockArticulo(ArticuloDTO ArticuloDTO) {
        if (stockArticulos.isEmpty()) return null;
        return stockArticulos.get(ArticuloDTO);
    }

    /**
     * Establece una nueva lista de artículos.
     * @param ArticuloDTOS nueva lista de artículos.
     */
    public void setArticulos(List<ArticuloDTO> ArticuloDTOS) {
        this.ArticuloDTOS = ArticuloDTOS;
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
     * @param ArticuloDTO artículo a actualizar.
     * @param stock nueva cantidad disponible.
     */
    public void setStockArticulo(ArticuloDTO ArticuloDTO, Integer stock) {
        this.stockArticulos.put(ArticuloDTO, stock);
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un artículo a la lista de artículos.
     * @param ArticuloDTO artículo a añadir.
     */
    public void addArticulo(ArticuloDTO ArticuloDTO) {
        ArticuloDTOS.add(ArticuloDTO);
    }
    /**
     * Añade stock para un artículo determinado.
     * @param ArticuloDTO artículo a actualizar.
     * @param stock cantidad a añadir.
     */
    public void addStockArticulo(ArticuloDTO ArticuloDTO, int stock) {
        stockArticulos.put(ArticuloDTO, stock);
    }

    /**
     * Elimina un artículo de la lista.
     * @param ArticuloDTO artículo a eliminar.
     */
    public void removeArticulo(ArticuloDTO ArticuloDTO) {
        ArticuloDTOS.remove(ArticuloDTO);
    }
    /**
     * Elimina el stock de un artículo.
     * @param ArticuloDTO artículo del cual eliminar el stock.
     */
    public void removeStockArticulo(ArticuloDTO ArticuloDTO) {
        stockArticulos.remove(ArticuloDTO);
    }

    /**
     * Reemplaza un artículo por uno nuevo.
     * @param ArticuloDTOOld artículo original.
     * @param ArticuloDTONew nuevo artículo.
     */
    public void updateArticulo(ArticuloDTO ArticuloDTOOld, ArticuloDTO ArticuloDTONew) {
        int index = ArticuloDTOS.indexOf(ArticuloDTOOld);
        if (index != -1) {
            ArticuloDTOS.set(index, ArticuloDTONew);
        }
    }
    /**
     * Actualiza el stock de un artículo.
     * @param ArticuloDTO artículo a actualizar.
     * @param stockNew nueva cantidad de stock.
     */
    public void updateStockArticulo(ArticuloDTO ArticuloDTO, int stockNew) {
        stockArticulos.put(ArticuloDTO, stockNew);
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
        if (ArticuloDTOS.isEmpty()) codigo = "ART000";
        else {
            String lastCodigo = ArticuloDTOS.getLast().getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(lastCodigo.substring(3)) + 1);
        }
        return new ArticuloDTO(codigo, descripcion, precio, preparacion);
    }

    /**
     * Precarga una lista de artículos con stock para pruebas.
     * @param configuracion define si se carga el modo por defecto.
     * @return true si se cargó correctamente, false si hubo error.
     */
    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            try {
                ArticuloDTOS.clear();
                ArticuloDTO ArticuloDTOTemp = new ArticuloDTO();
                ArticuloDTOTemp = makeArticulo("Guitarra española de juguete.", 6f, 50);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 5);
                ArticuloDTOTemp = makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 100);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 9);
                ArticuloDTOTemp = makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 70);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 14);
                ArticuloDTOTemp = makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 30);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 20);
                ArticuloDTOTemp = makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 200);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 8);
                ArticuloDTOTemp = makeArticulo("Muñeca Nancy - Famosa.", 20f, 25);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 10);
                ArticuloDTOTemp = makeArticulo("Madelman - Figura de acción articulada.", 15f, 1000);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 12);
                ArticuloDTOTemp = makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 450);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 14);
                ArticuloDTOTemp = makeArticulo("Simon - Juego electrónico de memoria.", 14f, 245);
                addArticulo(ArticuloDTOTemp);
                addStockArticulo(ArticuloDTOTemp, 20);
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
        for (ArticuloDTO ArticuloDTO : ArticuloDTOS) {
            if (ArticuloDTO.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}

package javalinos.onlinestore.modelo.gestores.Local;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;

import java.util.*;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los artículos y su stock.
 */
public class ModeloArticulosLocal implements IModeloArticulos {

    private List<ArticuloDTO> articulosDTO;
    private LinkedHashMap<ArticuloDTO, Integer> stockArticulos;
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
    public List<ArticuloDTO> getArticulosDTO() {
        return articulosDTO;
    }
    /**
     * Devuelve un artículo según su posición en la lista.
     * @param index posición en la lista.
     * @return artículo encontrado o null si está fuera de rango.
     */
    public ArticuloDTO getArticuloDTOIndex(int index) throws Exception {
        if (index < 0 || index >= articulosDTO.size()) return null;
        return articulosDTO.get(index);
    }

    @Override
    public ArticuloDTO getArticuloDTOId(Integer id) throws Exception {
        return null;
    }

    @Override
    public ArticuloDTO getArticuloDTOCodigo(String codigo) throws Exception {
        return null;
    }

    @Override
    public Articulo getArticuloEntidadId(Integer id) throws Exception {
        return null;
    }

    @Override
    public Articulo getArticuloEntidadCodigo(String codigo) throws Exception {
        return null;
    }

    /**
     * Devuelve el mapa de stock de todos los artículos.
     *
     * @return mapa con artículos y cantidades disponibles.
     */
    public LinkedHashMap<ArticuloDTO, Integer> getArticuloStocksDTO() throws Exception {
        return stockArticulos;
    }

    @Override
    public Map<Integer, Integer> getArticuloStocksDTOIds() throws Exception {
        return Map.of();
    }

    /**
     * Devuelve el stock de un artículo concreto.
     * @param articuloDTO artículo a consultar.
     * @return unidades disponibles o null si no está en el mapa.
     */
    public Integer getStockArticulo(ArticuloDTO articuloDTO) throws Exception {
        if (stockArticulos.isEmpty()) return null;
        return stockArticulos.get(articuloDTO);
    }

    /**
     * Establece una nueva lista de artículos.
     * @param articulosDTO nueva lista de artículos.
     */
    public void setArticulos(List<ArticuloDTO> articulosDTO) throws Exception {
        this.articulosDTO = articulosDTO;
    }
    /**
     * Establece un nuevo mapa de stock.
     * @param stockArticulos mapa de artículos con su stock.
     */
    public void setStockArticulos(LinkedHashMap<ArticuloDTO, Integer> stockArticulos) throws Exception {
        this.stockArticulos = stockArticulos;
    }
    /**
     * Establece el stock de un artículo específico.
     * @param articuloDTO artículo a actualizar.
     * @param stock nueva cantidad disponible.
     */
    public void updateStockArticulo(ArticuloDTO articuloDTO, Integer stock) throws Exception {
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
    public void addArticuloStock(ArticuloDTO articuloDTO, int stock) throws Exception {
        stockArticulos.put(articuloDTO, stock);
    }

    /**
     * Elimina un artículo de la lista.
     * @param articuloDTO artículo a eliminar.
     */
    public void removeArticulo(ArticuloDTO articuloDTO) throws Exception {
        articulosDTO.remove(articuloDTO);
    }

    @Override
    public void removeArticulosAll() throws Exception {

    }

    /**
     * Elimina el stock de un artículo.
     * @param articuloDTO artículo del cual eliminar el stock.
     */
    public void removeArticuloStock(ArticuloDTO articuloDTO) throws Exception {
        stockArticulos.remove(articuloDTO);
    }

    @Override
    public void removeArticulosStockAll() throws Exception {
        stockArticulos.clear();
    }

    /**
     * Reemplaza un artículo por uno nuevo.
     * @param articuloDTOOld artículo original.
     * @param articuloDTONew nuevo artículo.
     */
    public void updateArticulo(ArticuloDTO articuloDTOOld, ArticuloDTO articuloDTONew) throws Exception {
        int index = articulosDTO.indexOf(articuloDTOOld);
        if (index != -1) {
            articulosDTO.set(index, articuloDTONew);
        }
    }

    @Override
    public void updateArticuloStock(ArticuloDTO articuloDTONew, Integer stockNew) throws Exception {

    }

    @Override
    public void updateArticuloStockSP(ArticuloDTO articuloDTONew, Integer stockNew) throws Exception {

    }

    /**
     * Actualiza el stock de un artículo.
     * @param articuloDTO artículo a actualizar.
     * @param stockNew nueva cantidad de stock.
     */
    public void updateStockArticulo(ArticuloDTO articuloDTO, int stockNew) throws Exception {
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
            try {
                articulosDTO.clear();
                stockArticulos.clear();
                ArticuloDTO articuloDTOTemp = new ArticuloDTO();
                articuloDTOTemp = makeArticulo("Guitarra española de juguete.", 6f, 50);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 5);
                articuloDTOTemp = makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 100);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 9);
                articuloDTOTemp = makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 70);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 14);
                articuloDTOTemp = makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 30);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 20);
                articuloDTOTemp = makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 200);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 8);
                articuloDTOTemp = makeArticulo("Muñeca Nancy - Famosa.", 20f, 25);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 10);
                articuloDTOTemp = makeArticulo("Madelman - Figura de acción articulada.", 15f, 1000);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 12);
                articuloDTOTemp = makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 450);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 14);
                articuloDTOTemp = makeArticulo("Simon - Juego electrónico de memoria.", 14f, 245);
                addArticulo(articuloDTOTemp);
                addArticuloStock(articuloDTOTemp, 20);
            }
            catch (Exception e) {
                return;
            }
    }

    @Override
    public int getIdArticuloDTO(ArticuloDTO articuloDTO) throws Exception {
        return 0;
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

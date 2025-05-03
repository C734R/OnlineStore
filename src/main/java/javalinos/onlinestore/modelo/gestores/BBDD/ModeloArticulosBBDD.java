package javalinos.onlinestore.modelo.gestores.BBDD;

import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;

import java.util.*;

import static javalinos.onlinestore.OnlineStore.configuracion;

/**
 * Modelo encargado de gestionar las operaciones relacionadas con los artículos y su stock.
 */
public class ModeloArticulosBBDD implements IModeloArticulos
{
    private final FactoryDAO factoryDAO;
    /**
     * Constructor por defecto. Inicializa las listas de artículos y su stock.
     */
    public ModeloArticulosBBDD(FactoryDAO factoryDAO)
    {
        this.factoryDAO = factoryDAO;
    }

    //*************************** CRUD ARTÍCULOS ***************************//

    /**
     * Añade un artículo a la lista de artículos.
     * @param articuloDTO artículo a añadir.
     */
    public void addArticulo(ArticuloDTO articuloDTO) throws Exception
    {
        Articulo articulo = new Articulo(articuloDTO);
        factoryDAO.getDAOArticulo().insertar(articulo);
    }


    /**
     * Elimina un artículo de la lista.
     * @param articuloDTO artículo a eliminar.
     */
    public void removeArticulo(ArticuloDTO articuloDTO) throws Exception
    {
        Articulo articulo = getArticuloEntidadCodigo(articuloDTO.getCodigo());
        factoryDAO.getDAOArticulo().eliminar(articulo.getId());
    }

    public void removeArticulosAll() throws Exception
    {
        factoryDAO.getDAOArticulo().eliminarTodos();
    }

    /**
     * Reemplaza un artículo por uno nuevo.
     * @param articuloDTOOld artículo original.
     * @param articuloDTONew nuevo artículo.
     */
    public void updateArticulo(ArticuloDTO articuloDTOOld, ArticuloDTO articuloDTONew) throws Exception
    {
        Articulo articuloOld = getArticuloEntidadCodigo(articuloDTOOld.getCodigo());
        Articulo articuloNew = new Articulo(articuloDTOOld);
        articuloNew.setId(articuloOld.getId());
        factoryDAO.getDAOArticulo().actualizar(articuloNew);
    }

    public void updateArticuloStock(ArticuloDTO articuloDTONew , Integer stockNew) throws Exception {
        // Conviertes de DTO a Entidad (sin ID)
        Articulo articuloEntidadNew = new Articulo(articuloDTONew);
        // Extraes código único de DTO
        String codigoArticuloNuevo = articuloEntidadNew.getCodigo();
        // Obtienes EntidadOld
        Articulo articuloEntidadOld = getArticuloEntidadCodigo(codigoArticuloNuevo);
        // Asignas ID EntidadOld a EntidadNew
        articuloEntidadNew.setId(articuloEntidadOld.getId());
        // Actualizas stock pasando EntidadNew y StockNew
        factoryDAO.getDAOArticulo().actualizarArticuloStock(articuloEntidadNew, stockNew);
    }

    public void updateArticuloStockSP(ArticuloDTO articuloDTONew, Integer stockNew) throws Exception {
        Articulo articuloEntidadNew = new Articulo(articuloDTONew);
        String codigoArticuloNuevo = articuloEntidadNew.getCodigo();
        Articulo articuloEntidadOld = getArticuloEntidadCodigo(codigoArticuloNuevo);
        articuloEntidadNew.setId(articuloEntidadOld.getId());
        factoryDAO.getDAOArticulo().actualizarArticuloConStockSP(articuloEntidadNew, stockNew);
    }

    //*************************** GETTERS & SETTERS ARTÍCULOS ***************************//

    /**
     * Devuelve una lista con todos los artículos registrados.
     * @return lista de artículos.
     */
    private List<Articulo> getArticulos() throws Exception
    {
        return factoryDAO.getDAOArticulo().getTodos();
    }

    public List<ArticuloDTO> getArticulosDTO() throws Exception
    {
        return articulosEntidadesToDTO(getArticulos());
    }

    private List<Articulo> articulosDTOToEntidade(List<ArticuloDTO> articulosDTO)
    {
        List<Articulo> articulos = new ArrayList<>();
        for (ArticuloDTO articuloDTO : articulosDTO) {
            Articulo articulo = new Articulo(articuloDTO);
            articulos.add(articulo);
        }
        return articulos;
    }

    private List<ArticuloDTO> articulosEntidadesToDTO(List<Articulo> articulos)
    {
        List<ArticuloDTO> articulosDTO = new ArrayList<>();
        for (Articulo articulo : articulos) {
            ArticuloDTO articuloDTO = new ArticuloDTO(articulo);
            articulosDTO.add(articuloDTO);
        }
        return articulosDTO;
    }
    /**
     * Devuelve un artículo según su posición en la lista.
     * @param index posición en la lista.
     * @return artículo encontrado o null si está fuera de rango.
     */
    public ArticuloDTO getArticuloDTOIndex(int index) throws Exception
    {
        List<ArticuloDTO> articulosDTO = getArticulosDTO();
        if (index < 0 || index >= articulosDTO.size()) return null;
        return articulosDTO.get(index);
    }

    public int getIdArticuloDTO(ArticuloDTO articuloDTO) throws Exception
    {
        return factoryDAO.getDAOArticulo().getArticuloCodigo(articuloDTO.getCodigo()).getId();
    }

    public Articulo getArticuloEntidadCodigo(String codigo) throws Exception
    {
        return factoryDAO.getDAOArticulo().getArticuloCodigo(codigo);
    }

    public ArticuloDTO getArticuloDTOId(Integer id) throws Exception
    {
        return new ArticuloDTO(getArticuloEntidadId(id));
    }

    public ArticuloDTO getArticuloDTOCodigo(String codigo) throws Exception
    {
        return new ArticuloDTO(getArticuloEntidadCodigo(codigo));
    }


    public Articulo getArticuloEntidadId(Integer id) throws Exception
    {
        return factoryDAO.getDAOArticulo().getPorId(id);
    }

    /**
     * Establece una nueva lista de artículos.
     * @param articulosDTO nueva lista de artículos.
     */
    public void setArticulos(List<ArticuloDTO> articulosDTO) throws Exception
    {
        removeArticulosAll();
        factoryDAO.getDAOArticulo().insertarTodos(articulosDTOToEntidade(articulosDTO));
    }

    //*************************** CRUD STOCKARTÍCULOS ***************************//

    /**
     * Añade stock para un artículo determinado.
     * @param articuloDTO artículo a actualizar.
     * @param stock cantidad a añadir.
     */
    public void addArticuloStock(ArticuloDTO articuloDTO, int stock) throws Exception
    {
        Articulo articulo = getArticuloEntidadCodigo(articuloDTO.getCodigo());
        ArticuloStock articuloStock = new ArticuloStock(articulo, stock);
        factoryDAO.getDAOArticuloStock().insertar(articuloStock);
    }

    /**
     * Elimina el stock de un artículo.
     * @param articuloDTO artículo del cual eliminar el stock.
     */
    public void removeArticuloStock(ArticuloDTO articuloDTO) throws Exception
    {
        Articulo articulo = getArticuloEntidadCodigo(articuloDTO.getCodigo());
        factoryDAO.getDAOArticuloStock().eliminar(articulo.getId());
    }

    public void removeArticulosStockAll() throws Exception
    {
        factoryDAO.getDAOArticuloStock().eliminarTodos();
    }

    /**
     * Devuelve el mapa de stock de todos los artículos.
     *
     * @return mapa con artículos y cantidades disponibles.
     */
    public LinkedHashMap<ArticuloDTO, Integer> getArticuloStocksDTO() throws Exception {
        List<ArticuloStock> articuloStocks = getArticuloStocksEntidades();
        return mapearArticuloStocks(articuloStocks);
    }

    public Map<Integer, Integer> getArticuloStocksDTOIds() throws Exception {
        List<ArticuloStock> articuloStocks = getArticuloStocksEntidades();
        return mapearArticuloStocksId(articuloStocks);
    }

    private List<ArticuloStock> getArticuloStocksEntidades() throws Exception {
        return factoryDAO.getDAOArticuloStock().getTodos();
    }

    private LinkedHashMap<ArticuloDTO, Integer> mapearArticuloStocks (List<ArticuloStock> articuloStocks) throws Exception {
        LinkedHashMap<ArticuloDTO, Integer> articuloStocksDTO = new LinkedHashMap<>();
        for (ArticuloStock articuloStock : articuloStocks) {
            ArticuloDTO articuloDTO = getArticuloDTOEntidadArticuloStock(articuloStock);
            articuloStocksDTO.put(articuloDTO, articuloStock.getStock());
        }
        return articuloStocksDTO;
    }

    private ArticuloDTO getArticuloDTOEntidadArticuloStock(ArticuloStock articuloStock) throws Exception {
        return switch (configuracion) {
            case JDBC_MYSQL ->  getArticuloDTOId(articuloStock.getArticuloId());
            case JPA_HIBERNATE_MYSQL -> getArticuloDTOId(articuloStock.getArticulo().getId());
            default -> null;
        };
    }

    private Map<Integer, Integer> mapearArticuloStocksId (List<ArticuloStock> articuloStocks) throws Exception {
        Map<Integer, Integer> stockIdArticulo = new LinkedHashMap<>();
        for (ArticuloStock articuloStock : articuloStocks) {
            stockIdArticulo.put(articuloStock.getArticuloId(), articuloStock.getStock());
        }
        return stockIdArticulo;
    }

    /**
     * Establece un nuevo mapa de stock.
     * @param stockArticulos mapa de artículos con su stock.
     */
    public void setStockArticulos(LinkedHashMap<ArticuloDTO, Integer> stockArticulos) throws Exception
    {
        List<ArticuloStock> articuloStocks = new ArrayList<>();
        for (ArticuloDTO articuloDTO : stockArticulos.keySet()) {
            Articulo articulo = getArticuloEntidadCodigo(articuloDTO.getCodigo());
            ArticuloStock articuloStock = new ArticuloStock(articulo.getId(), stockArticulos.get(articuloDTO));
            articuloStocks.add(articuloStock);
        }
        factoryDAO.getDAOArticuloStock().insertarTodos(articuloStocks);
    }

    /**
     * Devuelve el stock de un artículo concreto.
     * @param articuloDTO artículo a consultar.
     * @return unidades disponibles o null si no está en el mapa.
     */
    public Integer getStockArticulo(ArticuloDTO articuloDTO) throws Exception
    {
        Articulo articulo = getArticuloEntidadCodigo(articuloDTO.getCodigo());
        return factoryDAO.getDAOArticuloStock().getPorId(articulo.getId()).getStock();
    }

    /**
     * Establece el stock de un artículo específico.
     * @param articuloDTO artículo a actualizar.
     * @param stock nueva cantidad disponible.
     */
    public void updateStockArticulo(ArticuloDTO articuloDTO, Integer stock) throws Exception {
        Articulo articulo = getArticuloEntidadCodigo(articuloDTO.getCodigo());
        ArticuloStock articuloStock = new ArticuloStock(articulo.getId(), stock);
        factoryDAO.getDAOArticuloStock().actualizar(articuloStock);
    }

    //*************************** CREAR DATOS ***************************//

    /**
     * Crea un nuevo artículo con un código generado automáticamente.
     * @param descripcion descripción del artículo.
     * @param precio precio del artículo.
     * @param preparacion tiempo de preparación en minutos.
     * @return instancia del nuevo artículo.
     */
    public ArticuloDTO makeArticulo(String descripcion, Float precio, Integer preparacion) throws Exception {
        String codigo;
        List<ArticuloDTO> articulosDTO = getArticulosDTO();
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
    public void loadArticulos() throws Exception {
        try {
            removeArticulosAll();
            removeArticulosStockAll();

            ArticuloDTO articuloDTOTemp;
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
        catch (Exception e)
        {
            throw new Exception("Error al precargar artículos.", e);
        }
    }
}

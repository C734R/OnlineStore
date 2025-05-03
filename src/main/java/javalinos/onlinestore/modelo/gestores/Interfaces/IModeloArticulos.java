package javalinos.onlinestore.modelo.gestores.Interfaces;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.Entidades.Articulo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IModeloArticulos {

    List<ArticuloDTO> getArticulosDTO() throws Exception;
    ArticuloDTO getArticuloDTOIndex(int index) throws Exception;
    ArticuloDTO getArticuloDTOId(Integer id) throws Exception;
    ArticuloDTO getArticuloDTOCodigo(String codigo) throws Exception;
    Articulo getArticuloEntidadId(Integer id) throws Exception;
    Articulo getArticuloEntidadCodigo(String codigo) throws Exception;
    LinkedHashMap<ArticuloDTO, Integer> getArticuloStocksDTO() throws Exception;
    public Map<Integer, Integer> getArticuloStocksDTOIds() throws Exception;
    Integer getStockArticulo(ArticuloDTO articuloDTO) throws Exception;
    void setArticulos(List<ArticuloDTO> articulosDTO) throws Exception;
    void setStockArticulos(LinkedHashMap<ArticuloDTO, Integer> stockArticulos) throws Exception;
    void addArticulo(ArticuloDTO articuloDTO) throws Exception;
    void addArticuloStock(ArticuloDTO articuloDTO, int stock) throws Exception;
    void removeArticulo(ArticuloDTO articuloDTO) throws Exception;
    void removeArticulosAll() throws Exception;
    void removeArticuloStock(ArticuloDTO articuloDTO) throws Exception;
    void removeArticulosStockAll() throws Exception;
    void updateArticulo(ArticuloDTO articuloDTOold, ArticuloDTO articuloDTONew) throws Exception;
    void updateArticuloStock(ArticuloDTO articuloDTONew, Integer stockNew) throws Exception;
    void updateArticuloStockSP(ArticuloDTO articuloDTONew, Integer stockNew) throws Exception;
    void updateStockArticulo(ArticuloDTO articuloDTOold, Integer stock) throws Exception;
    ArticuloDTO makeArticulo(String descripcion, Float precio, Integer preparacion) throws Exception;
    void loadArticulos() throws Exception;
    int getIdArticuloDTO(ArticuloDTO articuloDTO) throws Exception;
}

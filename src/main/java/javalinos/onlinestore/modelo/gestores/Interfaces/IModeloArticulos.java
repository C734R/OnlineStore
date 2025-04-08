package javalinos.onlinestore.modelo.gestores.Interfaces;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;

import java.util.List;
import java.util.Map;

public interface IModeloArticulos {


    List<ArticuloDTO> getArticulos();
    ArticuloDTO getArticuloIndex(int index);
    Map<ArticuloDTO, Integer> getStockArticulos();
    Integer getStockArticulo(ArticuloDTO articuloDTO);
    void setArticulos(List<ArticuloDTO> articulosDTO);
    void setStockArticulos(Map<ArticuloDTO, Integer> stockArticulos);
    void setStockArticulo(ArticuloDTO articuloDTO, Integer stock);
    void addArticulo(ArticuloDTO articuloDTO);
    void addStockArticulo(ArticuloDTO articuloDTO, int stock);
    void removeArticulo(ArticuloDTO articuloDTO);
    void removeStockArticulo(ArticuloDTO articuloDTO);
    void updateArticulo(ArticuloDTO articuloDTOold, ArticuloDTO articuloDTONew);
    void updateStockArticulo(ArticuloDTO articuloDTOold, int stock);
    ArticuloDTO makeArticulo(String descripcion, Float precio, Integer preparacion);
    void loadArticulos();

    }

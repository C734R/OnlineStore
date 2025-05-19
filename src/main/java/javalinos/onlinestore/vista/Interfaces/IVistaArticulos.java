package javalinos.onlinestore.vista.Interfaces;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;

import java.util.List;
import java.util.Map;

public interface IVistaArticulos extends IVistaBase{

    float askPrecio(float min, float max);
    void showListArticulos(List<ArticuloDTO> articulosDTO);
    void showListArticulosStock(Map<ArticuloDTO, Integer> articuloStockMap);
    void showListArticulosNumerada(List<ArticuloDTO> articulosDTO);
    void showArticulo(ArticuloDTO articuloDTO);
    void showStockArticulos(Map<ArticuloDTO, Integer> articuloStockMap);

    int askRemoveArticulo(List<ArticuloDTO> articulosDTO);
}

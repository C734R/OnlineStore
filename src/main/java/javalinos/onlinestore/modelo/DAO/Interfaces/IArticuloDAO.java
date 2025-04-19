package javalinos.onlinestore.modelo.DAO.Interfaces;

import javalinos.onlinestore.modelo.Entidades.Articulo;

public interface IArticuloDAO extends IBaseDAO<Articulo, Integer> {

    Articulo getArticuloCodigo(String codigo) throws Exception;

    void actualizarArticuloStock(Articulo articuloNew, Integer stockNew) throws Exception;
}

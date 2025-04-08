package javalinos.onlinestore.modelo.DAO.Interfaces;

import javalinos.onlinestore.modelo.Entidades.Categoria;

public interface ICategoriaDAO extends IBaseDAO<Categoria, Integer> {

    Categoria getPorNombre(String nombre) throws Exception;
}

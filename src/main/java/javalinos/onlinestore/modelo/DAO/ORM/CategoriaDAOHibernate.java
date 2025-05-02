package javalinos.onlinestore.modelo.DAO.ORM;

import javalinos.onlinestore.modelo.DAO.Interfaces.ICategoriaDAO;
import javalinos.onlinestore.modelo.Entidades.Categoria;

public class CategoriaDAOHibernate extends BaseDAOHIbernate<Categoria, Integer> implements ICategoriaDAO {

    public CategoriaDAOHibernate() {
        super(Categoria.class);
    }
}

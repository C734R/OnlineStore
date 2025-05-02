package javalinos.onlinestore.modelo.DAO.ORM;


import javalinos.onlinestore.modelo.DAO.Interfaces.IArticuloStockDAO;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;

public class ArticuloStockDAOHibernate extends BaseDAOHIbernate<ArticuloStock, Integer> implements IArticuloStockDAO {

    public ArticuloStockDAOHibernate() {
        super(ArticuloStock.class);
    }
}

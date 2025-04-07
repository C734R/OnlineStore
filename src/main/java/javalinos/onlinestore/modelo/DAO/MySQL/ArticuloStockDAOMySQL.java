package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IArticuloStockDAO;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloStockDAOMySQL extends BaseDAOMySQL<ArticuloStock, Integer> implements IArticuloStockDAO {

    public ArticuloStockDAOMySQL(Connection conexion) throws SQLException {
        super(conexion);
        super.tabla = "ArticuloStock";
    }

    @Override
    public ArticuloStock objetoResulset(ResultSet rs) throws SQLException {
        return new ArticuloStock(
                rs.getInt("articulo"),
                rs.getInt("stock")
        );
    }

    @Override
    public String definirSet() {
        return "articulo = ?, stock = ?";
    }

    @Override
    public Integer definirId(ArticuloStock articuloStock) {
        return articuloStock.getArticulo();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, ArticuloStock articuloStock) throws SQLException {
        stmt.setInt(1, articuloStock.getArticulo());
        stmt.setInt(2, articuloStock.getStock());
    }
}

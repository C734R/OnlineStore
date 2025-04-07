package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IArticuloDAO;
import javalinos.onlinestore.modelo.Entidades.Articulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDAOMySQLMySQL extends BaseDAOMySQL<Articulo, Integer> implements IArticuloDAO {

    public ArticuloDAOMySQLMySQL(Connection conexion) throws SQLException {
        super(conexion);
        super.tabla = "Articulo";
    }

    @Override
    public Articulo objetoResulset(ResultSet rs) throws SQLException {
        return new Articulo(
                rs.getInt("id"),
                rs.getString("codigo"),
                rs.getString("descripcion"),
                rs.getFloat("precio"),
                rs.getInt("minutosPreparacion")
        );
    }

    @Override
    public String definirSet() {
        return "codigo = ?, descripcion = ?, precio = ?, minutosPreparacion = ?";
    }

    @Override
    public Integer definirId(Articulo articulo) {
        return articulo.getId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, Articulo articulo) throws SQLException {
            stmt.setString(1, articulo.getCodigo());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setFloat(3, articulo.getPrecio());
            stmt.setInt(4, articulo.getPreparacion());
    }

}

package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.ICategoriaDAO;
import javalinos.onlinestore.modelo.Entidades.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDAOMySQL extends BaseDAOMySQL<Categoria, Integer> implements ICategoriaDAO {

    public CategoriaDAOMySQL(Connection conexion) throws SQLException {
        super(conexion);
        this.tabla = "categoria";
    }

    @Override
    public Categoria objetoResulset(ResultSet rs) throws SQLException {
        return new Categoria(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getFloat("cuota"),
                rs.getFloat("descuento")
        );
    }

    @Override
    public String definirSet() {
        return "nombre = ?, cuota = ?, descuento = ?";
    }

    @Override
    public String definirValues() { return  "( ?, ?, ? )"; }

    @Override
    public String definirColumnas() {
        return "(nombre, cuota, descuento)";
    }

    @Override
    public void definirSetInsert(PreparedStatement stmt, Categoria entidad) throws SQLException {
        stmt.setString(1, entidad.getNombre());
        stmt.setFloat(2, entidad.getCuota());
        stmt.setFloat(3, entidad.getDescuento());
    }
    @Override
    public Integer definirId(Categoria categoria) {
        return categoria.getId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, Categoria categoria) throws SQLException {
        stmt.setString(1, categoria.getNombre());
        stmt.setFloat(2, categoria.getCuota());
        stmt.setFloat(3, categoria.getDescuento());
    }
}

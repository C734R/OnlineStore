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
        this.tabla = "Categoria";
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
    public Integer definirId(Categoria categoria) {
        return categoria.getId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, Categoria categoria) throws SQLException {
        stmt.setString(1, categoria.getNombre());
        stmt.setFloat(2, categoria.getCuota());
        stmt.setFloat(3, categoria.getDescuento());
    }

    @Override
    public Categoria getPorNombre(String nombre) throws Exception {

        Categoria entidad = null;
        // Crear query
        String query = "SELECT * FROM " + tabla + " WHERE nombre = ?";
        try
        {
            // Detener autocommit
            conexion.setAutoCommit(false);
            // Crear prepared statement
            PreparedStatement stmt1 = conexion.prepareStatement(query);
            // Definir
            stmt1.setObject(1, nombre);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                entidad = objetoResulset(rs);
            }
            rs.close();
            stmt1.close();
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception(e.getMessage());
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
        return entidad;

    }

}

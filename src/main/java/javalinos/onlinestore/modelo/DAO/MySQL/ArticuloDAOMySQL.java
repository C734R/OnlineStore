package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IArticuloDAO;
import javalinos.onlinestore.modelo.Entidades.Articulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDAOMySQL extends BaseDAOMySQL<Articulo, Integer> implements IArticuloDAO
{

    public ArticuloDAOMySQL(Connection conexion) throws SQLException
    {
        super(conexion);
        super.tabla = "articulo";
    }

    @Override
    public Articulo objetoResulset(ResultSet rs) throws SQLException
    {
        return new Articulo(
                rs.getInt("id"),
                rs.getString("codigo"),
                rs.getString("descripcion"),
                rs.getFloat("precio"),
                rs.getInt("minutosPreparacion")
        );
    }

    @Override
    public String definirSet()
    {
        return "codigo = ?, descripcion = ?, precio = ?, minutosPreparacion = ?";
    }

    @Override
    public String definirValues() { return  "( ?, ?, ?, ? )"; }

    @Override
    public String definirColumnas() { return "(codigo, descripcion, precio, minutosPreparacion)"; }
    @Override
    public void definirSetInsert(PreparedStatement stmt, Articulo entidad) throws SQLException {
        stmt.setString(1, entidad.getCodigo());
        stmt.setString(2, entidad.getDescripcion());
        stmt.setFloat(3, entidad.getPrecio());
        stmt.setInt(4, entidad.getMinutosPreparacion());
    }

    @Override
    public Integer definirId(Articulo articulo)
    {
        return articulo.getId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, Articulo articulo) throws SQLException
    {
            stmt.setString(1, articulo.getCodigo());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setFloat(3, articulo.getPrecio());
            stmt.setInt(4, articulo.getMinutosPreparacion());
    }

    public Articulo getArticuloCodigo(String codigo) throws Exception
    {
        Articulo articulo = null;
        String sql = "SELECT * FROM " + tabla + " WHERE codigo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql))
        {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery())
            {
                int filas = 0;
                while (rs.next())
                {
                    filas++;
                    if (filas > 1) throw new Exception("Existe más de una coincidencia de código en la tabla " + tabla + ".");
                    articulo = objetoResulset(rs);
                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener el artículo por código.", e);
        }
        return articulo;
    }

}

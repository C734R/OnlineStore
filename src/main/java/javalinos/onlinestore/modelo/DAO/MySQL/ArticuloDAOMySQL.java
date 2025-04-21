package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.DAO.Interfaces.IArticuloDAO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;

import java.math.BigDecimal;
import java.sql.*;

public class ArticuloDAOMySQL extends BaseDAOMySQL<Articulo, Integer> implements IArticuloDAO
{
    private FactoryDAO factoryDAO;

    public ArticuloDAOMySQL(Connection conexion, FactoryDAO factoryDAO) throws SQLException
    {
        super(conexion);
        super.tabla = "articulo";
        this.factoryDAO = factoryDAO;
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

    public void actualizarArticuloStock(Articulo articuloNew, Integer stockNew) throws Exception {
        String sql = "UPDATE " + tabla + " SET " + definirSet() + " WHERE id = ?";
        boolean autocommitOriginal = conexion.getAutoCommit();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            conexion.setAutoCommit(false);
            mapearUpdate(stmt, articuloNew);
            int ultimoParametro = obtenerUltimoParametro(stmt);
            Object entidadId = definirId(articuloNew);
            if (entidadId == null) throw new Exception("La pedido no tiene un ID definido. No se puede actualizar.");
            stmt.setInt(ultimoParametro, (int) entidadId);
            int filas = stmt.executeUpdate();
            if (filas <= 0) {
                throw new Exception("No se ha encontrado el artículo con ID: " + entidadId);
            }
            if (stockNew != null) ActualizarStock(articuloNew, stockNew);
            conexion.commit();
        } catch (Exception e) {
            conexion.rollback();
            throw new Exception("Error al actualizar artículo y stock en la base de datos.", e);
        } finally {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }

    private void ActualizarStock(Articulo articuloNew, Integer stockNew) throws Exception {
        ArticuloStock articuloStock = factoryDAO.getDAOArticuloStock().getPorId(articuloNew.getId());
        articuloStock.setStock(stockNew);
        factoryDAO.getDAOArticuloStock().actualizar(articuloStock);
    }

    public void actualizarArticuloConStockSP(Articulo articulo, int stockNuevo) throws Exception {
        String call = "{CALL actualizar_articulo_con_stock(?, ?, ?, ?, ?, ?)}";

        try (CallableStatement stmt = conexion.prepareCall(call)) {
            stmt.setInt(1, articulo.getId());
            stmt.setString(2, articulo.getCodigo());
            stmt.setString(3, articulo.getDescripcion());
            stmt.setBigDecimal(4, BigDecimal.valueOf(articulo.getPrecio()));
            stmt.setInt(5, articulo.getMinutosPreparacion());
            stmt.setInt(6, stockNuevo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar artículo con stock mediante procedimiento almacenado.", e);
        }
    }
}

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
        super.tabla = "articulostock";
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
    public String definirValues() { return  "( ?, ? )"; }

    @Override
    public String definirColumnas() { return "(articulo, stock)"; }

    @Override
    public void definirSetInsert(PreparedStatement stmt, ArticuloStock entidad) throws SQLException {
        stmt.setInt(1, entidad.getArticuloId());
        stmt.setInt(2, entidad.getStock());
    }
    @Override
    public Integer definirId(ArticuloStock articuloStock) {
        return articuloStock.getArticuloId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, ArticuloStock articuloStock) throws SQLException {
        stmt.setInt(1, articuloStock.getArticuloId());
        stmt.setInt(2, articuloStock.getStock());
    }

    @Override
    public ArticuloStock getPorId(Integer id) throws Exception {
        ArticuloStock entidad = null;
        String query = "SELECT * FROM " + tabla + " WHERE articulo = ?";
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                    entidad = objetoResulset(rs);
                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener entidad por Id de la base de datos.", e);
        }
        return entidad;
    }
    @Override
    public void insertar(ArticuloStock entidad) throws Exception
    {
        String query = "INSERT INTO " + tabla + " " + definirColumnas() + " VALUES " + definirValues();
        boolean autocommitOriginal = conexion.getAutoCommit();
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            definirSetInsert(stmt, entidad);
            int filas = stmt.executeUpdate();
            if (filas <= 0) throw new Exception("No se pudo insertar la entidad: \n" + entidad.toString());
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al insertar entidad en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }

    @Override
    public void actualizar(ArticuloStock entidad) throws Exception {
        String query = "UPDATE " + tabla + " SET " + definirSet() + " WHERE articulo = ?";
        boolean autocommitOriginal = conexion.getAutoCommit();
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            // Definir parámetros en función de entidad
            mapearUpdate(stmt, entidad);
            // Obtenemos la posición del último parámetro que define el where
            int ultimoParametro = obtenerUltimoParametro(stmt);
            // Obtiene el Id de la entidad
            Object entidadId = definirId(entidad);
            if (entidadId == null) throw new Exception("La entidad no tiene un ID definido. No se puede actualizar.");
            // Establecer el ID en el último parámetro
            stmt.setInt(ultimoParametro, (int)entidadId);

            // Ejecutar update y obtener filas modificadas
            int filas = stmt.executeUpdate();

            // Si el número de filas no es mayor que 0
            if (filas <= 0) {
                throw new Exception("No se ha encontrado la entidad con ID: " + entidadId);
            }
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al actualizar entidad en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        String query = "DELETE FROM " + tabla + " WHERE articulo = ?";
        boolean autocommitOriginal = conexion.getAutoCommit();
        try (PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas <= 0) {
                throw new Exception("No se ha encontrado la entidad con ID: " + id);
            }
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al eliminar entidad en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }
}

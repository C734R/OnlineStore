package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IBaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BaseDAOMySQL<T, K> implements IBaseDAO<T, K> {

    protected Connection conexion;
    protected String tabla;

    public BaseDAOMySQL(Connection conexion) throws SQLException {
        this.conexion = conexion;
    }

    @Override
    public T objetoResulset(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public String definirSet() {
        return null;
    }

    @Override
    public Integer definirId(T entidad) {
        return null;
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, T entidad) throws SQLException {}

    @Override
    public Map<Map<Boolean, String>, T> getPorId(K id) throws SQLException {
        Map<Map<Boolean, String>, T> resultado = new LinkedHashMap<>();

        String query = "SELECT * FROM " + tabla + " WHERE id = ?";
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt1 = conexion.prepareStatement(query);
            stmt1.setObject(1, id);
            ResultSet rs = stmt1.executeQuery();
            if (rs.next()) {
                T entidad = objetoResulset(rs);
                resultado.put(Map.of(true, "Entidad recuperada con éxito."), entidad);
            }
            else {
                resultado.put(Map.of(false, "No se encontró la entidad con ID: " + id), null);
            }
            rs.close();
            stmt1.close();
            conexion.commit();
        }
        catch (SQLException e)
        {
            conexion.rollback();
            resultado.put(Map.of(false, "Error al buscar la entidad: " + e.getMessage()), null);
        } finally
        {
            conexion.setAutoCommit(true);
        }
        return resultado;
    }



    @Override
    public Map<Map<Boolean, String>, List<T>> buscarTodos() throws SQLException {
        Map<Map<Boolean, String>, List<T>> resultado = new LinkedHashMap<>();
        List<T> entidades = new ArrayList<>();
        String query = "SELECT * FROM " + tabla;
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt1 = conexion.prepareStatement(query);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                T entidad = objetoResulset(rs);
                entidades.add(entidad);
            }
            rs.close();
            stmt1.close();
            conexion.commit();
            resultado.put(Map.of(true, "Entidades recuperadas con éxito."), entidades);
        }
        catch (SQLException e)
        {
            conexion.rollback();
            resultado = new HashMap<>();
            resultado.put(Map.of(false, "Error al recuperar las entidades: " + e.getMessage()), null);
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
        return resultado;
    }

    @Override
    public HashMap<Boolean, String> insertar(T entidad) throws SQLException {
        HashMap<Boolean, String> resultado = new LinkedHashMap<>();
        String query = "INSERT INTO " + tabla + " VALUES (?)";
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt1 = conexion.prepareStatement(query);
            stmt1.setObject(1, entidad);
            stmt1.executeUpdate();
            stmt1.close();
            conexion.commit();
            resultado.put(true,"Entidad insertada con éxito.");
        }
        catch (SQLException e)
        {
            conexion.rollback();
            resultado = new HashMap<>();
            resultado.put(false, "Error al insertar la entidad: " + e.getMessage());
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
        return resultado;
    }

    @Override
    public HashMap<Boolean, String> actualizar(T entidad) throws SQLException {
        HashMap<Boolean, String> resultado = new LinkedHashMap<>();
        String query = "UPDATE " + tabla + " SET " + definirSet() + " WHERE id = ?";
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt = conexion.prepareStatement(query);
            mapearUpdate(stmt, entidad);

            // Establecer el ID en el último parámetro
            Object id = definirId(entidad);
            stmt.setObject(stmt.getParameterMetaData().getParameterCount(), id);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                resultado.put(true, "Entidad actualizada con éxito.");
            } else {
                resultado.put(false, "No se ha encontrado la entidad con ID: " + id);
            }
            stmt.close();
            conexion.commit();
        }
        catch (SQLException e)
        {
            conexion.rollback();
            resultado = new HashMap<>();
            resultado.put(false, "Error al actualizar la entidad: " + e.getMessage());
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
        return resultado;
    }

    @Override
    public HashMap<Boolean, String> eliminar(K id) throws SQLException {
        HashMap<Boolean, String> resultado = new LinkedHashMap<>();
        String query = "DELETE FROM " + tabla + " WHERE id = ?";
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setObject(1, id);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                resultado.put(true, "Entidad eliminada con éxito.");
            }
            else resultado.put(false, "No se ha encontrado la entidad con ID: " + id);
            stmt.close();
            conexion.commit();
        }
        catch (SQLException e)
        {
            conexion.rollback();
            resultado = new HashMap<>();
            resultado.put(false, "Error al eliminar la entidad: " + e.getMessage());
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
        return resultado;
    }
}

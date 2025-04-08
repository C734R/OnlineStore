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
    public T getPorId(K id) throws Exception {
        T entidad = null;
        // Crear query
        String query = "SELECT * FROM " + tabla + " WHERE id = ?";
        try
        {
            // Detener autocommit
            conexion.setAutoCommit(false);
            // Crear prepared statement
            PreparedStatement stmt1 = conexion.prepareStatement(query);
            // Definir
            stmt1.setObject(1, id);
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

    @Override
    public List<T> getTodos() throws Exception {
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
        return entidades;
    }

    @Override
    public void insertar(T entidad) throws Exception {
        String query = "INSERT INTO " + tabla + " VALUES (?)";
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt1 = conexion.prepareStatement(query);
            stmt1.setObject(1, entidad);
            stmt1.executeUpdate();
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
    }

    @Override
    public void actualizar(T entidad) throws Exception {
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

            if (filas <= 0) {
                throw new Exception("No se ha encontrado la entidad con ID: " + id);
            }
            stmt.close();
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
    }

    @Override
    public void eliminar(K id) throws Exception {
        String query = "DELETE FROM " + tabla + " WHERE id = ?";
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setObject(1, id);
            int filas = stmt.executeUpdate();
            if (filas <= 0) {
                throw new Exception("No se ha encontrado la entidad con ID: " + id);
            }
            stmt.close();
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
    }

    @Override
    public void eliminarTodos() throws Exception {
        String query = "DELETE FROM " + tabla;
        try
        {
            conexion.setAutoCommit(false);
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.executeUpdate();
            stmt.close();
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
    }
}

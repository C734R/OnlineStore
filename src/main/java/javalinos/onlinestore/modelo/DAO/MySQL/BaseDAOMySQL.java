package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IBaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class BaseDAOMySQL<T, K> implements IBaseDAO<T, K> {

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
    public void definirSetInsert(PreparedStatement stmt, T entidad) throws SQLException {}

    @Override
    public String definirColumnas(){
        return null;
    }


    @Override
    public java.lang.Integer definirId(T entidad) {
        return null;
    }

    @Override
    public int obtenerUltimoParametro(PreparedStatement stmt) throws SQLException {
        try
        {
            return stmt.getParameterMetaData().getParameterCount();
        }
        catch (SQLException e){
            throw new SQLException("Error al obtener número del último parámetro del prepared statement", e);
        }
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, T entidad) throws SQLException {}

    @Override
    public T getPorId(K id) throws Exception {
        T entidad = null;
        String query = "SELECT * FROM " + tabla + " WHERE id = ?";
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
    public T getPorNombreUnico(String nombre) throws Exception {

        T entidad = null;
        String query = "SELECT * FROM " + tabla + " WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query))
        {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                int filas = 0;
                while (rs.next()) {
                    filas++;
                    if (filas > 1) throw new Exception("Existe más de un nombre igual en la tabla " + tabla + ".");
                    entidad = objetoResulset(rs);

                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener la entidad de la base de datos.", e);
        }
        return entidad;
    }

    @Override
    public List<T> getTodos() throws Exception {
        List<T> entidades = new ArrayList<>();
        String query = "SELECT * FROM " + tabla;
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T entidad = objetoResulset(rs);
                    entidades.add(entidad);
                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener las entidades de la base de datos.", e);
        }
        return entidades;
    }

    @Override
    public void insertar(T entidad) throws Exception
    {
        String query = "INSERT INTO " + tabla + " " + definirColumnas() + " VALUES " + definirValues();
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            definirSetInsert(stmt, entidad);
            int filas = stmt.executeUpdate();
            if (filas <= 0) throw new Exception("No se pudo insertar la entidad: \n" + entidad.toString());
            stmt.close();
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al insertar entidad en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
    }

    @Override
    public void insertarTodos(List<T> entidades) throws Exception
    {
        try
        {
            for (T entidad : entidades) {
                insertar(entidad);
            }
        }
        catch (Exception e)
        {
            throw new Exception("Error al insertar las entidades en la base de datos.", e);
        }

    }

    @Override
    public void actualizar(T entidad) throws Exception {
        String query = "UPDATE " + tabla + " SET " + definirSet() + " WHERE id = ?";
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
            conexion.setAutoCommit(true);
        }
    }

    @Override
    public void eliminar(K id) throws Exception {
        String query = "DELETE FROM " + tabla + " WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            stmt.setInt(1, (int)id);
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
            conexion.setAutoCommit(true);
        }
    }

    @Override
    public void eliminarTodos() throws Exception {
        String query = "DELETE FROM " + tabla;
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            stmt.executeUpdate();
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
        }
        finally
        {
            conexion.setAutoCommit(true);
        }
    }
}

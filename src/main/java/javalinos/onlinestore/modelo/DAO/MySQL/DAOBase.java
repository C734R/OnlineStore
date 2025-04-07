package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IDAOBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DAOBase<T, K> implements IDAOBase<T, K> {

    protected final Connection conexion;

    public DAOBase(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public T getPorId(K id) throws SQLException {
        return null;
    }

    @Override
    public List<T> buscarTodos() throws SQLException {
        return List.of();
    }

    @Override
    public HashMap<Boolean, String> insertar(T entidad) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Boolean, String> actualizar(T entidad) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Boolean, String> eliminar(K id) throws SQLException {
        return null;
    }
}

package javalinos.onlinestore.modelo.DAO.Interfaces;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IDAOBase<T, K> {

    T getPorId(K id) throws SQLException;

    List<T> buscarTodos() throws SQLException;

    HashMap<Boolean, String> insertar(T entidad) throws SQLException;

    HashMap<Boolean, String> actualizar(T entidad) throws SQLException;

    HashMap<Boolean, String> eliminar(K id) throws SQLException;
}
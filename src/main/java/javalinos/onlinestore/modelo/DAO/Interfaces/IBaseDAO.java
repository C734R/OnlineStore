package javalinos.onlinestore.modelo.DAO.Interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IBaseDAO<T, K> {

    T objetoResulset(ResultSet rs) throws SQLException;

    String definirSet();

    Integer definirId(T entidad);

    void mapearUpdate(PreparedStatement stmt, T entidad) throws SQLException;

    T getPorId(K id) throws Exception;

    List<T> getTodos() throws Exception;

    void insertar(T entidad) throws Exception;

    void actualizar(T entidad) throws Exception;

    void eliminar(K id) throws Exception;

    void eliminarTodos() throws Exception;
}
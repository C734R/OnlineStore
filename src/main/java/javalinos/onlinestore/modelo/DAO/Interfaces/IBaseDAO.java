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

    Map<Map<Boolean, String> ,T> getPorId(K id) throws SQLException;

    Map<Map<Boolean, String>, List<T>> buscarTodos() throws SQLException;

    HashMap<Boolean, String> insertar(T entidad) throws SQLException;

    HashMap<Boolean, String> actualizar(T entidad) throws SQLException;

    HashMap<Boolean, String> eliminar(K id) throws SQLException;

}
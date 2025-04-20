package javalinos.onlinestore.modelo.DAO.Interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IBaseDAO<T, K> {

    T objetoResulset(ResultSet rs) throws SQLException;
    String definirSet();
    void definirSetInsert(PreparedStatement stmt, T entidad) throws SQLException;
    String definirValues();
    String definirColumnas();
    Integer definirId(T entidad);
    int obtenerUltimoParametro(PreparedStatement stmt) throws SQLException;
    void mapearUpdate(PreparedStatement stmt, T entidad) throws SQLException;
    T getPorId(K id) throws Exception;
    T getPorNombreUnico(String nombre) throws Exception;
    List<T> getTodos() throws Exception;
    void insertar(T entidad) throws Exception;
    void insertarTodos(List<T> entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(K id) throws Exception;
    void eliminarTodos() throws Exception;
}
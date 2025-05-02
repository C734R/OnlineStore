package javalinos.onlinestore.utils.Conexiones;

import java.sql.Connection;

public interface IConexionBBDD extends AutoCloseable {
    Connection getConexion();
}
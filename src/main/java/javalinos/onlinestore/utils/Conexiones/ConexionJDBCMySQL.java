package javalinos.onlinestore.utils.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionJDBCMySQL implements IConexionBBDD {

    private final Connection conexion;

    public ConexionJDBCMySQL(String host, String usuario, String clave, String basedatos) throws SQLException {
        String url = "jdbc:mysql://" + host + "/" + basedatos;
        conexion = DriverManager.getConnection(url, usuario, clave);
    }

    public Connection getConexion() {
        return conexion;
    }

    @Override
    public void close() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}

package javalinos.onlinestore.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD {

    private Connection conexion;

    public ConexionBBDD(String host, String usuario, String contrasena, String basedatos) throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + basedatos, usuario,contrasena);
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrar() throws SQLException {
        conexion.close();
    }
}

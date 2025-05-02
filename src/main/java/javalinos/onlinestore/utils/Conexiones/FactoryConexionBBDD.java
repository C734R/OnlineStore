package javalinos.onlinestore.utils.Conexiones;

import javalinos.onlinestore.Configuracion;

public class FactoryConexionBBDD {

    public static IConexionBBDD crearConexion(
            Configuracion configuracion,
            String host, String usuario,
            String contrasena,
            String basedatos)
            throws Exception
    {
        return switch (configuracion) {
            case JDBC_MYSQL -> new ConexionJDBCMySQL(host, usuario, contrasena, basedatos);
            default -> throw new Exception("Tipo de conexión no soportado: " + configuracion);
        };
    }
}

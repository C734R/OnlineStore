package javalinos.onlinestore.utils.Conexiones;

public interface IConexionBBDD extends AutoCloseable {
    Object getConexion();
}
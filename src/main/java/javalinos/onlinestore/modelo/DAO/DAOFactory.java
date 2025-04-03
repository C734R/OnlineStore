package javalinos.onlinestore.modelo.DAO;

import javalinos.onlinestore.modelo.DAO.MySQL.DAOArticulo;
import javalinos.onlinestore.modelo.DAO.MySQL.DAOCliente;

import java.sql.Connection;

public class DAOFactory {

    protected final Connection conexion;

    public DAOFactory (Connection conexion) {
        this.conexion = conexion;
    }

    public DAOCliente getDAOCliente() {
        return new DAOCliente(conexion);

    }

    public DAOArticulo getDAOArticulo() {
        return new DAOArticulo(conexion);
    }
}

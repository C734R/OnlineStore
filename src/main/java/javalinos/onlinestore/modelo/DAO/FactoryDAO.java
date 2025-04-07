package javalinos.onlinestore.modelo.DAO;

import javalinos.onlinestore.modelo.DAO.MySQL.ArticuloDAOMySQLMySQL;
import javalinos.onlinestore.modelo.DAO.MySQL.ClienteDAOMySQL;
import javalinos.onlinestore.modelo.DAO.MySQL.PedidoDAOMySQL;

import java.sql.Connection;
import java.sql.SQLException;

public class FactoryDAO {

    protected final Connection conexion;
    protected final int configuracion;

    public FactoryDAO(Connection conexion, int configuracion) {
        this.conexion = conexion;
        this.configuracion = configuracion;
    }

    public ClienteDAOMySQL getDAOCliente() throws SQLException {
        switch (configuracion) {
            case 1:
                return new ClienteDAOMySQL(conexion);
            case 2:
                return null;
            default:
                return null;
        }
    }

    public ArticuloDAOMySQLMySQL getDAOArticulo() throws SQLException {
        switch (configuracion) {
            case 1:
                return new ArticuloDAOMySQLMySQL(conexion);
            case 2:
                return null;
            default:
                return null;
        }
    }

    public PedidoDAOMySQL getDAOPedido() throws SQLException {
        switch (configuracion) {
            case 1:
                return new PedidoDAOMySQL(conexion);
            case 2:
                return null;
            default:
                return null;
        }
    }

}

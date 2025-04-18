package javalinos.onlinestore.modelo.DAO;

import javalinos.onlinestore.modelo.DAO.Interfaces.*;
import javalinos.onlinestore.modelo.DAO.MySQL.*;


import java.sql.Connection;
import java.sql.SQLException;

public class FactoryDAO {

    protected final Connection conexion;
    protected final int configuracion;
    public final FactoryDAO factoryDAO;

    public FactoryDAO(int configuracion, Connection conexion) {
        this.conexion = conexion;
        this.configuracion = configuracion;
        this.factoryDAO = this;
    }

    public IClienteDAO getDAOCliente() throws SQLException {
        return switch (configuracion) {
            case 1 -> new ClienteDAOMySQL(conexion);
            case 2 -> null;
            default -> null;
        };
    }

    public IArticuloDAO getDAOArticulo() throws SQLException {
        return switch (configuracion) {
            case 1 -> new ArticuloDAOMySQL(conexion);
            case 2 -> null;
            default -> null;
        };
    }

    public IPedidoDAO getDAOPedido() throws SQLException {
        return switch (configuracion) {
            case 1 -> new PedidoDAOMySQL(conexion, factoryDAO);
            case 2 -> null;
            default -> null;
        };
    }

    public ICategoriaDAO getDAOCategoria() throws SQLException {
        return switch (configuracion) {
            case 1 -> new CategoriaDAOMySQL(conexion);
            case 2 -> null;
            default -> null;
        };
    }

    public IArticuloStockDAO getDAOArticuloStock() throws SQLException {
        return switch (configuracion) {
            case 1 -> new ArticuloStockDAOMySQL(conexion);
            case 2 -> null;
            default -> null;
        };
    }

}

package javalinos.onlinestore.modelo.DAO;

import javalinos.onlinestore.Configuracion;
import javalinos.onlinestore.modelo.DAO.Interfaces.*;
import javalinos.onlinestore.modelo.DAO.MySQL.*;
import javalinos.onlinestore.modelo.DAO.ORM.*;


import java.sql.Connection;
import java.sql.SQLException;

public class FactoryDAO {

    protected final Connection conexion;
    protected final Configuracion configuracion;
    public final FactoryDAO factoryDAO;

    public FactoryDAO(Configuracion configuracion, Connection conexion) {
        this.conexion = conexion;
        this.configuracion = configuracion;
        this.factoryDAO = this;
    }

    public IClienteDAO getDAOCliente() throws SQLException {
        return switch (configuracion) {
            case JDBC_MYSQL -> new ClienteDAOMySQL(conexion);
            case JPA_HIBERNATE_MYSQL -> new ClienteDAOHibernate();
            default -> null;
        };
    }

    public IArticuloDAO getDAOArticulo() throws SQLException {
        return switch (configuracion) {
            case JDBC_MYSQL -> new ArticuloDAOMySQL(conexion, factoryDAO);
            case JPA_HIBERNATE_MYSQL -> new ArticuloDAOHibernate();
            default -> null;
        };
    }

    public IPedidoDAO getDAOPedido() throws SQLException {
        return switch (configuracion) {
            case JDBC_MYSQL -> new PedidoDAOMySQL(conexion, factoryDAO);
            case JPA_HIBERNATE_MYSQL -> new PedidoDAOHibernate();
            default -> null;
        };
    }

    public ICategoriaDAO getDAOCategoria() throws SQLException {
        return switch (configuracion) {
            case JDBC_MYSQL -> new CategoriaDAOMySQL(conexion);
            case JPA_HIBERNATE_MYSQL -> new CategoriaDAOHibernate();
            default -> null;
        };
    }

    public IArticuloStockDAO getDAOArticuloStock() throws SQLException {
        return switch (configuracion) {
            case JDBC_MYSQL -> new ArticuloStockDAOMySQL(conexion);
            case JPA_HIBERNATE_MYSQL -> new ArticuloStockDAOHibernate();
            default -> null;
        };
    }

}

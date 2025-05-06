package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.Configuracion;
import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.gestores.BBDD.*;
import javalinos.onlinestore.modelo.gestores.Interfaces.*;
import javalinos.onlinestore.modelo.gestores.Local.*;
import javalinos.onlinestore.utils.Conexiones.FactoryConexionBBDD;
import javalinos.onlinestore.utils.Conexiones.IConexionBBDD;

import static javalinos.onlinestore.OnlineStore.configuracion;

public class FactoryModelo implements AutoCloseable {

    private final FactoryDAO factoryDAO;
    private final IConexionBBDD conexion;

    public FactoryModelo() throws Exception {
        if (configuracion == Configuracion.JDBC_MYSQL) {
            this.conexion = FactoryConexionBBDD.crearConexion(
                    configuracion,
                    "localhost:3306",
                    "root",
                    "1234", "OnlineStore");
            this.factoryDAO = new FactoryDAO(conexion.getConexion());
        }
        else if (configuracion == Configuracion.JPA_HIBERNATE_MYSQL) {
            this.conexion = null;
            this.factoryDAO = new FactoryDAO(null);
        }
        else {
            this.conexion = null;
            this.factoryDAO = null;
        }

    }

    public IModeloClientes getModeloClientes() {
        if (configuracion == Configuracion.LOCAL) {
            return new ModeloClientesLocal();
        }
        else {
            return new ModeloClientesBBDD(factoryDAO);
        }

    }

    public IModeloArticulos getModeloArticulos() {
        if (configuracion == Configuracion.LOCAL) {
            return new ModeloArticulosLocal();
        }
        else {
            return new ModeloArticulosBBDD(factoryDAO);
        }
    }

    public IModeloPedidos getModeloPedidos() {
        if (configuracion == Configuracion.LOCAL) {
            return new ModeloPedidosLocal();
        }
        else {
            return new ModeloPedidosBBDD(factoryDAO, getModeloArticulos(), getModeloClientes());
        }
    }

    public void close() throws Exception {
        if (conexion != null) {
            conexion.close();
        }
    }
}

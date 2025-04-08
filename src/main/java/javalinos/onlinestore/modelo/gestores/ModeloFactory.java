package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.DAO.ConexionBBDD;
import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.gestores.BBDD.*;
import javalinos.onlinestore.modelo.gestores.Interfaces.*;
import javalinos.onlinestore.modelo.gestores.Local.*;

import java.sql.SQLException;

public class ModeloFactory {

    private final int configuracion;
    private final FactoryDAO factoryDAO;
    private final ConexionBBDD conexion;

    public ModeloFactory(int configuracion) throws SQLException {
        this.configuracion = configuracion;
        if (configuracion == 1) {
            this.conexion = new ConexionBBDD("localhost:3306","root","1234","OnlineStore");
            this.factoryDAO = new FactoryDAO(configuracion, conexion.getConexion());
        }
        else if (configuracion == 2) {
            this.conexion = null;
            this.factoryDAO = null;
        }
        else {
            this.conexion = null;
            this.factoryDAO = null;
        }

    }

    public IModeloClientes getModeloClientes() {
        if (configuracion == 0) {
            return new ModeloClientesLocal();
        }
        else {
            return new ModeloClientesBBDD(factoryDAO);
        }
    }

    public IModeloPedidos getModeloPedidos() {
        if (configuracion == 0) {
            return new ModeloPedidosLocal();
        }
        else {
            return new ModeloPedidosBBDD(factoryDAO);
        }
    }

    public IModeloArticulos getModeloArticulos() {
        if (configuracion == 0) {
            return new ModeloArticulosLocal();
        }
        else {
            return new ModeloArticulosBBDD(factoryDAO);
        }
    }
}

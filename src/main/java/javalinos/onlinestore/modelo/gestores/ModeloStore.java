package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.gestores.Interfaces.*;
import javalinos.onlinestore.modelo.gestores.Local.*;


/**
 * Clase contenedora del modelo general de la tienda.
 * Agrupa los modelos de clientes, artículos y pedidos.
 */
public class ModeloStore {

    private IModeloClientes mClientes;
    private IModeloArticulos mArticulos;
    private IModeloPedidos mPedidos;
    /**
     * Constructor principal.
     * @param mClientes modelo de gestión de clientes.
     * @param mArticulos modelo de gestión de artículos.
     * @param mPedidos modelo de gestión de pedidos.
     */
    public ModeloStore(IModeloClientes mClientes, IModeloArticulos mArticulos, IModeloPedidos mPedidos) {

        this.mClientes = mClientes;
        this.mArticulos = mArticulos;
        this.mPedidos = mPedidos;
    }
    /**
     * Constructor vacío. Inicializa sin modelos cargados.
     */
    public ModeloStore() {
        this.mClientes = null;
        this.mArticulos = null;
        this.mPedidos = null;
    }
    /**
     * Devuelve el modelo de clientes.
     * @return instancia de ModeloClientes.
     */
    public IModeloClientes getModeloClientes() {
        return mClientes;
    }
    /**
     * Establece el modelo de clientes.
     * @param mClientes modelo de clientes.
     */
    public void setModeloClientes(IModeloClientes mClientes) {
        this.mClientes = mClientes;
    }
    /**
     * Devuelve el modelo de pedidos.
     * @return instancia de ModeloPedidos.
     */
    public IModeloPedidos getModeloPedidos() {
        return mPedidos;
    }
    /**
     * Establece el modelo de pedidos.
     * @param mPedidos modelo de pedidos.
     */
    public void setModeloPedidos(IModeloPedidos mPedidos) {
        this.mPedidos = mPedidos;
    }
    /**
     * Establece el modelo de artículos.
     * @param mArticulos modelo de artículos.
     */
    public void setModeloArticulos(IModeloArticulos mArticulos) {
        this.mArticulos = mArticulos;
    }
    /**
     * Devuelve el modelo de artículos.
     * @return instancia de ModeloArticulos.
     */
    public IModeloArticulos getModeloArticulos() {
        return mArticulos;
    }

}

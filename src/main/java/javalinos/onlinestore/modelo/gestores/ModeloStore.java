package javalinos.onlinestore.modelo.gestores;

/**
 * Clase contenedora del modelo general de la tienda.
 * Agrupa los modelos de clientes, artículos y pedidos.
 */
public class ModeloStore {

    private ModeloClientes mClientes;
    private ModeloPedidos mPedidos;
    private ModeloArticulos mArticulos;
    /**
     * Constructor principal.
     * @param mClientes modelo de gestión de clientes.
     * @param mArticulos modelo de gestión de artículos.
     * @param mPedidos modelo de gestión de pedidos.
     */
    public ModeloStore(ModeloClientes mClientes, ModeloArticulos mArticulos, ModeloPedidos mPedidos) {

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
    public ModeloClientes getModeloClientes() {
        return mClientes;
    }
    /**
     * Establece el modelo de clientes.
     * @param mClientes modelo de clientes.
     */
    public void setModeloClientes(ModeloClientes mClientes) {
        this.mClientes = mClientes;
    }
    /**
     * Devuelve el modelo de pedidos.
     * @return instancia de ModeloPedidos.
     */
    public ModeloPedidos getModeloPedidos() {
        return mPedidos;
    }
    /**
     * Establece el modelo de pedidos.
     * @param mPedidos modelo de pedidos.
     */
    public void setModeloPedidos(ModeloPedidos mPedidos) {
        this.mPedidos = mPedidos;
    }
    /**
     * Establece el modelo de artículos.
     * @param mArticulos modelo de artículos.
     */
    public void setModeloArticulos(ModeloArticulos mArticulos) {
        this.mArticulos = mArticulos;
    }
    /**
     * Devuelve el modelo de artículos.
     * @return instancia de ModeloArticulos.
     */
    public ModeloArticulos getModeloArticulos() {
        return mArticulos;
    }

}

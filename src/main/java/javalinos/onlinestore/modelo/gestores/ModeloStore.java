package javalinos.onlinestore.modelo.gestores;

public class ModeloStore {

    private ModeloClientes mClientes;
    private ModeloPedidos mPedidos;
    private ModeloArticulos mArticulos;

    public ModeloStore(ModeloClientes mClientes, ModeloArticulos mArticulos, ModeloPedidos mPedidos) {

        this.mClientes = mClientes;
        this.mArticulos = mArticulos;
        this.mPedidos = mPedidos;
    }

    public ModeloStore() {
        this.mClientes = null;
        this.mArticulos = null;
        this.mPedidos = null;
    }

    public ModeloClientes getModeloClientes() {
        return mClientes;
    }

    public void setModeloClientes(ModeloClientes mClientes) {
        this.mClientes = mClientes;
    }

    public ModeloPedidos getModeloPedidos() {
        return mPedidos;
    }

    public void setModeloPedidos(ModeloPedidos mPedidos) {
        this.mPedidos = mPedidos;
    }

    public void setModeloArticulos(ModeloArticulos mArticulos) {
        this.mArticulos = mArticulos;
    }

    public ModeloArticulos getModeloArticulos() {
        return ModeloArticulos.getInstancia();
    }



}

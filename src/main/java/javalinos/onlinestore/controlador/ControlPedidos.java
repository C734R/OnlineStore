package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Pedido;
import javalinos.onlinestore.vista.VistaPedidos;

public class ControlPedidos extends ControlBase{

    private VistaPedidos vPedidos;
    private ModeloArticulos mArticulos;
    private ModeloClientes mClientes;

    public ControlPedidos(ModeloStore mStore, VistaPedidos vPedidos) {
        super.setmStore(mStore);
        this.mArticulos = mStore.getModeloArticulos();
        this.mClientes = mStore.getModeloClientes();
        this.vPedidos = vPedidos;
    }

    public ControlPedidos() {
        super();
        vPedidos = null;
    }

    public VistaPedidos getVistaPedidos() {
        return vPedidos;
    }

    public void setVistaPedidos(VistaPedidos vPedidos) {
        this.vPedidos = vPedidos;
    }

    public void iniciar() {
        int opcion;
        while(true) {
            vPedidos.showCabecera();
            vPedidos.showMenu(2);
            opcion = vPedidos.askInt("Introduce una opción", 0, 4,false, false);
            switch (opcion) {
                case 1:
                    addPedidos();
                    break;
                case 2:
                    removePedidos();
                    break;
                case 3:
                    listPedidos(vPedidos.askBoolean("¿Deseas filtrar por usuario?", true));
                    break;
                case 4:
                    listPedidosPendientes(vPedidos.askBoolean("¿Deseas filtrar por usuario?", true));
                    break;
                case 5:
                    listPedidosEnviados(vPedidos.askBoolean("¿Deseas filtrar por usuario?", true));
                case 0:
                    vPedidos.showMensaje("Volviendo al menú principal...", true);
                    return;
                default:
                    vPedidos.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.", true);
            }
        }
    }

    public void addPedidos() {}

    public void removePedidos() {
        vPedidos.showMensaje("Vuelva a intentarlo.", true);
    }

    public void listPedidos(boolean fCliente) {
        if(fCliente) {
            vPedidos.showListClientes(this.getModeloStore().getModeloClientes().getClientes());
        }
    }

    public void listPedidosPendientes(boolean fCliente) {}

    public void listPedidosEnviados(boolean fCliente) {}

    private void calcPedido() {}

    private boolean checkPreparacion(Pedido pedido) {
        return false;
    }

    public boolean loadPedidos(int configuracion) {
        if (configuracion == 0) {
            return this.getModeloStore().getModeloPedidos().loadPedidos(configuracion, mClientes.getClientes(), mArticulos.getArticulos());
        }
        else {
            return false;
        }
    }
}

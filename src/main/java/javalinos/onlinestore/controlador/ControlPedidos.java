package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Pedido;
import javalinos.onlinestore.vista.VistaPedidos;

public class ControlPedidos extends ControlBase{

    private VistaPedidos vPedidos;

    public ControlPedidos(ModeloStore mStore, VistaPedidos vPedidos) {
        super(mStore);
        this.vPedidos = vPedidos;
    }
    public ControlPedidos() {
        vPedidos = null;
    }

    public VistaPedidos getvPedidos() {
        return vPedidos;
    }

    public void setvPedidos(VistaPedidos vPedidos) {
        this.vPedidos = vPedidos;
    }

    public void iniciar() {}

    public void addPedidos() {}

    public void removePedidos() {}

    public void listPedidos(boolean fCliente) {}

    public void listPedidosPendientes(boolean fCliente) {}

    public void listPedidosEnviados(boolean fCliente) {}

    private void calcPedido() {}

    private boolean checkPreparacion(Pedido pedido) {
        return false;
    }

    public boolean loadPedidos() {
        return false;
    }
}

package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;
import javalinos.onlinestore.vista.VistaPedidos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javalinos.onlinestore.utils.Utilidades.listToStr;

public class ControlPedidos extends ControlBase{

    private VistaPedidos vPedidos;
    private ModeloArticulos mArticulos;
    private ModeloClientes mClientes;
    private ModeloPedidos mPedidos;

    public ControlPedidos(ModeloStore mStore, VistaPedidos vPedidos) {
        super.setModeloStore(mStore);
        this.mArticulos = mStore.getModeloArticulos();
        this.mClientes = mStore.getModeloClientes();
        this.mPedidos = mStore.getModeloPedidos();
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
                    listPedidos(vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true));
                    break;
                case 4:
                    listPedidosPendientes(vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true));
                    break;
                case 5:
                    listPedidosEnviados(vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true));
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
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos = null;
        if(fCliente) {
            if (!mClientes.getClientes().isEmpty()) {
                vPedidos.showOptions(listToStr(this.mClientes.getClientes()),3 , true, true);
                int indexCliente = -1 + vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 1, mClientes.sizeClientes(), true, true);
                if (indexCliente < 0) return;
                Cliente cliente = mClientes.getClientes().get(indexCliente);
                pedidos = mPedidos.getPedidosCliente(cliente);
                if (pedidos.isEmpty()) {
                    vPedidos.showMensaje("No hay pedidos registrados para este cliente.", true);
                }
            }
            else vPedidos.showMensajePausa("Error. No existen clientes registrados.", true);
        }
        else {
            pedidos = mPedidos.getPedidos();
        }
        vPedidos.showListPedidos(pedidos,fCliente);
        vPedidos.showMensajePausa("", true);
    }

    public void listPedidosPendientes(boolean fCliente) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos = null;
        if(fCliente) {
            if (!mClientes.getClientes().isEmpty()) {
                vPedidos.showOptions(listToStr(this.mClientes.getClientes()),3 , true, true);
                int indexCliente = -1 + vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 1, mClientes.sizeClientes(), true, true);
                if (indexCliente < 0) return;
                Cliente cliente = mClientes.getClientes().get(indexCliente);
                pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), false, cliente);
                if (pedidos.isEmpty()) {
                    vPedidos.showMensaje("No hay pedidos pendientes registrados para este cliente.", true);
                }
            }
            else vPedidos.showMensajePausa("Error. No existen clientes registrados.", true);
        }
        else {
            pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), false, null);
        }
        vPedidos.showListPedidos(pedidos,fCliente);
        vPedidos.showMensajePausa("", true);
    }

    public void listPedidosEnviados(boolean fCliente) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos = null;
        if(fCliente) {
            if (!mClientes.getClientes().isEmpty()) {
                vPedidos.showOptions(listToStr(this.mClientes.getClientes()), 3, true, true);
                int indexCliente = -1 + vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 1, mClientes.sizeClientes(), true, true);
                if (indexCliente < 0) return;
                Cliente cliente = mClientes.getClientes().get(indexCliente);
                pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), true, cliente);
                if (pedidos.isEmpty()) {
                    vPedidos.showMensaje("No hay pedidos enviados registrados para este cliente.", true);
                }
            }
            else vPedidos.showMensajePausa("Error. No existen clientes registrados.", true);
        }
        else {
            pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), true, null);
        }
        vPedidos.showListPedidos(pedidos,fCliente);
        vPedidos.showMensajePausa("", true);
    }

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

package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Articulo;
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
            opcion = vPedidos.askInt("Introduce una opción", 0, 5,false, false);
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
                    break;
                case 0:
                    vPedidos.showMensaje("Volviendo al menú principal...", true);
                    return;
                default:
                    vPedidos.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.", true);
            }
        }
    }

    public void addPedidos() {
        List<Articulo> articulosDisponibles = new ArrayList<>();


        vPedidos.showOptions(listToStr(this.mClientes.getClientes()),3 , true, true);
        int indexCliente = vPedidos.askInt("Selecciona el cliente que quiere hacer el pedido", 0, mClientes.sizeClientes(), true, true);
        if (indexCliente == 0) return;
        indexCliente = indexCliente - 1;
        Cliente cliente = mClientes.getClientes().get(indexCliente);

        for (Articulo articulo : mArticulos.getArticulos()){
            if (articulo.getStock() > 0){
                articulosDisponibles.add(articulo);
            }
        }

        if (articulosDisponibles.size() > 0) {
            vPedidos.showOptions(listToStr(articulosDisponibles), 3, true, true);
            int indexArticulo = vPedidos.askInt("Selecciona el articulo que quiere comprar entre los disponibles", 0, articulosDisponibles.size(), true, true);
            if (indexArticulo == 0) return;
            indexArticulo = indexArticulo - 1;
            Articulo articulo = mArticulos.getArticulos().get(indexArticulo);

            Integer stockComprado = vPedidos.askInt("Ingresa la cantidad que quiere comprar", 1, articulo.getStock(), true, true);

            LocalDate fechaPedido = vPedidos.askFecha("del pedido");

            float precioEnvio = 5f;

            float precioFinal = articulo.getPrecio() * stockComprado + precioEnvio;

            Pedido pedido = mPedidos.makePedido(cliente, articulo, stockComprado, fechaPedido, precioEnvio, precioFinal);

            if (mPedidos.makePedido(cliente, articulo, stockComprado, fechaPedido, precioEnvio, precioFinal) != null) {
                vPedidos.showMensaje("Pedido añadido correctamente", true);
                mPedidos.addPedido(pedido);
            } else {
                vPedidos.showMensaje("Error al añadir el pedido.. Saliendo", true);
            }
        }
        else{
            vPedidos.showMensaje("No hay articulos disponibles para comprar en este momento", true);
        }
    }

    public void removePedidos() {
        List<Pedido> pedidos = mPedidos.getPedidos();

        // Comprobamos que existen pedidos en la lista.
        if (pedidos.isEmpty()) {
            vPedidos.showMensaje("No hay pedidos disponibles para borrar.", true);
            return;
        }

        // Obtenemos el último número de pedido iterando sobre la lista
        int ultimoNumeroPedido = 0;
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() > ultimoNumeroPedido) {
                ultimoNumeroPedido = pedido.getNumero();
            }
        }

        // Pedimos al usuario que introduzca el numero del pedido que desea borrar.
        Integer numPedidoBorrar = vPedidos.askInt("Ingresa el numero de pedido que quieres borrar: ", 1, ultimoNumeroPedido, true, true);

        // Creamos un booleano por si no se encuentra el numero de pedido escrito por el usuario.
        boolean pedidoEncontrado = false;

        // Creamos una lista donde se guardará el pedido a eliminar
        Pedido pedidoAEliminar = null;

        // Iteramos en la lista de todos los pedidos buscando el pedido con el mismo numero que el introducido.
        for (Pedido pedido : pedidos){
            if (pedido.getNumero().equals(numPedidoBorrar)){
                pedidoAEliminar = pedido;
                pedidoEncontrado = true;
            }
        }

        // Si no se ha encontrado el numero de pedido se enseña este mensaje.
        if (!pedidoEncontrado){
            vPedidos.showMensaje("Este numero de pedido no coincide con ningun pedido del sistema", true);
            vPedidos.showMensaje("Vuelva a intentarlo.", true);
        }
        else{
            getModeloStore().getModeloPedidos().removePedido(pedidoAEliminar);
            vPedidos.showMensaje("El pedido ha sido eliminado correctamente.", true);
        }
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
                int indexCliente = vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 0, mClientes.sizeClientes(), true, true);
                if (indexCliente == 0) return;
                indexCliente = indexCliente - 1;
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
                int indexCliente = vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 0, mClientes.sizeClientes(), true, true);
                if (indexCliente == 0) return;
                indexCliente = indexCliente - 1;
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
                int indexCliente = vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 0, mClientes.sizeClientes(), true, true);
                if (indexCliente == 0) return;
                indexCliente = indexCliente - 1;
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

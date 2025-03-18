package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Pedido;
import javalinos.onlinestore.vista.VistaPedidos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void removePedidos() {
        // Pedimos al usuario que introduzca el numero del pedido que desea borrar.
        String numPedidoBorrar = vPedidos.askString("Ingresa el numero de pedido que quieres borrar: ", 10);

        // Creamos un booleano por si no se encuentra el numero de pedido escrito por el usuario.
        boolean pedidoEncontrado = false;

        // Creamos una lista con todos los pedidos sobre la que vamos a iterar.
        List<Pedido> todosLosPedidos = getmStore().getModeloPedidos().getPedidos();

        // Iteramos en la lista de todos los pedidos buscando el pedido con el mismo numero que el introducido.
        for (Pedido pedido : todosLosPedidos){
            if (pedido.getNumero().equals(numPedidoBorrar)){
                getmStore().getModeloPedidos().removePedido(pedido);
                pedidoEncontrado = true;
            }
        }

        // Si no se ha encontrado el numero de pedido se enseña este mensaje.
        if (!pedidoEncontrado){
            vPedidos.showMensaje("Este numero de pedido no coincide con ningun pedido del sistema", true);
        }
    }

    public void listPedidos(boolean fCliente) {
        List<Pedido> listaPedidos;

        // Si no se quiere filtrar por cliente se devuelven todos los pedidos.
        if (!fCliente){
            listaPedidos = getmStore().getModeloPedidos().getPedidos();
        }
        else{
            // Obtener una lista con todos los pedidos
            List<Pedido> todosLosPedidos = getmStore().getModeloPedidos().getPedidos();

            // Obtener el correo del cliente por el que se quiere filtrar.
            String correoCliente = vPedidos.askString("Introduce el correo del cliente que quieres ver los pedidos: ", 100);

            // Falta añadir comprobación para saber si existe algún cliente con ese correo.

            // La lista de pedidos será la nueva lista donde guardaremos los pedidos del cliente con ese correo.
            listaPedidos = new ArrayList<>();
            // Iterar en la lista de todos los pedidos buscando el correo del cliente deseado.
            for (Pedido pedido : todosLosPedidos){
                if (pedido.getCliente().getEmail().equalsIgnoreCase(correoCliente)){
                    listaPedidos.add(pedido);
                }
            }

            // Mostramos mensaje de lista vacía si el cliente no tiene pedidos.
            if (listaPedidos.isEmpty()){
                vPedidos.showMensaje("Este cliente no tiene pedidos.", true);
            }

        }
    }

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

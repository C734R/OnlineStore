package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModeloPedidos {

    private List<String> opciones;
    private List<Pedido> pedidos;

    public ModeloPedidos(List<String> opciones, List<Pedido> pedidos) {
        this.opciones = opciones;
        this.pedidos = pedidos;
    }

    public ModeloPedidos() {
        this.opciones = null;
        this.pedidos = null;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public Pedido makePedido(Cliente cliente, Articulo articulo, Integer cantidad, LocalDate fechaHora, Float envio) {
        return new Pedido();
    }

    public List<Pedido> getPedidosCliente(Cliente cliente) {
        return new ArrayList<>();
    }

    public List<Pedido> getPedidosPendientes() {
        return new ArrayList<>();
    }

    public List<Pedido> getPedidosEnviados() {
        return new ArrayList<>();
    }

    public void loadPedidos() {

    }

}

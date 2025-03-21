package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModeloPedidos {

    private List<Pedido> pedidos;

    public ModeloPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public ModeloPedidos() {
        this.pedidos = new ArrayList<Pedido>();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido makePedido(Cliente cliente, Articulo articulo, Integer cantidad, LocalDate fechahora, Float envio, Float precio) {
        int numPedido;
        if (pedidos.isEmpty()) numPedido = 1;
        else numPedido = pedidos.getLast().getNumero() + 1;
        return new Pedido(numPedido, cliente, articulo, cantidad, fechahora, envio, precio);
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public Pedido getPedidoNumero(int numero) {
        return pedidos.get(numero);
    }

    public List<Pedido> getPedidosCliente(Cliente cliente) {

        if (pedidos.isEmpty()) return null;
        if (cliente != null) {
            List<Pedido> pedidosCliente = new ArrayList<Pedido>();
            for (Pedido pedido : pedidos) {
                if (pedido.getCliente().equals(cliente)) {
                    pedidosCliente.add(pedido);
                }
            }
            return pedidosCliente;
        }
        else return pedidos;
    }

    public List<Pedido> getPedidosPendientesEnviados(LocalDate hoy, Boolean enviado, Cliente cliente) {
        List<Pedido> listaPedidos = new ArrayList<Pedido>();
        List<Pedido> listaPedidosSolicitados = new ArrayList<Pedido>();

        if (pedidos.isEmpty()) return null;
        if (cliente != null) {
            List<Pedido> pedidosCliente = new ArrayList<Pedido>();
            for (Pedido pedido : pedidos) {
                if (pedido.getCliente().equals(cliente)) {
                    pedidosCliente.add(pedido);
                }
            }
            listaPedidos = pedidosCliente;
        }
        else listaPedidos = pedidos;

        for (Pedido pedido : listaPedidos) {
            LocalDate fechaPedido = pedido.getFechahora();
            Float preparacionFloat = pedido.getArticulo().getPreparacion();
            int diasPreparacion = (int) Math.ceil(preparacionFloat * pedido.getCantidad());
            if (!enviado & hoy.isBefore(fechaPedido.plusDays(diasPreparacion))) {
                listaPedidosSolicitados.add(pedido);
            }
            else if (enviado & hoy.isAfter(pedido.getFechahora().plusDays(diasPreparacion))) {
                listaPedidosSolicitados.add(pedido);
            }
        }

        listaPedidos.clear();

        return listaPedidosSolicitados;
    }

    public boolean loadPedidos(int condiguracion, List<Cliente> clientes, List<Articulo> articulos) {
        try {
            this.pedidos.clear();

            this.pedidos.add(makePedido(clientes.get(0), articulos.get(0), 2, LocalDate.now(), 5.99f, 19.99f));
            this.pedidos.add(makePedido(clientes.get(4), articulos.get(1), 1, LocalDate.now().minusDays(1), 3.50f, 45.50f));
            this.pedidos.add(makePedido(clientes.get(7), articulos.get(2), 5, LocalDate.now().minusDays(3), 7.99f, 12.00f));
            this.pedidos.add(makePedido(clientes.get(1), articulos.get(3), 3, LocalDate.now().minusWeeks(1), 4.99f, 29.99f));
            this.pedidos.add(makePedido(clientes.get(5), articulos.get(4), 4, LocalDate.now().minusMonths(1), 6.50f, 59.99f));
            this.pedidos.add(makePedido(clientes.get(8), articulos.get(5), 2, LocalDate.now().minusDays(2), 5.00f, 99.99f));
            this.pedidos.add(makePedido(clientes.get(2), articulos.get(6), 1, LocalDate.now().minusWeeks(2), 3.99f, 149.99f));
            this.pedidos.add(makePedido(clientes.get(6), articulos.get(7), 6, LocalDate.now().minusMonths(2), 8.99f, 24.99f));
            this.pedidos.add(makePedido(clientes.get(3), articulos.get(8), 3, LocalDate.now().minusDays(5), 7.00f, 75.00f));
            return true;
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}

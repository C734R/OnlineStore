package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModeloPedidos {

    private List<Pedido> pedidos;

    public ModeloPedidos() {
        this.pedidos = new ArrayList<Pedido>();
    }

    public ModeloPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve todos los pedidos
     * @return List<Pedido> - Lista con todos los pedidos
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Establece la lista de pedidos.
     * @param pedidos Lista de pedidos.
     */
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    //*************************** CRUD ***************************//

    /**
     * Añadir pedido a la lista de pedidos
     * @param pedido El pedido que se quiere añadir
     */
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    /**
     * Eliminar pedido
     * @param pedido El pedido que se quiere eliminar
     */
    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
    }


    public Pedido getPedido(int id) {
        return pedidos.get(id);
    }

    /**
     * Actualizar pedido
     * @param pedidoOld se le pasa el pedido old
     * @param pedidoNew se le pasa el pedido new
     */
    public void updatePedido(Pedido pedidoOld, Pedido pedidoNew) {
        int index = pedidos.indexOf(pedidoOld);
        if (index != -1) {
            pedidos.set(index, pedidoNew);
        }

    }

    //*************************** Obtener datos ***************************//

    /**
     * Obtener un pedido por número de pedido
     * @param numero El número de pedido del pedido que se quiere obtener
     * @return Pedido deseado
     */
    public Pedido getPedidoNumero(int numero) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() == numero) {
                return pedido;
            }
        }
        return null;
    }

    /**
     * Obtener pedidos de un cliente
     * @param cliente El cliente del que se quieren ver los pedidos
     * @return Lista con los pedidos del cliente
     */
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
        return null;
    }

    /**
     * Lista con pedidos enviados o pendientes de envío.
     * @param enviado true para pedidos enviados, false para pendientes
     * @param cliente Cliente que realiza los pedidos
     * @return Lista de pedidos filtrados por estado y cliente
     */
    public List<Pedido> getPedidosPendientesEnviados(Boolean enviado, Cliente cliente) {
        List<Pedido> listaFiltrada = new ArrayList<>();

        if (cliente != null) {
            for (Pedido pedido : pedidos) {
                if (pedido.getCliente().equals(cliente)) {
                    listaFiltrada.add(pedido);
                }
            }
        } else {
            listaFiltrada = pedidos;
        }

        List<Pedido> resultado = new ArrayList<>();

        for (Pedido pedido : listaFiltrada) {
            boolean estaEnviado = checkEnviado(pedido);

            if (enviado && estaEnviado) {
                resultado.add(pedido);
            } else if (!enviado && !estaEnviado) {
                resultado.add(pedido);
            }
        }

        return resultado;
    }

    /**
     * Comprueba si se ha enviado un pedido
     * @param pedido el pedido que se va a comprobar
     * @return Boolean con si se ha enviado o no
     */
    public boolean checkEnviado(Pedido pedido) {
        LocalDate hoy = LocalDate.now();
        LocalDate finPreparacion = pedido.getFechahora().plusDays(pedido.getDiasPreparacion());
        return hoy.isAfter(finPreparacion);
    }

    /**
     * Obtener úlitmo número de pedido
     * @return Int con el último número de pedido
     */
    public int getLastNumPedido() {
        return pedidos.getLast().getNumero();
    }

    /**
     * Obtener primer número de pedido
     * @return Int con el primer número de pedido
     */
    public int getFirstNumPedido() {
        return pedidos.getFirst().getNumero();
    }

    /**
     * Crear nuevo pedido
     * @param cliente Cliente que realiza el pedido
     * @param articulo Artículo comprado en el pedido
     * @param cantidad Cantidad comprada
     * @param fechahora Fecha y hora del pedido
     * @param envio Coste de envío del pedido
     * @return El nuevo pedido
     */
    public Pedido makePedido(Cliente cliente, Articulo articulo, Integer cantidad, LocalDate fechahora, Float envio) {
        int numPedido;
        if (pedidos.isEmpty()) numPedido = 1;
        else numPedido = pedidos.getLast().getNumero() + 1;
        return new Pedido(numPedido, cliente, articulo, cantidad, fechahora, calcEnvioTotal(cantidad, envio), calcPrecioTotal(articulo, cantidad, envio, cliente));
    }

    private float calcPrecioTotal(Articulo articulo, int stockComprado, float precioEnvio, Cliente cliente) {
        return (((articulo.getPrecio() * stockComprado) + calcEnvioTotal(stockComprado, precioEnvio))) * (((100f - (100f * cliente.getDescuento())) / 100f));
    }

    public Float calcEnvioTotal(Integer cantidad, Float precioEnvio) {
        return precioEnvio + (1.05f * cantidad);
    }

    /**
     * Cargar los pedidos en el programa
     * @param configuracion define la configuración seleccionada.
     * @param clientes Cliente que realiza el pedido
     * @param articulos Artículo comprado
     * @return Boolean si ha cargado bien o no los pedidos
     */
    public boolean loadPedidos(int configuracion, List<Cliente> clientes, List<Articulo> articulos) {
        if (configuracion == 0) {
        try {
            pedidos.clear();

            pedidos.add(makePedido(clientes.get(0), articulos.get(0), 2, LocalDate.now().minusDays(3), 5f));
            pedidos.add(makePedido(clientes.get(4), articulos.get(1), 1, LocalDate.now().minusDays(1), 5f));
            pedidos.add(makePedido(clientes.get(7), articulos.get(2), 5, LocalDate.now().minusDays(3), 5f));
            pedidos.add(makePedido(clientes.get(1), articulos.get(3), 3, LocalDate.now().minusWeeks(1), 5f));
            pedidos.add(makePedido(clientes.get(5), articulos.get(4), 4, LocalDate.now().minusMonths(1), 5f));
            pedidos.add(makePedido(clientes.get(8), articulos.get(5), 2, LocalDate.now().minusDays(2), 5f));
            pedidos.add(makePedido(clientes.get(2), articulos.get(6), 1, LocalDate.now().minusWeeks(2), 5f));
            pedidos.add(makePedido(clientes.get(6), articulos.get(7), 6, LocalDate.now().minusMonths(2), 5f));
            pedidos.add(makePedido(clientes.get(3), articulos.get(8), 3, LocalDate.now().minusDays(5), 5f));
            pedidos.add(makePedido(clientes.get(0), articulos.get(1), 10, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(4), articulos.get(2), 7, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(7), articulos.get(3), 12, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(1), articulos.get(4), 6, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(5), articulos.get(5), 15, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(8), articulos.get(6), 9, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(2), articulos.get(7), 11, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(6), articulos.get(0), 8, LocalDate.now(), 5f));
            pedidos.add(makePedido(clientes.get(3), articulos.get(1), 13, LocalDate.now(), 5f));

            return true;
        } catch (Exception e) {
            return false;
        }
        }
        return false;
    }
}

package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los pedidos.
 */
public class ModeloPedidos {

    private List<Pedido> pedidos;
    /**
     * Constructor por defecto. Inicializa una lista vacía de pedidos.
     */
    public ModeloPedidos() {
        this.pedidos = new ArrayList<Pedido>();
    }
    /**
     * Constructor alternativo que recibe una lista de pedidos ya existente.
     * @param pedidos lista de pedidos precargada.
     */
    public ModeloPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve todos los pedidos.
     * @return lista de todos los pedidos.
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Establece la lista de pedidos.
     * @param pedidos lista de pedidos a establecer.
     */
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    //*************************** CRUD ***************************//

    /**
     * Añade un pedido a la lista.
     * @param pedido pedido a añadir.
     */
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    /**
     * Elimina un pedido de la lista.
     * @param pedido pedido a eliminar.
     */
    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    /**
     * Obtiene un pedido por su índice en la lista.
     * @param id índice del pedido.
     * @return pedido en la posición indicada.
     */
    public Pedido getPedido(int id) {
        return pedidos.get(id);
    }

    /**
     * Actualiza un pedido existente con nueva información.
     * @param pedidoOld pedido original.
     * @param pedidoNew nuevo pedido con datos actualizados.
     */
    public void updatePedido(Pedido pedidoOld, Pedido pedidoNew) {
        int index = pedidos.indexOf(pedidoOld);
        if (index != -1) {
            pedidos.set(index, pedidoNew);
        }

    }

    //*************************** Obtener datos ***************************//

    /**
     * Obtiene un pedido según su número identificador.
     * @param numero número del pedido.
     * @return el pedido con ese número o null si no existe.
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
     * Obtiene todos los pedidos realizados por un cliente.
     * @param cliente cliente a consultar.
     * @return lista de pedidos realizados por ese cliente.
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
     * Obtiene pedidos enviados o pendientes, con opción de filtrar por cliente.
     * @param enviado true para enviados, false para pendientes.
     * @param cliente cliente a filtrar (puede ser null).
     * @return lista de pedidos según los filtros.
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
     * Comprueba si un pedido ya ha sido enviado.
     * @param pedido pedido a comprobar.
     * @return true si ya ha pasado su fecha de preparación.
     */
    public boolean checkEnviado(Pedido pedido) {
        LocalDate hoy = LocalDate.now();
        LocalDate finPreparacion = pedido.getFechahora().plusDays(pedido.getDiasPreparacion());
        return hoy.isAfter(finPreparacion);
    }

    /**
     * Devuelve el número del último pedido registrado.
     * @return número del último pedido.
     */
    public int getLastNumPedido() {
        return pedidos.getLast().getNumero();
    }

    /**
     * Devuelve el número del primer pedido registrado.
     * @return número del primer pedido.
     */
    public int getFirstNumPedido() {
        return pedidos.getFirst().getNumero();
    }

    /**
     * Crea un nuevo pedido.
     * @param cliente cliente que realiza el pedido.
     * @param articulo artículo solicitado.
     * @param cantidad unidades pedidas.
     * @param fechahora fecha del pedido.
     * @param envio coste de envío.
     * @return el nuevo pedido creado.
     */
    public Pedido makePedido(Cliente cliente, Articulo articulo, Integer cantidad, LocalDate fechahora, Float envio) {
        int numPedido;
        if (pedidos.isEmpty()) numPedido = 1;
        else numPedido = pedidos.getLast().getNumero() + 1;
        return new Pedido(numPedido, cliente, articulo, cantidad, fechahora, calcEnvioTotal(cantidad, envio), calcPrecioTotal(articulo, cantidad, envio, cliente));
    }
    /**
     * Calcula el precio total de un pedido.
     * @param articulo artículo pedido.
     * @param stockComprado cantidad.
     * @param precioEnvio precio base del envío.
     * @param cliente cliente que realiza el pedido.
     * @return precio total tras aplicar descuentos.
     */
    private float calcPrecioTotal(Articulo articulo, int stockComprado, float precioEnvio, Cliente cliente) {
        return (((articulo.getPrecio() * stockComprado) + calcEnvioTotal(stockComprado, precioEnvio))) * (((100f - (100f * cliente.getDescuento())) / 100f));
    }
    /**
     * Calcula el precio final del envío con recargo por unidad.
     * @param cantidad número de artículos.
     * @param precioEnvio precio base del envío.
     * @return coste total del envío.
     */
    public Float calcEnvioTotal(Integer cantidad, Float precioEnvio) {
        return precioEnvio + (1.05f * cantidad);
    }

    /**
     * Carga pedidos de prueba en función de la configuración.
     * @param configuracion define si se carga el modo por defecto.
     * @param clientes lista de clientes disponibles.
     * @param articulos lista de artículos disponibles.
     * @return true si se cargaron correctamente, false si hubo error.
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

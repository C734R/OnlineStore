package javalinos.onlinestore.modelo.gestores.Local;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los pedidoDTOS.
 */
public class ModeloPedidosLocal implements IModeloPedidos {

    private List<PedidoDTO> pedidos;

    /**
     * Constructor por defecto. Inicializa una lista vacía de pedidoDTOS.
     */
    public ModeloPedidosLocal() {
        this.pedidos = new ArrayList<PedidoDTO>();
    }
    /**
     * Constructor alternativo que recibe una lista de pedidos ya existente.
     * @param pedidos lista de pedidos precargada.
     */
    public ModeloPedidosLocal(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve todos los pedidoDTOS.
     * @return lista de todos los pedidoDTOS.
     */
    public List<PedidoDTO> getPedidosDTO() {
        return pedidos;
    }

    /**
     * Establece la lista de pedidos.
     * @param pedidos lista de pedidos a establecer.
     */
    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }


    //*************************** CRUD ***************************//

    /**
     * Añade un pedidos a la lista.
     * @param pedidos pedidos a añadir.
     */
    public void addPedido(PedidoDTO pedidos) {
        this.pedidos.add(pedidos);
    }

    @Override
    public void addPedidoStock(PedidoDTO pedido) throws Exception {

    }

    /**
     * Elimina un pedidos de la lista.
     * @param pedidos pedidos a eliminar.
     */
    public void removePedido(PedidoDTO pedidos) {
        this.pedidos.remove(pedidos);
    }

    @Override
    public void removePedidoStock(PedidoDTO pedidos) throws Exception {

    }

    /**
     * Obtiene un pedido por su índice en la lista.
     * @param id índice del pedido.
     * @return pedido en la posición indicada.
     */
    public PedidoDTO getPedidoDTO(int id) {
        return pedidos.get(id);
    }

    /**
     * Actualiza un pedido existente con nueva información.
     * @param pedidoDTOOld pedido original.
     * @param pedidoDTONew nuevo pedido con datos actualizados.
     */
    public void updatePedido(PedidoDTO pedidoDTOOld, PedidoDTO pedidoDTONew) {
        int index = pedidos.indexOf(pedidoDTOOld);
        if (index != -1) {
            pedidos.set(index, pedidoDTONew);
        }

    }

    @Override
    public void updatePedidoStock(PedidoDTO pedidoDTOOld, PedidoDTO pedidoDTONew) throws Exception {

    }

    //*************************** Obtener datos ***************************//

    /**
     * Obtiene un pedido según su número identificador.
     * @param numero número del pedido.
     * @return el pedido con ese número o null si no existe.
     */
    public PedidoDTO getPedidoDTONumero(int numero) {
        for (PedidoDTO pedidoDTO : pedidos) {
            if (pedidoDTO.getNumero() == numero) {
                return pedidoDTO;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los pedidoDTOS realizados por un clienteDTO.
     * @param ClienteDTO clienteDTO a consultar.
     * @return lista de pedidoDTOS realizados por ese clienteDTO.
     */
    public List<PedidoDTO> getPedidosDTOCliente(ClienteDTO ClienteDTO) {

        if (pedidos.isEmpty()) return null;
        if (ClienteDTO != null) {
            List<PedidoDTO> pedidosCliente = new ArrayList<PedidoDTO>();
            for (PedidoDTO pedidoDTO : pedidos) {
                if (pedidoDTO.getCliente().equals(ClienteDTO)) {
                    pedidosCliente.add(pedidoDTO);
                }
            }
            return pedidosCliente;
        }
        return null;
    }

    /**
     * Obtiene pedidoDTOS enviados o pendientes, con opción de filtrar por clienteDTO.
     * @param enviado true para enviados, false para pendientes.
     * @param ClienteDTO clienteDTO a filtrar (puede ser null).
     * @return lista de pedidoDTOS según los filtros.
     */
    public List<PedidoDTO> getPedidosPendientesEnviados(Boolean enviado, ClienteDTO ClienteDTO) {
        List<PedidoDTO> listaFiltrada = new ArrayList<>();

        if (ClienteDTO != null) {
            for (PedidoDTO pedidoDTO : pedidos) {
                if (pedidoDTO.getCliente().equals(ClienteDTO)) {
                    listaFiltrada.add(pedidoDTO);
                }
            }
        } else {
            listaFiltrada = pedidos;
        }

        List<PedidoDTO> resultado = new ArrayList<>();

        for (PedidoDTO pedidoDTO : listaFiltrada) {
            boolean estaEnviado = checkEnviado(pedidoDTO);

            if (enviado && estaEnviado) {
                resultado.add(pedidoDTO);
            } else if (!enviado && !estaEnviado) {
                resultado.add(pedidoDTO);
            }
        }

        return resultado;
    }

    /**
     * Comprueba si un pedidoDTO ya ha sido enviado.
     * @param pedidoDTO pedidoDTO a comprobar.
     * @return true si ya ha pasado su fecha de preparación.
     */
    public boolean checkEnviado(PedidoDTO pedidoDTO) {
        LocalDate hoy = LocalDate.now();
        LocalDate finPreparacion = pedidoDTO.getFechahora().plusDays(pedidoDTO.getDiasPreparacion());
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
     * @param ClienteDTO clienteDTO que realiza el pedido.
     * @param ArticuloDTO artículo solicitado.
     * @param cantidad unidades pedidas.
     * @param fechahora fecha del pedido.
     * @param envio coste de envío.
     * @return el nuevo pedido creado.
     */
    public PedidoDTO makePedido(ClienteDTO ClienteDTO, ArticuloDTO ArticuloDTO, Integer cantidad, LocalDate fechahora, Float envio) {
        int numPedido;
        if (pedidos.isEmpty()) numPedido = 1;
        else numPedido = pedidos.getLast().getNumero() + 1;
        return new PedidoDTO(numPedido, ClienteDTO, ArticuloDTO, cantidad, fechahora, calcEnvioTotal(cantidad, envio), calcPrecioTotal(ArticuloDTO, cantidad, envio, ClienteDTO));
    }
    /**
     * Calcula el precio total de un pedido.
     * @param ArticuloDTO artículo pedido.
     * @param stockComprado cantidad.
     * @param precioEnvio precio base del envío.
     * @param ClienteDTO clienteDTO que realiza el pedido.
     * @return precio total tras aplicar descuentos.
     */
    private float calcPrecioTotal(ArticuloDTO ArticuloDTO, int stockComprado, float precioEnvio, ClienteDTO ClienteDTO) {
        return (((ArticuloDTO.getPrecio() * stockComprado) + calcEnvioTotal(stockComprado, precioEnvio))) * (((100f - (100f * ClienteDTO.getDescuento())) / 100f));
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
     * Carga pedidoDTOS de prueba en función de la configuración.
     *
     * @param clientesDTO  lista de clienteDTOS disponibles.
     * @param articulosDTO lista de artículos disponibles.
     */
    public void loadPedidos(List<ClienteDTO> clientesDTO, List<ArticuloDTO> articulosDTO) {
        pedidos.clear();

        pedidos.add(makePedido(clientesDTO.get(0), articulosDTO.get(0), 2, LocalDate.now().minusDays(3), 5f));
        pedidos.add(makePedido(clientesDTO.get(4), articulosDTO.get(1), 1, LocalDate.now().minusDays(1), 5f));
        pedidos.add(makePedido(clientesDTO.get(7), articulosDTO.get(2), 5, LocalDate.now().minusDays(3), 5f));
        pedidos.add(makePedido(clientesDTO.get(1), articulosDTO.get(3), 3, LocalDate.now().minusWeeks(1), 5f));
        pedidos.add(makePedido(clientesDTO.get(5), articulosDTO.get(4), 4, LocalDate.now().minusMonths(1), 5f));
        pedidos.add(makePedido(clientesDTO.get(8), articulosDTO.get(5), 2, LocalDate.now().minusDays(2), 5f));
        pedidos.add(makePedido(clientesDTO.get(2), articulosDTO.get(6), 1, LocalDate.now().minusWeeks(2), 5f));
        pedidos.add(makePedido(clientesDTO.get(6), articulosDTO.get(7), 6, LocalDate.now().minusMonths(2), 5f));
        pedidos.add(makePedido(clientesDTO.get(3), articulosDTO.get(8), 3, LocalDate.now().minusDays(5), 5f));
        pedidos.add(makePedido(clientesDTO.get(0), articulosDTO.get(1), 10, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(4), articulosDTO.get(2), 7, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(7), articulosDTO.get(3), 12, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(1), articulosDTO.get(4), 6, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(5), articulosDTO.get(5), 15, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(8), articulosDTO.get(6), 9, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(2), articulosDTO.get(7), 11, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(6), articulosDTO.get(0), 8, LocalDate.now(), 5f));
        pedidos.add(makePedido(clientesDTO.get(3), articulosDTO.get(1), 13, LocalDate.now(), 5f));
    }
}

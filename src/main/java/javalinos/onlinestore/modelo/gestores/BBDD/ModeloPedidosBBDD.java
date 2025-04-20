package javalinos.onlinestore.modelo.gestores.BBDD;

import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.Entidades.Pedido;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los pedidoDTOS.
 */
public class ModeloPedidosBBDD implements IModeloPedidos
{

    private FactoryDAO factoryDAO;
    private IModeloArticulos mArticulos;
    private IModeloClientes mClientes;
    /**
     * Constructor por defecto. Inicializa una lista vacía de pedidoDTOS.
     */
    public ModeloPedidosBBDD(FactoryDAO factoryDAO, IModeloArticulos mArticulos, IModeloClientes mClientes)
    {
        this.factoryDAO = factoryDAO;
        this.mArticulos = mArticulos;
        this.mClientes = mClientes;
    }

    //*************************** CRUD PEDIDOS ***************************//

    /**
     * Añade un pedidoDTO a la lista.
     * @param pedidoDTO pedidoDTO a añadir.
     */
    public void addPedido(PedidoDTO pedidoDTO) throws Exception
    {
        Pedido pedido = pedidoDTOtoEntidad(pedidoDTO);
        factoryDAO.getDAOPedido().insertar(pedido);
    }

    public void addPedidoStock(PedidoDTO pedidoDTO) throws Exception {
        Pedido pedido = pedidoDTOtoEntidad(pedidoDTO);
        factoryDAO.getDAOPedido().insertarConStock(pedido);
    }

    /**
     * Elimina un pedidoDTO de la lista.
     * @param pedidoDTO pedidoDTO a eliminar.
     */
    public void removePedido(PedidoDTO pedidoDTO) throws Exception
    {
        Pedido pedido = getPedidoEntidadNumero(pedidoDTO.getNumero());
        factoryDAO.getDAOPedido().eliminar(pedido.getId());
    }

    public void removePedidoStock(PedidoDTO pedidoDTO) throws Exception {
        Pedido pedido = getPedidoEntidadNumero(pedidoDTO.getNumero());
        factoryDAO.getDAOPedido().eliminarConStock(pedido);
    }

    public void removePedidosAll() throws Exception
    {
        factoryDAO.getDAOPedido().eliminarTodos();
    }

    /**
     * Obtiene un pedido por su índice en la lista.
     * @param id índice del pedido.
     * @return pedido en la posición indicada.
     */
    public PedidoDTO getPedidoDTO(int id) throws Exception
    {
        Pedido pedido = getPedidoEntidadId(id);
        return pedidoEntidadToPedidoDTO(pedido);
    }

    private PedidoDTO pedidoEntidadToPedidoDTO(Pedido pedido) throws Exception
    {
        ArticuloDTO articuloDTO = mArticulos.getArticuloDTOId(pedido.getArticulo());
        ClienteDTO clienteDTO = mClientes.getClienteDTOId(pedido.getCliente());
        return new PedidoDTO(pedido, clienteDTO, articuloDTO);
    }

    private Pedido pedidoDTOtoEntidad(PedidoDTO pedidoDTO) throws Exception {
        return new Pedido(pedidoDTO, mClientes.getIdClienteDTO(pedidoDTO.getCliente()), mArticulos.getIdArticuloDTO(pedidoDTO.getArticulo()));
    }

    private Pedido getPedidoEntidadNumero(int numero) throws Exception
    {
        return factoryDAO.getDAOPedido().getPedidoNumero(numero);
    }

    private Pedido getPedidoEntidadId(int id) throws Exception
    {
        return factoryDAO.getDAOPedido().getPorId(id);
    }

    /**
     * Actualiza un pedido existente con nueva información.
     * @param pedidoDTOOld pedido original.
     * @param pedidoDTONew nuevo pedido con datos actualizados.
     */
    public void updatePedido(PedidoDTO pedidoDTOOld, PedidoDTO pedidoDTONew) throws Exception
    {
        Pedido pedidoOld = getPedidoEntidadNumero(pedidoDTOOld.getNumero());
        Pedido pedidoNew = new Pedido (pedidoDTONew, mClientes.getIdClienteDTO(pedidoDTONew.getCliente()), mArticulos.getIdArticuloDTO(pedidoDTONew.getArticulo()));
        pedidoNew.setId(pedidoOld.getId());
        factoryDAO.getDAOPedido().actualizar(pedidoNew);
    }

    public void updatePedidoStock(PedidoDTO pedidoDTOOld, PedidoDTO pedidoDTONew) throws Exception {
        Pedido pedidoOld = getPedidoEntidadNumero(pedidoDTOOld.getNumero());
        Pedido pedidoNew = new Pedido (pedidoDTONew, mClientes.getIdClienteDTO(pedidoDTONew.getCliente()), mArticulos.getIdArticuloDTO(pedidoDTONew.getArticulo()));
        pedidoNew.setId(pedidoOld.getId());
        Integer diferenciaStock = pedidoOld.getCantidad() - pedidoNew.getCantidad();
        factoryDAO.getDAOPedido().actualizarConStock(pedidoNew, diferenciaStock);
    }

    private Articulo getArticuloPedidoDTO(PedidoDTO pedidoDTO) throws Exception
    {
        return factoryDAO.getDAOArticulo().getArticuloCodigo(pedidoDTO.getArticulo().getCodigo());
    }

    private Cliente getClientePedidoDTO(PedidoDTO pedidoDTO) throws Exception
    {
        return factoryDAO.getDAOCliente().getClienteNIF(pedidoDTO.getCliente().getNif());
    }

    //*************************** Getters & Setters ***************************//


    private List<Pedido> getPedidos() throws Exception
    {
        return factoryDAO.getDAOPedido().getTodos();
    }

    /**
     * Devuelve todos los pedidoDTOS.
     * @return lista de todos los pedidoDTOS.
     */
    public List<PedidoDTO> getPedidosDTO() throws Exception {
        return pedidosEntidadToDTO(getPedidos());
    }

    private List<PedidoDTO> pedidosEntidadToDTO(List<Pedido> pedidos) throws Exception {
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = new PedidoDTO(
                    pedido,
                    getClienteDTOPedido(pedido),
                    getArticuloDTOPedido(pedido)
            );
            pedidosDTO.add(pedidoDTO);
        }
        return pedidosDTO;
    }

    private List<Pedido> pedidosDTOtoEntidad(List<PedidoDTO> pedidosDTO) throws Exception {
        List<Pedido> pedidosEntidad = new ArrayList<>();
        for (PedidoDTO pedidoDTO : pedidosDTO) {
            Pedido pedido = getPedidoEntidadNumero(pedidoDTO.getNumero());
            pedidosEntidad.add(pedido);
        }
        return pedidosEntidad;
    }

    private ArticuloDTO getArticuloDTOPedido (Pedido pedido) throws Exception
    {
        return mArticulos.getArticuloDTOId(pedido.getArticulo());
    }

    private ClienteDTO getClienteDTOPedido(Pedido pedido) throws Exception
    {
        return mClientes.getClienteDTOId(pedido.getCliente());
    }

    /**
     * Establece la lista de pedidosDTO.
     * @param pedidosDTO lista de pedidosDTO a establecer.
     */
    public void setPedidos(List<PedidoDTO> pedidosDTO) throws Exception
    {
        List<Pedido> pedidosEntidad = pedidosDTOtoEntidad(pedidosDTO);
        factoryDAO.getDAOPedido().insertarTodos(pedidosEntidad);
    }

    //*************************** Obtener datos ***************************//

    /**
     * Obtiene un pedido según su número identificador.
     * @param numero número del pedido.
     * @return el pedido con ese número o null si no existe.
     */
    public PedidoDTO getPedidoDTONumero(int numero) throws Exception
    {
        Pedido pedido = getPedidoEntidadNumero(numero);
        return pedidoEntidadToPedidoDTO(pedido);
    }

    /**
     * Obtiene todos los pedidos realizados por un cliente.
     * @param clienteDTO cliente a consultar.
     * @return lista de pedidos realizados por ese cliente.
     */
    public List<PedidoDTO> getPedidosDTOCliente(ClienteDTO clienteDTO) throws Exception
    {
        Cliente cliente = factoryDAO.getDAOCliente().getClienteNIF(clienteDTO.getNif());
        return pedidosEntidadToDTO(getPedidosEntidadCliente(cliente));
    }

    private List<Pedido> getPedidosEntidadCliente(Cliente cliente) throws Exception
    {
        return factoryDAO.getDAOPedido().getPedidosCliente(cliente);
    }

    /**
     * Obtiene pedidos enviados o pendientes, con opción de filtrar por cliente.
     * @param enviado true para enviados, false para pendientes.
     * @param clienteDTO cliente a filtrar (puede ser null).
     * @return lista de pedidos según los filtros.
     */
    public List<PedidoDTO> getPedidosPendientesEnviados(Boolean enviado, ClienteDTO clienteDTO) throws Exception
    {
        List<PedidoDTO> listaPedidosPE;
        if(clienteDTO != null) listaPedidosPE = getPedidosDTOCliente(clienteDTO);
        else listaPedidosPE = getPedidosDTO();

        Iterator<PedidoDTO> iteradorPedidos = listaPedidosPE.iterator();
        while (iteradorPedidos.hasNext()) {
            PedidoDTO pedidoDTO = iteradorPedidos.next();
            boolean estaEnviado = checkEnviado(pedidoDTO);

            // Si quiero enviados y no está enviado, borrar
            // Si quiero pendientes y está enviado, borrar
            if ((enviado && !estaEnviado) || (!enviado && estaEnviado)) {
                iteradorPedidos.remove();
            }
        }

        return listaPedidosPE;
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
    public int getLastNumPedido() throws Exception
    {
        List<PedidoDTO> pedidosDTO = getPedidosDTO();
        return pedidosDTO.getLast().getNumero();
    }

    /**
     * Devuelve el número del primer pedido registrado.
     * @return número del primer pedido.
     */
    public int getFirstNumPedido() throws Exception
    {
        List<PedidoDTO> pedidosDTO = getPedidosDTO();
        return pedidosDTO.getFirst().getNumero();
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
    public PedidoDTO makePedido(ClienteDTO ClienteDTO, ArticuloDTO ArticuloDTO, Integer cantidad, LocalDate fechahora, Float envio) throws Exception
    {
        List<PedidoDTO> pedidosDTO = getPedidosDTO();
        int numPedido;
        if (pedidosDTO.isEmpty()) numPedido = 1;
        else numPedido = pedidosDTO.getLast().getNumero() + 1;
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
    private float calcPrecioTotal(ArticuloDTO ArticuloDTO, int stockComprado, float precioEnvio, ClienteDTO ClienteDTO)
    {
        return (((ArticuloDTO.getPrecio() * stockComprado) + calcEnvioTotal(stockComprado, precioEnvio))) * (((100f - (100f * ClienteDTO.getDescuento())) / 100f));
    }
    /**
     * Calcula el precio final del envío con recargo por unidad.
     * @param cantidad número de artículos.
     * @param precioEnvio precio base del envío.
     * @return coste total del envío.
     */
    public Float calcEnvioTotal(Integer cantidad, Float precioEnvio)
    {
        return precioEnvio + (1.05f * cantidad);
    }

    /**
     * Carga pedidoDTOS de prueba en función de la configuración.
     * @param ClienteDTOS lista de clienteDTOS disponibles.
     * @param ArticuloDTOS lista de artículos disponibles.
     */
    public void loadPedidos(List<ClienteDTO> ClienteDTOS, List<ArticuloDTO> ArticuloDTOS) throws Exception
    {
        try {
            removePedidosAll();

            addPedido(makePedido(ClienteDTOS.get(0), ArticuloDTOS.get(0), 2, LocalDate.now().minusDays(3), 5f));
            addPedido(makePedido(ClienteDTOS.get(4), ArticuloDTOS.get(1), 1, LocalDate.now().minusDays(1), 5f));
            addPedido(makePedido(ClienteDTOS.get(7), ArticuloDTOS.get(2), 5, LocalDate.now().minusDays(3), 5f));
            addPedido(makePedido(ClienteDTOS.get(1), ArticuloDTOS.get(3), 3, LocalDate.now().minusWeeks(1), 5f));
            addPedido(makePedido(ClienteDTOS.get(5), ArticuloDTOS.get(4), 4, LocalDate.now().minusMonths(1), 5f));
            addPedido(makePedido(ClienteDTOS.get(8), ArticuloDTOS.get(5), 2, LocalDate.now().minusDays(2), 5f));
            addPedido(makePedido(ClienteDTOS.get(2), ArticuloDTOS.get(6), 1, LocalDate.now().minusWeeks(2), 5f));
            addPedido(makePedido(ClienteDTOS.get(6), ArticuloDTOS.get(7), 6, LocalDate.now().minusMonths(2), 5f));
            addPedido(makePedido(ClienteDTOS.get(3), ArticuloDTOS.get(8), 3, LocalDate.now().minusDays(5), 5f));
            addPedido(makePedido(ClienteDTOS.get(0), ArticuloDTOS.get(1), 10, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(4), ArticuloDTOS.get(2), 7, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(7), ArticuloDTOS.get(3), 12, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(1), ArticuloDTOS.get(4), 6, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(5), ArticuloDTOS.get(5), 15, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(8), ArticuloDTOS.get(6), 9, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(2), ArticuloDTOS.get(7), 11, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(6), ArticuloDTOS.get(0), 8, LocalDate.now(), 5f));
            addPedido(makePedido(ClienteDTOS.get(3), ArticuloDTOS.get(1), 13, LocalDate.now(), 5f));

        }
        catch (Exception e)
        {
            throw new Exception("Error al precargar pedidos.", e);
        }

    }
}

package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaPedidos;

import java.time.LocalDate;
import java.util.*;

import static javalinos.onlinestore.OnlineStore.cClientes;
import static javalinos.onlinestore.utils.Utilidades.listToStr;
/**
 * Controlador para gestionar la lógica relacionada con los pedidos.
 */
public class ControlPedidos extends ControlBase{

    private VistaPedidos vPedidos;
    private IModeloArticulos mArticulos;
    private IModeloClientes mClientes;
    private IModeloPedidos mPedidos;
    private float precioEnvio = 5f;

    /**
     * Constructor principal de ControlPedidos.
     * @param mStore el ModeloStore que contiene todos los modelos
     * @param vPedidos la vista de pedidos asociada
     */
    public ControlPedidos(ModeloStore mStore, VistaPedidos vPedidos) {
        super.setModeloStore(mStore);
        this.mArticulos = mStore.getModeloArticulos();
        this.mClientes = mStore.getModeloClientes();
        this.mPedidos = mStore.getModeloPedidos();
        this.vPedidos = vPedidos;
    }
    /**
     * Constructor vacío para ControlPedidos (sin vista asociada).
     */
    public ControlPedidos() {
        super();
        vPedidos = null;
    }

    //*************************** Getters & Setters ***************************//
    /**
     * Devuelve la vista de pedidos.
     * @return VistaPedidos asociada
     */
    public VistaPedidos getVistaPedidos() {
        return vPedidos;
    }
    /**
     * Asigna la vista de pedidos.
     * @param vPedidos vista a asignar
     */
    public void setVistaPedidos(VistaPedidos vPedidos) {
        this.vPedidos = vPedidos;
    }
    /**
     * Devuelve el precio de envío actual.
     * @return precio de envío en euros
     */
    public float getPrecioEnvio() {
        return precioEnvio;
    }
    /**
     * Establece el precio de envío.
     * @param precioEnvio nuevo precio de envío
     */
    public void setPrecioEnvio(float precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    //*************************** Menu gestión pedidos ***************************//

    /**
     * Inicia el menú de gestión de pedidos desde la vista.
     */
    public void iniciar()
    {
        int opcion;
        while (true)
        {
            vPedidos.showCabecera();
            vPedidos.showMenu(2);
            opcion = vPedidos.askInt("Introduce una opción", 0, 6, false, false);
            switch (opcion)
            {
                case 1:
                    addPedidos();
                    break;
                case 2:
                    removePedidos();
                    break;
                case 3:
                    updatePedido();
                    break;
                case 4:
                    showListPedidos(askFiltrarCliente(0));
                    break;
                case 5:
                    showListPedidosPendientesEnviados(askFiltrarCliente(2), false);
                    break;
                case 6:
                    showListPedidosPendientesEnviados(askFiltrarCliente(1), true);
                    break;
                case 0:
                    vPedidos.showMensaje("Volviendo al menú principal...", true);
                    return;
                default:
                    vPedidos.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.", true);
            }
        }
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un nuevo pedido al sistema.
     */
    public void addPedidos()
    {
        vPedidos.showMensaje("******** Añadir Pedido ********", true);
        List<ArticuloDTO> articulosDisponibles = null;
        try {
            articulosDisponibles = mArticulos.getArticulosDTO();
            for (ArticuloDTO articuloDTO : articulosDisponibles)
            {
                if (mArticulos.getStockArticulo(articuloDTO) <= 0)
                {
                    articulosDisponibles.remove(articuloDTO);
                }
            }
        } catch (Exception e) {
            vPedidos.showMensaje("No existen artículos disponibles para su compra.", true);
            return;
        }
        ClienteDTO clienteDTO = askCliente(true);
        if(clienteDTO == null) return;
        try
        {

            if (!articulosDisponibles.isEmpty())
            {
                vPedidos.showOptions(listToStr(articulosDisponibles), 0, true, true, true);
                int indexArticulo = vPedidos.askInt("Selecciona el articulo que quiere comprar entre los disponibles", 1, articulosDisponibles.size(), true, true);
                if (indexArticulo == -99999) return;
                indexArticulo = indexArticulo - 1;
                ArticuloDTO articuloDTO = mArticulos.getArticuloIndex(indexArticulo);

                int stockComprado = vPedidos.askInt("Ingresa la cantidad que quiere comprar", 1, mArticulos.getStockArticulo(articuloDTO), true, true);
                if (stockComprado == -99999) return;

                PedidoDTO pedido = mPedidos.makePedido(clienteDTO, articuloDTO, stockComprado, LocalDate.now(), precioEnvio);
                if (pedido == null)
                {
                    vPedidos.showMensajePausa("Error. No se ha podido crear el pedido.", true);
                    return;
                }
                mPedidos.addPedidoStock(pedido);
                vPedidos.showMensajePausa("Pedido añadido correctamente y actualizado stock.", true);
            }
            else vPedidos.showMensaje("No hay artículos disponibles para comprar en este momento", true);
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al añadir pedido." + e, true);

        }
    }

    /**
     * Elimina un pedido pendiente seleccionado por el usuario.
     */
    public void removePedidos()
    {
        List<PedidoDTO> pedidos;
        try
        {
            pedidos = mPedidos.getPedidosPendientesEnviados(false, null);
            Map<ArticuloDTO,Integer> stockArticulos;

            stockArticulos = mArticulos.getArticuloStocksDTO();

            if (pedidos.isEmpty()) {
                vPedidos.showMensajePausa("Error. No existen pedidos a eliminar.", true);
                return;
            }
            vPedidos.showListPedidos(pedidos,null, true);
            int numPedidoBorrar = vPedidos.askInt("Ingresa el numero de pedido que quieres borrar: ", 1, pedidos.size(), true, true);
            if(numPedidoBorrar == -99999) return;
            PedidoDTO pedidoDTO = pedidos.get(numPedidoBorrar-1);
            mPedidos.removePedidoStock(pedidoDTO);
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al borrar pedido." + e, true);
            return;
        }
        vPedidos.showMensajePausa("El pedido ha sido eliminado correctamente.", true);
    }

    /**
     * Modifica un pedido pendiente cambiando cliente y/o cantidad.
     */
    public void updatePedido()
    {
        vPedidos.showMensaje("******** Modificar PedidoDTO ********", true);
        try {
            List<PedidoDTO> pedidoDTOS = mPedidos.getPedidosPendientesEnviados(false, null);
            Map<ArticuloDTO, Integer> stockArticulos = mArticulos.getArticuloStocksDTO();
            if (pedidoDTOS.isEmpty()) {
                vPedidos.showMensajePausa("No hay pedidos disponibles para modificar.", true);
                return;
            }

            vPedidos.showListPedidos(pedidoDTOS, null, true);
            int seleccion = vPedidos.askInt("Selecciona el número del pedido a modificar", 1, pedidoDTOS.size(), true, true);
            if (seleccion == -99999) return;

            PedidoDTO pedidoDTOOld = pedidoDTOS.get(seleccion - 1);

            ClienteDTO nuevoClienteDTO = vPedidos.askClienteOpcional(mClientes.getClientesDTO(), pedidoDTOOld.getCliente());
            if (nuevoClienteDTO == null) nuevoClienteDTO = pedidoDTOOld.getCliente();

            int maxStock = stockArticulos.get(pedidoDTOOld.getArticulo()) + pedidoDTOOld.getCantidad();
            Integer nuevaCantidad = vPedidos.askIntOpcional("Cantidad actual: " + pedidoDTOOld.getCantidad(), 1, maxStock);
            if (nuevaCantidad == null) nuevaCantidad = pedidoDTOOld.getCantidad();

            PedidoDTO pedidoDTONew = mPedidos.makePedido(nuevoClienteDTO, pedidoDTOOld.getArticulo(), nuevaCantidad, LocalDate.now(), precioEnvio);
            mPedidos.updatePedidoStock(pedidoDTOOld,pedidoDTONew);
            vPedidos.showMensajePausa("PedidoDTO actualizado correctamente.", true);
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al modificar pedido." + e, true);
        }
    }

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra todos los pedidos o filtra por clienteDTO si se proporciona.
     * @param clienteDTO clienteDTO por el que se quiere filtrar
     */
    public void showListPedidos(ClienteDTO clienteDTO)
    {
        try
        {
            if (mPedidos.getPedidosDTO().isEmpty()) {
                vPedidos.showMensajePausa("Aún no existen pedidos registrados.", true);
                return;
            }
            List<PedidoDTO> pedidoDTOS;
            if(clienteDTO != null) {
                pedidoDTOS = mPedidos.getPedidosDTOCliente(clienteDTO);
                if (pedidoDTOS.isEmpty()) {
                    vPedidos.showMensajePausa("No hay pedidos registrados para este clienteDTO.", true);
                    return;
                }
            }
            else pedidoDTOS = mPedidos.getPedidosDTO();
            vPedidos.showListPedidos(pedidoDTOS, clienteDTO, false);
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al mostrar pedidos." + e, true);
        }

    }

    /**
     * Muestra pedidos pendientes o enviados según se indique.
     * @param clienteDTO clienteDTO por el que se quiere filtrar
     * @param enviado true para pedidos enviados, false para pendientes
     */
    public void showListPedidosPendientesEnviados(ClienteDTO clienteDTO, boolean enviado)
    {
        try
        {
            if (mPedidos.getPedidosDTO().isEmpty()) {
                vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
                return;
            }
            List<PedidoDTO> pedidoDTOS = mPedidos.getPedidosPendientesEnviados(enviado, clienteDTO);
            if (pedidoDTOS.isEmpty()) {
                if (enviado) {
                    vPedidos.showMensaje("No hay pedidos enviados registrados" +
                            (clienteDTO != null ? " para este clienteDTO." : "."), true);
                } else {
                    vPedidos.showMensaje("No hay pedidos pendientes registrados" +
                            (clienteDTO != null ? " para este clienteDTO." : "."), true);
                }
                return;
            }
            vPedidos.showListPedidos(pedidoDTOS, clienteDTO, false);
        }
        catch (Exception e)
        {
            if(!enviado)vPedidos.showMensajePausa("Error al mostrar pedidos pendientes." + e, true);
            else vPedidos.showMensajePausa("Error al mostrar pedidos enviados." + e, true);
        }

    }

    /**
     * Muestra todos los pedidos en la vista.
     * @param clienteDTO cliente por el que se filtran los pedidos
     */
    public void showPedidosCliente(ClienteDTO clienteDTO)
    {
        try
        {
            vPedidos.showPedidos(mPedidos.getPedidosDTO(), clienteDTO);
        } catch (Exception e) {
            vPedidos.showMensajePausa("Error al mostrar pedidos." + e, true);
        }
    }

    //*************************** Pedir datos ***************************//

    /**
     * Pide al usuario que seleccione o cree un cliente.
     * @param crear true si se permite crear un nuevo cliente
     * @return cliente seleccionado o creado
     */
    public ClienteDTO askCliente (boolean crear)
    {
        int indexCliente;
        List<ClienteDTO> clientesDTO = cClientes.getListaClientes();
        vPedidos.showListClientes(clientesDTO);
        if(crear) indexCliente = vPedidos.askInt("Selecciona un cliente existente o crea uno eligiendo " + (cClientes.getIndexCliente(true)+2), (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+2), true, true);
        else indexCliente = vPedidos.askInt("Selecciona un cliente", (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+1), true, true);
        if (indexCliente == -99999) return null;

        if (crear && indexCliente == (cClientes.getIndexCliente(true)+2)) {
            cClientes.addCliente();
            return cClientes.getCliente(cClientes.getIndexCliente(true));
        }

        ClienteDTO ClienteDTO = cClientes.getCliente(indexCliente-1);
        if (ClienteDTO == null) {
            vPedidos.showMensajePausa("Error. El cliente no existe. Volviendo...", true);
            return null;
        }
        return ClienteDTO;
    }

    /**
     * Pregunta si se desea filtrar por cliente y devuelve el seleccionado.
     * @param tipoFiltro tipo de filtrado: 0=todos, 1=enviados, 2=pendientes
     * @return cliente seleccionado o null
     */
    public ClienteDTO askFiltrarCliente(int tipoFiltro)
    {
        boolean filtrar = vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true);
        if (filtrar) return askClienteFiltro(tipoFiltro);
        return null;
    }

    /**
     * Solicita al usuario seleccionar un cliente filtrado por tipo.
     * @param tipoFiltrado 0=todos, 1=enviados, 2=pendientes
     * @return ClienteDTO seleccionado o null
     */
    public ClienteDTO askClienteFiltro(int tipoFiltrado)
    {
        int indexCliente;
        try {
            List<PedidoDTO> pedidoDTOS = mPedidos.getPedidosDTO();
            List<ClienteDTO> clientesPedidos = new ArrayList<>();
            if (tipoFiltrado == 0) {
                for (PedidoDTO pedidoDTO : pedidoDTOS) {
                    if (clientesPedidos.contains(pedidoDTO.getCliente())) continue;
                    clientesPedidos.add(pedidoDTO.getCliente());
                }
                if (clientesPedidos.isEmpty()) {
                    vPedidos.showMensajePausa(("No hay pedidos registrados para ningún cliente."), true);
                    return null;
                }
            } else if (tipoFiltrado == 1) {
                for (PedidoDTO pedidoDTO : pedidoDTOS) {
                    if (checkEnviado(pedidoDTO)) continue;
                    if (clientesPedidos.contains(pedidoDTO.getCliente())) continue;
                    clientesPedidos.add(pedidoDTO.getCliente());
                }
                if (clientesPedidos.isEmpty()) {
                    vPedidos.showMensajePausa(("No hay pedidos enviados registrados para ningún cliente."), true);
                    return null;
                }
            } else if (tipoFiltrado == 2) {
                for (PedidoDTO pedidoDTO : pedidoDTOS) {
                    if (!checkEnviado(pedidoDTO)) continue;
                    if (clientesPedidos.contains(pedidoDTO.getCliente())) continue;
                    clientesPedidos.add(pedidoDTO.getCliente());
                }
                if (clientesPedidos.isEmpty()) {
                    vPedidos.showMensajePausa(("No hay pedidos pendientes registrados para ningún cliente."), true);
                    return null;
                }
            }
            vPedidos.showListClientesPedidos(clientesPedidos);
            indexCliente = vPedidos.askInt("Selecciona un clienteDTO", 1, clientesPedidos.size(), true, true);
            if (indexCliente == -99999) return null;
            ClienteDTO ClienteDTO = clientesPedidos.get(indexCliente - 1);
            if (ClienteDTO == null) {
                vPedidos.showMensajePausa("Error. El cliente no existe. Volviendo...", true);
                return null;
            }
            return ClienteDTO;
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al mostrar pedidos filtrados." + e, true);
            return null;
        }
    }

    //*************************** Creación y cálculo de datos ***************************//

    /**
     * Verifica si un pedidoDTO ha sido enviado.
     * @param pedidoDTO el pedidoDTO a verificar
     * @return true si ha sido enviado, false en caso contrario
     */
    private boolean checkEnviado(PedidoDTO pedidoDTO)
    {
        return mPedidos.checkEnviado(pedidoDTO);
    }

    /**
     * Carga los pedidos desde un archivo o fuente externa.
     */
    public void loadPedidos() throws Exception
    {
            getModeloStore().getModeloPedidos().loadPedidos(mClientes.getClientesDTO(), mArticulos.getArticulosDTO());
    }

}

package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.Consola.VistaPedidos;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.time.LocalDate;
import java.util.*;

import static javalinos.onlinestore.OnlineStore.*;
import static javalinos.onlinestore.utils.Utilidades.listToStr;
/**
 * Controlador para gestionar la lógica relacionada con los pedidos.
 */
public class ControlPedidos extends ControlBase{

    private IVistaPedidos vPedidos;
    private IModeloArticulos mArticulos;
    private IModeloClientes mClientes;
    private IModeloPedidos mPedidos;
    private float precioEnvio = 5f;

    /**
     * Constructor principal de ControlPedidos.
     * @param mStore el ModeloStore que contiene todos los modelos
     * @param vPedidos la vista de pedidos asociada
     */
    public ControlPedidos(ModeloStore mStore, IVistaPedidos vPedidos) {
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
    public IVistaPedidos getVistaPedidos() {
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
            opcion = vPedidos.askInt("Introduce una opción", 0, 6, false, false, true);
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
                    mostrarListaPedidos();
                    vPedidos.showMensajePausa("",true);
                    break;
                case 5:
                    mostrarListaPedidosPendientes();
                    vPedidos.showMensajePausa("",true);
                    break;
                case 6:
                    mostrarListaPedidosEnviados();
                    vPedidos.showMensajePausa("",true);
                    break;
                case 0:
                    vPedidos.showMensaje("Volviendo al menú principal...", true);
                    return;
                default:
                    vPedidos.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.", true);
            }
        }
    }

    public void mostrarListaPedidosEnviados() {
        Boolean filtrarCliente = cPedidos.askFiltrarCliente();
        if (filtrarCliente == null) return;
        if (filtrarCliente) cPedidos.showListPedidosPendientesEnviados(cPedidos.askClienteFiltro(2), true);
        else cPedidos.showListPedidosPendientesEnviados(null, true);
    }

    public void mostrarListaPedidosPendientes() {
        Boolean filtrarCliente = cPedidos.askFiltrarCliente();
        if (filtrarCliente == null) return;
        if (filtrarCliente) cPedidos.showListPedidosPendientesEnviados(cPedidos.askClienteFiltro(1), false);
        else cPedidos.showListPedidosPendientesEnviados(null, false);    }

    public void mostrarListaPedidos() {
        Boolean filtrarCliente = cPedidos.askFiltrarCliente();
        if (filtrarCliente == null) return;
        if (filtrarCliente) cPedidos.showListPedidos(cPedidos.askClienteFiltro(0));
        else cPedidos.showListPedidos(null);
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un nuevo pedido al sistema.
     */
    public void addPedidos()
    {
        vPedidos.showMensaje("******** Añadir Pedido ********", true);
        List<ArticuloDTO> articulosDisponibles;
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
                int indexArticulo = vPedidos.askInt("Selecciona el articulo que quiere comprar entre los disponibles", 1, articulosDisponibles.size(), true, true, true);
                if (indexArticulo == -99999) return;
                indexArticulo = indexArticulo - 1;
                ArticuloDTO articuloDTO = mArticulos.getArticuloDTOIndex(indexArticulo);

                int stockComprado = vPedidos.askInt("Ingresa la cantidad que quiere comprar", 1, mArticulos.getStockArticulo(articuloDTO), true, true, true);
                if (stockComprado == -99999) return;

                PedidoDTO pedido = mPedidos.makePedido(clienteDTO, articuloDTO, stockComprado, LocalDate.now(), precioEnvio);
                if (pedido == null)
                {
                    vPedidos.showMensajePausa("Error. No se ha podido crear el pedido.", true);
                    return;
                }
                mPedidos.addPedidoStockSP(pedido);
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
        vPedidos.showMensaje("******** Eliminar Pedido ********", true);
        try
        {
            pedidos = mPedidos.getPedidosPendientesEnviados(false, null);

            if (pedidos.isEmpty()) {
                vPedidos.showMensajePausa("Error. No existen pedidos a eliminar.", true);
                return;
            }

            int numPedidoBorrar = vPedidos.askPedidoRemove(pedidos);
            if(numPedidoBorrar == -99999) return;
            PedidoDTO pedidoDTO = pedidos.get(numPedidoBorrar-1);

            mPedidos.removePedidoStockSP(pedidoDTO);
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
        vPedidos.showMensaje("******** Modificar Pedido ********", true);
        try {
            List<PedidoDTO> pedidosDTO = mPedidos.getPedidosPendientesEnviados(false, null);
            if (pedidosDTO.isEmpty()) {
                vPedidos.showMensajePausa("No hay pedidos disponibles para modificar.", true);
                return;
            }
            int seleccion = vPedidos.askPedidoModificar(pedidosDTO);
            if (seleccion == -99999) return;
            PedidoDTO pedidoDTOOld = pedidosDTO.get(seleccion - 1);

            ClienteDTO nuevoClienteDTO = vPedidos.askClienteOpcional(mClientes.getClientesDTO(), pedidoDTOOld.getCliente());
            if (nuevoClienteDTO == null) nuevoClienteDTO = pedidoDTOOld.getCliente();
            Integer stockBBDD = mArticulos.getArticuloStocksDTOIds().get(mArticulos.getIdArticuloDTO(pedidoDTOOld.getArticulo()));
            if (stockBBDD == null) {
                vPedidos.showMensajePausa("No se ha encontrado stock para el artículo.", true);
                return;
            }
            int maxStock = stockBBDD + pedidoDTOOld.getCantidad();
            Integer nuevaCantidad = vPedidos.askIntOpcional("Cantidad actual: " + pedidoDTOOld.getCantidad() + ". Cantidad máxima: " + maxStock, 1, maxStock);
            if (nuevaCantidad == null) nuevaCantidad = pedidoDTOOld.getCantidad();

            PedidoDTO pedidoDTONew = mPedidos.makePedido(nuevoClienteDTO, pedidoDTOOld.getArticulo(), nuevaCantidad, LocalDate.now(), precioEnvio);
            mPedidos.updatePedidoStockSP(pedidoDTOOld, pedidoDTONew);
            vPedidos.showMensajePausa("Pedido actualizado correctamente.", true);
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al modificar pedido." + e, true);
        }
    }

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra todos los pedidos o filtra por cliente si se proporciona.
     * @param clienteDTO cliente por el que se quiere filtrar
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
                    vPedidos.showMensajePausa("No hay pedidos registrados para este cliente.", true);
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
     * @param clienteDTO cliente por el que se quiere filtrar
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
                            (clienteDTO != null ? " para este cliente." : "."), true);
                } else {
                    vPedidos.showMensaje("No hay pedidos pendientes registrados" +
                            (clienteDTO != null ? " para este cliente." : "."), true);
                }
                return;
            }
            if(!enviado) vPedidos.showListPedidosPendientes(pedidoDTOS, clienteDTO);
            else vPedidos.showListPedidosEnviados(pedidoDTOS, clienteDTO);
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
        if(crear) indexCliente = vPedidos.askInt("Selecciona un cliente existente o crea uno eligiendo " + (cClientes.getIndexCliente(true)+2), (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+2), true, true, true);
        else indexCliente = vPedidos.askInt("Selecciona un cliente", (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+1), true, true, true);
        if (indexCliente == -99999) return null;

        if (crear && indexCliente == (cClientes.getIndexCliente(true)+2)) {
            cClientes.addCliente();
            if (indexCliente != (cClientes.getIndexCliente(true)+1)){
                vClientes.showMensajePausa("No se ha podido crear el cliente.", true);
                return null;
            }
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
     * @return cliente seleccionado o null
     */
    public Boolean askFiltrarCliente()
    {
        return vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true);
    }

    /**
     * Solicita al usuario seleccionar un cliente filtrado por tipo.
     * @param tipoFiltrado 0=todos, 1=enviados, 2=pendientes
     * @return cliente seleccionado o null
     */
    public ClienteDTO askClienteFiltro(int tipoFiltrado)
    {
        int indexCliente;
        try {
            List<ClienteDTO> todosLosClientes = mClientes.getClientesDTO();
            List<ClienteDTO> clientesPedidos = new ArrayList<>();

            for (ClienteDTO cliente : todosLosClientes) {
                List<PedidoDTO> pedidosFiltrados = null;

                if (tipoFiltrado == 0)
                {
                    pedidosFiltrados = mPedidos.getPedidosDTOCliente(cliente);
                }
                else if (tipoFiltrado == 1)
                {
                    pedidosFiltrados = mPedidos.getPedidosPendientesEnviados(false, cliente);
                }
                else if (tipoFiltrado == 2)
                {
                    pedidosFiltrados = mPedidos.getPedidosPendientesEnviados(true, cliente);
                }

                if (pedidosFiltrados != null && !pedidosFiltrados.isEmpty()) {
                    clientesPedidos.add(cliente);
                }
            }

            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa("No hay clientes con pedidos para este filtro.", true);
                return null;
            }

            indexCliente = vPedidos.askClienteFiltro(tipoFiltrado, clientesPedidos);
            if (indexCliente == -99999) return null;

            ClienteDTO clienteSeleccionado = clientesPedidos.get(indexCliente - 1);
            if (clienteSeleccionado == null) {
                vPedidos.showMensajePausa("Error. El cliente no existe. Volviendo...", true);
                return null;
            }

            return clienteSeleccionado;
        }
        catch (Exception e)
        {
            vPedidos.showMensajePausa("Error al mostrar pedidos filtrados: " + e, true);
            return null;
        }
    }

    //*************************** Creación y cálculo de datos ***************************//

    /**
     * Verifica si un pedido ha sido enviado.
     * @param pedidoDTO el pedido a verificar
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

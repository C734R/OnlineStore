package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;
import javalinos.onlinestore.modelo.gestores.Local.ModeloArticulosLocal;
import javalinos.onlinestore.modelo.gestores.Local.ModeloClientesLocal;
import javalinos.onlinestore.modelo.gestores.Local.ModeloPedidosLocal;
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
    public void iniciar() {
        int opcion;
        try {
            while (true) {
                vPedidos.showCabecera();
                vPedidos.showMenu(2);
                opcion = vPedidos.askInt("Introduce una opción", 0, 6, false, false);
                switch (opcion) {
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
        catch (Exception e) {
            vPedidos.showMensaje("Error al realizar la operación: " + e.getMessage(), true);
        }
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un nuevo pedido al sistema.
     */
    public void addPedidos() throws Exception {
        vPedidos.showMensaje("******** Añadir PedidoDTO ********", true);
        List<ArticuloDTO> articulosDisponibles = new ArrayList<>();
        ClienteDTO ClienteDTO = askCliente(true);
        if(ClienteDTO == null) return;
        for (ArticuloDTO ArticuloDTO : mArticulos.getArticulos()){
            if (mArticulos.getStockArticulos().get(ArticuloDTO) > 0){
                articulosDisponibles.add(ArticuloDTO);
            }
        }
        if (!articulosDisponibles.isEmpty()) {
            vPedidos.showOptions(listToStr(articulosDisponibles), 0, true, true, true);
            int indexArticulo = vPedidos.askInt("Selecciona el articuloDTO que quiere comprar entre los disponibles", 1, articulosDisponibles.size(), true, true);
            if (indexArticulo == -99999) return;
            indexArticulo = indexArticulo - 1;
            ArticuloDTO ArticuloDTO = mArticulos.getArticuloIndex(indexArticulo);

            int stockComprado = vPedidos.askInt("Ingresa la cantidad que quiere comprar", 1, mArticulos.getStockArticulo(ArticuloDTO), true, true);
            if (stockComprado == -99999) return;

            PedidoDTO pedidoDTO = mPedidos.makePedido(ClienteDTO, ArticuloDTO, stockComprado, LocalDate.now(), precioEnvio);
            if(pedidoDTO == null) {
                vPedidos.showMensajePausa("Error. No se ha podido crear el pedidoDTO.", true);
                return;
            }
            mPedidos.addPedido(pedidoDTO);
            mArticulos.updateStockArticulo(ArticuloDTO, mArticulos.getStockArticulo(ArticuloDTO) - stockComprado);
            vPedidos.showMensajePausa("PedidoDTO añadido correctamente", true);
        }
        else vPedidos.showMensaje("No hay articulos disponibles para comprar en este momento", true);
    }

    /**
     * Elimina un pedido pendiente seleccionado por el usuario.
     */
    public void removePedidos() {
        List<PedidoDTO> pedidoDTOS = mPedidos.getPedidosPendientesEnviados(false, null);
        Map<ArticuloDTO,Integer> stockArticulos = mArticulos.getStockArticulos();
        if (pedidoDTOS.isEmpty()) {
            vPedidos.showMensajePausa("Error. No existen pedidoDTOS a eliminar.", true);
            return;
        }
        vPedidos.showListPedidos(pedidoDTOS,null, true);
        int numPedidoBorrar = vPedidos.askInt("Ingresa el numero de pedidoDTO que quieres borrar: ", 1, pedidoDTOS.size(), true, true);
        if(numPedidoBorrar == -99999) return;
        PedidoDTO pedidoDTO = pedidoDTOS.get(numPedidoBorrar-1);

        ArticuloDTO ArticuloDTO = pedidoDTO.getArticulo();
        mPedidos.removePedido(pedidoDTO);
        mArticulos.updateStockArticulo(ArticuloDTO, stockArticulos.get(ArticuloDTO) + pedidoDTO.getCantidad());
        vPedidos.showMensajePausa("El pedidoDTO ha sido eliminado correctamente.", true);
    }

    /**
     * Modifica un pedido pendiente cambiando cliente y/o cantidad.
     */
    public void updatePedido() throws Exception {
        vPedidos.showMensaje("******** Modificar PedidoDTO ********", true);
        List<PedidoDTO> pedidoDTOS = mPedidos.getPedidosPendientesEnviados(false, null);
        Map<ArticuloDTO,Integer> stockArticulos= mArticulos.getStockArticulos();
        if (pedidoDTOS.isEmpty()) {
            vPedidos.showMensajePausa("No hay pedidoDTOS disponibles para modificar.", true);
            return;
        }

        vPedidos.showListPedidos(pedidoDTOS, null, true);
        int seleccion = vPedidos.askInt("Selecciona el número del pedido a modificar", 1, pedidoDTOS.size(), true, true);
        if (seleccion == -99999) return;

        PedidoDTO pedidoDTOOld = pedidoDTOS.get(seleccion - 1);

        ClienteDTO nuevoClienteDTO = vPedidos.askClienteOpcional(mClientes.getClientes(), pedidoDTOOld.getCliente());
        if (nuevoClienteDTO == null) nuevoClienteDTO = pedidoDTOOld.getCliente();

        int maxStock = stockArticulos.get(pedidoDTOOld.getArticulo()) + pedidoDTOOld.getCantidad();
        Integer nuevaCantidad = vPedidos.askIntOpcional("Cantidad actual: " + pedidoDTOOld.getCantidad(), 1, maxStock);
        if (nuevaCantidad == null) nuevaCantidad = pedidoDTOOld.getCantidad();

        PedidoDTO pedidoDTONew = mPedidos.makePedido(nuevoClienteDTO, pedidoDTOOld.getArticulo(), nuevaCantidad, LocalDate.now(), precioEnvio);
        mPedidos.updatePedido(pedidoDTOOld, pedidoDTONew);

        int stockNew = maxStock - nuevaCantidad;
        mArticulos.updateStockArticulo(pedidoDTOOld.getArticulo(), stockNew);
        vPedidos.showMensajePausa("PedidoDTO actualizado correctamente.", true);
    }

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra todos los pedidos o filtra por clienteDTO si se proporciona.
     * @param ClienteDTO ClienteDTO por el que se quiere filtrar
     */
    public void showListPedidos(ClienteDTO ClienteDTO) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensajePausa("Aún no existen pedidoDTOS registrados.", true);
            return;
        }
        List<PedidoDTO> pedidoDTOS;
        if(ClienteDTO != null) {
            pedidoDTOS = mPedidos.getPedidosCliente(ClienteDTO);
            if (pedidoDTOS.isEmpty()) {
                vPedidos.showMensajePausa("No hay pedidoDTOS registrados para este clienteDTO.", true);
                return;
            }
        }
        else pedidoDTOS = mPedidos.getPedidos();
        vPedidos.showListPedidos(pedidoDTOS, ClienteDTO, false);
    }

    /**
     * Muestra pedidos pendientes o enviados según se indique.
     * @param ClienteDTO ClienteDTO por el que se quiere filtrar
     * @param enviado true para pedidos enviados, false para pendientes
     */
    public void showListPedidosPendientesEnviados(ClienteDTO ClienteDTO, boolean enviado) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidoDTOS registrados.", true);
            return;
        }

        List<PedidoDTO> pedidoDTOS = mPedidos.getPedidosPendientesEnviados(enviado, ClienteDTO);

        if (pedidoDTOS.isEmpty()) {
            if (enviado) {
                vPedidos.showMensaje("No hay pedidoDTOS enviados registrados" +
                        (ClienteDTO != null ? " para este clienteDTO." : "."), true);
            } else {
                vPedidos.showMensaje("No hay pedidoDTOS pendientes registrados" +
                        (ClienteDTO != null ? " para este clienteDTO." : "."), true);
            }
            return;
        }

        vPedidos.showListPedidos(pedidoDTOS, ClienteDTO, false);
    }

    /**
     * Muestra todos los pedidos en la vista.
     * @param ClienteDTO ClienteDTO por el que se filtran los pedidos
     */
    public void showPedidos(ClienteDTO ClienteDTO) {
        vPedidos.showPedidos(mPedidos.getPedidos(), ClienteDTO);
    }

    //*************************** Pedir datos ***************************//

    /**
     * Pide al usuario que seleccione o cree un cliente.
     * @param crear true si se permite crear un nuevo cliente
     * @return ClienteDTO seleccionado o creado
     */
    public ClienteDTO askCliente (boolean crear) throws Exception {
        int indexCliente;
        List<ClienteDTO> ClienteDTOS = cClientes.getListaClientes();
        vPedidos.showListClientes(ClienteDTOS);
        if(crear) indexCliente = vPedidos.askInt("Selecciona un clienteDTO existente o crea uno eligiendo " + (cClientes.getIndexCliente(true)+2), (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+2), true, true);
        else indexCliente = vPedidos.askInt("Selecciona un clienteDTO", (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+1), true, true);
        if (indexCliente == -99999) return null;

        if (crear && indexCliente == (cClientes.getIndexCliente(true)+2)) {
            cClientes.addCliente();
            return cClientes.getCliente(cClientes.getIndexCliente(true));
        }

        ClienteDTO ClienteDTO = cClientes.getCliente(indexCliente-1);
        if (ClienteDTO == null) {
            vPedidos.showMensajePausa("Error. El clienteDTO no existe. Volviendo...", true);
            return null;
        }
        return ClienteDTO;
    }

    /**
     * Pregunta si se desea filtrar por cliente y devuelve el seleccionado.
     * @param tipoFiltro tipo de filtrado: 0=todos, 1=enviados, 2=pendientes
     * @return ClienteDTO seleccionado o null
     */
    public ClienteDTO askFiltrarCliente(int tipoFiltro) {
        boolean filtrar = vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true);
        if (filtrar) return askClienteFiltro(tipoFiltro);
        return null;
    }

    /**
     * Solicita al usuario seleccionar un cliente filtrado por tipo.
     * @param tipoFiltrado 0=todos, 1=enviados, 2=pendientes
     * @return ClienteDTO seleccionado o null
     */
    public ClienteDTO askClienteFiltro(int tipoFiltrado) {
        int indexCliente;
        List<PedidoDTO> pedidoDTOS = mPedidos.getPedidos();
        List<ClienteDTO> clientesPedidos = new ArrayList<>();
        if (tipoFiltrado == 0) {
            for (PedidoDTO pedidoDTO : pedidoDTOS) {
                if (clientesPedidos.contains(pedidoDTO.getCliente())) continue;
                clientesPedidos.add(pedidoDTO.getCliente());
            }
            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa(("No hay pedidoDTOS registrados para ningún clienteDTO."), true);
                return null;
            }
        }
        else if (tipoFiltrado == 1) {
            for (PedidoDTO pedidoDTO : pedidoDTOS) {
                if (checkEnviado(pedidoDTO)) continue;
                if (clientesPedidos.contains(pedidoDTO.getCliente())) continue;
                clientesPedidos.add(pedidoDTO.getCliente());
            }
            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa(("No hay pedidoDTOS enviados registrados para ningún clienteDTO."), true);
                return null;
            }
        }
        else if (tipoFiltrado == 2) {
            for (PedidoDTO pedidoDTO : pedidoDTOS) {
                if (!checkEnviado(pedidoDTO)) continue;
                if (clientesPedidos.contains(pedidoDTO.getCliente())) continue;
                clientesPedidos.add(pedidoDTO.getCliente());
            }
            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa(("No hay pedidoDTOS pendientes registrados para ningún clienteDTO."), true);
                return null;
            }
        }
        vPedidos.showListClientesPedidos(clientesPedidos);
        indexCliente = vPedidos.askInt("Selecciona un clienteDTO", 1, clientesPedidos.size(), true, true);
        if (indexCliente == -99999) return null;
        ClienteDTO ClienteDTO = clientesPedidos.get(indexCliente-1);
        if (ClienteDTO == null) {
            vPedidos.showMensajePausa("Error. El clienteDTO no existe. Volviendo...", true);
            return null;
        }
        return ClienteDTO;
    }

    //*************************** Creación y cálculo de datos ***************************//

    /**
     * Verifica si un pedidoDTO ha sido enviado.
     * @param pedidoDTO el pedidoDTO a verificar
     * @return true si ha sido enviado, false en caso contrario
     */
    private boolean checkEnviado(PedidoDTO pedidoDTO) {
        return mPedidos.checkEnviado(pedidoDTO);
    }

    /**
     * Carga los pedidos desde un archivo o fuente externa.
     */
    public void loadPedidos() throws Exception {
            getModeloStore().getModeloPedidos().loadPedidos(mClientes.getClientes(), mArticulos.getArticulos());
    }

}

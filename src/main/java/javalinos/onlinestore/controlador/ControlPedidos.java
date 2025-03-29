package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloPedidos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;
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
    private ModeloArticulos mArticulos;
    private ModeloClientes mClientes;
    private ModeloPedidos mPedidos;
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
        while(true) {
            vPedidos.showCabecera();
            vPedidos.showMenu(2);
            opcion = vPedidos.askInt("Introduce una opción", 0, 6,false, false);
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

    //*************************** CRUD ***************************//

    /**
     * Añade un nuevo pedido al sistema.
     */
    public void addPedidos() {
        vPedidos.showMensaje("******** Añadir Pedido ********", true);
        List<Articulo> articulosDisponibles = new ArrayList<>();
        Cliente cliente = askCliente(true);
        if(cliente == null) return;
        for (Articulo articulo : mArticulos.getArticulos()){
            if (mArticulos.getStockArticulos().get(articulo) > 0){
                articulosDisponibles.add(articulo);
            }
        }
        if (!articulosDisponibles.isEmpty()) {
            vPedidos.showOptions(listToStr(articulosDisponibles), 0, true, true, true);
            int indexArticulo = vPedidos.askInt("Selecciona el articulo que quiere comprar entre los disponibles", 1, articulosDisponibles.size(), true, true);
            if (indexArticulo == -99999) return;
            indexArticulo = indexArticulo - 1;
            Articulo articulo = mArticulos.getArticuloIndex(indexArticulo);

            int stockComprado = vPedidos.askInt("Ingresa la cantidad que quiere comprar", 1, mArticulos.getStockArticulo(articulo), true, true);
            if (stockComprado == -99999) return;

            Pedido pedido = mPedidos.makePedido(cliente, articulo, stockComprado, LocalDate.now(), precioEnvio);
            if(pedido == null) {
                vPedidos.showMensajePausa("Error. No se ha podido crear el pedido.", true);
                return;
            }
            mPedidos.addPedido(pedido);
            mArticulos.updateStockArticulo(articulo, mArticulos.getStockArticulo(articulo) - stockComprado);
            vPedidos.showMensajePausa("Pedido añadido correctamente", true);
        }
        else vPedidos.showMensaje("No hay articulos disponibles para comprar en este momento", true);
    }

    /**
     * Elimina un pedido pendiente seleccionado por el usuario.
     */
    public void removePedidos() {
        List<Pedido> pedidos = mPedidos.getPedidosPendientesEnviados(false, null);
        Map<Articulo,Integer> stockArticulos = mArticulos.getStockArticulos();
        if (pedidos.isEmpty()) {
            vPedidos.showMensajePausa("Error. No existen pedidos a eliminar.", true);
            return;
        }
        vPedidos.showListPedidos(pedidos,null, true);
        int numPedidoBorrar = vPedidos.askInt("Ingresa el numero de pedido que quieres borrar: ", 1, pedidos.size(), true, true);
        if(numPedidoBorrar == -99999) return;
        Pedido pedido = pedidos.get(numPedidoBorrar-1);

        Articulo articulo = pedido.getArticulo();
        mPedidos.removePedido(pedido);
        mArticulos.updateStockArticulo(articulo, stockArticulos.get(articulo) + pedido.getCantidad());
        vPedidos.showMensajePausa("El pedido ha sido eliminado correctamente.", true);
    }

    /**
     * Modifica un pedido pendiente cambiando cliente y/o cantidad.
     */
    public void updatePedido() {
        vPedidos.showMensaje("******** Modificar Pedido ********", true);
        List<Pedido> pedidos = mPedidos.getPedidosPendientesEnviados(false, null);
        Map<Articulo,Integer> stockArticulos= mArticulos.getStockArticulos();
        if (pedidos.isEmpty()) {
            vPedidos.showMensajePausa("No hay pedidos disponibles para modificar.", true);
            return;
        }

        vPedidos.showListPedidos(pedidos, null, true);
        int seleccion = vPedidos.askInt("Selecciona el número del pedido a modificar", 1, pedidos.size(), true, true);
        if (seleccion == -99999) return;

        Pedido pedidoOld = pedidos.get(seleccion - 1);

        Cliente nuevoCliente = vPedidos.askClienteOpcional(mClientes.getClientes(), pedidoOld.getCliente());
        if (nuevoCliente == null) nuevoCliente = pedidoOld.getCliente();

        int maxStock = stockArticulos.get(pedidoOld.getArticulo()) + pedidoOld.getCantidad();
        Integer nuevaCantidad = vPedidos.askIntOpcional("Cantidad actual: " + pedidoOld.getCantidad(), 1, maxStock);
        if (nuevaCantidad == null) nuevaCantidad = pedidoOld.getCantidad();

        Pedido pedidoNew = mPedidos.makePedido(nuevoCliente, pedidoOld.getArticulo(), nuevaCantidad, LocalDate.now(), precioEnvio);
        mPedidos.updatePedido(pedidoOld, pedidoNew);

        int stockNew = maxStock - nuevaCantidad;
        mArticulos.updateStockArticulo(pedidoOld.getArticulo(), stockNew);
        vPedidos.showMensajePausa("Pedido actualizado correctamente.", true);
    }

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra todos los pedidos o filtra por cliente si se proporciona.
     * @param cliente Cliente por el que se quiere filtrar
     */
    public void showListPedidos(Cliente cliente) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensajePausa("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos;
        if(cliente != null) {
            pedidos = mPedidos.getPedidosCliente(cliente);
            if (pedidos.isEmpty()) {
                vPedidos.showMensajePausa("No hay pedidos registrados para este cliente.", true);
                return;
            }
        }
        else pedidos = mPedidos.getPedidos();
        vPedidos.showListPedidos(pedidos, cliente, false);
    }

    /**
     * Muestra pedidos pendientes o enviados según se indique.
     * @param cliente Cliente por el que se quiere filtrar
     * @param enviado true para pedidos enviados, false para pendientes
     */
    public void showListPedidosPendientesEnviados(Cliente cliente, boolean enviado) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }

        List<Pedido> pedidos = mPedidos.getPedidosPendientesEnviados(enviado, cliente);

        if (pedidos.isEmpty()) {
            if (enviado) {
                vPedidos.showMensaje("No hay pedidos enviados registrados" +
                        (cliente != null ? " para este cliente." : "."), true);
            } else {
                vPedidos.showMensaje("No hay pedidos pendientes registrados" +
                        (cliente != null ? " para este cliente." : "."), true);
            }
            return;
        }

        vPedidos.showListPedidos(pedidos, cliente, false);
    }

    /**
     * Muestra todos los pedidos en la vista.
     * @param cliente Cliente por el que se filtran los pedidos
     */
    public void showPedidos(Cliente cliente) {
        vPedidos.showPedidos(mPedidos.getPedidos(), cliente);
    }

    //*************************** Pedir datos ***************************//

    /**
     * Pide al usuario que seleccione o cree un cliente.
     * @param crear true si se permite crear un nuevo cliente
     * @return Cliente seleccionado o creado
     */
    public Cliente askCliente (boolean crear) {
        int indexCliente;
        List<Cliente> clientes = cClientes.getListaClientes();
        vPedidos.showListClientes(clientes);
        if(crear) indexCliente = vPedidos.askInt("Selecciona un cliente existente o crea uno eligiendo " + (cClientes.getIndexCliente(true)+2), (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+2), true, true);
        else indexCliente = vPedidos.askInt("Selecciona un cliente", (cClientes.getIndexCliente(false)+1), (cClientes.getIndexCliente(true)+1), true, true);
        if (indexCliente == -99999) return null;

        if (crear && indexCliente == (cClientes.getIndexCliente(true)+2)) {
            cClientes.addCliente();
            return cClientes.getCliente(cClientes.getIndexCliente(true));
        }

        Cliente cliente = cClientes.getCliente(indexCliente-1);
        if (cliente == null) {
            vPedidos.showMensajePausa("Error. El cliente no existe. Volviendo...", true);
            return null;
        }
        return cliente;
    }

    /**
     * Pregunta si se desea filtrar por cliente y devuelve el seleccionado.
     * @param tipoFiltro tipo de filtrado: 0=todos, 1=enviados, 2=pendientes
     * @return Cliente seleccionado o null
     */
    public Cliente askFiltrarCliente(int tipoFiltro) {
        boolean filtrar = vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true);
        if (filtrar) return askClienteFiltro(tipoFiltro);
        return null;
    }

    /**
     * Solicita al usuario seleccionar un cliente filtrado por tipo.
     * @param tipoFiltrado 0=todos, 1=enviados, 2=pendientes
     * @return Cliente seleccionado o null
     */
    public Cliente askClienteFiltro(int tipoFiltrado) {
        int indexCliente;
        List<Pedido> pedidos = mPedidos.getPedidos();
        List<Cliente> clientesPedidos = new ArrayList<>();
        if (tipoFiltrado == 0) {
            for (Pedido pedido : pedidos) {
                if (clientesPedidos.contains(pedido.getCliente())) continue;
                clientesPedidos.add(pedido.getCliente());
            }
            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa(("No hay pedidos registrados para ningún cliente."), true);
                return null;
            }
        }
        else if (tipoFiltrado == 1) {
            for (Pedido pedido : pedidos) {
                if (checkEnviado(pedido)) continue;
                if (clientesPedidos.contains(pedido.getCliente())) continue;
                clientesPedidos.add(pedido.getCliente());
            }
            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa(("No hay pedidos enviados registrados para ningún cliente."), true);
                return null;
            }
        }
        else if (tipoFiltrado == 2) {
            for (Pedido pedido : pedidos) {
                if (!checkEnviado(pedido)) continue;
                if (clientesPedidos.contains(pedido.getCliente())) continue;
                clientesPedidos.add(pedido.getCliente());
            }
            if (clientesPedidos.isEmpty()) {
                vPedidos.showMensajePausa(("No hay pedidos pendientes registrados para ningún cliente."), true);
                return null;
            }
        }
        vPedidos.showListClientesPedidos(clientesPedidos);
        indexCliente = vPedidos.askInt("Selecciona un cliente", 1, clientesPedidos.size(), true, true);
        if (indexCliente == -99999) return null;
        Cliente cliente = clientesPedidos.get(indexCliente-1);
        if (cliente == null) {
            vPedidos.showMensajePausa("Error. El cliente no existe. Volviendo...", true);
            return null;
        }
        return cliente;
    }

    //*************************** Creación y cálculo de datos ***************************//

    /**
     * Verifica si un pedido ha sido enviado.
     * @param pedido el pedido a verificar
     * @return true si ha sido enviado, false en caso contrario
     */
    private boolean checkEnviado(Pedido pedido) {
        return mPedidos.checkEnviado(pedido);
    }

    /**
     * Carga los pedidos desde un archivo o fuente externa.
     * @param configuracion configuración de carga
     * @return true si se cargaron correctamente, false si falló
     */
    public boolean loadPedidos(int configuracion) {
        if (configuracion == 0) {
            return this.getModeloStore().getModeloPedidos().loadPedidos(configuracion, mClientes.getClientes(), mArticulos.getArticulos());
        }
        else {
            return false;
        }
    }

}

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
import java.util.ArrayList;
import java.util.List;

import static javalinos.onlinestore.OnlineStore.cClientes;
import static javalinos.onlinestore.utils.Utilidades.listToStr;

public class ControlPedidos extends ControlBase{

    private VistaPedidos vPedidos;
    private ModeloArticulos mArticulos;
    private ModeloClientes mClientes;
    private ModeloPedidos mPedidos;

    /**
     * Constructor de ControlPedidos
     *
     * @param mStore el ModeloStore que se va a utilizar
     *
     * @param vPedidos la vista que se va a utilizar
     */
    public ControlPedidos(ModeloStore mStore, VistaPedidos vPedidos) {
        super.setModeloStore(mStore);
        this.mArticulos = mStore.getModeloArticulos();
        this.mClientes = mStore.getModeloClientes();
        this.mPedidos = mStore.getModeloPedidos();
        this.vPedidos = vPedidos;
    }

    public ControlPedidos() {
        super();
        vPedidos = null;
    }

    //*************************** Getters & Setters ***************************//

    public VistaPedidos getVistaPedidos() {
        return vPedidos;
    }

    public void setVistaPedidos(VistaPedidos vPedidos) {
        this.vPedidos = vPedidos;
    }

    //*************************** Menu gestión pedidos ***************************//

    /**
     * Inicia el menú de gestión de pedidos
     */
    public void iniciar() {
        int opcion;
        while(true) {
            vPedidos.showCabecera();
            vPedidos.showMenu(2);
            opcion = vPedidos.askInt("Introduce una opción", 0, 5,false, false);
            switch (opcion) {
                case 1:
                    addPedidos();
                    break;
                case 2:
                    removePedidos();
                    break;
                case 3:
                    showListPedidos(askFiltrarCliente());
                    break;
                case 4:
                    showListPedidosPendientes(askFiltrarCliente());
                    break;
                case 5:
                    showListPedidosEnviados(askFiltrarCliente());
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
     * Metodo para añadir pedidos
     */
    public void addPedidos() {
        vPedidos.showMensaje("******** Añadir Pedido ********", true);
        List<Articulo> articulosDisponibles = new ArrayList<>();
        Cliente cliente = askCliente(true);

        for (Articulo articulo : mArticulos.getArticulos()){
            if (articulo.getStock() > 0){
                articulosDisponibles.add(articulo);
            }
        }

        if (!articulosDisponibles.isEmpty()) {
            vPedidos.showOptions(listToStr(articulosDisponibles), 3, true, true);
            int indexArticulo = vPedidos.askInt("Selecciona el articulo que quiere comprar entre los disponibles", 0, articulosDisponibles.size(), true, true);
            if (indexArticulo == 0) return;
            indexArticulo = indexArticulo - 1;
            Articulo articulo = mArticulos.getArticulos().get(indexArticulo);

            Integer stockComprado = vPedidos.askInt("Ingresa la cantidad que quiere comprar", 1, articulo.getStock(), true, true);

            LocalDate fechaPedido = vPedidos.askFecha("del pedido");

            float precioEnvio = 5f;

            float precioFinal = calcPedido(articulo, stockComprado, precioEnvio);

            Pedido pedido = mPedidos.makePedido(cliente, articulo, stockComprado, fechaPedido, precioEnvio, precioFinal);

            if (mPedidos.makePedido(cliente, articulo, stockComprado, fechaPedido, precioEnvio, precioFinal) != null) {
                vPedidos.showMensaje("Pedido añadido correctamente", true);
                mPedidos.addPedido(pedido);
            } else {
                vPedidos.showMensaje("Error al añadir el pedido.. Saliendo", true);
            }
        }
        else{
            vPedidos.showMensaje("No hay articulos disponibles para comprar en este momento", true);
        }
    }

    /**
     * Metodo para eliminar pedidos
     */
    public void removePedidos() {
        List<Pedido> pedidos = mPedidos.getPedidos();
        if (!pedidos.isEmpty()) {
            vPedidos.showMensajePausa("Error. No existen pedidos a eliminar.", true);
        }
        showPedidos( null);
        // Pedimos al usuario que introduzca el número del pedido que desea borrar.
        int numPedidoBorrar = vPedidos.askInt("Ingresa el numero de pedido que quieres borrar: ", 1, mPedidos.getLastNumPedido(), true, true);

        // Creamos un booleano por si no se encuentra el número de pedido escrito por el usuario.
        boolean pedidoEncontrado = false;

        // Obtener pedido a eliminar
        mPedidos.removePedido(mPedidos.getPedidoNumero(numPedidoBorrar));
        vPedidos.showMensaje("El pedido ha sido eliminado correctamente.", true);
    }

    //*************************** Obtener listas ***************************//

    /**
     * Metodo para listar pedidos filtrando o no por cliente
     *
     * @param cliente El cliente por el que se quiere filtrar
     * @return Lista con los pedidos deseados
     */
    public List<Pedido> listPedidos(Cliente cliente) {
        List<Pedido> pedidos = listPedidosCheck();
        if (cliente == null) return pedidos;
        List<Pedido> pedidosCliente = new ArrayList<>();
        for (Pedido pedido : pedidos){
            if (pedido.getCliente().equals(cliente)) pedidosCliente.add(pedido);
        }
        return pedidosCliente;
    }

    /**
     * Listar los pedidos pendientes de envio
     *
     * @param cliente Si se quiere filtrar por cliente
     * @return Lista con los pedidos deseados
     */
    public List<Pedido> listPedidosPendientes(Cliente cliente) {
        List<Pedido> pedidos = listPedidos(cliente);
        if (pedidos.isEmpty()) return pedidos;
        List<Pedido> pedidosPendientesCliente = new ArrayList<>();
        for(Pedido pedido : pedidos){
            if (!checkEnviado(pedido)) pedidosPendientesCliente.add(pedido);
        }
        return pedidosPendientesCliente;
    }

    /**
     * Listar los pedidos enviados
     *
     * @param cliente Si se quiere filtrar por cliente
     * @return Lista con los pedidos deseados
     */
    public List<Pedido> listPedidosEnviados(Cliente cliente) {
        List<Pedido> pedidos = listPedidosCheck();
        if (pedidos == null) return pedidos;
        List<Pedido> pedidosEnviadosCliente = new ArrayList<>();
        for(Pedido pedido : pedidos){
            if (checkEnviado(pedido)) pedidosEnviadosCliente.add(pedido);
        }
        return pedidosEnviadosCliente;
    }

    /**
     * Comprobar si existen pedidos
     *
     * @return Lista con los pedidos existentes
     */
    public List<Pedido> listPedidosCheck() {
        List<Pedido> pedidos = mPedidos.getPedidos();
        if (pedidos.isEmpty()){
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return null;
        }
        return pedidos;
    }

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra por pantalla los pedidos por cliente
     *
     * @param cliente El cliente por el que se filtran
     */
    public void showListPedidos(Cliente cliente) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos = null;
        if(cliente != null) {
            if (!mClientes.getClientes().isEmpty()) {
                vPedidos.showOptions(listToStr(this.mClientes.getClientes()),3 , true, true);
                int indexCliente = vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 0, mClientes.sizeClientes(), true, true);
                if (indexCliente == 0) return;
                indexCliente = indexCliente - 1;
                Cliente ncliente = mClientes.getClientes().get(indexCliente);
                pedidos = mPedidos.getPedidosCliente(ncliente);
                if (pedidos.isEmpty()) {
                    vPedidos.showMensaje("No hay pedidos registrados para este cliente.", true);
                }
            }
            else vPedidos.showMensajePausa("Error. No existen clientes registrados.", true);
        }
        else {
            pedidos = mPedidos.getPedidos();
        }
        vPedidos.showListPedidos(pedidos,cliente);
        vPedidos.showMensajePausa("", true);
    }

    /**
     * Muestra por pantalla los pedidos pendientes de enviar
     *
     * @param cliente El cliente por el que se quiere filtrar
     */
    public void showListPedidosPendientes(Cliente cliente) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos = null;
        if(cliente != null) {
            if (!mClientes.getClientes().isEmpty()) {
                vPedidos.showOptions(listToStr(this.mClientes.getClientes()),3 , true, true);
                int indexCliente = vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 0, mClientes.sizeClientes(), true, true);
                if (indexCliente == 0) return;
                indexCliente = indexCliente - 1;
                Cliente ncliente = mClientes.getClientes().get(indexCliente);
                pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), false, ncliente);
                if (pedidos.isEmpty()) {
                    vPedidos.showMensaje("No hay pedidos pendientes registrados para este cliente.", true);
                }
            }
            else vPedidos.showMensajePausa("Error. No existen clientes registrados.", true);
        }
        else {
            pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), false, null);
        }
        vPedidos.showListPedidos(pedidos,cliente);
        vPedidos.showMensajePausa("", true);
    }

    /**
     * Muestra por pantalla los pedidos enviados
     *
     * @param cliente El cliente por el que se quiere filtrar
     */
    public void showListPedidosEnviados(Cliente cliente) {
        if (mPedidos.getPedidos().isEmpty()) {
            vPedidos.showMensaje("Aún no existen pedidos registrados.", true);
            return;
        }
        List<Pedido> pedidos = null;
        if(cliente != null) {
            if (!mClientes.getClientes().isEmpty()) {
                vPedidos.showOptions(listToStr(this.mClientes.getClientes()), 3, true, true);
                int indexCliente = vPedidos.askInt("Selecciona el cliente del que deseas mostrar los pedidos", 0, mClientes.sizeClientes(), true, true);
                if (indexCliente == 0) return;
                indexCliente = indexCliente - 1;
                Cliente ncliente = mClientes.getClientes().get(indexCliente);
                pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), true, ncliente);
                if (pedidos.isEmpty()) {
                    vPedidos.showMensaje("No hay pedidos enviados registrados para este cliente.", true);
                }
            }
            else vPedidos.showMensajePausa("Error. No existen clientes registrados.", true);
        }
        else {
            pedidos = mPedidos.getPedidosPendientesEnviados(LocalDate.now(), true, null);
        }
        vPedidos.showListPedidos(pedidos,cliente);
        vPedidos.showMensajePausa("", true);
    }

    public void showPedidosEnviados(Cliente cliente) {
        List<Pedido> pedidos = listPedidosEnviados(cliente);
        if (listPedidosEnviados(cliente).isEmpty()) {
            vPedidos.showMensajePausa(("No hay pedidos enviados registrados."), true);
            return;
        }
        vPedidos.showPedidosEnviados(pedidos, cliente);
    }

    public void showPedidosPendientes(Cliente cliente) {
        List<Pedido> pedidos = listPedidosPendientes(cliente);
        if (listPedidosPendientes(cliente).isEmpty()) {
            vPedidos.showMensajePausa(("No hay pedidos enviados registrados."), true);
            return;
        }
        vPedidos.showPedidosPendientes(pedidos,cliente);
    }

    /**
     * Muestra todos los pedidos
     *
     * @param cliente El cliente por el que se quiere filtrar
     */
    public void showPedidos(Cliente cliente) {
        vPedidos.showPedidos(mPedidos.getPedidos(), cliente);
    }

    //*************************** Pedir datos ***************************//

    /**
     * Pide al usuario que introduzca un cliente
     *
     * @param crear Si se quiere crear un cliente nuevo
     * @return Cliente seleccionado
     */
    public Cliente askCliente (boolean crear) {
        int numSocio;
        List<Cliente> clientes = cClientes.getListaClientes();
        vPedidos.showListClientes(clientes);
        if(crear) numSocio = vPedidos.askInt("Selecciona un cliente existente o crea uno eligiendo " + (cClientes.getIndexCliente(true)+1), cClientes.getIndexCliente(false), (cClientes.getIndexCliente(true)+1), true, true);
        else numSocio = vPedidos.askInt("Selecciona un cliente", cClientes.getIndexCliente(false), cClientes.getIndexCliente(true), true, true);
        if (numSocio == -99999) return null;
        if (numSocio == (cClientes.getIndexCliente(true)+1)) {
            cClientes.addCliente();
        }
        Cliente cliente = cClientes.getCliente(numSocio);
        if (cliente == null) {
            vPedidos.showMensajePausa("Error. El cliente no existe. Volviendo...", true);
            return null;
        }
        return cliente;
    }

    /**
     * Metodo para filtrar por cliente
     *
     * @return Cliente por el que se quiere filtrar o null
     */
    public Cliente askFiltrarCliente() {
        boolean filtrar = vPedidos.askBoolean("¿Deseas filtrar por usuario?", true, true);
        if (filtrar) return askCliente(false);
        return null;
    }

    //*************************** Creación y cálculo de datos ***************************//

    /**
     * Calcular precio del pedido
     *
     * @param articulo artículo comprado
     * @param stockComprado Stock comprado
     * @param precioEnvio Precio del envio del pedido
     * @return Float con el precio del pedido
     */
    private float calcPedido(Articulo articulo, int stockComprado, float precioEnvio) {
        return articulo.getPrecio() * stockComprado + (precioEnvio * (float) Math.pow(1.1f, stockComprado));
    }

    /**
     * Comprueba si se ha enviado un pedido
     *
     * @param pedido el pedido que se va a comprobar
     * @return Boolean con si se ha enviado o no
     */
    private boolean checkEnviado(Pedido pedido) {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaEnvio = pedido.getFechahora().plusDays(pedido.getDiasPreparacion());
        return fechaHoy.isAfter(fechaEnvio);
    }

    /**
     * Carga los pedidos en el programa
     *
     * @param configuracion
     * @return Boolean con si se cargan bien o no
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

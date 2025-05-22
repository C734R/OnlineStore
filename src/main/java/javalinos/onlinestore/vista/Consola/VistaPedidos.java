package javalinos.onlinestore.vista.Consola;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static javalinos.onlinestore.utils.Utilidades.listToStr;
/**
 * Vista encargada de mostrar y gestionar la interacción con el usuario para los pedidos.
 * - Entidades relacionadas: PedidoDTO, ClienteDTO, ArticuloDTO
 */
public class VistaPedidos extends VistaBase implements IVistaPedidos {
    /**
     * Constructor por defecto. Inicializa la cabecera y el menú principal de pedidos.
     */
    public VistaPedidos() {
        String cabecera = """
                *********************************************
                **           Gestión de Pedidos            **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList(
                "Añadir pedido",
                "Eliminar pedido",
                "Editar pedido",
                "Listar todos los pedidos",
                "Listar pedidos pendientes",
                "Listar pedidos enviados"));
        super.setListaMenu(listaMenu);
    }
    /**
     * Constructor alternativo con parámetros personalizados.
     * @param cabecera título de la vista.
     * @param listaMenu opciones del menú.
     */
    public VistaPedidos(String cabecera, List<String> listaMenu) {
        super(cabecera, listaMenu);
    }

    /**
     * Muestra una lista con todos los pedidos o filtrando por clienteDTO.
     * @param pedidosDTO Lista de pedidos.
     * @param clienteDTO cliente para filtrar (puede ser null).
     * @param opcion Opción extra para formato de salida.
     */
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {
        if (clienteDTO == null) {
            showMensaje("******************* LISTA DE PEDIDOS *******************", true);
            showOptions(listToStr(pedidosDTO), 0, true, false, opcion);
            showMensaje("********************************************************", true);
        }
        else {
            showMensaje("******************* LISTA DE PEDIDOS DEL CLIENTE  " + pedidosDTO.getFirst().getCliente().getNombre() + " *******************", true);
            showOptions(listToStr(pedidosDTO), 0, true, false, opcion);
            showMensaje("********************************************************", true);
        }
    }

    /**
     * Muestra una lista de clientes.
     * @param clientesDTO Lista con los clientes
     */
    public void showListClientes(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES", true, true, false);
    }
    /**
     * Muestra una lista con todos los clientes.
     * @param clientesDTO Lista de clientes.
     */
    public void showListClientesPedidos(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES CON PEDIDOS", true, true, false);
    }
    /**
     * Muestra una lista de clientes que tienen al menos un pedido.
     * @param clientesDTO Lista de clientes con pedidos.
     */
    public void showListClientesPedidosPendientes(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES CON PEDIDOS PENDIENTES", true, true, false);
    }
    /**
     * Muestra una lista de clientes con pedidos enviados.
     * @param clientesDTO Lista de clientes con pedidos enviados.
     */
    public void showListClientesPedidosEnviados(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES CON PEDIDOS ENVIADOS", true, true, false);
    }
    /**
     * Muestra la lista de artículos disponibles.
     * @param articulosDTO Lista de artículos.
     */
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        showListGenerica(articulosDTO, "LISTA DE ARTICULOS", true, true, false);
    }

    /**
     * Muestra todos los pedidos, filtrando si se indica clienteDTO.
     * @param pedidosDTO Lista de pedidos.
     * @param clienteDTO clientes (puede ser null).
     */
    public void showPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        if (clienteDTO == null) showListGenerica(pedidosDTO, "PEDIDOS", true, true, false);
        else showListGenerica(pedidosDTO, "PEDIDOS DEL CLIENTE " + clienteDTO.getNombre(), true, true, false);
    }

    /**
     * Muestra los pedidos pendientes, filtrando si se indica cliente.
     * @param pedidosDTO Lista de pedidos pendientes.
     * @param clienteDTO cliente (puede ser null).
     */
    public void showListPedidosPendientes(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        if (clienteDTO == null) showListGenerica(pedidosDTO, "PEDIDOS PENDIENTES", true, true, false);
        else showListGenerica(pedidosDTO, "PEDIDOS PENDIENTES DEL CLIENTE " + clienteDTO.getNombre(), true, true, false);
    }

    /**
     * Muestra los pedidos enviados, filtrando si se indica cliente.
     * @param pedidosDTO Lista de pedidos enviados.
     * @param clienteDTO cliente (puede ser null).
     */
    public void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        if (clienteDTO == null) showListGenerica(pedidosDTO, "PEDIDOS ENVIADOS", true, true, false);
        else showListGenerica(pedidosDTO, "PEDIDOS ENVIADOS DEL CLIENTE " + clienteDTO.getNombre(), true, true, false);
    }

    /**
     * Solicita al usuario seleccionar un cliente o mantener el actual (ENTER).
     * @param clientesDTO Lista de clientes disponibles.
     * @param clienteDTOActual cliente actualmente asociado.
     * @return cliente seleccionado o null si se mantiene el actual.
     */
    public ClienteDTO askClienteOpcional(List<ClienteDTO> clientesDTO, ClienteDTO clienteDTOActual) {
        if (clientesDTO.isEmpty()) {
            showMensajePausa("No hay clientes registrados.", true);
            return null;
        }

        showListClientes(clientesDTO);
        showMensaje("Cliente actual: " + clienteDTOActual.getNombre() + " (" + clienteDTOActual.getNif() + ")", true);
        showMensaje("Selecciona un nuevo cliente o pulsa ENTER para mantener el actual:", true);

        Scanner scanner = new Scanner(System.in);
        String index = scanner.nextLine();

        if(index.isEmpty()) return null;
        try {
            int valorIndex = Integer.parseInt(index);
            if (valorIndex < 1 || valorIndex > clientesDTO.size()) {
                showMensajePausa("Índice fuera de rango. Se mantendrá el cliente actual.", true);
                return null;
            }
            return clientesDTO.get(valorIndex - 1);
        } catch (NumberFormatException e) {
            showMensajePausa("Entrada no válida. Se mantendrá el cliente actual.", true);
            return null;
        }
    }

    @Override
    public int askPedidoModificar(List<PedidoDTO> pedidosDTO) {
        showListPedidos(pedidosDTO, null, true);
        return askInt("Selecciona el número del pedido a modificar", 1, pedidosDTO.size(), true, true, true);
    }

    @Override
    public int askClienteFiltro(int tipoFiltrado, List<ClienteDTO> clientesPedidos) {
        if(tipoFiltrado == 0) showListClientesPedidos(clientesPedidos);
        else if (tipoFiltrado == 1) showListClientesPedidosPendientes(clientesPedidos);
        else showListClientesPedidosEnviados(clientesPedidos);
        return askInt("Selecciona un cliente", 1, clientesPedidos.size(), true, true, true);
    }

    @Override
    public int askPedidoRemove(List<PedidoDTO> pedidos) {
        showListPedidos(pedidos,null, true);
        return askInt("Ingresa el numero de pedido que quieres borrar: ", 1, pedidos.size(), true, true, true);
    }
}

package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static javalinos.onlinestore.utils.Utilidades.listToStr;
/**
 * Vista encargada de mostrar y gestionar la interacción con el usuario para los pedidoDTOS.
 * - Entidades relacionadas: PedidoDTO, ClienteDTO, ArticuloDTO
 */
public class VistaPedidos extends VistaBase {
    /**
     * Constructor por defecto. Inicializa la cabecera y el menú principal de pedidoDTOS.
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
                "Listar todos los pedidoDTOS",
                "Listar pedidoDTOS pendientes",
                "Listar pedidoDTOS enviados"));
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
     * Muestra una lista con todos los pedidoDTOS o filtrando por clienteDTO.
     * @param pedidoDTOS Lista de pedidoDTOS.
     * @param ClienteDTO ClienteDTO para filtrar (puede ser null).
     * @param opcion Opción extra para formato de salida.
     */
    public void showListPedidos(List<PedidoDTO> pedidoDTOS, ClienteDTO ClienteDTO, boolean opcion) {
        if (ClienteDTO == null) {
            showMensaje("******************* LISTA DE PEDIDOS *******************", true);
            showOptions(listToStr(pedidoDTOS), 0, true, false, opcion);
            showMensaje("********************************************************", true);
        }
        else {
            showMensaje("******************* LISTA DE PEDIDOS DEL CLIENTE  " + pedidoDTOS.getFirst().getCliente().getNombre() + " *******************", true);
            showOptions(listToStr(pedidoDTOS), 0, true, false, opcion);
            showMensaje("********************************************************", true);
        }
    }

    /**
     * Muestra una lista de clienteDTOS.
     *
     * @param ClienteDTOS Lista con los clienteDTOS
     */
    public void showListClientes(List<ClienteDTO> ClienteDTOS) {
        showListGenerica(ClienteDTOS, "LISTA DE CLIENTES", true, true);
    }
    /**
     * Muestra una lista con todos los clienteDTOS.
     * @param ClienteDTOS Lista de clienteDTOS.
     */
    public void showListClientesPedidos(List<ClienteDTO> ClienteDTOS) {
        showListGenerica(ClienteDTOS, "LISTA DE CLIENTES CON PEDIDOS", true, true);
    }
    /**
     * Muestra una lista de clienteDTOS que tienen al menos un pedido.
     * @param ClienteDTOS Lista de clienteDTOS con pedidoDTOS.
     */
    public void showListClientesPedidosPendientes(List<ClienteDTO> ClienteDTOS) {
        showListGenerica(ClienteDTOS, "LISTA DE CLIENTES CON PEDIDOS PENDIENTES", true, true);
    }
    /**
     * Muestra una lista de clienteDTOS con pedidoDTOS enviados.
     * @param ClienteDTOS Lista de clienteDTOS con pedidoDTOS enviados.
     */
    public void showListClientesPedidosEnviados(List<ClienteDTO> ClienteDTOS) {
        showListGenerica(ClienteDTOS, "LISTA DE CLIENTES CON ENVIADOS", true, true);
    }
    /**
     * Muestra la lista de artículos disponibles.
     * @param ArticuloDTOS Lista de artículos.
     */
    public void showListArticulos(List<ArticuloDTO> ArticuloDTOS) {
        showListGenerica(ArticuloDTOS, "LISTA DE ARTICULOS", true, false);
    }

    /**
     * Muestra todos los pedidoDTOS, filtrando si se indica clienteDTO.
     * @param pedidoDTOS Lista de pedidoDTOS.
     * @param ClienteDTO ClienteDTO (puede ser null).
     */
    public void showPedidos(List<PedidoDTO> pedidoDTOS, ClienteDTO ClienteDTO) {
        if (ClienteDTO == null) showListGenerica(pedidoDTOS, "PEDIDOS", true, true);
        else showListGenerica(pedidoDTOS, "PEDIDOS DEL CLIENTE " + ClienteDTO.getNombre(), true, true);
    }

    /**
     * Muestra los pedidoDTOS pendientes, filtrando si se indica clienteDTO.
     * @param pedidoDTOS Lista de pedidoDTOS pendientes.
     * @param ClienteDTO ClienteDTO (puede ser null).
     */
    public void showPedidosPendientes(List<PedidoDTO> pedidoDTOS, ClienteDTO ClienteDTO) {
        if (ClienteDTO == null) showListGenerica(pedidoDTOS, "PEDIDOS PENDIENTES", true, true);
        else showListGenerica(pedidoDTOS, "PEDIDOS PENDIENTES DEL CLIENTE " + ClienteDTO.getNombre(), true, true);
    }

    /**
     * Muestra los pedidoDTOS enviados, filtrando si se indica clienteDTO.
     * @param pedidoDTOS Lista de pedidoDTOS enviados.
     * @param ClienteDTO ClienteDTO (puede ser null).
     */
    public void showPedidosEnviados(List<PedidoDTO> pedidoDTOS, ClienteDTO ClienteDTO) {
        if (ClienteDTO == null) showListGenerica(pedidoDTOS, "PEDIDOS ENVIADOS", true, true);
        else showListGenerica(pedidoDTOS, "PEDIDOS ENVIADOS DEL CLIENTE " + ClienteDTO.getNombre(), true, true);
    }

    /**
     * Solicita al usuario seleccionar un cliente o mantener el actual (ENTER).
     * @param ClienteDTOS Lista de clienteDTOS disponibles.
     * @param ClienteDTOActual ClienteDTO actualmente asociado.
     * @return ClienteDTO seleccionado o null si se mantiene el actual.
     */
    public ClienteDTO askClienteOpcional(List<ClienteDTO> ClienteDTOS, ClienteDTO ClienteDTOActual) {
        if (ClienteDTOS.isEmpty()) {
            showMensajePausa("No hay clienteDTOS registrados.", true);
            return null;
        }

        showListClientes(ClienteDTOS);
        showMensaje("ClienteDTO actual: " + ClienteDTOActual.getNombre() + " (" + ClienteDTOActual.getNif() + ")", true);
        showMensaje("Selecciona un nuevo cliente o pulsa ENTER para mantener el actual:", true);

        Scanner scanner = new Scanner(System.in);
        String index = scanner.nextLine();

        if(index.isEmpty()) return null;
        try {
            int valorIndex = Integer.parseInt(index);
            if (valorIndex < 1 || valorIndex > ClienteDTOS.size()) {
                showMensajePausa("Índice fuera de rango. Se mantendrá el cliente actual.", true);
                return null;
            }
            return ClienteDTOS.get(valorIndex - 1);
        } catch (NumberFormatException e) {
            showMensajePausa("Entrada no válida. Se mantendrá el cliente actual.", true);
            return null;
        }
    }
}

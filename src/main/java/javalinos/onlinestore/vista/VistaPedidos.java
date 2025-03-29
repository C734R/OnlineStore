package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static javalinos.onlinestore.utils.Utilidades.listToStr;
/**
 * Vista encargada de mostrar y gestionar la interacción con el usuario para los pedidos.
 * - Entidades relacionadas: Pedido, Cliente, Articulo
 */
public class VistaPedidos extends VistaBase {
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
     * Muestra una lista con todos los pedidos o filtrando por cliente.
     * @param pedidos Lista de pedidos.
     * @param cliente Cliente para filtrar (puede ser null).
     * @param opcion Opción extra para formato de salida.
     */
    public void showListPedidos(List<Pedido> pedidos, Cliente cliente, boolean opcion) {
        if (cliente == null) {
            showMensaje("******************* LISTA DE PEDIDOS *******************", true);
            showOptions(listToStr(pedidos), 0, true, false, opcion);
            showMensaje("********************************************************", true);
        }
        else {
            showMensaje("******************* LISTA DE PEDIDOS DEL CLIENTE  " + pedidos.getFirst().getCliente().getNombre() + " *******************", true);
            showOptions(listToStr(pedidos), 0, true, false, opcion);
            showMensaje("********************************************************", true);
        }
    }

    /**
     * Muestra una lista de clientes.
     *
     * @param clientes Lista con los clientes
     */
    public void showListClientes(List<Cliente> clientes) {
        showListGenerica(clientes, "LISTA DE CLIENTES", true, true);
    }
    /**
     * Muestra una lista con todos los clientes.
     * @param clientes Lista de clientes.
     */
    public void showListClientesPedidos(List<Cliente> clientes) {
        showListGenerica(clientes, "LISTA DE CLIENTES CON PEDIDOS", true, true);
    }
    /**
     * Muestra una lista de clientes que tienen al menos un pedido.
     * @param clientes Lista de clientes con pedidos.
     */
    public void showListClientesPedidosPendientes(List<Cliente> clientes) {
        showListGenerica(clientes, "LISTA DE CLIENTES CON PEDIDOS PENDIENTES", true, true);
    }
    /**
     * Muestra una lista de clientes con pedidos enviados.
     * @param clientes Lista de clientes con pedidos enviados.
     */
    public void showListClientesPedidosEnviados(List<Cliente> clientes) {
        showListGenerica(clientes, "LISTA DE CLIENTES CON ENVIADOS", true, true);
    }
    /**
     * Muestra la lista de artículos disponibles.
     * @param articulos Lista de artículos.
     */
    public void showListArticulos(List<Articulo> articulos) {
        showListGenerica(articulos, "LISTA DE ARTICULOS", true, false);
    }

    /**
     * Muestra todos los pedidos, filtrando si se indica cliente.
     * @param pedidos Lista de pedidos.
     * @param cliente Cliente (puede ser null).
     */
    public void showPedidos(List<Pedido> pedidos, Cliente cliente) {
        if (cliente == null) showListGenerica(pedidos, "PEDIDOS", true, true);
        else showListGenerica(pedidos, "PEDIDOS DEL CLIENTE " + cliente.getNombre(), true, true);
    }

    /**
     * Muestra los pedidos pendientes, filtrando si se indica cliente.
     * @param pedidos Lista de pedidos pendientes.
     * @param cliente Cliente (puede ser null).
     */
    public void showPedidosPendientes(List<Pedido> pedidos, Cliente cliente) {
        if (cliente == null) showListGenerica(pedidos, "PEDIDOS PENDIENTES", true, true);
        else showListGenerica(pedidos, "PEDIDOS PENDIENTES DEL CLIENTE " + cliente.getNombre(), true, true);
    }

    /**
     * Muestra los pedidos enviados, filtrando si se indica cliente.
     * @param pedidos Lista de pedidos enviados.
     * @param cliente Cliente (puede ser null).
     */
    public void showPedidosEnviados(List<Pedido> pedidos, Cliente cliente) {
        if (cliente == null) showListGenerica(pedidos, "PEDIDOS ENVIADOS", true, true);
        else showListGenerica(pedidos, "PEDIDOS ENVIADOS DEL CLIENTE " + cliente.getNombre(), true, true);
    }

    /**
     * Solicita al usuario seleccionar un cliente o mantener el actual (ENTER).
     * @param clientes Lista de clientes disponibles.
     * @param clienteActual Cliente actualmente asociado.
     * @return Cliente seleccionado o null si se mantiene el actual.
     */
    public Cliente askClienteOpcional(List<Cliente> clientes, Cliente clienteActual) {
        if (clientes.isEmpty()) {
            showMensajePausa("No hay clientes registrados.", true);
            return null;
        }

        showListClientes(clientes);
        showMensaje("Cliente actual: " + clienteActual.getNombre() + " (" + clienteActual.getNif() + ")", true);
        showMensaje("Selecciona un nuevo cliente o pulsa ENTER para mantener el actual:", true);

        Scanner scanner = new Scanner(System.in);
        String index = scanner.nextLine();

        if(index.isEmpty()) return null;
        try {
            int valorIndex = Integer.parseInt(index);
            if (valorIndex < 1 || valorIndex > clientes.size()) {
                showMensajePausa("Índice fuera de rango. Se mantendrá el cliente actual.", true);
                return null;
            }
            return clientes.get(valorIndex - 1);
        } catch (NumberFormatException e) {
            showMensajePausa("Entrada no válida. Se mantendrá el cliente actual.", true);
            return null;
        }
    }
}

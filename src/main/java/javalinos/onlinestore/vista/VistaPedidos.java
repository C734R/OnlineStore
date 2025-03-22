package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VistaPedidos extends VistaBase {

    public VistaPedidos() {
        String cabecera = """
                *********************************************
                **           Gestión de Pedidos            **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList(
                "Añadir pedido",
                "Eliminar pedido",
                "Listar todos los pedidos",
                "Listar pedidos pendientes",
                "Listar pedidos enviados"));
        super.setListaMenu(listaMenu);
    }

    public VistaPedidos(String cabecera, List<String> listaMenu) {
        super(cabecera, listaMenu);
    }

    public void showListPedidos(List<Pedido> pedidos, Cliente cliente) {
        if (!cliente) showListGenerica(pedidos, "LISTA DE PEDIDOS", true, false);
        else showListGenerica(pedidos, "LISTA DE PEDIDOS DEL CLIENTE " + pedidos.getFirst().getCliente().getNombre(), true, false);
    }

    public void showListClientes(List<Cliente> clientes) {
        showListGenerica(clientes, "LISTA DE CLIENTES", true, true);
    }

    public void showListArticulos(List<Articulo> articulos) {
        showListGenerica(articulos, "LISTA DE ARTICULOS", true, false);
    }

    public void showPedidos(List<Pedido> pedidos, Cliente cliente) {
        if (cliente == null) showListGenerica(pedidos, "PEDIDOS", true, true);
        else showListGenerica(pedidos, "PEDIDOS DEL CLIENTE " + cliente.getNombre(), true, true);
    }


    public void showPedidosPendientes(List<Pedido> pedidos, Cliente cliente) {
        if (cliente == null) showListGenerica(pedidos, "PEDIDOS PENDIENTES", true, true);
        else showListGenerica(pedidos, "PEDIDOS PENDIENTES DEL CLIENTE " + cliente.getNombre(), true, true);
    }

    public void showPedidosEnviados(List<Pedido> pedidos, Cliente cliente) {
        if (cliente == null) showListGenerica(pedidos, "PEDIDOS ENVIADOS", true, true);
        else showListGenerica(pedidos, "PEDIDOS ENVIADOS DEL CLIENTE " + cliente.getNombre(), true, true);
    }
}

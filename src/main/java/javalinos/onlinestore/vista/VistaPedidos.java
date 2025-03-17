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
                "Añadir pedido", "Eliminar pedido",
                "Listar pedidos por cliente",
                "Listar pedidos por fecha"));
        super.setListaMenu(listaMenu);
    }

    public VistaPedidos(String cabecera, List<String> listaMenu) {
        super(cabecera, listaMenu);
    }

    public void showListPedidos(List<Pedido> pedidos, Boolean cliente) {
        if (!cliente) showListGenerica(pedidos, "LISTA DE INSCRIPCIONES", true, false);
        else showListGenerica(pedidos, "LISTA DE EXCURSIONES DEL SOCIO " + pedidos.getFirst().getCliente().getNombre(), true, false);
    }

    public void showListClientes(List<Cliente> clientes) {
        showListGenerica(clientes, "LISTA DE CLIENTES", true, false);
    }

    public void showListArticulos(List<Articulo> articulos) {
        showListGenerica(articulos, "LISTA DE ARTICULOS", true, false);
    }


}

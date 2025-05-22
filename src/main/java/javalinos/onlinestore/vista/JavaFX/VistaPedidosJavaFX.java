package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.net.URL;
import java.util.*;

import static javalinos.onlinestore.OnlineStore.cPedidos;

public class VistaPedidosJavaFX extends VistaBaseJavaFX implements IVistaPedidos {

    @FXML private Button botonNuevoPedido;
    @FXML private Button botonEliminarPedido;
    @FXML private Button botonEditarPedido;
    @FXML private Button botonListarTodosPedidos;
    @FXML private Button botonListarPedidosPendientes;
    @FXML private Button botonListarPedidosEnviados;
    @FXML private Button botonVolver;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        botonNuevoPedido.setOnAction(event -> cPedidos.addPedidos());
        botonEliminarPedido.setOnAction(event -> cPedidos.removePedidos());
        botonEditarPedido.setOnAction(event -> cPedidos.updatePedido());
        botonListarTodosPedidos.setOnAction(event -> cPedidos.mostrarListaPedidos());
        botonListarPedidosPendientes.setOnAction(event -> cPedidos.mostrarListaPedidosPendientes());
        botonListarPedidosEnviados.setOnAction(event -> cPedidos.mostrarListaPedidosEnviados());
        botonVolver.setOnAction(event -> GestorEscenas.cerrarVentana("GestionPedidos"));
    }

    @Override
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {
        if (clienteDTO == null) {
            showListGenerica(pedidosDTO, "LISTA DE PEDIDOS", true, false, false);
        }
        else {
            showListGenerica(pedidosDTO, "LISTA DE PEDIDOS DEL CLIENTE  " + pedidosDTO.getFirst().getCliente().getNombre(), true, false, false);
        }
    }

    @Override
    public void showListClientes(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES", true, true, false);
    }

    @Override
    public void showListClientesPedidos(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES CON PEDIDOS", true, false, false);
    }

    @Override
    public void showListClientesPedidosPendientes(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES CON PEDIDOS PENDIENTES", true, false, false);
    }

    @Override
    public void showListClientesPedidosEnviados(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES CON PEDIDOS ENVIADOS", true, false, false);
    }

    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        showListGenerica(articulosDTO, "LISTA DE ARTICULOS", true, true, false);
    }

    @Override
    public void showPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        if (clienteDTO == null) showListGenerica(pedidosDTO, "PEDIDOS", true, false, false);
        else showListGenerica(pedidosDTO, "PEDIDOS DEL CLIENTE " + clienteDTO.getNombre(), true, false, false);
    }

    @Override
    public void showListPedidosPendientes(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        if (clienteDTO == null) showListGenerica(pedidosDTO, "PEDIDOS PENDIENTES", true, false, false);
        else showListGenerica(pedidosDTO, "PEDIDOS PENDIENTES DEL CLIENTE " + clienteDTO.getNombre(), true, false, false);
    }

    @Override
    public void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        if (clienteDTO == null) showListGenerica(pedidosDTO, "PEDIDOS ENVIADOS", true, false, false);
        else showListGenerica(pedidosDTO, "PEDIDOS ENVIADOS DEL CLIENTE " + clienteDTO.getNombre(), true, false, false);
    }

    @Override
    public ClienteDTO askClienteOpcional(List<ClienteDTO> clientesDTO, ClienteDTO clienteDTOActual) {
        LinkedHashMap<String, Integer> mapa = new LinkedHashMap<>();
        mapa.put("", 0);
        int index = 1;
        for (ClienteDTO clienteDTO : clientesDTO) {
            mapa.put(clienteDTO.getNombre() + " - " + clienteDTO.getNif() + " - " + clienteDTO.getEmail(), index);
            index++;
        }

        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int seleccion;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el nuevo cliente (VACÍO para mantener actual)");

        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            seleccion = mapa.get(respuesta);
        }
        else return null;
        if (seleccion == 0) return null;
        return clientesDTO.get(seleccion-1);
    }

    @Override
    public int askPedidoModificar(List<PedidoDTO> pedidosDTO) {
        LinkedHashMap<String, Integer> mapa = new LinkedHashMap<>();
        int index = 1;
        for (PedidoDTO pedido : pedidosDTO) {
            mapa.put(pedido.getNumero() + " - " + pedido.getCliente().getNombre() + " - " + pedido.getArticulo().getDescripcion() + " - " + pedido.getCantidad(), index);
            index++;
        }

        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int seleccion;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el pedido a modificar");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            if (respuesta.isEmpty()) return -99999;
            seleccion = mapa.get(respuesta);
        }
        else return -99999;
        if (seleccion == 0) {
            showMensajePausa("Volviendo atrás...", true);
            return -99999;
        }
        return seleccion;
    }

    @Override
    public int askClienteFiltro(int tipoFiltrado, List<ClienteDTO> clientesPedidos) {

        LinkedHashMap<String, Integer> mapa = new LinkedHashMap<>();
        int index = 1;
        for (ClienteDTO cliente : clientesPedidos) {
            mapa.put(cliente.getNombre() + " - " + cliente.getNif() + " - " + cliente.getEmail(), index);
            index++;
        }

        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int seleccion;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione un cliente por el que filtrar");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            if (respuesta.isEmpty()) return -99999;
            seleccion = mapa.get(respuesta);
        }
        else return -99999;
        if (seleccion == 0) {
            showMensajePausa("Volviendo atrás...", true);
            return -99999;
        }
        return seleccion;
    }

    @Override
    public int askPedidoRemove(List<PedidoDTO> pedidosDTO) {
        LinkedHashMap<String, Integer> mapa = new LinkedHashMap<>();
        int index = 1;
        for (PedidoDTO pedido : pedidosDTO) {
            mapa.put(pedido.getNumero() + " - " + pedido.getCliente().getNombre() + " - " + pedido.getArticulo().getDescripcion() + " - " + pedido.getCantidad(), index);
            index++;
        }

        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int seleccion;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el pedido a eliminar");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            if (respuesta.isEmpty()) return -99999;
            seleccion = mapa.get(respuesta);
        }
        else return -99999;
        if (seleccion == 0) {
            showMensajePausa("Volviendo atrás...", true);
            return -99999;
        }
        return seleccion;    }

}

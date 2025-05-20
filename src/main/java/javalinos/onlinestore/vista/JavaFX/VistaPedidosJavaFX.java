package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javalinos.onlinestore.OnlineStore.cPedidos;
import static javalinos.onlinestore.OnlineStore.cClientes;

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
        botonListarTodosPedidos.setOnAction(event -> cPedidos.showListPedidos(null));
        botonListarPedidosPendientes.setOnAction(event -> cPedidos.showListPedidosPendientesEnviados(null, false));
        botonListarPedidosEnviados.setOnAction(event -> cPedidos.showListPedidosPendientesEnviados(null, true));
        botonVolver.setOnAction(event -> GestorEscenas.cerrarVentana("GestionPedidos"));
    }

    @Override
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {
        clienteDTO = askClienteOpcional(cClientes.getListaClientes(), null);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pedidosDTO.size(); i++) {
            PedidoDTO pedido = pedidosDTO.get(i);
            if (clienteDTO != null && clienteDTO == pedido.getCliente()){
                builder.append(i + 1).append(" - ")
                        .append(pedido.getNumero()).append(" - ")
                        .append(pedido.getArticulo()).append(" - ")
                        .append(pedido.getCliente()).append(" - ")
                        .append(pedido.getCantidad()).append(" - ")
                        .append(pedido.getPrecio()).append("\n");
            }
            else if (clienteDTO == null){
                builder.append(i + 1).append(" - ")
                        .append(pedido.getNumero()).append(" - ")
                        .append(pedido.getArticulo()).append(" - ")
                        .append(pedido.getCliente()).append(" - ")
                        .append(pedido.getCantidad()).append(" - ")
                        .append(pedido.getPrecio()).append("\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listado de Pedidos");
        alert.setHeaderText("Pedidos existentes:");
        alert.setContentText(builder.toString());
        alert.showAndWait();

    }

    @Override
    public void showListClientes(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesPedidos(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesPedidosPendientes(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesPedidosEnviados(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {

    }

    @Override
    public void showPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {

    }

    @Override
    public void showListPedidosPendientes(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        clienteDTO = askClienteOpcional(cClientes.getListaClientes(), null);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pedidosDTO.size(); i++) {
            PedidoDTO pedido = pedidosDTO.get(i);
            if (clienteDTO != null && clienteDTO == pedido.getCliente()){
                builder.append(i + 1).append(" - ")
                        .append(pedido.getNumero()).append(" - ")
                        .append(pedido.getArticulo()).append(" - ")
                        .append(pedido.getCliente()).append(" - ")
                        .append(pedido.getCantidad()).append(" - ")
                        .append(pedido.getPrecio()).append("\n");
            }
            else if (clienteDTO == null){
                builder.append(i + 1).append(" - ")
                        .append(pedido.getNumero()).append(" - ")
                        .append(pedido.getArticulo()).append(" - ")
                        .append(pedido.getCliente()).append(" - ")
                        .append(pedido.getCantidad()).append(" - ")
                        .append(pedido.getPrecio()).append("\n");
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listado de Pedidos Pendientes de Envio");
        alert.setHeaderText("Pedidos existentes:");
        alert.setContentText(builder.toString());
        alert.showAndWait();
    }

    @Override
    public void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        clienteDTO = askClienteOpcional(cClientes.getListaClientes(), null);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pedidosDTO.size(); i++) {
            PedidoDTO pedido = pedidosDTO.get(i);
            if (clienteDTO != null && clienteDTO == pedido.getCliente()){
                builder.append(i + 1).append(" - ")
                        .append(pedido.getNumero()).append(" - ")
                        .append(pedido.getArticulo()).append(" - ")
                        .append(pedido.getCliente()).append(" - ")
                        .append(pedido.getCantidad()).append(" - ")
                        .append(pedido.getPrecio()).append("\n");
            }
            else if (clienteDTO == null){
                builder.append(i + 1).append(" - ")
                        .append(pedido.getNumero()).append(" - ")
                        .append(pedido.getArticulo()).append(" - ")
                        .append(pedido.getCliente()).append(" - ")
                        .append(pedido.getCantidad()).append(" - ")
                        .append(pedido.getPrecio()).append("\n");
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listado de Pedidos Enviados");
        alert.setHeaderText("Pedidos existentes:");
        alert.setContentText(builder.toString());
        alert.showAndWait();
    }

    @Override
    public ClienteDTO askClienteOpcional(List<ClienteDTO> clientesDTO, ClienteDTO clienteDTOActual) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filtro por cliente");
        dialog.setHeaderText("Introduzca el DNI del cliente");
        dialog.setContentText("DNI:");

        var result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String dniIngresado = result.get();
                for (ClienteDTO cliente : clientesDTO) {
                    if (cliente.getNif().equalsIgnoreCase(dniIngresado)){
                        return cliente;
                    }
                }
            } catch (NumberFormatException ignored) {}
            dialog.setHeaderText("Valor inválido. Introduce un número entre");
        } else {
            return null;
        }
        return null;
    }

}

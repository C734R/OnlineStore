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
        botonListarTodosPedidos.setOnAction(event -> askFiltroCliente(TipoListado.TODOS));
        botonListarPedidosPendientes.setOnAction(event -> askFiltroCliente(TipoListado.PENDIENTES));
        botonListarPedidosEnviados.setOnAction(event -> askFiltroCliente(TipoListado.ENVIADOS));
        botonVolver.setOnAction(event -> GestorEscenas.cerrarVentana("GestionPedidos"));
    }

    @Override
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {
        StringBuilder builder = new StringBuilder();
        if (pedidosDTO != null) {
            for (int i = 0; i < pedidosDTO.size(); i++) {
                PedidoDTO pedido = pedidosDTO.get(i);
                if (clienteDTO == null || clienteDTO.equals(pedido.getCliente())) {
                    builder.append("--------------------------------------------------------------\n")
                            .append(i + 1).append(" - Pedido\n")
                            .append("Número de pedido: ").append(pedido.getNumero()).append(" - ")
                            .append(pedido.getArticulo()).append(" - ")
                            .append(pedido.getCliente()).append(" - ")
                            .append(pedido.getCantidad()).append(" - ")
                            .append(pedido.getPrecio()).append("\n")
                            .append("-------------------------------------------------------------\n");
                }
            }
        }

        TextArea textArea = new TextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listado de Pedidos");
        alert.setHeaderText("Pedidos existentes:");
        alert.getDialogPane().setContent(textArea);

        textArea.setPrefWidth(600);
        textArea.setPrefHeight(400);

        alert.showAndWait();

    }

    private void askFiltroCliente(TipoListado tipoListado) { // Recibe el tipo de listado deseado
        Map<String, Integer> mapa = Map.of(
                "Sí", 1,
                "No", 2,
                "Volver", 0
        );
        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int filtro = 0;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("¿Quieres filtrar por cliente?");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent()) {
            respuesta = resultado.get();
            filtro = mapa.get(respuesta);
        }
        if (filtro == 0) {
            showMensajePausa("Volviendo atrás...", true);
            return;
        }

        ClienteDTO clienteDTO = null;
        if (filtro == 1) {
            clienteDTO = cPedidos.askCliente(false);
            if (clienteDTO == null) {
                showMensajePausa("Selección de cliente cancelada. Volviendo atrás...", true);
                return;
            }
        }
        if (tipoListado == TipoListado.PENDIENTES) {
            cPedidos.showListPedidosPendientesEnviados(clienteDTO, false);
        } else if (tipoListado == TipoListado.ENVIADOS) {
            cPedidos.showListPedidosPendientesEnviados(clienteDTO, true);
        } else if (tipoListado == TipoListado.TODOS) {
            cPedidos.showListPedidos(clienteDTO);
        }
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
        StringBuilder builder = new StringBuilder();

        if (pedidosDTO == null || pedidosDTO.isEmpty()) {
            builder.append("No existen pedidos pendientes de envío para mostrar.");
        } else {
            for (int i = 0; i < pedidosDTO.size(); i++) {
                PedidoDTO pedido = pedidosDTO.get(i);
                if (clienteDTO == null || clienteDTO.equals(pedido.getCliente())) {
                    builder.append("-------------------------------------------------------------\n")
                            .append(i + 1).append(" - ")
                            .append("Número de pedido: ").append(pedido.getNumero()).append(" - ")
                            .append(pedido.getArticulo()).append(" - ")
                            .append(pedido.getCliente()).append(" - ")
                            .append(pedido.getCantidad()).append(" - ")
                            .append(pedido.getPrecio()).append("\n")
                            .append("-------------------------------------------------------------\n");
                }
            }
            if (builder.isEmpty() && clienteDTO != null) {
                builder.append("No hay pedidos pendientes de envío para el cliente seleccionado.");
            }
        }
        TextArea textArea = new TextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listado de Pedidos Pendientes de Envío");
        alert.setHeaderText("Pedidos existentes:");
        alert.getDialogPane().setContent(textArea);

        textArea.setPrefWidth(600);
        textArea.setPrefHeight(400);

        alert.showAndWait();
    }

    @Override
    public void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {
        StringBuilder builder = new StringBuilder();

        if (pedidosDTO == null || pedidosDTO.isEmpty()) {
            builder.append("No existen pedidos enviados para mostrar.");
        } else {
            for (int i = 0; i < pedidosDTO.size(); i++) {
                PedidoDTO pedido = pedidosDTO.get(i);
                if (clienteDTO == null || clienteDTO.equals(pedido.getCliente())) {
                    builder.append("-------------------------------------------------------------\n")
                            .append(i + 1).append(" - ")
                            .append("Número de pedido: ").append(pedido.getNumero()).append(" - ")
                            .append(pedido.getArticulo()).append(" - ")
                            .append(pedido.getCliente()).append(" - ")
                            .append(pedido.getCantidad()).append(" - ")
                            .append(pedido.getPrecio()).append("\n")
                            .append("-------------------------------------------------------------\n");
                }
            }
            if (builder.isEmpty() && clienteDTO != null) {
                builder.append("No hay pedidos enviados para el cliente seleccionado.");
            }
        }

        TextArea textArea = new TextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listado de Pedidos Enviados");
        alert.setHeaderText("Pedidos existentes:");
        alert.getDialogPane().setContent(textArea);

        textArea.setPrefWidth(600);
        textArea.setPrefHeight(400);

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

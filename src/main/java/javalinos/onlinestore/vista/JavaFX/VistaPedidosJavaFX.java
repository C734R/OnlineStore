package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.controlador.ControlPedidos;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static javalinos.onlinestore.OnlineStore.cClientes;

public class VistaPedidosJavaFX extends VistaBaseJavaFX implements IVistaPedidos {
    private ControlPedidos cPedidos;

    @FXML private TableView<PedidoDTO> tablaPedidos;

    @FXML private TableColumn<PedidoDTO, Integer> columnaNumero;
    @FXML private TableColumn<PedidoDTO, ClienteDTO> columnaCliente;
    @FXML private TableColumn<PedidoDTO, ArticuloDTO> columnaArticulo;
    @FXML private TableColumn<PedidoDTO, Integer> columnaCantidad;
    @FXML private TableColumn<PedidoDTO, LocalDate> columnaFecha;
    @FXML private TableColumn<PedidoDTO, Float> columnaEnvio;
    @FXML private TableColumn<PedidoDTO, Float> columnaPrecio;

    @FXML private Button botonNuevoPedido;
    @FXML private Button botonEliminarPedido;
    @FXML private Button botonEditarPedido;
    @FXML private Button botonListarTodosPedidos;
    @FXML private Button botonListarPedidosPendientes;
    @FXML private Button botonListarPedidosEnviados;
    @FXML private Button botonVolver;


    @FXML
    public void initialize() {
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
        tablaPedidos.getItems().addAll(pedidosDTO);
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

    }

    @Override
    public void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {

    }

    @Override
    public ClienteDTO askClienteOpcional(List<ClienteDTO> clientesDTO, ClienteDTO clienteDTOActual) {
        return null;
    }

}

package javalinos.onlinestore.vista.JavaFX;

import javalinos.onlinestore.controlador.ControlArticulos;
import javalinos.onlinestore.controlador.ControlPedidos;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;
import javalinos.onlinestore.vista.JavaFX.NuevoPedidoJavaFX;

import java.util.List;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaPedidosJavaFX extends VistaBaseJavaFX implements IVistaPedidos {

    private ControlPedidos cPedidos;

    @FXML
    private TableView<PedidoDTO> tablaPedidos;

    @FXML
    private TableColumn<PedidoDTO, Integer> columnaNumero;
    @FXML
    private TableColumn<PedidoDTO, ClienteDTO> columnaCliente;
    @FXML
    private TableColumn<PedidoDTO, String> columnaArticulo;
    @FXML
    private TableColumn<PedidoDTO, Integer> columnaCantidad;
    @FXML
    private TableColumn<PedidoDTO, LocalDate> columnaFecha;
    @FXML
    private TableColumn<PedidoDTO, Float> columnaEnvio;
    @FXML
    private TableColumn<PedidoDTO, Float> columnaPrecio;

    @FXML
    private Button botonNuevoPedido;
    @FXML
    private Button botonEliminarPedido;
    @FXML
    private Button botonEditarPedido;
    @FXML
    private Button botonListarTodosPedidos;
    @FXML
    private Button botonListarPedidosPendientes;
    @FXML
    private Button botonListarPedidosEnviados;

    public VistaPedidosJavaFX(ControlArticulos cArticulos) {
        this.cPedidos = cPedidos;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        columnaArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        columnaEnvio.setCellValueFactory(new PropertyValueFactory<>("envio"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        botonNuevoPedido.setOnAction(event -> addPedido());
        botonEliminarPedido.setOnAction(event -> removePedido());
        botonEditarPedido.setOnAction(event -> modPedido());
        botonListarTodosPedidos.setOnAction(event -> listarPedido(null));
        botonListarPedidosEnviados.setOnAction(event -> showPedidosEnviados(null, true));
        botonListarPedidosPendientes.setOnAction(event -> showPedidosEnviados(null, false));
    }

    private void addPedido() { cPedidos.addPedidos(); }

    @FXML
    private void removePedido() { cPedidos.removePedidos(); }

    private void modPedido() {
        cPedidos.updatePedido();
    }

    private void listarPedido(ClienteDTO clienteDTO) {
        tablaPedidos.getItems().clear();
        cPedidos.showListPedidos(clienteDTO);
    }

    private void showPedidosEnviados(ClienteDTO clienteDTO, boolean enviado){
        tablaPedidos.getItems().clear();
        cPedidos.showListPedidosPendientesEnviados(clienteDTO, enviado);
    }

    @Override
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {
        tablaPedidos.getItems().clear();
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

    public void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

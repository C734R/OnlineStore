package javalinos.onlinestore.vista.JavaFX;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;

import java.util.List;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaPedidosJavaFX extends VistaBaseJavaFX implements IVistaPedidos {

    private List<PedidoDTO> listaPedidos; // Para almacenar la lista actual de pedidos

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

    private IModeloPedidos modeloPedidos;

    public void initialize(URL location, ResourceBundle resources) {
        // Aquí configurarías las columnas de la tabla (setCellValueFactory)
        columnaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        columnaArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        columnaEnvio.setCellValueFactory(new PropertyValueFactory<>("envio"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
    }

    @FXML
    private void NuevoPedido() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormularioNuevoPedido.fxml"));
            Parent root = loader.load();

            FormularioNuevoPedidoJavaFX controller = loader.getController();
            controller.setModeloPedidos(modeloPedidos);
            Stage stage = new Stage();
            stage.setTitle("Nuevo Pedido");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            cargarPedidos();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Abrir Formulario", null, "No se pudo abrir el formulario: " + e.getMessage());
        }
    }

    private void cargarPedidos() {
        try {
            List<PedidoDTO> pedidos = modeloPedidos.getPedidosDTO();
            showListPedidos(pedidos, null, false); // O con el cliente actual si lo mantienes
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Cargar Pedidos", null, "No se pudieron cargar los pedidos: " + e.getMessage());
        }
    }

    @Override
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {
        ObservableList<PedidoDTO> observablePedidos = FXCollections.observableArrayList(pedidosDTO);
        tablaPedidos.setItems(observablePedidos);

        String mensaje = "Lista de pedidos cargada.";
        if (clienteDTO != null) {
            mensaje = "Pedidos del cliente " + clienteDTO.getNombre() + " cargados.";
        }
        if (opcion) { // Puedes usar 'opcion' para indicar si son pendientes/enviados
            mensaje += " (Filtrados).";
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Lista de Pedidos", null, mensaje);
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

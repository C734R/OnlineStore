package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.controlador.ControlClientes;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaClientes;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VistaClientesJavaFX extends VistaBaseJavaFX implements IVistaClientes {

    private ControlClientes cClientes;

    @FXML private Button btnAddCliente;
    @FXML private Button btnModCliente;
    @FXML private Button btnDeleteCliente;
    @FXML private Button btnListClientes;

    @FXML private TableView<ClienteDTO> tblClientes;
    @FXML private TableColumn<ClienteDTO, String> colNombre;
    @FXML private TableColumn<ClienteDTO, String> colDomicilio;
    @FXML private TableColumn<ClienteDTO, String> colEmail;
    @FXML private TableColumn<ClienteDTO, String> colDNI;
    @FXML private TableColumn<ClienteDTO, String> colCategoria;


    @FXML private TextField txtNomeCliente;
    @FXML private TextField txtDNI;
    @FXML private TextField txtEmail;

    public VistaClientesJavaFX(ControlClientes cClientes) {
        this.cClientes = cClientes;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Suscripciones botones
        btnAddCliente.setOnAction(event -> addCliente());
        btnDeleteCliente.setOnAction(event -> removeCliente());
        btnListClientes.setOnAction(event -> listarClientes());
        btnModCliente.setOnAction(event -> modCliente());
    }

    private void listarClientes() {

        cClientes.showListClientes();
    }

    private void addCliente() {
        cClientes.addCliente();
    }

    private void removeCliente() {
        cClientes.removeCliente();
    }

    private void modCliente() {
        cClientes.modCliente();
    }

    @Override
    public String askNIF(boolean modificar, boolean reintentar, boolean sinFin) {

        return "";
    }

    @Override
    public String askEmail(boolean modificar, boolean reintentar, boolean sinFin) {
        return "";
    }

    @Override
    public int askMetodoEliminar() {
        return 0;
    }

    @Override
    public int askCategoriaCliente() {
        return 0;
    }

    @Override
    public void showListClientes(List<ClienteDTO> clientesDTO) {
        tblClientes.getItems().clear();
        tblClientes.getItems().addAll(clientesDTO);
    }

    @Override
    public void showListClientesNumerada(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesCategoria(List<ClienteDTO> clientesDTO, CategoriaDTO categoriaDTO) {

    }

    @Override
    public void showMods() {

    }

    @Override
    public void showCategorias() {

    }

    @Override
    public void showMetodosEliminar() {

    }

    @Override
    public void showCliente(ClienteDTO clienteDTO) {

    }
}

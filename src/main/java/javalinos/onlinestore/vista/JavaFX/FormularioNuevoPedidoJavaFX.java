package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;

import java.net.URL;
import java.time.LocalDateTime; // Importa LocalDateTime
import java.util.ResourceBundle;

public class FormularioNuevoPedidoJavaFX implements Initializable {

    @FXML
    private ComboBox<ClienteDTO> comboCliente;
    @FXML
    private ComboBox<ArticuloDTO> comboArticulo;
    @FXML
    private TextField txtCantidad;
    // @FXML
    // private DatePicker dateFecha; // Ya no necesitamos que el usuario seleccione la fecha
    @FXML
    private TextField txtEnvio;
    @FXML
    private TextField txtPrecio;

    private IModeloPedidos modeloPedidos; // Necesitas una referencia al modelo

    public void setModeloPedidos(IModeloPedidos modeloPedidos) {
        this.modeloPedidos = modeloPedidos;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void GuardarNuevoPedido() {
        if (modeloPedidos != null) {
            ClienteDTO clienteSeleccionado = comboCliente.getValue();
            ArticuloDTO articuloSeleccionado = comboArticulo.getValue();
            String cantidadTexto = txtCantidad.getText();
            String envioTexto = txtEnvio.getText();
            String precioTexto = txtPrecio.getText();

            try {
                int cantidad = Integer.parseInt(cantidadTexto);
                float envio = Float.parseFloat(envioTexto);
                float precio = Float.parseFloat(precioTexto);

                if (clienteSeleccionado != null && articuloSeleccionado != null) {
                    LocalDateTime fechaCreacion = LocalDateTime.now();
                    PedidoDTO nuevoPedido = modeloPedidos.makePedido(clienteSeleccionado, articuloSeleccionado, cantidad, fechaCreacion.toLocalDate(), envio); // Usa toLocalDate() si tu DTO usa LocalDate
                    modeloPedidos.addPedidoStock(nuevoPedido); // O la versión SP
                    // Cerrar la ventana después de guardar
                    // ((Stage) txtCantidad.getScene().getWindow()).close();
                } else {
                    // Mostrar mensaje de error si faltan datos esenciales
                }
            } catch (NumberFormatException e) {
                // Mostrar mensaje de error si los formatos son incorrectos
            } catch (Exception e) {
                // Mostrar mensaje de error al guardar
            }
        }
    }

    @FXML
    private void CancelarNuevoPedido() {
        // Cerrar la ventana
        // ((Stage) txtCantidad.getScene().getWindow()).close();
    }
}

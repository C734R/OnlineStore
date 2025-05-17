package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;

import java.net.URL;
import java.time.LocalDateTime; // Importa LocalDateTime
import java.util.ResourceBundle;

public class NuevoPedidoJavaFX implements Initializable {

    @FXML
    private ComboBox<ClienteDTO> comboCliente;
    @FXML
    private ComboBox<ArticuloDTO> comboArticulo;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtEnvio;
    @FXML
    private Label lblPrecioTotal;

    private IModeloPedidos modeloPedidos;
    private VistaPedidosJavaFX vistaPedidos;

    public void setModeloPedidos(IModeloPedidos modeloPedidos) {
        this.modeloPedidos = modeloPedidos;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboCliente.setOnAction(event -> actualizarPrecioTotal());
        comboArticulo.setOnAction(event -> actualizarPrecioTotal());
        txtCantidad.setOnKeyReleased(event -> actualizarPrecioTotal());
        txtEnvio.setOnKeyReleased(event -> actualizarPrecioTotal());
    }

    @FXML
    private void GuardarNuevoPedido() {
        if (modeloPedidos != null) {
            ClienteDTO clienteSeleccionado = comboCliente.getValue();
            ArticuloDTO articuloSeleccionado = comboArticulo.getValue();
            String cantidadTexto = txtCantidad.getText();
            String envioTexto = txtEnvio.getText();

            try {
                int cantidad = Integer.parseInt(cantidadTexto);
                float envio = Float.parseFloat(envioTexto);

                if (clienteSeleccionado != null && articuloSeleccionado != null) {
                    LocalDateTime fechaCreacion = LocalDateTime.now();
                    float precioTotal = modeloPedidos.calcPrecioTotal(articuloSeleccionado, cantidad, envio, clienteSeleccionado);

                    PedidoDTO nuevoPedido = modeloPedidos.makePedido(clienteSeleccionado, articuloSeleccionado, cantidad, fechaCreacion.toLocalDate(), envio);
                    nuevoPedido.setPrecio(precioTotal);
                    modeloPedidos.addPedidoStockSP(nuevoPedido);
                    cerrarVentana();
                } else {
                    if (vistaPedidos != null) {
                        vistaPedidos.mostrarAlerta(Alert.AlertType.WARNING, "Datos Incompletos", null, "Por favor, selecciona un cliente y un artículo.");
                    }
                }
            } catch (NumberFormatException e) {
                if (vistaPedidos != null) {
                    vistaPedidos.mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", null, "Por favor, ingresa números válidos para la cantidad y el envío.");
                }
            } catch (Exception e) {
                if (vistaPedidos != null) {
                    vistaPedidos.mostrarAlerta(Alert.AlertType.ERROR, "Error al Guardar", null, "Hubo un error al guardar el pedido: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void CancelarNuevoPedido() {
        ((Stage) txtCantidad.getScene().getWindow()).close();
    }

    private void actualizarPrecioTotal() {
        ClienteDTO clienteSeleccionado = comboCliente.getValue();
        ArticuloDTO articuloSeleccionado = comboArticulo.getValue();
        String cantidadTexto = txtCantidad.getText();
        String envioTexto = txtEnvio.getText();

        if (clienteSeleccionado != null && articuloSeleccionado != null && !cantidadTexto.isEmpty() && !envioTexto.isEmpty()) {
            try {
                int cantidad = Integer.parseInt(cantidadTexto);
                float envio = Float.parseFloat(envioTexto);
                float precioTotal = modeloPedidos.calcPrecioTotal(articuloSeleccionado, cantidad, envio, clienteSeleccionado);
                lblPrecioTotal.setText(String.format("%.2f €", precioTotal));
            } catch (NumberFormatException e) {
                lblPrecioTotal.setText("Formato Inválido");
            } catch (Exception e) {
                lblPrecioTotal.setText("Error al Calcular");
            }
        } else {
            lblPrecioTotal.setText("-");
        }
    }

    public void setVistaPedidos(VistaPedidosJavaFX vistaPedidos) {
        this.vistaPedidos = vistaPedidos;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtCantidad.getScene().getWindow();
        stage.close();
    }
}

package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloPedidos;

public class EliminarPedidoJavaFX {

    @FXML
    private TextField txtNumeroPedido;

    private IModeloPedidos modeloPedidos;
    private VistaPedidosJavaFX vistaPedidosJavaFX;

    public void setModeloPedidos(IModeloPedidos modeloPedidos) {
        this.modeloPedidos = modeloPedidos;
    }

    public void setVistaPedidosJavaFX(VistaPedidosJavaFX vistaPedidosJavaFX) {
        this.vistaPedidosJavaFX = vistaPedidosJavaFX;
    }

    @FXML
    private void EliminarPedido() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarPedido.fxml"));
            Parent root = loader.load();
            EliminarPedidoJavaFX controller = loader.getController();
            controller.setModeloPedidos(modeloPedidos);

            // Pasar la instancia de VistaPedidosJavaFX para poder llamar a cargarPedidos()
            // Asegúrate de que el nombre de tu variable para VistaPedidosJavaFX sea correcto
            // Si estás dentro de la misma clase, puedes pasar 'this' si el controlador
            // tiene un método para recibirlo (ej. setVistaPedidosJavaFX).

            // Asumiendo que 'EliminarPedidoJavaFX' tiene un método setVistaPedidosJavaFX:
            // controller.setVistaPedidosJavaFX(this);

            Stage stage = new Stage();
            stage.setTitle("Eliminar Pedido");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Abrir Formulario", null, "No se pudo abrir el formulario para eliminar pedido: " + e.getMessage());
        }
    }

    @FXML
    private void Cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNumeroPedido.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}

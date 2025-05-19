package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javalinos.onlinestore.vista.Interfaces.IVistaMenuPrincipal;

import java.net.URL;
import java.util.ResourceBundle;

import static javalinos.onlinestore.OnlineStore.cMenuPrincipal;

public class VistaMenuPrincipalJavaFX extends VistaBaseJavaFX implements IVistaMenuPrincipal {

    @FXML private Button btnGestionClientes;
    @FXML private Button btnGestionArticulos;
    @FXML private Button btnGestionPedidos;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnGestionClientes.setOnAction(event -> cMenuPrincipal.iniciarVentana(1));
        btnGestionArticulos.setOnAction(event -> cMenuPrincipal.iniciarVentana(2));
        btnGestionPedidos.setOnAction(event -> cMenuPrincipal.iniciarVentana(3));
        btnSalir.setOnAction(event -> cMenuPrincipal.salir());
    }

}

package javalinos.onlinestore.vista.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javalinos.onlinestore.controlador.ControlClientes;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaClientes;
import java.net.URL;
import java.util.*;

import static javalinos.onlinestore.OnlineStore.cClientes;
import static javalinos.onlinestore.OnlineStore.vClientes;
import static javalinos.onlinestore.utils.Utilidades.checkEmail;
import static javalinos.onlinestore.utils.Utilidades.checkNIF;

public class VistaClientesJavaFX extends VistaBaseJavaFX implements IVistaClientes {

    @FXML private Button btnAddCliente;
    @FXML private Button btnModCliente;
    @FXML private Button btnDeleteCliente;
    @FXML private Button btnListClientes;
    @FXML private Button btnListClientesCategoria;
    @FXML private Button btnVolver;

    @FXML private TableView<ClienteDTO> tblClientes;
    @FXML private TableColumn<ClienteDTO, String> colNombre;
    @FXML private TableColumn<ClienteDTO, String> colDomicilio;
    @FXML private TableColumn<ClienteDTO, String> colEmail;
    @FXML private TableColumn<ClienteDTO, String> colDNI;
    @FXML private TableColumn<ClienteDTO, String> colCategoria;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Suscripciones botones
        btnAddCliente.setOnAction(event -> cClientes.addCliente());
        btnDeleteCliente.setOnAction(event -> cClientes.removeCliente());
        btnModCliente.setOnAction(event -> cClientes.modCliente());
        btnListClientes.setOnAction(event -> cClientes.showListClientes());
        btnListClientesCategoria.setOnAction(event -> cClientes.showListClientesCategoria());
        btnVolver.setOnAction(event -> GestorEscenas.cerrarVentana("GestionClientes"));
    }

    @Override
    public String askNIF(boolean modificar, boolean reintentar, boolean sinFin) {
        String nif;
        int intentos = 0;
        if (!reintentar) intentos = 2;
        while (intentos < 3) {
            if (modificar) nif = askString("Introduce el nuevo NIF: ", 9, 15, false, false, false);
            else nif = askString("Introduce el NIF: ", 9, 15, false, false, false);
            if (nif == null || !checkNIF(nif)) {
                showMensajePausa("El DNI introducido es erróneo. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            } else if (checkNIF(nif)) return nif;
        }
        if (reintentar) showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    @Override
    public String askEmail(boolean modificar, boolean reintentar, boolean sinFin) {
        String email;
        int intentos = 0;
        if (!reintentar) intentos = 2;
        while (intentos < 3) {
            if (modificar) email = askString("Introduce el nuevo email: ", 4, 50, false, false, false);
            else email = askString("Introduce el email: ", 4, 50, false, false, false);
            if (email == null || !checkEmail(email)) {
                showMensajePausa("El email introducido es erróneo. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            } else return email;
        }
        if (reintentar) showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    @Override
    public int askMetodoEliminar() {
        Map<String, Integer> mapa = Map.of(
                "Por NIF", 1,
                "Por Email", 2
        );
        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int metodo = 0;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el método de eliminación");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            metodo = mapa.get(respuesta);
        } else return 0;
        if (metodo == 0) {
            showMensajePausa("Volviendo atrás...", true);
        }
        return metodo;
    }


    @Override
    public int askCategoriaCliente() {

        Map<String, Integer> mapa = Map.of(
                "Estándar", 1,
                "Premium", 2
        );
        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int categoria = 0;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione la categoría del cliente");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            categoria = mapa.get(respuesta);
        } else return 0;
        if (categoria == 0) {
            showMensajePausa("Volviendo atrás...", true);
        }
        return categoria;
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
        tblClientes.getItems().clear();
        tblClientes.getItems().addAll(clientesDTO);
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

    @Override
    public int askModificacion() {
        Map<String, Integer> mapa = Map.of(
                "Modificar nombre", 1,
                "Modificar domicilio", 2,
                "Modificar NIF", 3,
                "Modificar email", 4,
                "Modificar categoría", 5
        );
        String respuesta;
        List<String> opciones = new ArrayList<>(mapa.keySet());
        int metodo = 0;

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Seleccione una opción");
        dialogo.setHeaderText("Seleccione el método de eliminación");
        dialogo.setContentText("Opciones:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            respuesta = resultado.get();
            metodo = mapa.get(respuesta);
        } else return 0;
        if (metodo == 0) {
            showMensajePausa("Volviendo atrás...", true);
        }
        return metodo;
    }
}

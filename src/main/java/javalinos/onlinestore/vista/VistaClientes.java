package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.List;

public class VistaClientes extends VistaBase {

    public VistaClientes() {
        super();
    }

    public VistaClientes(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

    public String askNIF() {
        return "";
    }

    public String askEmail() {
        return "";
    }

    public int askTipoCliente() {
        return 0;
    }

    public void showMods() {

    }

    public void showTipos() {

    }

    public void showListClientes(List<Cliente> clientes) {

    }

    public void showListClientesTipo() {

    }

}

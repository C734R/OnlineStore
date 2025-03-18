package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javalinos.onlinestore.utils.Utilidades.checkNIF;

public class VistaClientes extends VistaBase {

    public VistaClientes() {
        String cabecera = """
                *********************************************
                **           Gestión de Clientes           **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList(
                "Añadir cliente", "Eliminar cliente",
                "Mostrar cliente", "Modificar cliente",
                "Listar clientes", "Listar cliente por tipo",
                "XXXX"));
        super.setListaMenu(listaMenu);    }

    public VistaClientes(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

    public String askNIF() {
        String nif;
        int intentos = 0;
        while (intentos < 3) {
            nif = askString("Introduce el NIF: ", 9);
            if (checkNIF(nif)) return nif;
            else {
                if(intentos<2) showMensaje("El DNI introducido es erróneo. Vuelve a intentarlo.", true);
                intentos++;
            }
        }
        showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
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

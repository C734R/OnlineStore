package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javalinos.onlinestore.utils.Utilidades.checkEmail;
import static javalinos.onlinestore.utils.Utilidades.checkNIF;

public class VistaClientes extends VistaBase {

    private final List<String> listMods = new ArrayList<>(Arrays.asList("Modificar número", "Modificar nombre", "Modificar domicilio", "Modificar NIF", "Modificar email", "Modificar categoría"));
    private final List<String> listCategorias = new ArrayList<>(Arrays.asList("Estándar", "Premium"));
    private final List<String> listMetodos = new ArrayList<>(Arrays.asList("Por NIF", "Por Email"));

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
                "Salir"));
        super.setListaMenu(listaMenu);
    }

    public VistaClientes(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }



    /**
     * Pide un NIF.
     * @return String devuelve el NIF.
     */
    public String askNIF() {
        String nif;
        int intentos = 0;
        while (intentos < 3) {
            nif = askNIF();
            if (checkNIF(nif)) return nif;
            else {
                if(intentos < 2) showMensaje("El DNI introducido es erróneo. Vuelve a intentarlo.", true);
                intentos++;
            }
        }
        showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    /**
     * Pide un Email
     * @return String devuelve el Email.
     */
    public String askEmail() {
        String email;
        int intentos = 0;
        while (intentos < 3) {
            System.out.print("Introduce el email: ");
            email = askEmail();
            if (checkEmail(email)) return email;
            else {
                if(intentos < 2) showMensaje("El email introducido es erróneo. Vuelve a intentarlo.", true);
                intentos++;
            }
        }
        showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    /**
     * Muestra la lista de clientes pasada por parámetro.
     * @param clientes Lista de clientes.
     */
    public void showListClientes(List<Cliente> clientes) {
        showListGenerica(clientes,"CLIENTES", true, false);
    }

    public void showListCategoriaCliente(List<Cliente> clientes, int categoria) {
        String mensaje = "LISTA DE CLIENTES DE CATEGORÍA ";
        if (categoria == 1) {
            mensaje = mensaje.concat("ESTÁNDAR ");
        } else if (categoria == 2) {
            mensaje = mensaje.concat("PREMIUM ");
        }
        else mensaje = mensaje.concat("DESCONOCIDO ");
        showListGenerica(clientes, mensaje, true, false);
    }

    /**
     * Pide seleccionar una categoría de cliente.
     * @return int devuelve opción seleccionada.
     */
    public int askCategoriaCliente() {
        while (true) {
            showCategorias();
            int categoria = askInt("Seleccione la categoría del cliente", 0, listCategorias.size(), false, false);
            if (categoria > 0 && categoria <= listCategorias.size()) return categoria;
            else if (categoria == 0) {
                showMensaje("Volviendo atrás...", true);
                return categoria;
            }
            else showMensaje("Introduce una de las opciones listadas.", true);
        }
    }

    /**
     * Muestra las modificaciones disponibles.
     */
    public void showMods() {
        showOptions(listMods,2, false, true);
    }

    /**
     * Muestra las categorías de cliente.
     */
    public void showCategorias() {
        showMensaje("******** TIPOS DE CATEGORÍA ********", true);
        showOptions(listCategorias, 3,false, true);
    }

    public int askMetodoEliminar() {
        while (true) {
            showMetodosEliminar();
            int metodo = askInt("Seleccione el método de eliminación", 0, listCategorias.size(), false, false);
            if (metodo > 0 && metodo <= listCategorias.size()) return metodo;
            else if (metodo == 0) {
                showMensaje("Volviendo atrás...", true);
                return metodo;
            }
            else showMensaje("Introduce una de las opciones listadas.", true);
        }
    }

    public void showMetodosEliminar() {
        showMensaje("******** METODOS DE ELIMINACIÓN DE USUARIO ********", true);
        showOptions(listMetodos, 3,false, true);
    }
}

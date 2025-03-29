package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javalinos.onlinestore.utils.Utilidades.checkEmail;
import static javalinos.onlinestore.utils.Utilidades.checkNIF;
/**
 * Vista encargada de la gestión e interacción con los clientes.
 * - Permite mostrar, solicitar y modificar datos de clientes.
 * - Entidades relacionadas: Cliente, Categoria
 */
public class VistaClientes extends VistaBase {

    private final List<String> listMods = new ArrayList<>(Arrays.asList("Modificar nombre", "Modificar domicilio", "Modificar NIF", "Modificar email", "Modificar categoría"));
    private final List<String> listCategorias = new ArrayList<>(Arrays.asList("Estándar", "Premium"));
    private final List<String> listMetodos = new ArrayList<>(Arrays.asList("Por NIF", "Por Email"));
    /**
     * Constructor por defecto. Inicializa cabecera y menú principal de clientes.
     */
    public VistaClientes() {
        String cabecera = """
                *********************************************
                **           Gestión de Clientes           **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList(
                "Añadir cliente", "Eliminar cliente",
                "Mostrar cliente", "Modificar cliente",
                "Listar clientes", "Listar cliente por tipo"));
        super.setListaMenu(listaMenu);
    }

    /**
     * Constructor alternativo con cabecera y menú personalizados.
     * @param cabecera texto de cabecera.
     * @param listMenu opciones del menú.
     */
    public VistaClientes(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

    //*************************** Pedir datos ***************************//

    /**
     * Solicita al usuario un NIF válido (3 intentos).
     * @return NIF introducido o null si se supera el límite de intentos.
     */
    public String askNIF() {
        String nif;
        int intentos = 0;
        while (intentos < 3) {
            nif = askString("Introduce el NIF: ", 9);
            if (checkNIF(nif)) return nif;
            else {
                if (intentos < 2) showMensaje("El DNI introducido es erróneo. Vuelve a intentarlo", true);
                intentos++;
            }
        }
        showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    /**
     * Solicita al usuario un email válido (3 intentos).
     * @param modificar indica si es parte de una modificación.
     * @return Email introducido o null si se supera el límite de intentos.
     */
    public String askEmail(boolean modificar) {
        String email;
        int intentos = 0;
        while (intentos < 3) {
            if (modificar) email = askString("Introduce el nuevo email: ", 50);
            else email = askString("Introduce el email: ", 50);
            if (checkEmail(email)) return email;
            else {
                if (intentos < 2) showMensaje("El email introducido es erróneo. Vuelve a intentarlo.", true);
                intentos++;
            }
        }
        showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    /**
     * Solicita el modo para eliminar un cliente.
     * @return opción seleccionada (0 = salir).
     */
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

    /**
     * Solicita al usuario que seleccione una categoría de cliente.
     * @return índice de categoría seleccionada o 0 para volver atrás.
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

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra una lista simple de clientes.
     * @param clientes lista de clientes.
     */
    public void showListClientes(List<Cliente> clientes) {
        showListGenerica(clientes,"CLIENTES", true, false);
    }

    /**
     * Muestra una lista numerada de clientes.
     * @param clientes lista de clientes.
     */
    public void showListClientesNumerada(List<Cliente> clientes) {
        showListGenerica(clientes,"CLIENTES NUMERADOS", true, true);
    }

    /**
     * Muestra un listado de clientes filtrado por categoría.
     * @param clientes lista de clientes filtrados.
     * @param categoria categoría seleccionada.
     */
    public void showListClientesCategoria(List<Cliente> clientes, Categoria categoria) {
        showListGenerica(clientes, "LISTA DE CLIENTES DE CATEGORÍA "+ categoria.getNombre(), true, false);
    }

    /**
     * Muestra las opciones de modificación de datos del cliente.
     */
    public void showMods() {
        showOptions(listMods,2, false, true, false);
    }

    /**
     * Muestra las categorías de cliente disponibles.
     */
    public void showCategorias() {
        showMensaje("******** TIPOS DE CATEGORÍA ********", true);
        showOptions(listCategorias, 3,false, true, false);
    }

    /**
     * Muestra los métodos de eliminación disponibles.
     */
    public void showMetodosEliminar() {
        showMensaje("******** METODOS DE ELIMINACIÓN DE USUARIO ********", true);
        showOptions(listMetodos, 3,false, true, false);
    }

    /**
     * Muestra los datos completos de un cliente.
     * @param cliente cliente a mostrar.
     */
    public void showCliente(Cliente cliente) {
        showMensaje("******** DATOS DEL CLIENTE " + cliente.getNombre() +" ********", true);
        showMensaje(cliente.toString(), true);
        showMensaje("*****************************************", true);
    }
}

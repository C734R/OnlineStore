package javalinos.onlinestore.vista.Consola;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaClientes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javalinos.onlinestore.utils.Utilidades.checkEmail;
import static javalinos.onlinestore.utils.Utilidades.checkNIF;
/**
 * Vista encargada de la gestión e interacción con los clientes.
 * - Permite mostrar, solicitar y modificar datos de clientes.
 * - Entidades relacionadas: ClienteDTO, CategoriaDTO
 */
public class VistaClientes extends VistaBase implements IVistaClientes {

    private final List<String> listMods = new ArrayList<>(Arrays.asList("Modificar nombre", "Modificar domicilio", "Modificar NIF", "Modificar email", "Modificar categoría"));
    private final List<String> listCategorias = new ArrayList<>(Arrays.asList("Estándar", "Premium"));
    private final List<String> listMetodos = new ArrayList<>(Arrays.asList("Por NIF", "Por Email"));
    /**
     * Constructor por defecto. Inicializa cabecera y menú principal de clienteDTOS.
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
    @Override
    public String askNIF(boolean modificar, boolean reintentar, boolean sinFin) {
        String nif;
        int intentos = 0;
        if (!reintentar) intentos = 2;
        while (intentos < 3) {
            if (modificar) nif = askString("Introduce el nuevo NIF: ", 9,15, false, false, false);
            else nif = askString("Introduce el NIF: ", 9,15, false, false, false);
            if (nif == null || !checkNIF(nif)) {
                showMensajePausa("El DNI introducido es erróneo. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
            else if (checkNIF(nif))return nif;
        }
        if (reintentar) showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    /**
     * Solicita al usuario un email válido (3 intentos).
     * @param modificar indica si es parte de una modificación.
     * @return Email introducido o null si se supera el límite de intentos.
     */
    @Override
    public String askEmail(boolean modificar, boolean reintentar, boolean sinFin) {
        String email;
        int intentos = 0;
        if (!reintentar) intentos = 2;
        while (intentos < 3) {
            if (modificar) email = askString("Introduce el nuevo email: ", 4, 50, false, false, false);
            else email = askString("Introduce el email: ", 4, 50, false, false, false);
            if (email == null || !checkEmail(email)){
                showMensajePausa("El email introducido es erróneo. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
            else return email;
        }
        if (reintentar) showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    /**
     * Solicita el modo para eliminar un cliente.
     * @return opción seleccionada (0 = salir).
     */
    @Override
    public int askMetodoEliminar() {
        while (true) {
            showMetodosEliminar();
            int metodo = askInt("Seleccione el método de eliminación", 0, listMetodos.size(), false, false, false);
            if (metodo > 0 && metodo <= listMetodos.size()) return metodo;
            else if (metodo == 0) {
                showMensajePausa("Volviendo atrás...", true);
                return metodo;
            }
            else showMensajePausa("Introduce una de las opciones listadas.", true);
        }
    }

    /**
     * Solicita al usuario que seleccione una categoría de cliente.
     * @return índice de categoría seleccionada o 0 para volver atrás.
     */
    @Override
    public int askCategoriaCliente() {
        while (true) {
            showCategorias();
            int categoria = askInt("Seleccione la categoría del cliente", 0, listCategorias.size(), false, false, false);
            if (categoria > 0 && categoria <= listCategorias.size()) return categoria;
            else if (categoria == 0) {
                showMensajePausa("Volviendo atrás...", true);
                return categoria;
            }
            else showMensajePausa("Introduce una de las opciones listadas.", true);
        }
    }

    //*************************** Mostrar datos ***************************//

    /**
     * Muestra una lista simple de clientes.
     * @param clientesDTO lista de clientes.
     */
    @Override
    public void showListClientes(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO,"CLIENTES", true, false, false);
    }

    /**
     * Muestra una lista numerada de clientes.
     * @param clientesDTO lista de clientes.
     */
    @Override
    public void showListClientesNumerada(List<ClienteDTO> clientesDTO) {
        showListGenerica(clientesDTO,"CLIENTES NUMERADOS", true, true, true);
    }

    /**
     * Muestra un listado de clientes filtrado por categoría.
     * @param clientesDTO lista de clientes filtrados.
     * @param categoriaDTO categoría seleccionada.
     */
    @Override
    public void showListClientesCategoria(List<ClienteDTO> clientesDTO, CategoriaDTO categoriaDTO) {
        showListGenerica(clientesDTO, "LISTA DE CLIENTES DE CATEGORÍA "+ categoriaDTO.getNombre(), true, false, false);
    }

    /**
     * Muestra las opciones de modificación de datos del cliente.
     */
    @Override
    public void showMods() {
        showOptions(listMods,2, false, true, false);
    }

    /**
     * Muestra las categorías de cliente disponibles.
     */
    @Override
    public void showCategorias() {
        showMensaje("******** TIPOS DE CATEGORÍA ********", true);
        showOptions(listCategorias, 3,false, true, false);
    }

    /**
     * Muestra los métodos de eliminación disponibles.
     */
    @Override
    public void showMetodosEliminar() {
        showMensaje("******** METODOS DE ELIMINACIÓN DE USUARIO ********", true);
        showOptions(listMetodos, 3,false, true, false);
    }

    /**
     * Muestra los datos completos de un cliente.
     * @param clienteDTO cliente a mostrar.
     */
    @Override
    public void showCliente(ClienteDTO clienteDTO) {
        showMensaje("******** DATOS DEL CLIENTE " + clienteDTO.getNombre() +" ********", true);
        showMensaje(clienteDTO.toString(), true);
        showMensaje("*****************************************", true);
        showMensajePausa("", true);
    }

    @Override
    public int askModificacion() {
        showMods();
        return askInt("Introduce el tipo de modificación que deseas realizar", 0, 5, false, false, true);
    }

    @Override
    public void showDatosCliente(ClienteDTO clienteDTO) {
        showMensaje("******** Datos del cliente a modificar ********", true);
        showMensaje(clienteDTO.toString(), true);
        showMensaje("***********************************************", true);

    }

}

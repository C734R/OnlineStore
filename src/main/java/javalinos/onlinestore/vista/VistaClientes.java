package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static javalinos.onlinestore.utils.Utilidades.checkEmail;
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

    private final Scanner scanner = new Scanner(System.in);

    public String askString(String mensaje, String valorPorDefecto) {
        System.out.print(mensaje + " [" + valorPorDefecto + "]: ");
        String entrada = scanner.nextLine();
        if (entrada.trim().isEmpty()) {
            return valorPorDefecto;
        }
        return entrada;
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
        String email;
        int intentos = 0;
        while (intentos < 3) {
            email = askEmail();
            if (checkEmail(email)) return email;
            else {
                if(intentos<2) showMensaje("El email introducido es erróneo. Vuelve a intentarlo.", true);
                intentos++;
            }
        }
        showMensajePausa("Has superado el número de intentos permitidos. Volviendo...", true);
        return null;
    }

    public Categoria askTipoCliente() {
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. ESTANDAR (Cuota: 0, Descuento: 0%)");
        System.out.println("2. PREMIUM (Cuota: 50, Descuento: 5%)");

        int opcion = askInt("Ingrese una opción: ", 1, 2, false, false);

        if (opcion == 1) {
            return Categoria.ESTANDAR;  // Usar el valor del enum directamente
        } else {
            return Categoria.PREMIUM;  // Usar el valor del enum directamente
        }
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

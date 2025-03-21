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
                "Salir"));
        super.setListaMenu(listaMenu);
    }

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
            nif = askString("Introduce el NIF: ", "12345678A");
            if (checkNIF(nif)) return nif;
            else {
                if(intentos < 2) showMensaje("El DNI introducido es erróneo. Vuelve a intentarlo.", true);
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
            System.out.print("Introduce el email: ");
            email = scanner.nextLine();
            if (checkEmail(email)) return email;
            else {
                if(intentos < 2) showMensaje("El email introducido es erróneo. Vuelve a intentarlo.", true);
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
            return Categoria.ESTANDAR;
        } else {
            return Categoria.PREMIUM;
        }
    }

    // Mostrar opciones de modificación
    public void showMods() {
        System.out.println("""
                ¿Qué dato deseas modificar?
                1. Nombre
                2. Domicilio
                3. NIF
                4. Email
                5. Tipo Cliente
                6. Salir
                """);
    }

    // Mostrar tipos de cliente
    public void showTipos() {
        System.out.println("""
                Tipos de clientes disponibles:
                1. ESTANDAR - Cuota: 0€, Descuento: 0%
                2. PREMIUM  - Cuota: 50€, Descuento: 5%
                """);
    }

    // Mostrar lista de clientes
    public void showListClientes(List<Cliente> clientes) {

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println("----------------------------------");
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Domicilio: " + cliente.getDomicilio());
                System.out.println("NIF: " + cliente.getNif());
                System.out.println("Email: " + cliente.getEmail());
                System.out.println("Categoría: " + cliente.getCategoria());
                System.out.println("----------------------------------");
            }
        }
        showMensajePausa("Pulsa Enter para continuar...", false);
    }

    // Mostrar clientes filtrados por tipo (PREMIUM o ESTANDAR)
    public void showListClientesTipo(List<Cliente> clientes, Categoria categoria) {
        System.out.println("Listado de clientes de tipo: " + categoria);
        boolean encontrado = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCategoria() == categoria) {
                System.out.println("----------------------------------");
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Domicilio: " + cliente.getDomicilio());
                System.out.println("NIF: " + cliente.getNif());
                System.out.println("Email: " + cliente.getEmail());
                System.out.println("----------------------------------");
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay clientes de tipo " + categoria + ".");
        }
        showMensajePausa("Pulsa Enter para continuar...", false);
    }

}

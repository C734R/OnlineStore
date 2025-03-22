package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Articulo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VistaArticulos extends VistaBase {

    public VistaArticulos() {
        String cabecera = """
                *********************************************
                **           Gestión de Artículos          **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList("Añadir artículo", "Eliminar artículo", "Listar artículos"));
        super.setListaMenu(listaMenu);
    }

    public VistaArticulos(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

    public float askPrecio(float min, float max) {
        Scanner scanner = new Scanner(System.in);
        float numero = 0;
        int intentos = 0;
        while (intentos < 3) {
            try {
                showMensaje("Introduce el precio de la excursión de " + min + "€ a " + max + "€: ", true);
                numero = scanner.nextFloat();
                if (numero >= min && numero <= max) return numero;
                else
                    showMensajePausa("Entrada fuera de rango. Introduce un número del " + min + " al " + max + ".", true);
            } catch (Exception e) {
                intentos++;
                showMensajePausa("Entrada inválida. Introduce un número del " + min + " al " + max + ".", true);
                scanner.next();
            }
        }
        showMensajePausa("Demasiados intentos fallidos. Volviendo...", true);
        return -1;
    }
}

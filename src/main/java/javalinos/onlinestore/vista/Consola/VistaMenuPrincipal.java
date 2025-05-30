package javalinos.onlinestore.vista.Consola;

import javalinos.onlinestore.vista.Interfaces.IVistaMenuPrincipal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Vista principal del sistema.
 * - Muestra el menú inicial con opciones de gestión de clientes, artículos y pedidos.
 * - Entidad relacionada: VistaBase
 */
public class VistaMenuPrincipal extends VistaBase implements IVistaMenuPrincipal {
    /**
     * Constructor por defecto.
     * Establece la cabecera con arte ASCII y el menú principal del sistema.
     */
    public VistaMenuPrincipal() {
        String cabecera = """
                ******** Ejecutando aplicación Online Store ********

                                                                \s
                           :&:                                 \s
                            #                                  \s
                            #                                  \s
                    _      _#_                                 \s
                   /|\\    ( # )              _________         \s
                  /_|_\\   / O \\                ,-'-.____()     \s
                ____|____( === )              (____.--""\"      \s
                \\_o_o_o_/ `---'               -'--'-    /\\  /\\ \s
                  XXXXXXXXXXXXXXXX  .-'''-. XXXXXXXXXXXX||__|| \s
                    __/  \\_        /(.) (.)\\    ||      |    | \s
                   '-o---o-'      ;    O    ;  (__)     | || | \s
                  XXXXXXXXXXXXXXXX \\ }---{ /XXXXXXXXXXXXXXXXXX \s
                                    '-...-'          _________ \s
                   XXXXXXXXX        XXXXXXX  /\\_/\\  (_)-----(_)\s
                   X       X        X     X ( o.o )   / (_) \\  \s
                   X       X        X     X  > - <   |_______| \s
                 XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \s
                 X                                           X \s
                 X                                           X \s
                 X      *** |   ONLINE & STORE   | ****      X \s
                 X                                           X \s
                 X                                           X \s
                 XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \s
                                                               \s
                                                               \s
                Bienvenido al sistema de gestión integral de tu tienda online.
                Iniciando Menú Principal...
                """;
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList("Gestión Clientes", "Gestión Artículos", "Gestión Pedidos"));
        super.setListaMenu(listaMenu);
    }
    /**
     * Constructor alternativo para personalizar cabecera y menú.
     * @param cabecera Título que se mostrará al usuario.
     * @param listMenu Opciones disponibles en el menú.
     */
    public VistaMenuPrincipal(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

}

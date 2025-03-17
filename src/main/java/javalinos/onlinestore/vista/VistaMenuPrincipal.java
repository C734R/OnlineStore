package javalinos.onlinestore.vista;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VistaMenuPrincipal extends VistaBase {

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
                 X       *** |   ONLINE & STORE   | ****     X
                 X                                           X \s
                 XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \s
                                                               \s
                                                               \s
                Bienvenido al sistema de gestión integral de tu tienda online.
                Iniciando MenuPrincipal...
                """;
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList("Menu Clientes", "Menu Artículos", "Menu Pedidos"));
        super.setListaMenu(listaMenu);
    }

    public VistaMenuPrincipal(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }

}

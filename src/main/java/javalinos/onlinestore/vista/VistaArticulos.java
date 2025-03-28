package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Articulo;

import java.util.*;

public class VistaArticulos extends VistaBase {

    public VistaArticulos() {
        String cabecera = """
                *********************************************
                **           Gestión de Artículos          **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList("Añadir artículo", "Eliminar artículo", "Listar artículos", "Modificar artículo", "Mostrar stock artículos"));
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
                showMensaje("Introduce el precio del articulo de " + min + "€ a " + max + "€: ", true);
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

    public void showListArticulos(List<Articulo> articulos) {
        showListGenerica(articulos,"ARTÍCULOS", true, false);
    }


    public void showListArticulosStock(Map<Articulo, Integer> articulos) {
        showMensaje("******** ARTÍCULOS CON STOCK ********", true);
        int i = 1;
        for (Map.Entry<Articulo, Integer> stockArticulo : articulos.entrySet()) {
            showMensaje("****************************************", true);
            Articulo articulo = stockArticulo.getKey();
            showMensaje(i++ + ". " +articulo.toString(), true);
            showMensaje(stockArticulo.getValue() + " Unidades", true);
            showMensaje("****************************************", true);
        }
        showMensaje("****************************************", true);
    }

    public void showListArticulosNumerada(List<Articulo> articulos) {
        showListGenerica(articulos,"ARTÍCULOS NUMERADOS", true, true);
    }

    public void showArticulo(Articulo articulo) {
        showMensaje("******** DATOS DEL ARTICULO " + articulo.getCodigo() +" ********", true);
        showMensaje(articulo.toString(), true);
        showMensaje("*****************************************", true);
    }

    public void showStockArticulos(Map<Articulo, Integer> articulos) {
        showMensaje("******** STOCK DE ARTÍCULOS ********", true);
        for (Map.Entry<Articulo, Integer> stockArticulo : articulos.entrySet()) {
            Articulo articulo = stockArticulo.getKey();
            showMensaje(articulo.getCodigo() + " - " + articulo.getDescripcion() + " - " + stockArticulo.getValue() + " Unidades", true);
        }
        showMensaje("****************************************", true);
    }
}

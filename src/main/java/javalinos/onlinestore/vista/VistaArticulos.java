package javalinos.onlinestore.vista;

import javalinos.onlinestore.modelo.primitivos.Articulo;

import java.util.*;
/**
 * Vista encargada de la interacción con el usuario para la gestión de artículos.
 * - Permite mostrar artículos y su stock.
 * - Solicita información como precios.
 */
public class VistaArticulos extends VistaBase {
    /**
     * Constructor por defecto. Inicializa la cabecera y el menú de artículos.
     */
    public VistaArticulos() {
        String cabecera = """
                *********************************************
                **           Gestión de Artículos          **
                *********************************************""";
        super.setCabecera(cabecera);
        List<String> listaMenu = new ArrayList<>(Arrays.asList("Añadir artículo", "Eliminar artículo", "Listar artículos", "Modificar artículo", "Mostrar stock artículos"));
        super.setListaMenu(listaMenu);
    }
    /**
     * Constructor alternativo con cabecera y lista de menú personalizada.
     * @param cabecera texto de cabecera.
     * @param listMenu lista de opciones del menú.
     */
    public VistaArticulos(String cabecera, List<String> listMenu) {
        super(cabecera, listMenu);
    }
    /**
     * Solicita el precio del artículo dentro de un rango dado.
     * @param min valor mínimo permitido.
     * @param max valor máximo permitido.
     * @return precio introducido o -1 si se superan los intentos.
     */
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
    /**
     * Muestra una lista de artículos sin numeración.
     * @param articulos lista de artículos.
     */
    public void showListArticulos(List<Articulo> articulos) {
        showListGenerica(articulos,"ARTÍCULOS", true, false);
    }

    /**
     * Muestra los artículos con su stock en formato detallado.
     * @param articulos mapa de artículos con unidades disponibles.
     */
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
    /**
     * Muestra una lista numerada de artículos.
     * @param articulos lista de artículos.
     */
    public void showListArticulosNumerada(List<Articulo> articulos) {
        showListGenerica(articulos,"ARTÍCULOS NUMERADOS", true, true);
    }
    /**
     * Muestra los datos detallados de un artículo.
     * @param articulo artículo a mostrar.
     */
    public void showArticulo(Articulo articulo) {
        showMensaje("******** DATOS DEL ARTICULO " + articulo.getCodigo() +" ********", true);
        showMensaje(articulo.toString(), true);
        showMensaje("*****************************************", true);
    }
    /**
     * Muestra el stock de todos los artículos.
     * @param articulos mapa de artículos y su cantidad.
     */
    public void showStockArticulos(Map<Articulo, Integer> articulos) {
        showMensaje("******** STOCK DE ARTÍCULOS ********", true);
        for (Map.Entry<Articulo, Integer> stockArticulo : articulos.entrySet()) {
            Articulo articulo = stockArticulo.getKey();
            showMensaje(articulo.getCodigo() + " - " + articulo.getDescripcion() + " - " + stockArticulo.getValue() + " Unidades", true);
        }
        showMensaje("****************************************", true);
    }
}

package javalinos.onlinestore.vista.Consola;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaArticulos;

import java.util.*;
/**
 * Vista encargada de la interacción con el usuario para la gestión de artículos.
 * - Permite mostrar artículos y su stock.
 * - Solicita información como precios.
 */
public class VistaArticulos extends VistaBase implements IVistaArticulos {
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
     * @param articulosDTO lista de artículos.
     */
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {
        showListGenerica(articulosDTO,"ARTÍCULOS", true, false);
    }

    /**
     * Muestra los artículos con su stock en formato detallado.
     * @param articuloStockMap mapa de artículos con unidades disponibles.
     */
    public void showListArticulosStock(Map<ArticuloDTO, Integer> articuloStockMap) {
        showMensaje("******** ARTÍCULOS CON STOCK ********", true);
        int i = 1;
        for (Map.Entry<ArticuloDTO, Integer> stockArticulo : articuloStockMap.entrySet()) {
            showMensaje("****************************************", true);
            ArticuloDTO articuloDTO = stockArticulo.getKey();
            showMensaje(i++ + ". " + articuloDTO.toString(), true);
            showMensaje(stockArticulo.getValue() + " Unidades", true);
            showMensaje("****************************************", true);
        }
        showMensaje("****************************************", true);
    }
    /**
     * Muestra una lista numerada de artículos.
     * @param articulosDTO lista de artículos.
     */
    public void showListArticulosNumerada(List<ArticuloDTO> articulosDTO) {
        showListGenerica(articulosDTO,"ARTÍCULOS NUMERADOS", true, true);
    }
    /**
     * Muestra los datos detallados de un artículo.
     * @param articuloDTO artículo a mostrar.
     */
    public void showArticulo(ArticuloDTO articuloDTO) {
        showMensaje("******** DATOS DEL ARTICULO " + articuloDTO.getCodigo() +" ********", true);
        showMensaje(articuloDTO.toString(), true);
        showMensaje("*****************************************", true);
    }
    /**
     * Muestra el stock de todos los artículos.
     * @param articuloStockMap mapa de artículos y su cantidad.
     */
    public void showStockArticulos(Map<ArticuloDTO, Integer> articuloStockMap) {
        showMensaje("******** STOCK DE ARTÍCULOS ********", true);
        for (Map.Entry<ArticuloDTO, Integer> stockArticulo : articuloStockMap.entrySet()) {
            ArticuloDTO articuloDTO = stockArticulo.getKey();
            showMensaje(articuloDTO.getCodigo() + " - " + articuloDTO.getDescripcion() + " - " + stockArticulo.getValue() + " Unidades", true);
        }
        showMensaje("****************************************", true);
    }
}

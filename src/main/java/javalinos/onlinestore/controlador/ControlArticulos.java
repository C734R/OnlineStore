package javalinos.onlinestore.controlador;
import java.util.List;
import java.util.Map;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.gestores.ModeloArticulos;

import static javalinos.onlinestore.utils.Utilidades.listToStr;

/**
 * Clase ControlArticulos
 */
public class ControlArticulos extends ControlBase {

    private VistaArticulos vArticulos;
    private ModeloArticulos mArticulos;

    /**
     * Constructor ControlAtículos
     * @param mStore ModeloStore para comunicación
     * @param vistaArticulos VistaArtículos para comunicación
     */
    public ControlArticulos(ModeloStore mStore, VistaArticulos vistaArticulos) {
        super(mStore);
        this.vArticulos = vistaArticulos;
        this.mArticulos = mStore.getModeloArticulos();
    }

    public ControlArticulos() {
        super();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devolver la vista artículos
     * @return VistaArtículos
     */
    public VistaArticulos getVistaArticulos() {
        return vArticulos;
    }

    /**
     * Cambiar vista artículos
     * @param vistaArticulos La nueva vista artículos
     */
    public void setVistaArticulos(VistaArticulos vistaArticulos) {
        this.vArticulos = vistaArticulos;
    }


    //*************************** Menu gestión artículos ***************************//
    /**
     * Inicia el menú de gestión de artículos
     */
    public void iniciar() {
        int opcion;
        while (true) {
            vArticulos.showCabecera();
            vArticulos.showMenu(2);
            opcion = vArticulos.askInt("Introduce una opción", 0, 5, false, false);
            switch (opcion) {
                case 1:
                    addArticulo();
                    break;
                case 2:
                    removeArticulo();
                    break;
                case 3:
                    listArticulos();
                    break;
                case 4:
                    updateArticulo();
                    break;
                case 5:
                    showStockArticulos(mArticulos.getStockArticulos());
                    vArticulos.showMensajePausa("", true);
                    break;
                case 0:
                    vArticulos.showMensaje("Volviendo al menú principal... ", true);
                    return;
                default:
                    vArticulos.showMensajePausa("Error. Opción inválida. Introduce una opción válida.", true);
            }
        }
    }

    private void showStockArticulos(Map<Articulo, Integer> stockArticulos) {
        vArticulos.showStockArticulos(stockArticulos);
    }

    //*************************** CRUD ***************************//
    /**
     * Añadir un artículo
     */
    public void addArticulo() {
        vArticulos.showMensaje("******** Añadir Artículo ********", true);

        int numeroArticulo = mArticulos.getArticulos().size();
        vArticulos.showMensaje("El siguiente código disponible es: ART00" + numeroArticulo, true);

        String descripcion = vArticulos.askString("Introduce la descripción del artículo", 250);
        if(descripcion == null) return;
        float precio = vArticulos.askPrecio(0.0f, 9999.0f);
        if(precio == -99999f) return;
        Float preparacion = vArticulos.askFloat("Introduce los días de preparación del artículo", 0.01f, 10.0f, true, false);
        if(preparacion == -99999f) return;
        int stock = vArticulos.askInt("Introduce la cantidad de stock del artículo", 0, 999, true, false);
        if(stock == -99999) return;

        Articulo articulo = mArticulos.makeArticulo(descripcion, precio, preparacion);
        if (articulo == null) {
            vArticulos.showMensajePausa("Error. No se ha podido crear el artículo.", true);
            return;
        }
        mArticulos.addArticulo(articulo);
        mArticulos.addStockArticulo(articulo, stock);
        vArticulos.showMensajePausa("Artículo añadido correctamente", true);
    }

    /**
     * Eliminar un artículo
     */
    public void removeArticulo() {
        vArticulos.showMensaje("******** Eliminar Artículo ********", true);

        List<Articulo> articulos = mArticulos.getArticulos();
        if (articulos.isEmpty()) {
            vArticulos.showMensajePausa("No hay artículos para eliminar.", true);
            return;
        }

        vArticulos.showListArticulosNumerada(articulos);
        vArticulos.showMensaje("Selecciona un artículo para eliminar: ", true);

        int seleccion = vArticulos.askInt("Introduce el número del artículo a eliminar", 1, articulos.size(), false, false);
        if(seleccion == -99999) return;

        Articulo articulo = articulos.get(seleccion-1);
        mArticulos.removeArticulo(articulo);
        mArticulos.removeStockArticulo(articulo);
        vArticulos.showMensajePausa("Artículo y stock eliminados correctamente.", true);
    }

    /**
     * Actualizar un artículo
     */
    public void updateArticulo() {
        vArticulos.showMensaje("******** Modificar Artículo ********", true);

        List<Articulo> articulos = mArticulos.getArticulos();
        if (articulos.isEmpty()) {
            vArticulos.showMensajePausa("No hay artículos disponibles para modificar.", true);
            return;
        }

        vArticulos.showListArticulosStock(mArticulos.getStockArticulos());
        int seleccion = vArticulos.askInt("Selecciona el número del artículo a modificar", 1, articulos.size(), true, true);
        if (seleccion == -99999) return;

        Articulo articuloOld = articulos.get(seleccion - 1);
        Articulo articuloNew = new Articulo(articuloOld);

        vArticulos.showMensaje("Deja un campo vacío para mantener el valor actual", true);

        String descripcion = vArticulos.askStringOpcional("Descripción actual: " + articuloOld.getDescripcion(), 250);
        if (descripcion != null && !descripcion.isEmpty()) articuloNew.setDescripcion(descripcion);

        // Precio
        Float precio = vArticulos.askPrecioOpcional("Precio actual: " + articuloOld.getPrecio(), 0.0f, 9999.0f);
        if (precio != null) articuloNew.setPrecio(precio);

        // Días de preparación
        Float preparacion = vArticulos.askFloatOpcional("Días de preparación actuales: " + articuloOld.getPreparacion(), 0.01f, 10.0f);
        if (preparacion != null) articuloNew.setPreparacion(preparacion);

        // Stock
        Integer stock = vArticulos.askIntOpcional("Stock actual: " + mArticulos.getStockArticulo(articuloOld), 0, 999);
        if (stock == null) stock = mArticulos.getStockArticulo(articuloOld);

        mArticulos.updateArticulo(articuloOld, articuloNew);
        mArticulos.removeStockArticulo(articuloOld);
        mArticulos.addStockArticulo(articuloNew, stock);
        vArticulos.showMensajePausa("Artículo y stock actualizado correctamente.", true);
    }

    //*************************** Obtener listas ***************************//


    /**
     * Listar los artículos
     */
    public void listArticulos() {
        vArticulos.showMensaje("******** Listar Artículos ********", true);

        List<Articulo> articulos = mArticulos.getArticulos();
        if (articulos.isEmpty()) {
            vArticulos.showMensajePausa("No hay artículos disponibles.", true);
            return;
        }

        vArticulos.showMensaje("Lista de artículos disponibles:", true);
        vArticulos.showListArticulos(articulos);
    }

    /**
     * Cargar los artículos del sistema
     * @param configuracion determina la configuración seleccionada
     * @return Si se cargan bien o no
     */
    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            return this.getModeloStore().getModeloArticulos().loadArticulos(configuracion);
        } else {
            return false;
        }
    }
}

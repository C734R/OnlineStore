package javalinos.onlinestore.controlador;
import java.util.List;
import java.util.Map;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.gestores.ModeloArticulos;

/**
 * Controlador para gestionar la lógica del módulo de artículos.
 */
public class ControlArticulos extends ControlBase {

    private VistaArticulos vArticulos;
    private ModeloArticulos mArticulos;

    /**
     * Constructor principal de ControlArticulos.
     * @param mStore modelo principal de la tienda
     * @param vistaArticulos vista de artículos asociada
     */
    public ControlArticulos(ModeloStore mStore, VistaArticulos vistaArticulos) {
        super(mStore);
        this.vArticulos = vistaArticulos;
        this.mArticulos = mStore.getModeloArticulos();
    }
    /**
     * Constructor por defecto de ControlArticulos.
     */
    public ControlArticulos() {
        super();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve la vista de artículos.
     * @return vista actual de artículos
     */
    public VistaArticulos getVistaArticulos() {
        return vArticulos;
    }

    /**
     * Asigna una nueva vista de artículos.
     * @param vistaArticulos vista a asignar
     */
    public void setVistaArticulos(VistaArticulos vistaArticulos) {
        this.vArticulos = vistaArticulos;
    }


    //*************************** Menu gestión artículos ***************************//

    /**
     * Inicia el menú de gestión de artículos.
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
                    showListArticulos();
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

    //*************************** CRUD ***************************//

    /**
     * Añade un nuevo artículo al sistema.
     */
    public void addArticulo() {
        vArticulos.showMensaje("******** Añadir Artículo ********", true);

        int numeroArticulo = mArticulos.getArticulos().size();
        vArticulos.showMensaje("El siguiente código disponible es: ART00" + numeroArticulo, true);

        String descripcion = vArticulos.askString("Introduce la descripción del artículo", 250);
        if(descripcion == null) return;
        float precio = vArticulos.askPrecio(0.0f, 9999.0f);
        if(precio == -99999f) return;
        int preparacion = vArticulos.askInt("Introduce los minutos de preparación del artículo", 1, 9999, true, false);
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
     * Elimina un artículo seleccionado por el usuario.
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
     * Modifica los datos de un artículo existente.
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
        Integer minutosPreparacion = vArticulos.askIntOpcional("Minutos de preparación actuales: " + articuloOld.getMinutosPreparacion(), 1, 9999);
        if (minutosPreparacion != null) articuloNew.setMinutosPreparacion(minutosPreparacion);

        // Stock
        Integer stock = vArticulos.askIntOpcional("Stock actual: " + mArticulos.getStockArticulo(articuloOld), 0, 999);
        if (stock == null) stock = mArticulos.getStockArticulo(articuloOld);

        mArticulos.updateArticulo(articuloOld, articuloNew);
        mArticulos.removeStockArticulo(articuloOld);
        mArticulos.addStockArticulo(articuloNew, stock);
        vArticulos.showMensajePausa("Artículo y stock actualizado correctamente.", true);
    }

    //*************************** Mostrar listados ***************************//


    /**
     * Muestra la lista completa de artículos.
     */
    public void showListArticulos() {
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
     * Muestra el stock de todos los artículos.
     * @param stockArticulos mapa con artículo y cantidad disponible
     */
    private void showStockArticulos(Map<Articulo, Integer> stockArticulos) {
        vArticulos.showStockArticulos(stockArticulos);
    }

    /**
     * Carga los artículos desde el sistema según la configuración.
     * @param configuracion código de configuración
     * @return true si se cargaron correctamente, false si hubo error
     */
    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            return this.getModeloStore().getModeloArticulos().loadArticulos(configuracion);
        } else {
            return false;
        }
    }
}

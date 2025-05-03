package javalinos.onlinestore.controlador;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.modelo.gestores.Local.ModeloArticulosLocal;

/**
 * Controlador para gestionar la lógica del módulo de artículos.
 */
public class ControlArticulos extends ControlBase {

    private VistaArticulos vArticulos;
    private IModeloArticulos mArticulos;

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
            opcion = vArticulos.askInt("Introduce una opción", 0, 5, false, false, true);
            switch (opcion) {
                case 1:
                    addArticulo();
                    break;
                case 2:
                    removeArticulo();
                    break;
                case 3:
                    showListArticulos();
                    vArticulos.showMensajePausa("",true);
                    break;
                case 4:
                    updateArticulo();
                    break;
                case 5:
                    showStockArticulos();
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
    public void addArticulo()
    {
        ArticuloDTO articuloDTO;
        vArticulos.showMensaje("******** Añadir Artículo ********", true);
        try
        {
            int numeroArticulo = mArticulos.getArticulosDTO().size();
            vArticulos.showMensaje("El siguiente código disponible es: ART00" + numeroArticulo, true);
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al obtener el número del último artículo.", true);
            return;
        }
        String descripcion = vArticulos.askString("Introduce la descripción del artículo: ", 1, 250, true, false, true);
        if(descripcion == null) return;
        float precio = vArticulos.askPrecio(0.0f, 9999.0f);
        if(precio == -99999f) return;
        int preparacion = vArticulos.askInt("Introduce los minutos de preparación del artículo", 1, 9999, true, false, true);
        if(preparacion == -99999f) return;
        int stock = vArticulos.askInt("Introduce la cantidad de stock del artículo", 0, 999, true, false, true);
        if(stock == -99999) return;
        try
        {
            articuloDTO = mArticulos.makeArticulo(descripcion, precio, preparacion);
            if (articuloDTO == null) {
                vArticulos.showMensajePausa("Error. No se ha podido crear el artículo.", true);
                return;
            }
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al crear un nuevo artículo." + e, true);
            return;
        }
        try
        {
            mArticulos.addArticulo(articuloDTO);
            mArticulos.addArticuloStock(articuloDTO, stock);
            vArticulos.showMensajePausa("Artículo añadido correctamente", true);
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al añadir el artículo y stock." + e, true);
        }
    }

    /**
     * Elimina un artículo seleccionado por el usuario.
     */
    public void removeArticulo()
    {
        List<ArticuloDTO> articulosDTO;
        vArticulos.showMensaje("******** Eliminar Artículo ********", true);
        try
        {
            articulosDTO = mArticulos.getArticulosDTO();
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al obtener la lista de artículos." + e, true);
            return;
        }
        if (articulosDTO.isEmpty())
        {
            vArticulos.showMensajePausa("No hay artículos para eliminar.", true);
            return;
        }
        vArticulos.showListArticulosNumerada(articulosDTO);
        vArticulos.showMensaje("Selecciona un artículo para eliminar: ", true);

        int seleccion = vArticulos.askInt("Introduce el número del artículo a eliminar", 1, articulosDTO.size(), true, true, true);
        if(seleccion == -99999) return;

        ArticuloDTO articuloDTO = articulosDTO.get(seleccion-1);
        try
        {
            mArticulos.removeArticulo(articuloDTO);
            vArticulos.showMensajePausa("Artículo y stock eliminados correctamente.", true);
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al eliminar artículo." + e, true);
        }
    }

    /**
     * Modifica los datos de un artículo existente.
     */
    public void updateArticulo()
    {
        List<ArticuloDTO> articulosDTO;
        boolean modificado = false;
        Integer stockNew;
        vArticulos.showMensaje("******** Modificar Artículo ********", true);
        try
        {
            articulosDTO = mArticulos.getArticulosDTO();
            if (articulosDTO.isEmpty()) {
                vArticulos.showMensajePausa("No hay artículos disponibles para modificar.", true);
                return;
            }
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al obtener el listado de artículos." + e, true);
            return;
        }
        try
        {
            vArticulos.showListArticulosStock(mArticulos.getArticuloStocksDTO());
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al mostrar el listado de stocks." + e, true);
            return;
        }
        int seleccion = vArticulos.askInt("Selecciona el número del artículo a modificar", 1, articulosDTO.size(), true, true, true);
        if (seleccion == -99999) return;

        ArticuloDTO articuloDTOOld = articulosDTO.get(seleccion - 1);
        ArticuloDTO articuloDTONew = new ArticuloDTO(articuloDTOOld);

        vArticulos.showMensaje("Deja un campo vacío para mantener el valor actual", true);

        String descripcion = vArticulos.askStringOpcional("Descripción actual: " + articuloDTOOld.getDescripcion(), 250);
        if (descripcion != null && !descripcion.isEmpty()) {
            articuloDTONew.setDescripcion(descripcion);
            modificado = true;
        }
        // Precio
        Float precio = vArticulos.askPrecioOpcional("Precio actual: " + articuloDTOOld.getPrecio(), 0.0f, 9999.0f);
        if (precio != null) {
            articuloDTONew.setPrecio(precio);
            modificado = true;
        }
        // Días de preparación
        Integer minutosPreparacion = vArticulos.askIntOpcional("Minutos de preparación actuales: " + articuloDTOOld.getMinutosPreparacion(), 1, 9999);
        if (minutosPreparacion != null) {
            articuloDTONew.setMinutosPreparacion(minutosPreparacion);
            modificado = true;
        }
        // Stock
        try
        {
            stockNew = vArticulos.askIntOpcional("Stock actual: " + mArticulos.getStockArticulo(articuloDTOOld), 0, 999);
            if (stockNew != null) modificado = true;
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al mostrar el stock actual. " + e, true);
            return;
        }
        if (!modificado) return;
        try {
            mArticulos.updateArticuloStockSP(articuloDTONew, stockNew);
            vArticulos.showMensajePausa("Artículo y stock actualizado correctamente.", true);
        } catch (Exception e) {
            vArticulos.showMensajePausa("Error al actualizar el stock. " + e, true);
        }

    }

    //*************************** Mostrar listados ***************************//


    /**
     * Muestra la lista completa de artículos.
     */
    public void showListArticulos() {
        List<ArticuloDTO> articulosDTO;
        vArticulos.showMensaje("******** Listar Artículos ********", true);
        try
        {
            articulosDTO = mArticulos.getArticulosDTO();
        }
        catch (Exception e)
        {
            vArticulos.showMensajePausa("Error al obtener la lista de artículos. " + e, true);
            return;
        }
        if (articulosDTO.isEmpty()) {
            vArticulos.showMensajePausa("No hay artículos disponibles.", true);
            return;
        }

        vArticulos.showMensaje("Lista de artículos disponibles:", true);
        vArticulos.showListArticulos(articulosDTO);
    }

    /**
     * Muestra el stock de todos los artículos.
     */
    private void showStockArticulos() {
        Map<ArticuloDTO, Integer> stockArticulos;
        try{
            stockArticulos = mArticulos.getArticuloStocksDTO();
        } catch (Exception e) {
            vArticulos.showMensajePausa("Error al obtener los stocks de los articulos", true);
            return;
        }
        vArticulos.showStockArticulos(stockArticulos);
    }

    /**
     * Carga los artículos desde el sistema según la configuración.
     */
    public void loadArticulos() throws Exception {
            getModeloStore().getModeloArticulos().loadArticulos();
    }
}

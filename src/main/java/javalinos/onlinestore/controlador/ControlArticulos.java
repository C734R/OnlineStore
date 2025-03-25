package javalinos.onlinestore.controlador;
import java.util.List;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaArticulos;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.controlador.ControlBase;

public class ControlArticulos extends ControlBase {

    private VistaArticulos vArticulos;
    private ModeloArticulos mArticulos;

    public ControlArticulos(ModeloStore mStore, VistaArticulos vistaArticulos) {
        super(mStore);
        this.vArticulos = vistaArticulos;
        this.mArticulos = mStore.getModeloArticulos();
    }

    public ControlArticulos() {
        super();
    }

    public VistaArticulos getVistaArticulos() {
        return vArticulos;
    }

    public void setVistaArticulos(VistaArticulos vistaArticulos) {
        this.vArticulos = vistaArticulos;
    }

    public void iniciar() {
        int opcion;
        while (true) {
            vArticulos.showCabecera();
            vArticulos.showMenu(2);
            opcion = vArticulos.askInt("Introduce una opción", 0, 3, false, false);
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
                case 0:
                    vArticulos.showMensaje("Volviendo al menú principal... ", true);
                    return;
                default:
                    vArticulos.showMensajePausa("Error. Opción inválida. Introduce una opción válida.", true);
            }
        }
    }

    public void addArticulo() {
        // Queremos obtener antes la lista de artículos:
        List<Articulo> articulos = mArticulos.getInstancia().getArticulos();

        // Añadir un artículo al código ya creado para que no sea del 1 al 9.
        int codigoMax = 0;
        if (!articulos.isEmpty()) {
            for (Articulo articulo : articulos) {
                String codigoArticulo = articulo.getCodigo(); // lo pasamos a string

                try {
                    String numeroCodigo = codigoArticulo.replaceAll("[^0-9]", ""); // pasamos de vuelta a string
                    int codigoArticuloInt = Integer.parseInt(numeroCodigo);
                    if (codigoArticuloInt > codigoMax) {
                        codigoMax = codigoArticuloInt;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error al analizar el código del artículo: " + codigoArticulo);
                }
            }
        } else {
            codigoMax = 0;
        }

        // Incrementamos el código máximo encontrado para obtener el siguiente código disponible, para que no se estanque en 9..
        int nuevoCodigo = codigoMax + 1;
        System.out.println("\nEl siguiente código disponible es: ART00" + nuevoCodigo);

        String descripcion = vArticulos.askString("Introduce la descripción del artículo", 250);
        Float precio = vArticulos.askPrecio(0.0f, 9999.0f);
        Float preparacion = vArticulos.askFloat("Introduce el tiempo de preparación del artículo", 0.01f, 10.0f, true, false);
        Integer stock = vArticulos.askInt("Introduce la cantidad de stock del artículo", 0, 999, true, false);

       //añadimos nuestro articulo
        Articulo articulo = mArticulos.makeArticulo(descripcion, precio, preparacion, stock);

        // Asignamos el nuevo código al artículo
        articulo.setCodigo("ART" + String.format("%03d", nuevoCodigo));

        // Verificamos si se ha añadido correctamente el artículo
        boolean exito = ModeloArticulos.getInstancia().addArticulo(articulo);
        if (exito) {
            vArticulos.showMensaje("Artículo añadido correctamente.", true);
        } else {
            vArticulos.showMensaje("Error al añadir el artículo.", true);
        }
    }
 // Cómo eliminar el artículo. Seleccionamos array.
    public void removeArticulo() {
        List<Articulo> articulos = ModeloArticulos.getInstancia().getArticulos();
        if (articulos.isEmpty()) {
            vArticulos.showMensaje("No hay artículos para eliminar.", true); // ya hay creado así quue no creo que se activa
            return;
        }

        // Mostramos la lista de artículos para eliminar
        vArticulos.showMensaje("Selecciona un artículo para eliminar:", false);
        for (int i = 0; i < articulos.size(); i++) {
            vArticulos.showMensaje((i + 1) + ". " + articulos.get(i).getCodigo() + " - " + articulos.get(i).getDescripcion() + "\n", false);
        }

        // Pedimos la selección del usuario
        int seleccion = vArticulos.askInt("Introduce el número del artículo a eliminar", 1, articulos.size(), false, false);
        Articulo articuloAEliminar = articulos.get(seleccion - 1);

        // Eliminamos el artículo
        boolean exito = ModeloArticulos.getInstancia().removeArticulo(articuloAEliminar);
        if (exito) {
            vArticulos.showMensaje("Artículo eliminado correctamente.", true);
        } else {
            vArticulos.showMensaje("Error al eliminar el artículo.", true);
        }
    }
        // Queremos listar los articulos
    public void listArticulos() {
        List<Articulo> articulos = ModeloArticulos.getInstancia().getArticulos();
        if (articulos.isEmpty()) {
            vArticulos.showMensaje("No hay artículos disponibles.", true);
            return;
        }
        // preguntar Alan
        vArticulos.showMensaje("Lista de artículos disponibles:", true);
        for (Articulo articulo : articulos) {
            System.out.println(articulo.getCodigo() + " - " + articulo.getDescripcion() + " - $" + articulo.getPrecio());
        }
    }
    // Cargamos los artículos. Preguntar a Alan**
    public boolean loadArticulos(int configuracion) {
        if (configuracion == 0) {
            return this.getModeloStore().getModeloArticulos().loadArticulos(configuracion);
        } else {
            return false;
        }
    }
}

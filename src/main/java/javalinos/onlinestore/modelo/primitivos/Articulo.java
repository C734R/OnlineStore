package javalinos.onlinestore.modelo.primitivos;

/**
 * Representa los artículos incluidos en nuestro programa.
 * Contiene datos como código, descripción, precio y tiempo de preparación.
 */
public class Articulo {
    private String codigo;
    private String descripcion;
    private Float precio;
    private Integer minutosPreparacion; // días!

    /**
     * Constructor de un artículo.
     * @param codigo código identificador del artículo.
     * @param descripcion descripción del artículo.
     * @param precio precio del artículo.
     * @param minutosPreparacion tiempo de preparación en minutos.
     */
    public Articulo(String codigo, String descripcion, Float precio, Integer minutosPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.minutosPreparacion = minutosPreparacion;
    }

    /**
     * Constructor vacío. Inicializa con valores por defecto.
     */
    public Articulo() {
        this.codigo = "";
        this.descripcion = "";
        this.precio = 0.0f;
        this.minutosPreparacion = null;
    }

    /**
     * Constructor de copia.
     * @param articulo artículo a copiar.
     */
    public Articulo(Articulo articulo) {
        this.codigo = articulo.getCodigo();
        this.descripcion = articulo.getDescripcion();
        this.precio = articulo.getPrecio();
        this.minutosPreparacion = articulo.getMinutosPreparacion();
    }

    /**
     * Devuelve el código del artículo.
     * @return código del artículo.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece un nuevo código para el artículo.
     * @param codigo nuevo código.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve la descripción del artículo.
     * @return descripción textual del artículo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del artículo.
     * @param descripcion nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el precio del artículo.
     * @return precio del artículo.
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Establece un nuevo precio para el artículo.
     * @param precio nuevo precio.
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el tiempo de preparación en minutos.
     * @return tiempo en minutos.
     */
    public Integer getMinutosPreparacion() {
        return minutosPreparacion;
    }

    /**
     * Establece el tiempo de preparación del artículo.
     * @param minutosPreparacion minutos requeridos para preparar el artículo.
     */
    public void setMinutosPreparacion(Integer minutosPreparacion) {
        this.minutosPreparacion = minutosPreparacion;
    }

    /**
     * Devuelve la representación textual del artículo.
     * @return string con los datos del artículo.
     */
    @Override
    public String toString() {
        return  "Código: " + codigo + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Precio " + precio + " €\n" +
                "Tiempo de preparacion: " + minutosPreparacion + " minutos";
    }
}

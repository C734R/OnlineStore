package javalinos.onlinestore.modelo.primitivos;

/**
 * Representa los artículos incluidos en nuestro programa.
 */
public class Articulo {
    private String codigo;
    private String descripcion;
    private Float precio;
    private Integer minutosPreparacion; // días!

    /**
     * Constructor de un artículo.
     *
     * @param codigo Codigo de artículo
     * @param descripcion Descripción de un artículo
     * @param precio Precio del artículo
     * @param minutosPreparacion Tiempo de preparación de un artículo
     */
    public Articulo(String codigo, String descripcion, Float precio, Integer minutosPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.minutosPreparacion = minutosPreparacion;
    }

    /**
     * Constructor con sobre carga
     */
    public Articulo() {
        this.codigo = "";
        this.descripcion = "";
        this.precio = 0.0f;
        this.minutosPreparacion = null;
    }

    /**
     * Constructor de copia
     */
    public Articulo(Articulo articulo) {
        this.codigo = articulo.getCodigo();
        this.descripcion = articulo.getDescripcion();
        this.precio = articulo.getPrecio();
        this.minutosPreparacion = articulo.getMinutosPreparacion();
    }

    /**
     * Devuelve el codigo de artículo
     *
     * @return El código de artículo deseado.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Configura un código de artículo
     *
     * @param codigo El nuevo código de artículo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve la descripción del artículo.
     *
     * @return String con la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripción de un artículo.
     *
     * @param descripcion La nueva descripción del artículo.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el precio de un artículo
     *
     * @return Float con el precio de artículo
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Configurar nuevo precio de artículo
     *
     * @param precio El nuevo precio
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el tiempo de preparación del artículo
     *
     * @return Float con el tiempo de preparación del artículo
     */
    public Integer getMinutosPreparacion() {
        return minutosPreparacion;
    }

    /**
     * Cambia el tiempo de preparación del artículo
     *
     * @param minutosPreparacion El nuevo tiempo de preparación
     */
    public void setMinutosPreparacion(Integer minutosPreparacion) {
        this.minutosPreparacion = minutosPreparacion;
    }

    /**
     * Devuelve el string completo de un artículo
     *
     * @return String con todos los componentes de un artículo
     */
    @Override
    public String toString() {
        return  "Código: " + codigo + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Precio " + precio + " €\n" +
                "Tiempo de preparacion: " + minutosPreparacion + " minutos";
    }
}

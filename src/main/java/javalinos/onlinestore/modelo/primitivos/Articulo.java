package javalinos.onlinestore.modelo.primitivos;

/**
 * Representa los artículos incluidos en nuestro programa.
 */
public class Articulo {
    private String codigo;
    private String descripcion;
    private Float precio;
    private Float preparacion; // días!
    private Integer stock;

    /**
     * Constructor de un artículo.
     *
     * @param codigo Codigo de artículo
     * @param descripcion Descripción de un artículo
     * @param precio Precio del artículo
     * @param preparacion Tiempo de preparación de un artículo
     * @param stock Stock disponible de un artículo
     */
    public Articulo(String codigo, String descripcion, Float precio, Float preparacion, Integer stock) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.preparacion = preparacion;
        this.stock = stock;
    }

    /**
     * Constructor con sobre carga
     */
    public Articulo() {
        this.codigo = "";
        this.descripcion = "";
        this.precio = 0.0f;
        this.preparacion = null;
        this.stock = 0;
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
    public Float getPreparacion() {
        return preparacion;
    }

    /**
     * Cambia el tiempo de preparación del artículo
     *
     * @param preparacion El nuevo tiempo de preparación
     */
    public void setPreparacion(Float preparacion) {
        this.preparacion = preparacion;
    }

    /**
     * Devuelve el stock de un artículo
     *
     * @return Int con la cantidad de stock del artículo
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * Cambia el Stock de un artículo
     *
     * @param stock El nuevo stock del artículo
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * Devuelve el string completo de un artículo
     *
     * @return String con todos los componentes de un artículo
     */
    @Override
    public String toString() {
        return  "\nCódigo: " + codigo + "\n" +
                "Descripcion: " + descripcion + "\n" +
                "Precio " + precio + " €\n" +
                "Tiempo de preparacion: " + preparacion + " días\n" +
                "Stock: " + stock + " unidades\n";
    }
}

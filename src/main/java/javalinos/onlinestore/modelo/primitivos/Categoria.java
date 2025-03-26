package javalinos.onlinestore.modelo.primitivos;

/**
 * Contiene una enumeración con el tipo de categoría de clientes
 */
public enum Categoria {

    ESTANDAR("Estándar", 0.0f, 0.0f), PREMIUM("Premium", 30.0f, 0.20f);

    private final String nombre;
    private final Float cuota;
    private final Float descuento;

    /**
     * Constructor de categoría
     *
     * @param nombre Nombre de la categoría
     * @param cuota Cuota a pagar
     * @param descuento Descuento aplicado a los pedidos
     */
    Categoria(String nombre, Float cuota, Float descuento) {
        this.nombre = nombre;
        this.cuota = cuota;
        this.descuento = descuento;
    }

    /**
     * Devuelve el nombre de la categoría
     *
     * @return String con el nombre de la categría
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la cuota de la categría
     *
     * @return Float con la cuota de la categría
     */
    public Float getCuota() {
        return cuota;
    }

    /**
     * Devuelve el descuento aplicado a la categría
     *
     * @return Float con el descuento de la categría
     */
    public Float getDescuento() {
        return descuento;
    }

    /**
     * Metodo para devolver una categoría completa
     *
     * @return String con una categoría completa
     */
    @Override
    public String toString() {
        return  "Categoría: " + nombre + "\n" +
                "Cuota: " + cuota + "\n" +
                "Descuento: " + descuento*100 + "%";
    }
}

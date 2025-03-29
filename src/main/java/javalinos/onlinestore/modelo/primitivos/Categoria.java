package javalinos.onlinestore.modelo.primitivos;

/**
 * Contiene una enumeración con el tipo de categoría de clientes.
 * Cada categoría tiene un nombre, una cuota asociada y un porcentaje de descuento.
 */
public enum Categoria {

    ESTANDAR("Estándar", 0.0f, 0.0f), PREMIUM("Premium", 30.0f, 0.20f);

    private final String nombre;
    private final Float cuota;
    private final Float descuento;

    /**
     * Constructor de categoría.
     * @param nombre nombre de la categoría.
     * @param cuota cuota mensual a pagar.
     * @param descuento porcentaje de descuento aplicado a los pedidos.
     */
    Categoria(String nombre, Float cuota, Float descuento) {
        this.nombre = nombre;
        this.cuota = cuota;
        this.descuento = descuento;
    }

    /**
     * Devuelve el nombre de la categoría.
     * @return nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la cuota mensual de la categoría.
     * @return cuota en euros.
     */
    public Float getCuota() {
        return cuota;
    }

    /**
     * Devuelve el descuento aplicado a los pedidos.
     * @return porcentaje de descuento.
     */
    public Float getDescuento() {
        return descuento;
    }

    /**
     * Devuelve una representación textual de la categoría.
     * @return cadena con nombre, cuota y descuento.
     */
    @Override
    public String toString() {
        return  "Categoría: " + nombre + "\n" +
                "Cuota: " + cuota + "\n" +
                "Descuento: " + descuento*100 + "%";
    }
}

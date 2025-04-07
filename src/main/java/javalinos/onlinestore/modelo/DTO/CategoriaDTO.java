package javalinos.onlinestore.modelo.DTO;

/**
 * Contiene una enumeración con el tipo de categoría de clientes.
 * Cada categoría tiene un nombre, una cuota asociada y un porcentaje de descuento.
 */
public class CategoriaDTO {

    private String nombre;
    private Float cuota;
    private Float descuento;

    /**
     * Constructor de categoría.
     * @param nombre nombre de la categoría.
     * @param cuota cuota mensual a pagar.
     * @param descuento porcentaje de descuento aplicado a los pedidos.
     */
    public CategoriaDTO(String nombre, Float cuota, Float descuento) {
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Devuelve la cuota mensual de la categoría.
     * @return cuota en euros.
     */
    public Float getCuota() {
        return cuota;
    }

    public void setCuota(Float cuota) {
        this.cuota = cuota;
    }
    /**
     * Devuelve el descuento aplicado a los pedidos.
     * @return porcentaje de descuento.
     */
    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
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

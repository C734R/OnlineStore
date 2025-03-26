package javalinos.onlinestore.modelo.primitivos;

/**
 * Contiene todos los datos de un cliente.
 */
public class Cliente {

    private String nombre;
    private String domicilio;
    private String nif;
    private String email;
    private Categoria categoria;

    /**
     * Constructor de un cliente.
     *
     * @param nombre String con Nombre del cliente
     * @param domicilio String con Domicilio del cliente
     * @param nif String con NIF del cliente
     * @param email String con Email del cliente
     * @param categoria enumCategoría del cliente
     */
    public Cliente(String nombre, String domicilio, String nif, String email, Categoria categoria) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
        this.categoria = categoria;
    }

    /**
     * Constructor de sobrecarga
     */
    public Cliente() {
        this.nombre = "";
        this.domicilio = "";
        this.nif = "";
        this.email = "";
        this.categoria = null;
    }

    /**
     * Cambiar nombre de un cliente
     *
     * @param nombre El nuevo nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Cambiar el domicilio del cliente
     *
     * @param domicilio El nuevo domicilio del cliente
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Cambiar NIF del cliente
     *
     * @param nif Nuevo NIF del cliente
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Cambiar Email del cliente
     *
     * @param email Nuevo Email del cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Cambiar categoría del cliente
     *
     * @param categoria Nueva categoría del cliente
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtener el nombre del cliente.
     *
     * @return String con el nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el domicilio del cliente
     *
     * @return String con el domicilio del cliente
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * Devuelve el NIF del cliente
     *
     * @return String con el NIF del cliente
     */
    public String getNif() {
        return nif;
    }

    /**
     * Devuelve le Email del cliente
     *
     * @return String con el email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Devuelve la categoría del cliente
     *
     * @return Enumeración con la categoría del cliente
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Devuelve la Cuota que tiene que pagar el cliente
     *
     * @return Float con la cuota a pagar del cliente
     */
    public Float getCuota() {
        return this.categoria.getCuota();
    }

    /**
     * Devuelve el descuento del cliente
     *
     * @return Float con el descuento del cliente
     */
    public Float getDescuento() {
        return this.categoria.getDescuento();
    }

    /**
     * Devuelve un cliente completo
     *
     * @return String con un cliente completo.
     */
    @Override
    public String toString() {
        return  "Nombre: " + nombre + "\n" +
                "Domicilio: " + domicilio + "\n" +
                "NIF: " + nif + "\n" +
                "Email: " + email + "\n" +
                categoria.toString();
    }
}

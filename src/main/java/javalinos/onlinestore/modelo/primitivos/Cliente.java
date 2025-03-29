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
     * @param nombre nombre del cliente.
     * @param domicilio domicilio del cliente.
     * @param nif NIF del cliente.
     * @param email email del cliente.
     * @param categoria categoría asociada al cliente.
     */
    public Cliente(String nombre, String domicilio, String nif, String email, Categoria categoria) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
        this.categoria = categoria;
    }

    /**
     * Constructor vacío. Inicializa con valores por defecto.
     */
    public Cliente() {
        this.nombre = "";
        this.domicilio = "";
        this.nif = "";
        this.email = "";
        this.categoria = null;
    }

    /**
     * Constructor de copia.
     * @param clienteOld cliente a copiar.
     */
    public Cliente(Cliente clienteOld) {
        this.nombre = clienteOld.getNombre();
        this.domicilio = clienteOld.getDomicilio();
        this.nif = clienteOld.getNif();
        this.email = clienteOld.getEmail();
        this.categoria = clienteOld.getCategoria();
    }

    /**
     * Cambia el nombre del cliente.
     * @param nombre nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Cambia el domicilio del cliente.
     * @param domicilio nuevo domicilio.
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Cambia el NIF del cliente.
     * @param nif nuevo NIF.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Cambia el email del cliente.
     * @param email nuevo email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Cambia la categoría del cliente.
     * @param categoria nueva categoría.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el domicilio del cliente.
     * @return domicilio del cliente.
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * Obtiene el NIF del cliente.
     * @return NIF del cliente.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Obtiene el email del cliente.
     * @return email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene la categoría del cliente.
     * @return categoría del cliente.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Obtiene la cuota a pagar del cliente, según su categoría.
     * @return cuota en euros.
     */
    public Float getCuota() {
        return this.categoria.getCuota();
    }

    /**
     * Obtiene el descuento disponible para el cliente.
     * @return porcentaje de descuento.
     */
    public Float getDescuento() {
        return this.categoria.getDescuento();
    }

    /**
     * Devuelve los datos del cliente en formato texto.
     * @return cadena con los datos del cliente.
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

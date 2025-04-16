package javalinos.onlinestore.modelo.DTO;

import javalinos.onlinestore.modelo.Entidades.Categoria;
import javalinos.onlinestore.modelo.Entidades.Cliente;

/**
 * Contiene todos los datos de un cliente.
 */
public class ClienteDTO {

    private String nombre;
    private String domicilio;
    private String nif;
    private String email;
    private CategoriaDTO categoriaDTO;

    /**
     * Constructor de un cliente.
     * @param nombre nombre del cliente.
     * @param domicilio domicilio del cliente.
     * @param nif NIF del cliente.
     * @param email email del cliente.
     * @param categoriaDTO categoría asociada al cliente.
     */
    public ClienteDTO(String nombre, String domicilio, String nif, String email, CategoriaDTO categoriaDTO) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
        this.categoriaDTO = categoriaDTO;
    }

    public ClienteDTO(Cliente cliente, CategoriaDTO categoriaDTO) {
        this.nombre = cliente.getNombre();
        this.domicilio = cliente.getDomicilio();
        this.nif = cliente.getNif();
        this.email = cliente.getEmail();
        this.categoriaDTO = categoriaDTO;
    }

    /**
     * Constructor vacío. Inicializa con valores por defecto.
     */
    public ClienteDTO() {
        this.nombre = "";
        this.domicilio = "";
        this.nif = "";
        this.email = "";
        this.categoriaDTO = null;
    }

    /**
     * Constructor de copia.
     * @param clienteDTO cliente a copiar.
     */
    public ClienteDTO(ClienteDTO clienteDTO) {
        this.nombre = clienteDTO.getNombre();
        this.domicilio = clienteDTO.getDomicilio();
        this.nif = clienteDTO.getNif();
        this.email = clienteDTO.getEmail();
        this.categoriaDTO = clienteDTO.getCategoria();
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
     * @param categoriaDTO nueva categoría.
     */
    public void setCategoria(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
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
    public CategoriaDTO getCategoria() {
        return categoriaDTO;
    }

    /**
     * Obtiene la cuota a pagar del cliente, según su categoría.
     * @return cuota en euros.
     */
    public Float getCuota() {
        return this.categoriaDTO.getCuota();
    }

    /**
     * Obtiene el descuento disponible para el cliente.
     * @return porcentaje de descuento.
     */
    public Float getDescuento() {
        return this.categoriaDTO.getDescuento();
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
                categoriaDTO.toString();
    }
}

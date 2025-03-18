package javalinos.onlinestore.modelo.primitivos;

public class Cliente {

    private String nombre;
    private String domicilio;
    private String nif;
    private String email;
    private Categoria categoria;

    public Cliente(String nombre, String domicilio, String nif, String email, Categoria categoria) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
        this.categoria = categoria;
    }

    public Cliente() {
        this.nombre = "";
        this.domicilio = "";
        this.nif = "";
        this.email = "";
        this.categoria = null;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getNif() {
        return nif;
    }

    public String getEmail() {
        return email;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Float getCuota() {
        return this.categoria.getCuota();
    }

    public Float getDescuento() {
        return this.categoria.getDescuento();
    }

    @Override
    public String toString() {
        return  "Nombre: " + nombre + "\n" +
                "Domicilio: " + domicilio + "\n" +
                "NIF: " + nif + "\n" +
                "Email: " + email + "\n" +
                categoria.toString();
    }
}

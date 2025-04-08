package javalinos.onlinestore.modelo.Entidades;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;

public class Cliente {
    private Integer id;
    private String nombre;
    private String domicilio;
    private String email;
    private String nif;
    private Integer categoria;

    public Cliente(Integer id, String nombre, String domicilio, String email, String nif, Integer categoria, Float cuota, Float descuento) {
        this.id = id;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.email = email;
        this.nif = nif;
        this.categoria = categoria;
    }

    public Cliente(ClienteDTO clienteDTO, Categoria categoria) {
        this.id = null;
        this.nombre = clienteDTO.getNombre();
        this.domicilio = clienteDTO.getDomicilio();
        this.email = clienteDTO.getEmail();
        this.nif = clienteDTO.getNif();
        this.categoria = categoria.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

}

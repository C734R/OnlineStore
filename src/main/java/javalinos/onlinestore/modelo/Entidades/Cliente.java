package javalinos.onlinestore.modelo.Entidades;

import jakarta.persistence.*;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String domicilio;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String nif;
    @Transient
    private Integer categoriaId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoriaId")
    private Categoria categoria;

    public Cliente() {}

    public Cliente(Integer id, String nombre, String domicilio, String email, String nif, Integer categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.email = email;
        this.nif = nif;
        this.categoriaId = categoriaId;
    }

    public Cliente(ClienteDTO clienteDTO, Categoria categoria) {
        this.id = null;
        this.nombre = clienteDTO.getNombre();
        this.domicilio = clienteDTO.getDomicilio();
        this.email = clienteDTO.getEmail();
        this.nif = clienteDTO.getNif();
        this.categoria = categoria;
        this.categoriaId = categoria != null ? categoria.getId() : null;
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

    public Integer getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(Integer categoria) {
        this.categoriaId = categoria;
    }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.categoriaId = categoria != null ? categoria.getId() : null;
    }

}

package javalinos.onlinestore.modelo.Entidades;

import jakarta.persistence.*;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false)
    private Float cuota;
    @Column(nullable = false)
    private Float descuento;

    public Categoria() {}

    public Categoria(Integer id, String nombre, Float cuota, Float descuento) {
        this.id = id;
        this.nombre = nombre;
        this.cuota = cuota;
        this.descuento = descuento;
    }

    public Categoria(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
        this.cuota = categoria.getCuota();
        this.descuento = categoria.getDescuento();
    }

    public Categoria(CategoriaDTO categoriaDTO)
    {
        this.id = null;
        this.nombre = categoriaDTO.getNombre();
        this.cuota = categoriaDTO.getCuota();
        this.descuento = categoriaDTO.getDescuento();
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

    public Float getCuota() {
        return cuota;
    }
    public void setCuota(Float cuota) {
        this.cuota = cuota;
    }

    public Float getDescuento() {
        return descuento;
    }
    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }
}

package javalinos.onlinestore.modelo.Entidades;

import jakarta.persistence.*;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;

@Entity
@Table(name = "articulo")
public class Articulo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Float precio;
    @Column(nullable = false)
    private Integer minutosPreparacion;

    public Articulo() {}

    public Articulo(Integer id, String codigo, String descripcion, Float precio, Integer minutosPreparacion)
    {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.minutosPreparacion = minutosPreparacion;
    }

    public Articulo(ArticuloDTO articuloDTO)
    {
        this.id = null;
        this.codigo = articuloDTO.getCodigo();
        this.descripcion = articuloDTO.getDescripcion();
        this.precio = articuloDTO.getPrecio();
        this.minutosPreparacion = articuloDTO.getMinutosPreparacion(preparacion);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getMinutosPreparacion() {
        return minutosPreparacion;
    }
    public void setMinutosPreparacion(Integer minutosPreparacion) {
        this.minutosPreparacion = minutosPreparacion;
    }
}

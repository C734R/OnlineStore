package javalinos.onlinestore.modelo.Entidades;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;

public class Articulo
{
    private Integer id;
    private String codigo;
    private String descripcion;
    private Float precio;
    private Integer minutosPreparacion;

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
        this.minutosPreparacion = articuloDTO.getMinutosPreparacion();
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

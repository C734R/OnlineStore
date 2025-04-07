package javalinos.onlinestore.modelo.Entidades;

public class Articulo {
    private Integer id;
    private String codigo;
    private String descripcion;
    private Float precio;
    private Integer preparacion;

    public Articulo(Integer id, String codigo, String descripcion, Float precio, Integer preparacion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.preparacion = preparacion;
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

    public Integer getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(Integer preparacion) {
        this.preparacion = preparacion;
    }
}

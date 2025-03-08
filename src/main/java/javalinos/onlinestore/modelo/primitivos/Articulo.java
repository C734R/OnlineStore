package javalinos.onlinestore.modelo.primitivos;

import java.time.LocalDate;

public class Articulo {

    private String codigo;
    private String descripcion;
    private Float precio;
    private LocalDate preparacion;
    private Integer stock;

    public Articulo(String codigo, String descripcion, Float precio, LocalDate preparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.preparacion = preparacion;
        this.stock = 0;
    }

    public Articulo() {
        this.codigo = "";
        this.descripcion = "";
        this.precio = 0.0f;
        this.preparacion = LocalDate.now();
        this.stock = 0;
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

    public LocalDate getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(LocalDate preparacion) {
        this.preparacion = preparacion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return  "Código: " + codigo + "\n" +
                "Descripcion='" + descripcion + "\n" +
                "Precio" + precio + "\n" +
                "Tiempo de preparacion: " + preparacion.toString() + "\n" +
                "Stock: " + stock;
    }
}

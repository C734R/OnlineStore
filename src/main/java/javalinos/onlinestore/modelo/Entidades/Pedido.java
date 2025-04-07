package javalinos.onlinestore.modelo.Entidades;

import java.time.LocalDate;

public class Pedido {
    private Integer id;
    private Integer numero;
    private Integer cliente;
    private Integer articulo;
    private Integer cantidad;
    private LocalDate fechahora;
    private Float envio;
    private Float precio;

    public Pedido(Integer id, Integer numero, Integer cliente, Integer articulo, Integer cantidad, LocalDate fechahora, Float envio, Float precio) {
        this.id = id;
        this.numero = numero;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechahora = fechahora;
        this.envio = envio;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getArticulo() {
        return articulo;
    }

    public void setArticulo(Integer articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDate fechahora) {
        this.fechahora = fechahora;
    }

    public Float getEnvio() {
        return envio;
    }

    public void setEnvio(Float envio) {
        this.envio = envio;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}

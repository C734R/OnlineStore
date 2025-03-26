package javalinos.onlinestore.modelo.primitivos;

import java.time.LocalDate;

public class Pedido {

    private Integer numero;
    private Cliente cliente;
    private Articulo articulo;
    private Integer cantidad;
    private LocalDate fechahora;
    private Float envio;
    private Float precio;
    private Integer diasPreparacion;

    public Pedido(Integer numero, Cliente cliente, Articulo articulo, Integer cantidad, LocalDate fechahora, Float envio, Float precio) {
        this.numero = numero;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechahora = fechahora;
        this.envio = envio;
        this.precio = precio;
        this.diasPreparacion = calcTotalPreparacion(articulo.getPreparacion(), cantidad);
    }

    public Pedido() {
        this.numero = null;
        this.cliente = null;
        this.articulo = null;
        this.cantidad = null;
        this.fechahora = null;
        this.envio = null;
        this.precio = null;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
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

    public Integer getDiasPreparacion() {
        return diasPreparacion;
    }

    public void setDiasPreparacion(Integer diasPreparacion) {
        this.diasPreparacion = diasPreparacion;
    }

    private Integer calcTotalPreparacion (Float preparacion, Integer cantidad) {
        return (int)Math.ceil(preparacion * cantidad);
    }

    @Override
    public String toString() {
        return  "Número de pedido: " + numero + "\n" +
                "Cliente: " + cliente + "\n" +
                "Articulo: " + articulo + "\n" +
                "Cantidad: " + cantidad + "\n" +
                "Fechahora: " + fechahora + "\n" +
                "Envio: " + envio + "\n" +
                "Días preparacion: " + diasPreparacion + "\n" +
                "Precio: " + precio;
    }
}

package javalinos.onlinestore.modelo.primitivos;

import java.time.LocalDate;
/**
 * Clase que representa un pedido realizado por un cliente.
 * Contiene información sobre el artículo, cantidades, fechas y coste.
 */
public class Pedido {

    private Integer numero;
    private Cliente cliente;
    private Articulo articulo;
    private Integer cantidad;
    private LocalDate fechahora;
    private Float envio;
    private Float precio;
    private Integer diasPreparacion;

    /**
     * Constructor principal de pedido.
     * @param numero número de pedido.
     * @param cliente cliente que realiza el pedido.
     * @param articulo artículo comprado.
     * @param cantidad unidades compradas.
     * @param fechahora fecha y hora del pedido.
     * @param envio coste de envío.
     * @param precio precio total del pedido.
     */
    public Pedido(Integer numero, Cliente cliente, Articulo articulo, Integer cantidad, LocalDate fechahora, Float envio, Float precio) {
        this.numero = numero;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechahora = fechahora;
        this.envio = envio;
        this.precio = precio;
        this.diasPreparacion = calcTotalPreparacion(articulo.getMinutosPreparacion(), cantidad);
    }
    /**
     * Constructor vacío.
     */
    public Pedido() {
        this.numero = null;
        this.cliente = null;
        this.articulo = null;
        this.cantidad = null;
        this.fechahora = null;
        this.envio = null;
        this.precio = null;
        this.diasPreparacion = null;
    }

    /**
     * Constructor de copia.
     * @param pedido objeto Pedido a copiar.
     */
    public Pedido(Pedido pedido) {
        this.numero = pedido.numero;
        this.cliente = pedido.cliente;
        this.articulo = pedido.articulo;
        this.cantidad = pedido.cantidad;
        this.fechahora = pedido.fechahora;
        this.envio = pedido.envio;
        this.precio = pedido.precio;
        this.diasPreparacion = pedido.diasPreparacion;
    }

    /**
     * Devuelve el número del pedido.
     * @return número del pedido.
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Establece el número del pedido.
     * @param numero nuevo número.
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Devuelve el cliente asociado al pedido.
     * @return cliente del pedido.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado al pedido.
     * @param cliente nuevo cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve el artículo del pedido.
     * @return artículo solicitado.
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * Establece el artículo del pedido.
     * @param articulo nuevo artículo.
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    /**
     * Devuelve la cantidad del pedido
     * @return Integer con la cantidad comprada en el pedido.
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de artículos en el pedido.
     * @param cantidad nueva cantidad.
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve la fecha y hora del pedido.
     * @return fecha y hora.
     */
    public LocalDate getFechahora() {
        return fechahora;
    }

    /**
     * Establece la fecha y hora del pedido.
     * @param fechahora nueva fecha y hora.
     */
    public void setFechahora(LocalDate fechahora) {
        this.fechahora = fechahora;
    }

    /**
     * Devuelve el coste de envío.
     * @return coste del envío.
     */
    public Float getEnvio() {
        return envio;
    }

    /**
     * Establece el coste de envío.
     * @param envio nuevo valor.
     */
    public void setEnvio(Float envio) {
        this.envio = envio;
    }

    /**
     * Devuelve el precio total del pedido.
     * @return precio total.
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio total del pedido.
     * @param precio nuevo precio.
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Devuelve los días de preparación estimados.
     * @return días de preparación.
     */
    public Integer getDiasPreparacion() {
        return diasPreparacion;
    }

    /**
     * Establece los días de preparación del pedido.
     * @param diasPreparacion nuevo valor.
     */
    public void setDiasPreparacion(Integer diasPreparacion) {
        this.diasPreparacion = diasPreparacion;
    }

    /**
     * Calcula los días de preparación del pedido según minutos y cantidad.
     * @param minutosPreparacion tiempo de preparación por unidad (en minutos).
     * @param cantidad número de unidades.
     * @return días necesarios (mínimo 1).
     */
    private Integer calcTotalPreparacion(Integer minutosPreparacion, Integer cantidad) {
        int totalMinutos = minutosPreparacion * cantidad;
        double dias = (double) totalMinutos / (60 * 24);
        return Math.max(1, (int) Math.ceil(dias));
    }

    /**
     * Devuelve una representación textual del pedido.
     * @return cadena con los detalles del pedido.
     */
    @Override
    public String toString() {
        return  "Número de pedido: " + numero + "\n" +
                "Cliente: " + cliente + "\n" +
                "Articulo: " + articulo + "\n" +
                "Cantidad: " + cantidad + "\n" +
                "Fecha de creación: " + fechahora + "\n" +
                "Coste del envío: " + String.format("%.2f",envio) + " €\n" +
                "Días preparación: " + diasPreparacion + "\n" +
                "Precio: " + String.format("%.2f",precio) + " €";
    }
}

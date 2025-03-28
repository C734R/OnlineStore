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

    /**
     * Constructor de pedidos
     *
     * @param numero Número del pedido
     * @param cliente Cliente que efectua el pedido
     * @param articulo Artículo comprado
     * @param cantidad Cantidad comprada
     * @param fechahora Fecha y hora del pedido
     * @param envio Coste de envío del pedido
     * @param precio Precio del pedido
     */
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
        this.diasPreparacion = null;
    }

    /**
     * Constructor de copia.
     * @param pedido se le pasa un Pedido.
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
     * Devuelve en numero del pedido
     * @return Integer con el numero del pedido
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Cambia el número del pedido
     * @param numero Nuevo número del pedido
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Devuelve el cliente que realiza el pedido
     * @return Cliente que efectua el pedido
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Cambia el cliente que ha efectuado el pedido
     * @param cliente El nuevo cliente del pedido
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve el artículo de un pedido
     * @return Articulo del pedido
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * Camibia el artículo del pedido
     * @param articulo El nuevo artículo del pedido
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
     * Cambia la cantidad comprada del pedido
     * @param cantidad Nueva cantidad comprada
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve la fecha y la hora del pedido
     * @return LocalDate con la fehca y hora del pedido
     */
    public LocalDate getFechahora() {
        return fechahora;
    }

    /**
     * Cambia la fecha y hora de un pedido
     * @param fechahora Nueva fecha y hora
     */
    public void setFechahora(LocalDate fechahora) {
        this.fechahora = fechahora;
    }

    /**
     * Devuelve el precio de envio
     * @return Float con el precio de envio
     */
    public Float getEnvio() {
        return envio;
    }

    /**
     * Cambia el precio del envio
     * @param envio Nuevo precio del envío
     */
    public void setEnvio(Float envio) {
        this.envio = envio;
    }

    /**
     * Devuelve el precio total del pedido
     * @return Float con el precio total del pedido
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Cambia el precio total del pedido
     * @param precio nuevo precio del pedido
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Devuelve los días de preparación de un pedido
     * @return Integer con el nº de días de preparación del pedido
     */
    public Integer getDiasPreparacion() {
        return diasPreparacion;
    }

    /**
     * Cambia el nº de días de preparación del pedido
     * @param diasPreparacion nuevo día de preparación
     */
    public void setDiasPreparacion(Integer diasPreparacion) {
        this.diasPreparacion = diasPreparacion;
    }

    /**
     * Calculo total de los días de preparación del pedido
     * @param preparacion Tiempo de preparación
     * @param cantidad Cantidad comprada
     * @return Integer con los días de preparación del pedido
     */
    private Integer calcTotalPreparacion (Float preparacion, Integer cantidad) {
        return (int)Math.ceil(preparacion * cantidad);
    }

    /**
     * Devuelve un pedido completo
     * @return String con un pedido completo
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

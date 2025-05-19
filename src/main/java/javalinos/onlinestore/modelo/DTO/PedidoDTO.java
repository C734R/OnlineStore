package javalinos.onlinestore.modelo.DTO;

import javalinos.onlinestore.modelo.Entidades.Pedido;

import java.time.LocalDate;
/**
 * Clase que representa un pedido realizado por un clienteDTO.
 * Contiene información sobre el artículo, cantidades, fechas y coste.
 */
public class PedidoDTO {

    private Integer numero;
    private ClienteDTO clienteDTO;
    private ArticuloDTO articuloDTO;
    private Integer cantidad;
    private LocalDate fechahora;
    private Float envio;
    private Float precio;
    private Integer diasPreparacion;

    /**
     * Constructor principal de pedido.
     * @param numero número de pedido.
     * @param clienteDTO clienteDTO que realiza el pedido.
     * @param articuloDTO artículo comprado.
     * @param cantidad unidades compradas.
     * @param fechahora fecha y hora del pedido.
     * @param envio coste de envío.
     * @param precio precio total del pedido.
     */
    public PedidoDTO(Integer numero, ClienteDTO clienteDTO, ArticuloDTO articuloDTO, Integer cantidad, LocalDate fechahora, Float envio, Float precio) {
        this.numero = numero;
        this.clienteDTO = clienteDTO;
        this.articuloDTO = articuloDTO;
        this.cantidad = cantidad;
        this.fechahora = fechahora;
        this.envio = envio;
        this.precio = precio;
        this.diasPreparacion = calcTotalPreparacion(articuloDTO.getMinutosPreparacion(preparacion), cantidad);
    }
    /**
     * Constructor vacío.
     */
    public PedidoDTO() {
        this.numero = null;
        this.clienteDTO = null;
        this.articuloDTO = null;
        this.cantidad = null;
        this.fechahora = null;
        this.envio = null;
        this.precio = null;
        this.diasPreparacion = null;
    }

    /**
     * Constructor de copia.
     * @param pedidoDTO objeto PedidoDTO a copiar.
     */
    public PedidoDTO(PedidoDTO pedidoDTO) {
        this.numero = pedidoDTO.numero;
        this.clienteDTO = pedidoDTO.clienteDTO;
        this.articuloDTO = pedidoDTO.articuloDTO;
        this.cantidad = pedidoDTO.cantidad;
        this.fechahora = pedidoDTO.fechahora;
        this.envio = pedidoDTO.envio;
        this.precio = pedidoDTO.precio;
        this.diasPreparacion = pedidoDTO.diasPreparacion;
    }

    public PedidoDTO(Pedido pedido, ClienteDTO clienteDTO, ArticuloDTO articuloDTO)
    {
        this.numero = pedido.getNumero();
        this.clienteDTO = clienteDTO;
        this.articuloDTO = articuloDTO;
        this.cantidad = pedido.getCantidad();
        this.fechahora = pedido.getFechahora();
        this.envio = pedido.getEnvio();
        this.precio = pedido.getPrecio();
        this.diasPreparacion = calcTotalPreparacion(articuloDTO.getMinutosPreparacion(preparacion), pedido.getCantidad());
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
     * Devuelve el clienteDTO asociado al pedido.
     * @return clienteDTO del pedido.
     */
    public ClienteDTO getCliente() {
        return clienteDTO;
    }

    /**
     * Establece el clienteDTO asociado al pedido.
     * @param ClienteDTO nuevo clienteDTO.
     */
    public void setCliente(ClienteDTO ClienteDTO) {
        this.clienteDTO = ClienteDTO;
    }

    /**
     * Devuelve el artículo del pedido.
     * @return artículo solicitado.
     */
    public ArticuloDTO getArticulo() {
        return articuloDTO;
    }

    /**
     * Establece el artículo del pedido.
     * @param ArticuloDTO nuevo artículo.
     */
    public void setArticulo(ArticuloDTO ArticuloDTO) {
        this.articuloDTO = ArticuloDTO;
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
                "Cliente: " + clienteDTO + "\n" +
                "Articulo: " + articuloDTO + "\n" +
                "Cantidad: " + cantidad + "\n" +
                "Fecha de creación: " + fechahora + "\n" +
                "Coste del envío: " + String.format("%.2f",envio) + " €\n" +
                "Días preparación: " + diasPreparacion + "\n" +
                "Precio: " + String.format("%.2f",precio) + " €";
    }
}

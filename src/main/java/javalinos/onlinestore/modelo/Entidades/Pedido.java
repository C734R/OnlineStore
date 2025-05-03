package javalinos.onlinestore.modelo.Entidades;

import jakarta.persistence.*;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;

import java.time.LocalDate;

@Entity
@Table(name= "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private Integer numero;
    @ManyToOne(optional = false)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
    @Transient
    private Integer clienteId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "articuloId")
    private Articulo articulo;
    @Transient
    private Integer articuloId;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private LocalDate fechahora;
    @Column(nullable = false)
    private Float envio;
    @Column(nullable = false)
    private Float precio;

    public Pedido() {}

    public Pedido(Integer id, Integer numero, Integer clienteId, Integer articuloId, Integer cantidad, LocalDate fechahora, Float envio, Float precio) {
        this.id = id;
        this.numero = numero;
        this.clienteId = clienteId;
        this.articuloId = articuloId;
        this.cantidad = cantidad;
        this.fechahora = fechahora;
        this.envio = envio;
        this.precio = precio;
    }

    public Pedido(PedidoDTO pedidoDTO, int clienteId, int articuloId)
    {
        this.id = null;
        this.numero = pedidoDTO.getNumero();
        this.clienteId = clienteId;
        this.articuloId = articuloId;
        this.cantidad = pedidoDTO.getCantidad();
        this.fechahora = pedidoDTO.getFechahora();
        this.envio = pedidoDTO.getEnvio();
        this.precio = pedidoDTO.getPrecio();
    }

    public Pedido(PedidoDTO pedidoDTO, Cliente cliente, Articulo articulo)
    {
        this.id = null;
        this.numero = pedidoDTO.getNumero();
        this.clienteId = cliente.getId();
        this.articuloId = articulo.getId();
        this.cantidad = pedidoDTO.getCantidad();
        this.fechahora = pedidoDTO.getFechahora();
        this.envio = pedidoDTO.getEnvio();
        this.precio = pedidoDTO.getPrecio();
        this.cliente = cliente;
        this.articulo = articulo;
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

    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.clienteId = cliente != null ? cliente.getId() : null;
    }

    public Integer getArticuloId() {
        return articuloId;
    }
    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Articulo getArticulo() {
        return articulo;
    }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        this.articuloId = articulo != null ? articulo.getId() : null;
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

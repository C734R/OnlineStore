package javalinos.onlinestore.modelo.ORM;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "pedido")
public class PedidoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private int numero;

    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private ClienteORM cliente;

    @ManyToOne
    @JoinColumn(name = "articulo", nullable = false)
    private ArticuloORM articulo;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fechahora")
    private LocalDate fecha;

    @Column(name = "envio")
    private Float precioEnvio;

    @Column(name = "precio")
    private Float precio;

    public PedidoORM(){}

    public PedidoORM(int numero, ClienteORM cliente, ArticuloORM articulo, int cantidad, LocalDate fecha, Float precioEnvio, Float precio) {
        this.numero = numero;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.precioEnvio = precioEnvio;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public ClienteORM getCliente() {
        return cliente;
    }

    public ArticuloORM getArticulo() {
        return articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Float getPrecioEnvio() {
        return precioEnvio;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCliente(ClienteORM cliente) {
        this.cliente = cliente;
    }

    public void setArticulo(ArticuloORM articulo) {
        this.articulo = articulo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setPrecioEnvio(Float precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "PedidoORM{" +
                "id=" + id +
                ", numero=" + numero +
                ", cliente=" + cliente +
                ", articulo=" + articulo +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", precioEnvio=" + precioEnvio +
                ", precio=" + precio +
                '}';
    }
}

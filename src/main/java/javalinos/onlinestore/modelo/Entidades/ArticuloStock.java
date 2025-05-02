package javalinos.onlinestore.modelo.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "articulostock")
public class ArticuloStock {

    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "articuloId")
    private Articulo articulo;
    @Transient
    private Integer articuloId;
    @Column(nullable = false)
    private Integer stock;

    public ArticuloStock() {}

    public ArticuloStock(Articulo articulo, Integer stock) {
        this.articulo = articulo;
        this.articuloId = articulo != null ? articulo.getId() : null;
        this.stock = stock;
    }

    public ArticuloStock(Integer articuloId, Integer stock) {
        this.articuloId = articuloId;
        this.stock = stock;
    }

    public Articulo getArticulo() {
        return articulo;
    }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        this.articuloId = articulo != null ? articulo.getId() : null;
    }

    public Integer getArticuloId() {
        return articuloId;
    }
    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

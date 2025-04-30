package javalinos.onlinestore.modelo.ORM;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido")
public class ArticuloStockORM {

    @OneToOne
    @JoinColumn(name = "articulo", nullable = false)
    private ArticuloORM articulo;

    @Column(name = "stock")
    private int stock;

    public ArticuloStockORM() {}

    public ArticuloStockORM(ArticuloORM articulo, int stock) {
        this.articulo = articulo;
        this.stock = stock;
    }

    public ArticuloORM getArticulo() {
        return articulo;
    }

    public int getStock() {
        return stock;
    }

    public void setArticulo(ArticuloORM articulo) {
        this.articulo = articulo;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ArticuloStockORM{" +
                "articulo=" + articulo +
                ", stock=" + stock +
                '}';
    }
}

package javalinos.onlinestore.modelo.Entidades;

public class ArticuloStock {
    private Integer articulo;
    private Integer stock;

    public ArticuloStock(Integer articulo, Integer stock) {
        this.articulo = articulo;
        this.stock = stock;
    }

    public Integer getArticulo() {
        return articulo;
    }

    public void setArticulo(Integer articulo) {
        this.articulo = articulo;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

package javalinos.onlinestore.modelo.DTO;

public class ArticuloStockDTO {
    private ArticuloDTO articulo;
    private Integer stock;

    public ArticuloStockDTO(ArticuloDTO articulo, Integer stock) {
        this.articulo = articulo;
        this.stock = stock;
    }

    public ArticuloDTO getArticulo() {
        return articulo;
    }

    public void setArticulo(ArticuloDTO articulo) {
        this.articulo = articulo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

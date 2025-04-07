package javalinos.onlinestore.modelo.DTO;

import javalinos.onlinestore.modelo.Entidades.*;

public class FactoryDTO {

    public FactoryDTO() {}

    public ClienteDTO CrearClienteDTO(Cliente cliente, CategoriaDTO categoria) {
        return new ClienteDTO(
                cliente.getNombre(),
                cliente.getDomicilio(),
                cliente.getNif(),
                cliente.getEmail(),
                categoria
        );
    }

    public CategoriaDTO CrearCategoriaDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getNombre(),
                categoria.getCuota(),
                categoria.getDescuento()
        );
    }

    public ArticuloDTO CrearArticuloDTO(Articulo articulo) {
        return new ArticuloDTO(
                articulo.getCodigo(),
                articulo.getDescripcion(),
                articulo.getPrecio(),
                articulo.getPreparacion()
        );
    }

    public ArticuloStockDTO CrearArticuloStockDTO(ArticuloDTO articulo, Integer stock) {
        return new ArticuloStockDTO(
                articulo,
                stock
        );
    }

    public PedidoDTO CrearPedidoDTO(Pedido pedido, ClienteDTO cliente, ArticuloDTO articulo) {
        return new PedidoDTO(
                pedido.getNumero(),
                cliente,
                articulo,
                pedido.getCantidad(),
                pedido.getFechahora(),
                pedido.getEnvio(),
                pedido.getPrecio()
        );
    }

}

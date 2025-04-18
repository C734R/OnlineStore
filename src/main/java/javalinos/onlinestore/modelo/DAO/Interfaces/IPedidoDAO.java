package javalinos.onlinestore.modelo.DAO.Interfaces;

import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.Entidades.Pedido;


import java.util.List;

public interface IPedidoDAO extends IBaseDAO<Pedido, Integer> {

    Pedido getPedidoNumero(int numero) throws Exception;
    List<Pedido> getPedidosCliente(Cliente cliente) throws Exception;
    void insertarConStock(Pedido pedido) throws Exception;
    void eliminarConStock(Pedido pedido) throws Exception;
    void actualizarConStock(Pedido pedidoNew, Integer diferenciaStock) throws Exception;

}

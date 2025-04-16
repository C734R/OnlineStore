package javalinos.onlinestore.modelo.DAO.Interfaces;

import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.Entidades.Pedido;

import java.util.List;

public interface IPedidoDAO extends IBaseDAO<Pedido, Integer> {

    Pedido getPedidoNumero(int numero) throws Exception;
    List<Pedido> getPedidosCliente(Cliente cliente) throws Exception;

}

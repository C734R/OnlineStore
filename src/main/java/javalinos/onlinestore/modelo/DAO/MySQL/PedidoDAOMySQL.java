package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IPedidoDAO;
import javalinos.onlinestore.modelo.Entidades.Pedido;

import java.sql.*;

public class PedidoDAOMySQL extends BaseDAOMySQL<Pedido, Integer> implements IPedidoDAO {

    public PedidoDAOMySQL(Connection conexion) throws SQLException {
        super(conexion);
        this.tabla = "Pedido";
    }

    @Override
    public Pedido objetoResulset(ResultSet rs) throws SQLException {
        return new Pedido (
                rs.getInt("id"),
                rs.getInt("numero"),
                rs.getInt("cliente"),
                rs.getInt("articulo"),
                rs.getInt("cantidad"),
                rs.getDate("fechahora").toLocalDate(),
                rs.getFloat("envio"),
                rs.getFloat("precio")
        );
    }

    @Override
    public String definirSet() {
        return "numero = ?, cliente = ?, articulo = ?, cantidad = ?, fechahora = ?, envio = ?, precio = ?";
    }

    @Override
    public Integer definirId(Pedido pedido) {
        return pedido.getId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, Pedido pedido) throws SQLException {
        stmt.setInt(1, pedido.getNumero());
        stmt.setInt(2, pedido.getCliente());
        stmt.setInt(3, pedido.getArticulo());
        stmt.setInt(4, pedido.getCantidad());
        stmt.setDate(5, Date.valueOf(pedido.getFechahora()));
        stmt.setFloat(4, pedido.getEnvio());
        stmt.setFloat(5, pedido.getPrecio());
    }

}

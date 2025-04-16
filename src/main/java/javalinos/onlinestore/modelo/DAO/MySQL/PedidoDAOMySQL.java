package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IPedidoDAO;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;
import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.Entidades.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOMySQL extends BaseDAOMySQL<Pedido, Integer> implements IPedidoDAO {

    public PedidoDAOMySQL(Connection conexion) throws SQLException {
        super(conexion);
        this.tabla = "pedido";
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
    public String definirValues() { return  "( ?, ?, ?, ?, ?, ?, ? )"; }

    @Override
    public String definirColumnas() {
        return "(numero, cliente, articulo, cantidad, fechahora, envio, precio)";
    }

    @Override
    public void definirSetInsert(PreparedStatement stmt, Pedido entidad) throws SQLException {
        stmt.setInt(1, entidad.getNumero());
        stmt.setInt(2, entidad.getCliente());
        stmt.setInt(3, entidad.getArticulo());
        stmt.setInt(4, entidad.getCantidad());
        stmt.setDate(5, Date.valueOf(entidad.getFechahora()));
        stmt.setFloat(6, entidad.getEnvio());
        stmt.setFloat(7, entidad.getPrecio());
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

    public Pedido getPedidoNumero(int numero) throws Exception
    {
        Pedido pedido = null;
        String query = "SELECT * FROM " + tabla + " WHERE numero = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query))
        {
               stmt.setInt(1, numero);
               try(ResultSet rs = stmt.executeQuery())
               {
                   while(rs.next())
                   {
                       pedido = objetoResulset(rs);
                   }
               }
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener el pedido por número: " + numero, e);
        }
        return pedido;
    }

    public List<Pedido> getPedidosCliente(Cliente cliente) throws Exception {
        List<Pedido> listaPedidos = new ArrayList<>();
        String query = "SELECT * FROM " + tabla + " WHERE cliente = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query))
        {
            stmt.setInt(1,cliente.getId());
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = objetoResulset(rs);
                    listaPedidos.add(pedido);
                }
            }
        }
        catch (SQLException e)
        {
            throw new Exception("Error al obtener los pedidos del cliente.", e);
        }
        return listaPedidos;
    }

}

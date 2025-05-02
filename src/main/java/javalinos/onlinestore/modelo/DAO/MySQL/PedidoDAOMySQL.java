package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.DAO.Interfaces.IPedidoDAO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;
import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.Entidades.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOMySQL extends BaseDAOMySQL<Pedido, Integer> implements IPedidoDAO {

    private final FactoryDAO factoryDAO;

    public PedidoDAOMySQL(Connection conexion, FactoryDAO factoryDAO) throws SQLException {
        super(conexion);
        this.tabla = "pedido";
        this.factoryDAO = factoryDAO;
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
        stmt.setInt(2, entidad.getClienteId());
        stmt.setInt(3, entidad.getArticuloId());
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
        stmt.setInt(2, pedido.getClienteId());
        stmt.setInt(3, pedido.getArticuloId());
        stmt.setInt(4, pedido.getCantidad());
        stmt.setDate(5, Date.valueOf(pedido.getFechahora()));
        stmt.setFloat(6, pedido.getEnvio());
        stmt.setFloat(7, pedido.getPrecio());
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

    public void insertarConStock(Pedido pedido) throws Exception
    {
        String query = "INSERT INTO " + tabla + " " + definirColumnas() + " VALUES " + definirValues();
        boolean autocommitOriginal = conexion.getAutoCommit();
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            definirSetInsert(stmt, pedido);
            int filas = stmt.executeUpdate();
            if (filas <= 0) throw new Exception("No se pudo insertar el pedido: \n" + pedido.toString());
            ActualizarStock(pedido, -pedido.getCantidad());
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al insertar pedido y actualizar stock en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }

    public void insertarConStockSP(Pedido pedido) throws Exception {
        String call = "{CALL insertar_pedido_con_stock(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(call))
        {
            definirSetInsert(stmt, pedido);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new Exception("Error al insertar pedido con stock mediante procedimiento almacenado.", e);
        }
    }

    public void eliminarConStock(Pedido pedido) throws Exception {
        String query = "DELETE FROM " + tabla + " WHERE id = ?";
        boolean autocommitOriginal = conexion.getAutoCommit();
        try (PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            stmt.setInt(1, pedido.getId());
            int filas = stmt.executeUpdate();
            if (filas <= 0) {
                throw new Exception("No se ha encontrado el pedido con ID: " + pedido.getId());
            }
            ActualizarStock(pedido, +pedido.getCantidad());
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al eliminar pedido y actualizar stock en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }

    public void eliminarConStockSP(Pedido pedido) throws Exception {
        String call = "{CALL eliminar_pedido_con_stock(?)}";
        try (CallableStatement stmt = conexion.prepareCall(call))
        {
            stmt.setInt(1, pedido.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new Exception("Error al eliminar pedido con stock mediante procedimiento almacenado.", e);
        }
    }

    public void actualizarConStock(Pedido pedidoNew, Integer diferenciaStock) throws Exception {
        String query = "UPDATE " + tabla + " SET " + definirSet() + " WHERE id = ?";
        boolean autocommitOriginal = conexion.getAutoCommit();
        try(PreparedStatement stmt = conexion.prepareStatement(query))
        {
            conexion.setAutoCommit(false);
            mapearUpdate(stmt, pedidoNew);
            int ultimoParametro = obtenerUltimoParametro(stmt);
            Object entidadId = definirId(pedidoNew);
            if (entidadId == null) throw new Exception("El pedido no tiene un ID definido. No se puede actualizar.");
            stmt.setInt(ultimoParametro, (int)entidadId);
            int filas = stmt.executeUpdate();
            if (filas <= 0) {
                throw new Exception("No se ha encontrado el pedido con ID: " + entidadId);
            }
            ActualizarStock(pedidoNew, diferenciaStock);
            conexion.commit();
        }
        catch (Exception e)
        {
            conexion.rollback();
            throw new Exception("Error al actualizar pedido y stock en la base de datos.", e);
        }
        finally
        {
            conexion.setAutoCommit(autocommitOriginal);
        }
    }

    private void ActualizarStock(Pedido pedido, Integer diferenciaStock) throws Exception {
        Articulo articulo = factoryDAO.getDAOArticulo().getPorId(pedido.getArticuloId());
        ArticuloStock articuloStock = factoryDAO.getDAOArticuloStock().getPorId(articulo.getId());
        articuloStock.setStock(articuloStock.getStock() + diferenciaStock);
        factoryDAO.getDAOArticuloStock().actualizar(articuloStock);
    }

    public void actualizarConStockSP(Pedido pedidoNew, Integer diferenciaStock) throws Exception {
        String call = "{CALL actualizar_pedido_con_stock(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(call))
        {
            stmt.setInt(1, pedidoNew.getId());
            stmt.setInt(2, pedidoNew.getNumero());
            stmt.setInt(3, pedidoNew.getClienteId());
            stmt.setInt(4, pedidoNew.getArticuloId());
            stmt.setInt(5, pedidoNew.getCantidad());
            stmt.setDate(6, Date.valueOf(pedidoNew.getFechahora()));
            stmt.setFloat(7, pedidoNew.getEnvio());
            stmt.setFloat(8, pedidoNew.getPrecio());
            stmt.setInt(9, diferenciaStock);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new Exception("Error al actualizar pedido con stock mediante procedimiento almacenado.", e);
        }
    }
}

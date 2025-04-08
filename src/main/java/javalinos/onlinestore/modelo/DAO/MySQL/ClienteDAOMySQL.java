package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IClienteDAO;
import javalinos.onlinestore.modelo.Entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClienteDAOMySQL extends BaseDAOMySQL<Cliente, Integer> implements IClienteDAO {

    public ClienteDAOMySQL(Connection conexion) throws SQLException {
        super(conexion);
        super.tabla = "Cliente";
    }

    @Override
    public Cliente objetoResulset(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("domicilio"),
                rs.getString("email"),
                rs.getString("nif"),
                rs.getInt("categoria"),
                rs.getFloat("cuota"),
                rs.getFloat("descuento")
        );
    }

    @Override
    public String definirSet() {
        return "nombre = ?, domicilio = ?, email = ?, nif = ?, categoria = ?";
    }

    @Override
    public Integer definirId(Cliente cliente) {
        return cliente.getId();
    }

    @Override
    public void mapearUpdate(PreparedStatement stmt, Cliente cliente) throws SQLException {
        stmt.setString(1, cliente.getNombre());
        stmt.setString(2, cliente.getDomicilio());
        stmt.setString(3, cliente.getEmail());
        stmt.setString(4, cliente.getNif());
        stmt.setInt(5, cliente.getCategoria());
    }

}

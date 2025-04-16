package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IClienteDAO;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;
import javalinos.onlinestore.modelo.Entidades.Categoria;
import javalinos.onlinestore.modelo.Entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAOMySQL extends BaseDAOMySQL<Cliente, Integer> implements IClienteDAO {

    public ClienteDAOMySQL(Connection conexion) throws SQLException {
        super(conexion);
        super.tabla = "cliente";
    }

    @Override
    public Cliente objetoResulset(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("domicilio"),
                rs.getString("email"),
                rs.getString("nif"),
                rs.getInt("categoria")
        );
    }

    @Override
    public String definirSet() {
        return "nombre = ?, domicilio = ?, email = ?, nif = ?, categoria = ?";
    }

    @Override
    public String definirValues() { return  "( ?, ?, ?, ?, ? )"; }

    @Override
    public String definirColumnas(){ return "(nombre, domicilio, email, nif, categoria)"; }

    @Override
    public void definirSetInsert(PreparedStatement stmt, Cliente entidad) throws SQLException {
        stmt.setString(1, entidad.getNombre());
        stmt.setString(2, entidad.getDomicilio());
        stmt.setString(3, entidad.getEmail());
        stmt.setString(4, entidad.getNif());
        stmt.setInt(5, entidad.getCategoria());
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

    public Cliente getClienteNIF(String nif) throws Exception {
        Cliente cliente = null;
        String sql = "SELECT * FROM " + tabla + " WHERE nif = ?";
        // Lanzar try del prepared statement
        try (PreparedStatement stmt = conexion.prepareStatement(sql))
        {
            // Definir parámetro
            stmt.setString(1, nif);
            // Obtener resulset
            try (ResultSet rs = stmt.executeQuery())
            {
                // Iniciar contador de filas
                int filas = 0;
                // Recorrer resultset
                while (rs.next())
                {
                    // Contar fila
                    filas++;
                    // Si hay más de una, excepción
                    if (filas > 1)
                        throw new Exception("Existe más de una coincidencia de NIF en la tabla " + tabla + ".");
                    // Convertir resultset en objeto
                    cliente = objetoResulset(rs);
                }
            }
        }
        catch (SQLException e)
        {
            // Lanzar excepción
            throw new Exception("Error al obtener cliente por NIF.", e);
        }
        return cliente;

    }

    public Cliente getClienteEmail(String email) throws Exception
    {
        Cliente cliente = null;
        String sql = "SELECT * FROM " + tabla + " WHERE email = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql))
        {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery())
            {
                int filas = 0;
                // Recorrer resultset
                while (rs.next())
                {
                    // Contar fila
                    filas++;
                    // Si hay más de una, excepción
                    if (filas > 1) throw new Exception("Existe más de una coincidencia de Email en la tabla " + tabla + ".");
                    // Convertir resultset en objeto
                    cliente = objetoResulset(rs);
                }
            }
        }
        catch (SQLException e)
        {
            throw new Exception("Error al obtener cliente por email.", e);
        }
        return cliente;
    }

    public List<Cliente> getClientesCategoria(Categoria categoria) throws Exception
    {
        List<Cliente> clientesCategoria = new ArrayList<>();
        String sql = "SELECT * FROM " + tabla + " WHERE categoria = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientesCategoria.add(objetoResulset(rs));
                }
            }
        }
        catch (SQLException e)
        {
            throw new Exception("Error al obtener clientes por categoria.", e);
        }
        return clientesCategoria;
    }

}

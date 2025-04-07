package javalinos.onlinestore.modelo.DAO.MySQL;

import javalinos.onlinestore.modelo.DAO.Interfaces.IDAOCliente;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DAOCliente implements IDAOCliente {

    private Connection conexion;

    public DAOCliente(Connection conexion) {
        this.conexion = conexion;
    }

    public Map<Boolean, String> insertCliente(Cliente cliente) {
        LinkedHashMap<Boolean, String> resultado = new LinkedHashMap<>();
        boolean exito = false;
        String mensaje = "";

        try {
            // Desactivar auto-commit para iniciar la transacción
            conexion.setAutoCommit(false);

            // Ejecutar una o más operaciones
            PreparedStatement stmt1 = conexion.prepareStatement("INSERT INTO clientes (nombre, domicilio, nif, email, categoriaId) VALUES (?, ?, ?, ?, ?)");
            stmt1.setString(1, cliente.getNombre());
            stmt1.setString(2, cliente.getDomicilio());
            stmt1.setString(3, cliente.getNif());
            stmt1.setString(4, cliente.getEmail());
            stmt1.setString(5, cliente.getCategoria().getNombre());

            stmt1.executeUpdate();

            // Confirmar transacción si fue bien
            conexion.commit();
            exito = true;

        } catch (SQLException e) {
            try {
                // En caso de error, deshacer cambios
                if (conexion != null) {
                    conexion.rollback();
                }
            }
            catch (SQLException rollbackEx) {
                mensaje = "Error al hacer rollback: " + rollbackEx.getMessage();
            }
            mensaje = "Error en la transacción: " + e.getMessage();

        }
        finally {
            try {
                // Restaurar el modo auto-commit por seguridad
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                }
            }
            catch (SQLException ex) {
                mensaje = "Error al restaurar autoCommit: " + ex.getMessage();
            }
        }
        resultado.put(exito, mensaje);
        return resultado;
    }

    @Override
    public Map<Boolean, String> updateCliente(Cliente cliente) {
        return Map.of();
    }

    @Override
    public Map<Boolean, String> deleteCliente(Cliente cliente) {
        return Map.of();
    }

    @Override
    public Map<Map<Boolean, String>, Cliente> getCliente(Cliente cliente) {
        return Map.of();
    }

    @Override
    public Map<Map<Boolean, String>, List<Cliente>> getAllClientes() {
        return Map.of();
    }

    @Override
    public Cliente getPorId(String id) throws SQLException {
        return null;
    }

    @Override
    public List<Cliente> buscarTodos() throws SQLException {
        return List.of();
    }

    @Override
    public HashMap<Boolean, String> insertar(Cliente entidad) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Boolean, String> actualizar(Cliente entidad) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Boolean, String> eliminar(String id) throws SQLException {
        return null;
    }
}

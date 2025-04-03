package javalinos.onlinestore.modelo.DAO.Interfaces;

import javalinos.onlinestore.modelo.DAO.ConexionBBDD;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IDAOCliente<Connection> extends IDAOBase<Cliente, String> {

    public Map<Boolean, String> insertCliente(Cliente cliente);
    public Map<Boolean, String> updateCliente(Cliente cliente);
    public Map<Boolean, String> deleteCliente(Cliente cliente);
    public Map<Map<Boolean, String>, Cliente> getCliente(Cliente cliente);
    public Map<Map<Boolean, String>, List<Cliente>> getAllClientes();
}

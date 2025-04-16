package javalinos.onlinestore.modelo.DAO.Interfaces;

import javalinos.onlinestore.modelo.Entidades.Categoria;
import javalinos.onlinestore.modelo.Entidades.Cliente;

import java.util.List;

public interface IClienteDAO extends IBaseDAO<Cliente, Integer> {

    public Cliente getClienteNIF(String nif) throws Exception;

    public Cliente getClienteEmail(String email) throws Exception;

    public List<Cliente> getClientesCategoria(Categoria categoria) throws Exception;
}

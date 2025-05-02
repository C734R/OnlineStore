package javalinos.onlinestore.modelo.DAO.ORM;

import javalinos.onlinestore.modelo.DAO.Interfaces.IClienteDAO;
import javalinos.onlinestore.modelo.Entidades.Categoria;
import javalinos.onlinestore.modelo.Entidades.Cliente;

import java.util.List;


public class ClienteDAOHibernate extends BaseDAOHIbernate<Cliente, Integer> implements IClienteDAO {

    public ClienteDAOHibernate() {
        super(Cliente.class);
    }

    @Override
    public Cliente getClienteNIF(String nif) throws Exception {
        try
        {
            return em.createQuery("FROM " + clase.getSimpleName() + " c WHERE c.nif = :nif", clase)
                    .setParameter("nif", nif)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener el cliente por su NIF.", e);
        }
        finally {
            em.close();
        }

    }

    @Override
    public Cliente getClienteEmail(String email) throws Exception {
        try
        {
            return em.createQuery("FROM " + clase.getSimpleName() + " c WHERE c.email = :email", clase)
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener el cliente por su email.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> getClientesCategoria(Categoria categoria) throws Exception {
        try
        {
            return em.createQuery("FROM " + clase.getSimpleName() + " c WHERE c.categoriaId = :categoriaId", clase)
                    .setParameter("categoriaId", categoria.getId())
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener el cliente por su categoría.", e);
        }
        finally {
            em.close();
        }
    }
}

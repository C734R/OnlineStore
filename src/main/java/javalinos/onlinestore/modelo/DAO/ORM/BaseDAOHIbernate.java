package javalinos.onlinestore.modelo.DAO.ORM;

import jakarta.persistence.EntityManager;
import javalinos.onlinestore.modelo.DAO.Interfaces.IBaseDAO;
import javalinos.onlinestore.utils.GestoresEntidades.GestorTransaccionesJPA;
import javalinos.onlinestore.utils.GestoresEntidades.ProveedorEntityManagerJPA;

import java.util.List;

public abstract class BaseDAOHIbernate<T, K> implements IBaseDAO<T, K>{

    protected EntityManager em = ProveedorEntityManagerJPA.crearEntityManager();
    protected final Class<T> clase;

    public BaseDAOHIbernate(Class<T> clase) {
        this.clase = clase;
    }

    @Override
    public T getPorId(K id) throws Exception {
        try
        {
            return em.find(clase, id);
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener entidad por su Id.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public T getPorNombreUnico(String nombre) throws Exception {
        try
        {
            return em.createQuery("FROM " + clase.getSimpleName() + " a WHERE a.nombre = :nombre", clase)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener entidad por su nombre.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public List<T> getTodos() throws Exception {

        try
        {
            return em.createQuery("FROM "  + clase.getSimpleName(), clase)
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener entidades de la tabla \"" + clase.getSimpleName() + "\".", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public void insertar(T entidad) throws Exception {
        try {
            GestorTransaccionesJPA.iniciar(em);
            em.persist(entidad);
            GestorTransaccionesJPA.commit();
        }
        catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al insertar entidad.",e);
        }
    }
    @Override
    public void insertarTodos(List<T> entidades) throws Exception {
        try
        {
            GestorTransaccionesJPA.iniciar(em);
            for (T entidad : entidades) insertar(entidad);
            GestorTransaccionesJPA.commit();
        }
        catch (Exception e)
        {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al insertar entidades.",e);
        }
    }
    @Override
    public void actualizar(T entidad) throws Exception {
        try {
            GestorTransaccionesJPA.iniciar(em);
            em.merge(entidad);
            GestorTransaccionesJPA.commit();
        } catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al actualizar entidad.",e);
        }
    }

    @Override
    public void eliminar(Object id) throws Exception {
        try {
            GestorTransaccionesJPA.iniciar(em);
            T entidad = em.find(clase, id);
            if (entidad != null) {
                em.remove(entidad);
            }
            GestorTransaccionesJPA.commit();
        } catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al eliminar entidad.",e);
        }
    }
    @Override
    public void eliminarTodos() throws Exception {
        try
        {
            GestorTransaccionesJPA.iniciar(em);
            em.createQuery("DELETE FROM " + clase.getSimpleName()).executeUpdate();
            GestorTransaccionesJPA.commit();
        }
        catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al eliminar todas las entidades de la tabla " + clase.getSimpleName() + ".",e);
        }
    }
}

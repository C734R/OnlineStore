package javalinos.onlinestore.modelo.ORM;

import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.utils.Conexiones.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ArticuloORM {

    public static void insertarArticulo(Articulo articulo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(articulo); // También puedes usar session.save(articulo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static Articulo obtenerArticuloPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Articulo.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Articulo> obtenerTodosLosArticulos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Articulo", Articulo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void actualizarArticulo(Articulo articulo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(articulo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void eliminarArticulo(Articulo articulo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(articulo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
package javalinos.onlinestore.utils.Conexiones;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ConexionHibernate {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}

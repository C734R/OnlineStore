package javalinos.onlinestore.utils.GestoresEntidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProveedorEntityManagerJPA {

    private static EntityManagerFactory emf;

    // Constructor privado para evitar instanciación
    private ProveedorEntityManagerJPA() {}

    // Instanciamos EntityManagerFactory cuando lo solicitamos por primera vez
    public static synchronized EntityManager crearEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("OnlineStoreHibernatePU");
        }
        return emf.createEntityManager();
    }

    // Cierre de EMF que también deja a null la instancia
    public static synchronized void cerrarEMF() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }
}
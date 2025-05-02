package javalinos.onlinestore.utils.GestoresEntidades;

import jakarta.persistence.EntityManager;

public class GestorTransaccionesJPA {

    // Un contexto para cada hilo
    private static final ThreadLocal<EntityManager> contexto = new ThreadLocal<>();

    // Inicia una transacción del EntityManager
    public static void iniciar(EntityManager em) {
        contexto.set(em);
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    // Ejecuta la transacción del EntityManager
    public static void commit() {
        EntityManager em = contexto.get();
        try
        {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        }
        finally {
            cerrarEntityManager(em);
            contexto.remove();
        }

    }

    // Revierte los cambios en la transacción del EntityManager
    public static void rollback() {
        EntityManager em = contexto.get();
        try{
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            cerrarEntityManager(em);
            contexto.remove();
        }
    }

    // Obtiene el EntityManager del hilo
    public static EntityManager getEntityManager() {
        return contexto.get();
    }


    // Cerrar el EntityManager si está abierto
    private static void cerrarEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
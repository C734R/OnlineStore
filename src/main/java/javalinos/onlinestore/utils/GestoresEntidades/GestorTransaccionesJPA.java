package javalinos.onlinestore.utils.GestoresEntidades;

import jakarta.persistence.EntityManager;

public class GestorTransaccionesJPA {

    // Almacena el EntityManager asociado al hilo actual
    private static final ThreadLocal<EntityManager> contexto = new ThreadLocal<>();

    // Asocia el EntityManager al hilo actual e inicia la transacción si no está activa
    public static void iniciar(EntityManager em) {
        contexto.set(em);
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    // Confirma la transacción activa del EntityManager y libera el contexto
    public static void commit() {
        EntityManager em = contexto.get();
        try {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        } finally {
            cerrarEntityManager(em);
            contexto.remove();
        }
    }

    // Revierte la transacción activa del EntityManager y libera el contexto
    public static void rollback() {
        EntityManager em = contexto.get();
        try {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            cerrarEntityManager(em);
            contexto.remove();
        }
    }

    // Devuelve el EntityManager asociado al hilo actual
    public static EntityManager getEntityManager() {
        return contexto.get();
    }

    // Cierra el EntityManager si sigue abierto
    private static void cerrarEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
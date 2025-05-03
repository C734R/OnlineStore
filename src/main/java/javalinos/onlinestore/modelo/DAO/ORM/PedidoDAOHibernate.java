package javalinos.onlinestore.modelo.DAO.ORM;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import javalinos.onlinestore.modelo.DAO.Interfaces.IPedidoDAO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;
import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.Entidades.Pedido;
import javalinos.onlinestore.utils.GestoresEntidades.GestorTransaccionesJPA;

import java.sql.Date;
import java.util.List;


public class PedidoDAOHibernate extends BaseDAOHIbernate<Pedido, Integer> implements IPedidoDAO {

    public PedidoDAOHibernate() {
        super(Pedido.class);
    }

    @Override
    public Pedido getPedidoNumero(int numero) throws Exception {
        try
        {
            return em.createQuery("FROM "+ clase.getSimpleName() +" p WHERE p.numero = :numero", clase)
                    .setParameter("numero", numero)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener el pedido por su número de pedido.", e);
        }
        finally {
            em.close();
        }

    }

    @Override
    public List<Pedido> getPedidosCliente(Cliente cliente) throws Exception {
        try
        {
            return em.createQuery("FROM "+ clase.getSimpleName() +" p WHERE p.cliente.id = :clienteId", clase)
                    .setParameter("clienteId", cliente.getId())
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener los pedidos del cliente.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public void insertarConStock(Pedido pedido) throws Exception {
        try
        {
            GestorTransaccionesJPA.iniciar(em);

            em.persist(pedido);

            // Actualizar Stock
            Articulo articulo = em.find(Articulo.class, pedido.getArticuloId());
            ArticuloStock articuloStock = em.find(ArticuloStock.class, articulo.getId());
            articuloStock.setStock(articuloStock.getStock() - pedido.getCantidad());
            em.merge(articuloStock);

            GestorTransaccionesJPA.commit();
        }
        catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al insertar pedido y actualizar stock en la base de datos.", e);
        }
    }

    @Override
    public void eliminarConStock(Pedido pedido) throws Exception {
        try
        {
            GestorTransaccionesJPA.iniciar(em);

            em.remove(pedido);

            // Actualizar Stock
            Articulo articulo = em.find(Articulo.class, pedido.getArticuloId());
            ArticuloStock articuloStock = em.find(ArticuloStock.class, articulo.getId());
            articuloStock.setStock(articuloStock.getStock() + pedido.getCantidad());
            em.merge(articuloStock);

            GestorTransaccionesJPA.commit();
        }
        catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al insertar pedido y actualizar stock en la base de datos.", e);
        }
    }

    @Override
    public void actualizarConStock(Pedido pedidoNew, Integer diferenciaStock) throws Exception {
        try {
            GestorTransaccionesJPA.iniciar(em);

            // Actualizar Pedido (merge)
            em.merge(pedidoNew);

            // Actualizar Stock
            Articulo articulo = em.find(Articulo.class, pedidoNew.getArticuloId());
            ArticuloStock articuloStock = em.find(ArticuloStock.class, articulo.getId());
            if (articuloStock == null) throw new Exception("No se ha encontrado el stock del artículo ID: " + articulo.getId());
            articuloStock.setStock(articuloStock.getStock() + diferenciaStock);

            em.merge(articuloStock);

            GestorTransaccionesJPA.commit();
        }
        catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al actualizar pedido y stock en la base de datos.", e);
        }

    }

    @Override
    public void insertarConStockSP(Pedido pedido) throws Exception {
        try
        {
            StoredProcedureQuery query = em.createStoredProcedureQuery("insertar_pedido_con_stock");

            query.registerStoredProcedureParameter("p_numero", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_clienteId", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_articuloId", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_cantidad", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_fechahora", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_envio", Float.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_precio", Float.class, ParameterMode.IN);

            query.setParameter("p_numero", pedido.getNumero());
            query.setParameter("p_clienteId", pedido.getClienteId());
            query.setParameter("p_articuloId", pedido.getArticuloId());
            query.setParameter("p_cantidad", pedido.getCantidad());
            query.setParameter("p_fechahora", Date.valueOf(pedido.getFechahora()));
            query.setParameter("p_envio", pedido.getEnvio());
            query.setParameter("p_precio", pedido.getPrecio());

            query.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Error al actualizar pedido con stock mediante procedimiento almacenado.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public void eliminarConStockSP(Pedido pedido) throws Exception {
        try
        {
            StoredProcedureQuery query = em.createStoredProcedureQuery("eliminar_pedido_con_stock");

            query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);

            query.setParameter("p_id", pedido.getId());

            query.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Error al actualizar pedido con stock mediante procedimiento almacenado.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public void actualizarConStockSP(Pedido pedidoNew, Integer diferenciaStock) throws Exception {
        try
        {
            StoredProcedureQuery query = em.createStoredProcedureQuery("actualizar_pedido_con_stock");

            query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_numero", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_clienteId", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_articuloId", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_cantidad", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_fechahora", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_envio", Float.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_precio", Float.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_diferenciaStock", Integer.class, ParameterMode.IN);

            query.setParameter("p_id", pedidoNew.getId());
            query.setParameter("p_numero", pedidoNew.getNumero());
            query.setParameter("p_clienteId", pedidoNew.getClienteId());
            query.setParameter("p_articuloId", pedidoNew.getArticuloId());
            query.setParameter("p_cantidad", pedidoNew.getCantidad());
            query.setParameter("p_fechahora", Date.valueOf(pedidoNew.getFechahora()));
            query.setParameter("p_envio", pedidoNew.getEnvio());
            query.setParameter("p_precio", pedidoNew.getPrecio());
            query.setParameter("p_diferenciaStock", diferenciaStock);

            query.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Error al actualizar pedido con stock mediante procedimiento almacenado.", e);
        }
        finally {
            em.close();
        }
    }
}

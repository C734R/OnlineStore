package javalinos.onlinestore.modelo.DAO.ORM;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import javalinos.onlinestore.modelo.DAO.Interfaces.IArticuloDAO;
import javalinos.onlinestore.modelo.Entidades.Articulo;
import javalinos.onlinestore.modelo.Entidades.ArticuloStock;
import javalinos.onlinestore.utils.GestoresEntidades.GestorTransaccionesJPA;

import java.sql.SQLException;

public class ArticuloDAOHibernate extends BaseDAOHIbernate<Articulo, Integer> implements IArticuloDAO {


    public ArticuloDAOHibernate() throws SQLException
    {
        super(Articulo.class);
    }


    @Override
    public Articulo getArticuloCodigo(String codigo) throws Exception {

        try
        {
            return em.createQuery("FROM " + clase.getSimpleName() + " a WHERE a.codigo = :codigo", clase)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new Exception("Error al obtener artículo por su código.", e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public void actualizarArticuloStock(Articulo articuloNew, Integer stockNew) throws Exception {
        try {
            GestorTransaccionesJPA.iniciar(em);

            em.merge(articuloNew);

            // Actualizar stock si es necesario
            if (stockNew != null) {
                ArticuloStock articuloStock = em.find(ArticuloStock.class, articuloNew.getId());
                if (articuloStock == null) throw new Exception("No se encontró el stock del artículo ID: " + articuloNew.getId());
                articuloStock.setStock(stockNew);
                em.merge(articuloStock);
            }

            GestorTransaccionesJPA.commit();
        }
        catch (Exception e) {
            GestorTransaccionesJPA.rollback();
            throw new Exception("Error al actualizar artículo y stock en la base de datos.", e);
        }

    }

    @Override
    public void actualizarArticuloConStockSP(Articulo articulo, int stockNuevo) throws Exception {
        try
        {
            StoredProcedureQuery query = em.createStoredProcedureQuery("actualizar_articulo_con_stock");

            query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_codigo", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_descripcion", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_precio", Float.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_minutosPreparacion", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_stockNuevo", Integer.class, ParameterMode.IN);

            query.setParameter("p_id", articulo.getId());
            query.setParameter("p_codigo", articulo.getCodigo());
            query.setParameter("p_descripcion", articulo.getDescripcion());
            query.setParameter("p_precio", articulo.getPrecio());
            query.setParameter("p_minutosPreparacion", articulo.getMinutosPreparacion());
            query.setParameter("p_stockNuevo", stockNuevo);

            query.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Error al actualizar artículo con stock mediante procedimiento almacenado.", e);
        }
        finally {
            em.close();
        }

    }
}
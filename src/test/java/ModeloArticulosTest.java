import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas para el modelo de gestión de artículos.
 * - Entidades relacionadas: Articulo
 */
public class ModeloArticulosTest {

    ModeloArticulos mArticulos;
    /**
     * Inicializa el modelo de artículos y carga datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        mArticulos = new ModeloArticulos();
        mArticulos.loadArticulos(0);
    }
    /**
     * Libera recursos después de cada test.
     */
    @AfterEach
    void tearDown() {
        mArticulos = null;
    }
    /**
     * Verifica que se cargan correctamente los artículos.
     */
    @Test
    void testLoadArticulos() {
        List<Articulo> articulos = mArticulos.getArticulos();
        assertEquals(9, articulos.size(), "Debe haber 9 artículos cargados");
    }
    /**
     * Verifica la creación de un artículo con valores válidos.
     */
    @Test
    void testMakeArticulo() {
        Articulo articulo = mArticulos.makeArticulo("Prueba", 15.45f, 300);
        assertEquals("Prueba", articulo.getDescripcion());
        assertEquals(15.45f, articulo.getPrecio());
        assertEquals(300, articulo.getMinutosPreparacion());
        assertTrue(articulo.getCodigo().startsWith("ART"));
    }
    /**
     * Verifica que se puede añadir un artículo y asignarle stock.
     */
    @Test
    void testAddArticuloAndStock() {
        Articulo nuevo = mArticulos.makeArticulo("Prueba", 12.5f, 50);
        mArticulos.addArticulo(nuevo);
        mArticulos.addStockArticulo(nuevo, 7);

        List<Articulo> articulos = mArticulos.getArticulos();
        Map<Articulo, Integer> stock = mArticulos.getStockArticulos();

        assertTrue(articulos.contains(nuevo));
        assertEquals(7, stock.get(nuevo));
    }
    /**
     * Verifica que se puede eliminar un artículo y su stock.
     */
    @Test
    void testRemoveArticuloAndStock() {
        Articulo articulo = mArticulos.getArticulos().getFirst();
        mArticulos.removeArticulo(articulo);
        mArticulos.removeStockArticulo(articulo);

        assertFalse(mArticulos.getArticulos().contains(articulo));
        assertFalse(mArticulos.getStockArticulos().containsKey(articulo));
    }
    /**
     * Verifica que se actualiza correctamente un artículo.
     */
    @Test
    void testUpdateArticulo() {
        Articulo articuloOld = mArticulos.getArticulos().getFirst();
        Articulo articuloNew = new Articulo(articuloOld.getCodigo(), "Nuevo nombre", 99.99f, 60);
        mArticulos.updateArticulo(articuloOld, articuloNew);

        Articulo obtenido = mArticulos.getArticuloIndex(0);
        assertEquals("Nuevo nombre", obtenido.getDescripcion());
        assertEquals(99.99f, obtenido.getPrecio(), 0.001);
    }
    /**
     * Verifica que se puede actualizar el stock de un artículo.
     */
    @Test
    void testUpdateStock() {
        Articulo articulo = mArticulos.getArticulos().getFirst();
        mArticulos.updateStockArticulo(articulo, 42);
        assertEquals(42, mArticulos.getStockArticulos().get(articulo));
    }
    /**
     * Verifica la obtención de un artículo por índice válido e inválido.
     */
    @Test
    void testGetArticuloIndex() {
        Articulo articulo = mArticulos.getArticulos().get(1);
        assertEquals(articulo, mArticulos.getArticuloIndex(1));
        assertNull(mArticulos.getArticuloIndex(-1));
        assertNull(mArticulos.getArticuloIndex(100));
    }
    /**
     * Verifica si un código de artículo existe en el sistema.
     */
    @Test
    void testCheckArticulo() {
        Articulo articulo = mArticulos.getArticulos().getFirst();
        assertTrue(mArticulos.checkArticulo(articulo.getCodigo()));
        assertFalse(mArticulos.checkArticulo("ART999"));
    }
    /**
     * Verifica que se pueden establecer listas de artículos y su stock manualmente.
     */
    @Test
    void testSetArticulosAndStock() {
        List<Articulo> copia = List.copyOf(mArticulos.getArticulos());
        Map<Articulo, Integer> stockCopia = Map.copyOf(mArticulos.getStockArticulos());

        mArticulos.setArticulos(copia);
        mArticulos.setStockArticulos(stockCopia);

        assertEquals(9, mArticulos.getArticulos().size());
        assertEquals(9, mArticulos.getStockArticulos().size());
    }
}
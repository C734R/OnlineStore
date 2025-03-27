package javalinos.onlinestore;

import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.primitivos.Articulo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModeloArticulosTest {

    ModeloArticulos mArticulos;

    @BeforeEach
    void setUp() {
        mArticulos = new ModeloArticulos();
        assertTrue(mArticulos.loadArticulos(1));
    }
    @AfterEach
    void tearDown() {
        mArticulos = null;
    }

    @Test
    void testLoadArticulos() {
        List<Articulo> articulos = mArticulos.getArticulos();
        assertEquals(9, articulos.size());
    }

    @Test
    void testMakeArticulo() {
        Articulo articulo = mArticulos.makeArticulo("Prueba", 15.45f, 0.43f, 36);
        assertEquals("Prueba", articulo.getDescripcion());
        assertEquals(15.45f, articulo.getPrecio(), 0.001);
        assertEquals(0.43f, articulo.getPreparacion(), 0.001);
        assertEquals(36, articulo.getStock());
        assertSame(articulo, mArticulos.getArticulo(10));
    }

    @Test
    void testAddArticulo() {
        Articulo articulo = mArticulos.makeArticulo("Prueba", 15.45f, 0.43f, 36);
        mArticulos.addArticulo(articulo);
        List<Articulo> articulos = mArticulos.getArticulos();
        assertEquals(10, articulos.size());
    }

    @Test
    void testRemoveArticulo() {
        List<Articulo> articulos = mArticulos.getArticulos();
        mArticulos.removeArticulo(articulos.getFirst());
        assertEquals(8, mArticulos.getArticulos().size());
    }

    @Test
    void testGetArticulos() {
        List<Articulo> articulos = mArticulos.getArticulos();
        assertEquals(9, articulos.size());
    }

    @Test
    void testGetArticulo() {
        mArticulos.getArticulo(1);
        //assertSame();
    }

    @Test
    void testSetArticulos() {
        List<Articulo> articulos = mArticulos.getArticulos();
        mArticulos.setArticulos(articulos);
        assertEquals(9, mArticulos.getArticulos().size());
    }

}

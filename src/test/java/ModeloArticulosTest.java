import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.gestores.Local.ModeloArticulosLocal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas para el modelo de gestión de artículos.
 * - Entidades relacionadas: ArticuloDTO
 */
public class ModeloArticulosTest {

    ModeloArticulosLocal mArticulos;
    /**
     * Inicializa el modelo de artículos y carga datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        mArticulos = new ModeloArticulosLocal();
        mArticulos.loadArticulos();
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
        List<ArticuloDTO> ArticuloDTOS = mArticulos.getArticulos();
        assertEquals(9, ArticuloDTOS.size(), "Debe haber 9 artículos cargados");
    }
    /**
     * Verifica la creación de un artículo con valores válidos.
     */
    @Test
    void testMakeArticulo() {
        ArticuloDTO ArticuloDTO = mArticulos.makeArticulo("Prueba", 15.45f, 300);
        assertEquals("Prueba", ArticuloDTO.getDescripcion());
        assertEquals(15.45f, ArticuloDTO.getPrecio());
        assertEquals(300, ArticuloDTO.getMinutosPreparacion());
        assertTrue(ArticuloDTO.getCodigo().startsWith("ART"));
    }
    /**
     * Verifica que se puede añadir un artículo y asignarle stock.
     */
    @Test
    void testAddArticuloAndStock() {
        ArticuloDTO nuevo = mArticulos.makeArticulo("Prueba", 12.5f, 50);
        mArticulos.addArticulo(nuevo);
        mArticulos.addStockArticulo(nuevo, 7);

        List<ArticuloDTO> ArticuloDTOS = mArticulos.getArticulos();
        Map<ArticuloDTO, Integer> stock = mArticulos.getStockArticulos();

        assertTrue(ArticuloDTOS.contains(nuevo));
        assertEquals(7, stock.get(nuevo));
    }
    /**
     * Verifica que se puede eliminar un artículo y su stock.
     */
    @Test
    void testRemoveArticuloAndStock() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulos().getFirst();
        mArticulos.removeArticulo(ArticuloDTO);
        mArticulos.removeStockArticulo(ArticuloDTO);

        assertFalse(mArticulos.getArticulos().contains(ArticuloDTO));
        assertFalse(mArticulos.getStockArticulos().containsKey(ArticuloDTO));
    }
    /**
     * Verifica que se actualiza correctamente un artículo.
     */
    @Test
    void testUpdateArticulo() {
        ArticuloDTO ArticuloDTOOld = mArticulos.getArticulos().getFirst();
        ArticuloDTO ArticuloDTONew = new ArticuloDTO(ArticuloDTOOld.getCodigo(), "Nuevo nombre", 99.99f, 60);
        mArticulos.updateArticulo(ArticuloDTOOld, ArticuloDTONew);

        ArticuloDTO obtenido = mArticulos.getArticuloIndex(0);
        assertEquals("Nuevo nombre", obtenido.getDescripcion());
        assertEquals(99.99f, obtenido.getPrecio(), 0.001);
    }
    /**
     * Verifica que se puede actualizar el stock de un artículo.
     */
    @Test
    void testUpdateStock() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulos().getFirst();
        mArticulos.updateStockArticulo(ArticuloDTO, 42);
        assertEquals(42, mArticulos.getStockArticulos().get(ArticuloDTO));
    }
    /**
     * Verifica la obtención de un artículo por índice válido e inválido.
     */
    @Test
    void testGetArticuloIndex() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulos().get(1);
        assertEquals(ArticuloDTO, mArticulos.getArticuloIndex(1));
        assertNull(mArticulos.getArticuloIndex(-1));
        assertNull(mArticulos.getArticuloIndex(100));
    }
    /**
     * Verifica si un código de artículo existe en el sistema.
     */
    @Test
    void testCheckArticulo() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulos().getFirst();
        assertTrue(mArticulos.checkArticulo(ArticuloDTO.getCodigo()));
        assertFalse(mArticulos.checkArticulo("ART999"));
    }
    /**
     * Verifica que se pueden establecer listas de artículos y su stock manualmente.
     */
    @Test
    void testSetArticulosAndStock() {
        List<ArticuloDTO> copia = List.copyOf(mArticulos.getArticulos());
        Map<ArticuloDTO, Integer> stockCopia = Map.copyOf(mArticulos.getStockArticulos());

        mArticulos.setArticulos(copia);
        mArticulos.setStockArticulos(stockCopia);

        assertEquals(9, mArticulos.getArticulos().size());
        assertEquals(9, mArticulos.getStockArticulos().size());
    }
}
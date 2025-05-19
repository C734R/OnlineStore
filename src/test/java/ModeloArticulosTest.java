import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.gestores.Local.ModeloArticulosLocal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
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
        List<ArticuloDTO> ArticuloDTOS = mArticulos.getArticulosDTO();
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
        assertEquals(300, ArticuloDTO.getMinutosPreparacion(preparacion));
        assertTrue(ArticuloDTO.getCodigo().startsWith("ART"));
    }
    /**
     * Verifica que se puede añadir un artículo y asignarle stock.
     */
    @Test
    void testAddArticuloAndStock() {
        ArticuloDTO nuevo = mArticulos.makeArticulo("Prueba", 12.5f, 50);
        mArticulos.addArticulo(nuevo);
        try {
            mArticulos.addArticuloStock(nuevo, 7);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<ArticuloDTO> ArticuloDTOS = mArticulos.getArticulosDTO();
        Map<ArticuloDTO, Integer> stock = null;
        try {
            stock = mArticulos.getArticuloStocksDTO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertTrue(ArticuloDTOS.contains(nuevo));
        assertEquals(7, stock.get(nuevo));
    }
    /**
     * Verifica que se puede eliminar un artículo y su stock.
     */
    @Test
    void testRemoveArticuloAndStock() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulosDTO().getFirst();
        try {
            mArticulos.removeArticulo(ArticuloDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            mArticulos.removeArticuloStock(ArticuloDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertFalse(mArticulos.getArticulosDTO().contains(ArticuloDTO));
        try {
            assertFalse(mArticulos.getArticuloStocksDTO().containsKey(ArticuloDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Verifica que se actualiza correctamente un artículo.
     */
    @Test
    void testUpdateArticulo() {
        ArticuloDTO ArticuloDTOOld = mArticulos.getArticulosDTO().getFirst();
        ArticuloDTO ArticuloDTONew = new ArticuloDTO(ArticuloDTOOld.getCodigo(), "Nuevo nombre", 99.99f, 60);
        try {
            mArticulos.updateArticulo(ArticuloDTOOld, ArticuloDTONew);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ArticuloDTO obtenido = null;
        try {
            obtenido = mArticulos.getArticuloDTOIndex(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("Nuevo nombre", obtenido.getDescripcion());
        assertEquals(99.99f, obtenido.getPrecio(), 0.001);
    }
    /**
     * Verifica que se puede actualizar el stock de un artículo.
     */
    @Test
    void testUpdateStock() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulosDTO().getFirst();
        try {
            mArticulos.updateStockArticulo(ArticuloDTO, 42);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(42, mArticulos.getArticuloStocksDTO().get(ArticuloDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Verifica la obtención de un artículo por índice válido e inválido.
     */
    @Test
    void testGetArticuloIndex() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulosDTO().get(1);
        try {
            assertEquals(ArticuloDTO, mArticulos.getArticuloDTOIndex(1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            assertNull(mArticulos.getArticuloDTOIndex(-1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            assertNull(mArticulos.getArticuloDTOIndex(100));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Verifica si un código de artículo existe en el sistema.
     */
    @Test
    void testCheckArticulo() {
        ArticuloDTO ArticuloDTO = mArticulos.getArticulosDTO().getFirst();
        assertTrue(mArticulos.checkArticulo(ArticuloDTO.getCodigo()));
        assertFalse(mArticulos.checkArticulo("ART999"));
    }
    /**
     * Verifica que se pueden establecer listas de artículos y su stock manualmente.
     */
    @Test
    void testSetArticulosAndStock() {
        List<ArticuloDTO> copia = List.copyOf(mArticulos.getArticulosDTO());
        LinkedHashMap<ArticuloDTO, Integer> stockCopia = null;
        try {
            stockCopia = (LinkedHashMap<ArticuloDTO, Integer>) Map.copyOf(mArticulos.getArticuloStocksDTO());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            mArticulos.setArticulos(copia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            mArticulos.setStockArticulos(stockCopia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(9, mArticulos.getArticulosDTO().size());
        try {
            assertEquals(9, mArticulos.getArticuloStocksDTO().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
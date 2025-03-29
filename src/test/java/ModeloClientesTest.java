import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas para el modelo de gestión de clientes.
 * - Entidades relacionadas: Cliente, Categoria
 */
public class ModeloClientesTest {

    ModeloClientes mClientes;
    /**
     * Inicializa el modelo de clientes y carga datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        mClientes = new ModeloClientes();
        mClientes.loadClientes(0); // Cargar datos de prueba
    }
    /**
     * Libera recursos después de cada test.
     */
    @AfterEach
    void tearDown() {
        mClientes = null;
    }
    /**
     * Verifica que se cargan correctamente los clientes.
     */
    @Test
    void testLoadClientes() {
        List<Cliente> clientes = mClientes.getClientes();
        assertEquals(10, clientes.size(), "Debe haber 10 clientes precargados");
    }
    /**
     * Verifica la búsqueda de un cliente por NIF.
     */
    @Test
    void testBuscarPorNif() {
        Cliente primero = mClientes.getClientes().get(0);
        Cliente encontrado = mClientes.getClienteNif(primero.getNif());
        assertNotNull(encontrado);
        assertEquals(primero.getNif(), encontrado.getNif());
    }
    /**
     * Verifica la búsqueda de un cliente por email.
     */
    @Test
    void testBuscarPorEmail() {
        Cliente primero = mClientes.getClientes().get(0);
        Cliente encontrado = mClientes.getClienteEmail(primero.getEmail());
        assertNotNull(encontrado);
        assertEquals(primero.getEmail(), encontrado.getEmail());
    }
    /**
     * Verifica que se puede añadir un nuevo cliente.
     */
    @Test
    void testAddCliente() {
        Cliente nuevo = mClientes.makeCliente("Ana", "Calle Nueva", "11111111H", "ana@email.com", mClientes.getCategoria(1));
        mClientes.addCliente(nuevo);

        Cliente buscado = mClientes.getClienteNif("11111111H");
        assertNotNull(buscado);
        assertEquals("Ana", buscado.getNombre());
    }
    /**
     * Verifica que se puede eliminar un cliente existente.
     */
    @Test
    void testDeleteCliente() {
        Cliente cliente = mClientes.getClientes().get(0);
        mClientes.removeCliente(cliente);

        Cliente eliminado = mClientes.getClienteNif(cliente.getNif());
        assertNull(eliminado);
    }
    /**
     * Verifica que se puede modificar el nombre de un cliente.
     */
    @Test
    void testModNombreCliente() {
        Cliente cliente = mClientes.getClientes().get(0);
        String nombreOriginal = cliente.getNombre();

        cliente.setNombre("NuevoNombre");
        mClientes.updateCliente(cliente, cliente);

        Cliente modificado = mClientes.getClienteNif(cliente.getNif());
        assertEquals("NuevoNombre", modificado.getNombre());
        assertNotEquals(nombreOriginal, modificado.getNombre());
    }
    /**
     * Verifica que se obtienen correctamente los clientes por categoría.
     */
    @Test
    void testClientesPorCategoria() {
        Cliente cliente = mClientes.getClientes().get(0);
        Categoria categoria = cliente.getCategoria();

        List<Cliente> mismos = mClientes.getClientesCategoria(categoria);
        assertFalse(mismos.isEmpty());
        assertTrue(mismos.stream().allMatch(c -> c.getCategoria().equals(categoria)));
    }
    /**
     * Verifica que se puede obtener un cliente por su índice.
     */
    @Test
    void testObtenerPorIndice() {
        Cliente cliente = mClientes.getClienteIndex(0);
        assertNotNull(cliente);
    }
    /**
     * Verifica los valores del primer y último índice de cliente.
     */
    @Test
    void testPrimerYUltimoIndice() {
        int primero = mClientes.getFirstIndexCliente();
        int ultimo = mClientes.getLastIndexCliente();
        int total = mClientes.getClientes().size();

        assertEquals(0, primero);
        assertEquals(total - 1, ultimo);
    }
    /**
     * Verifica la obtención de una categoría por su ID.
     */
    @Test
    void testObtenerCategoria() {
        Categoria categoria = mClientes.getCategoria(1);
        assertNotNull(categoria);
    }
}
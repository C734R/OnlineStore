import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModeloClientesTest {

    ModeloClientes mClientes;

    @BeforeEach
    void setUp() {
        mClientes = new ModeloClientes();
        mClientes.loadClientes(0); // Cargar datos de prueba
    }

    @AfterEach
    void tearDown() {
        mClientes = null;
    }

    @Test
    void testLoadClientes() {
        List<Cliente> clientes = mClientes.getClientes();
        assertEquals(10, clientes.size(), "Debe haber 10 clientes precargados");
    }

    @Test
    void testBuscarPorNif() {
        Cliente primero = mClientes.getClientes().get(0);
        Cliente encontrado = mClientes.getClienteNif(primero.getNif());
        assertNotNull(encontrado);
        assertEquals(primero.getNif(), encontrado.getNif());
    }

    @Test
    void testBuscarPorEmail() {
        Cliente primero = mClientes.getClientes().get(0);
        Cliente encontrado = mClientes.getClienteEmail(primero.getEmail());
        assertNotNull(encontrado);
        assertEquals(primero.getEmail(), encontrado.getEmail());
    }

    @Test
    void testAddCliente() {
        Cliente nuevo = mClientes.makeCliente("Ana", "Calle Nueva", "11111111H", "ana@email.com", mClientes.getCategoria(1));
        mClientes.addCliente(nuevo);

        Cliente buscado = mClientes.getClienteNif("11111111H");
        assertNotNull(buscado);
        assertEquals("Ana", buscado.getNombre());
    }

    @Test
    void testDeleteCliente() {
        Cliente cliente = mClientes.getClientes().get(0);
        mClientes.removeCliente(cliente);

        Cliente eliminado = mClientes.getClienteNif(cliente.getNif());
        assertNull(eliminado);
    }

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

    @Test
    void testClientesPorCategoria() {
        Cliente cliente = mClientes.getClientes().get(0);
        Categoria categoria = cliente.getCategoria();

        List<Cliente> mismos = mClientes.getClientesCategoria(categoria);
        assertFalse(mismos.isEmpty());
        assertTrue(mismos.stream().allMatch(c -> c.getCategoria().equals(categoria)));
    }

    @Test
    void testObtenerPorIndice() {
        Cliente cliente = mClientes.getClienteIndex(0);
        assertNotNull(cliente);
    }

    @Test
    void testPrimerYUltimoIndice() {
        int primero = mClientes.getFirstIndexCliente();
        int ultimo = mClientes.getLastIndexCliente();
        int total = mClientes.getClientes().size();

        assertEquals(0, primero);
        assertEquals(total - 1, ultimo);
    }

    @Test
    void testObtenerCategoria() {
        Categoria categoria = mClientes.getCategoria(1);
        assertNotNull(categoria);
    }
}
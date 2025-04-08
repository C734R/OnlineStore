import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.gestores.Local.ModeloClientesLocal;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas para el modelo de gestión de clienteDTOS.
 * - Entidades relacionadas: ClienteDTO, CategoriaDTO
 */
public class ModeloClientesTest {

    ModeloClientesLocal mClientes;
    /**
     * Inicializa el modelo de clienteDTOS y carga datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        mClientes = new ModeloClientesLocal(0, null);
        mClientes.loadClientes();
    }
    /**
     * Libera recursos después de cada test.
     */
    @AfterEach
    void tearDown() {
        mClientes = null;
    }
    /**
     * Verifica que se cargan correctamente los clienteDTOS.
     */
    @Test
    void testLoadClientes() throws Exception {
        List<ClienteDTO> ClienteDTOS = mClientes.getClientes();
        assertEquals(10, ClienteDTOS.size(), "Debe haber 10 clienteDTOS precargados");
    }
    /**
     * Verifica la búsqueda de un cliente por NIF.
     */
    @Test
    void testBuscarPorNif() throws Exception {
        ClienteDTO primero = mClientes.getClientes().get(0);
        ClienteDTO encontrado = mClientes.getClienteNif(primero.getNif());
        assertNotNull(encontrado);
        assertEquals(primero.getNif(), encontrado.getNif());
    }
    /**
     * Verifica la búsqueda de un cliente por email.
     */
    @Test
    void testBuscarPorEmail() throws Exception {
        ClienteDTO primero = mClientes.getClientes().getFirst();
        ClienteDTO encontrado = mClientes.getClienteEmail(primero.getEmail());
        assertNotNull(encontrado);
        assertEquals(primero.getEmail(), encontrado.getEmail());
    }
    /**
     * Verifica que se puede añadir un nuevo cliente.
     */
    @Test
    void testAddCliente() {
        ClienteDTO nuevo = mClientes.makeCliente("Ana", "Calle Nueva", "11111111H", "ana@email.com", mClientes.getCategoria(1));
        mClientes.addCliente(nuevo);

        ClienteDTO buscado = mClientes.getClienteNif("11111111H");
        assertNotNull(buscado);
        assertEquals("Ana", buscado.getNombre());
    }
    /**
     * Verifica que se puede eliminar un cliente existente.
     */
    @Test
    void testDeleteCliente() throws Exception {
        ClienteDTO ClienteDTO = mClientes.getClientes().getFirst();
        mClientes.removeCliente(ClienteDTO);

        ClienteDTO eliminado = mClientes.getClienteNif(ClienteDTO.getNif());
        assertNull(eliminado);
    }
    /**
     * Verifica que se puede modificar el nombre de un cliente.
     */
    @Test
    void testModNombreCliente() throws Exception {
        ClienteDTO ClienteDTO = mClientes.getClientes().getFirst();
        String nombreOriginal = ClienteDTO.getNombre();

        ClienteDTO.setNombre("NuevoNombre");
        mClientes.updateCliente(ClienteDTO, ClienteDTO);

        ClienteDTO modificado = mClientes.getClienteNif(ClienteDTO.getNif());
        assertEquals("NuevoNombre", modificado.getNombre());
        assertNotEquals(nombreOriginal, modificado.getNombre());
    }
    /**
     * Verifica que se obtienen correctamente los clienteDTOS por categoría.
     */
    @Test
    void testClientesPorCategoria() throws Exception {
        ClienteDTO ClienteDTO = mClientes.getClientes().getFirst();
        CategoriaDTO categoriaDTO = ClienteDTO.getCategoria();

        List<ClienteDTO> mismos = mClientes.getClientesCategoria(categoriaDTO);
        assertFalse(mismos.isEmpty());
        assertTrue(mismos.stream().allMatch(c -> c.getCategoria().equals(categoriaDTO)));
    }
    /**
     * Verifica que se puede obtener un cliente por su índice.
     */
    @Test
    void testObtenerPorIndice() {
        ClienteDTO ClienteDTO = mClientes.getClienteIndex(0);
        assertNotNull(ClienteDTO);
    }
    /**
     * Verifica los valores del primer y último índice de cliente.
     */
    @Test
    void testPrimerYUltimoIndice() throws Exception {
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
        CategoriaDTO categoriaDTO = mClientes.getCategoria(1);
        assertNotNull(categoriaDTO);
    }
}
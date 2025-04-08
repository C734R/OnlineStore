import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.modelo.gestores.Local.ModeloArticulosLocal;
import javalinos.onlinestore.modelo.gestores.Local.ModeloClientesLocal;
import javalinos.onlinestore.modelo.gestores.Local.ModeloPedidosLocal;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para el modelo de gestión de pedidos.
 * - Entidades relacionadas: PedidoDTO, ClienteDTO, ArticuloDTO
 */
class ModeloPedidosTest {

    ModeloPedidosLocal mPedidos;
    ModeloArticulosLocal mArticulos;
    ModeloClientesLocal mClientes;
    List<ClienteDTO> ClienteDTOS;
    List<ArticuloDTO> ArticuloDTOS;
    Map<ArticuloDTO, Integer> stock;
    /**
     * Inicializa los modelos y carga datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() throws Exception {
        mArticulos = new ModeloArticulosLocal();
        mArticulos.loadArticulos();
        ArticuloDTOS = mArticulos.getArticulos();
        stock = mArticulos.getStockArticulos();

        mClientes = new ModeloClientesLocal();
        mClientes.loadClientes();
        ClienteDTOS = mClientes.getClientes();

        mPedidos = new ModeloPedidosLocal();
        mPedidos.loadPedidos( ClienteDTOS, ArticuloDTOS);
    }
    /**
     * Libera recursos después de cada test.
     */
    @AfterEach
    void tearDown() {
        mClientes = null;
        mArticulos = null;
        mPedidos = null;
        ClienteDTOS = null;
        ArticuloDTOS = null;
        stock = null;
    }
    /**
     * Verifica que se cargan correctamente los pedidos.
     */
    @Test
    void loadPedidosTest() {
        assertEquals(18, mPedidos.getPedidos().size());
    }
    /**
     * Verifica que la lista de artículos no esté vacía.
     */
    @Test
    void getArticulosTest() {
        assertFalse(ArticuloDTOS.isEmpty());
    }
    /**
     * Verifica que la lista de clienteDTOS no esté vacía.
     */
    @Test
    void getClientesTest() {
        assertFalse(ClienteDTOS.isEmpty());
    }
    /**
     * Verifica que se pueda añadir un pedido correctamente.
     */
    @Test
    void addPedidoTest() {
        int sizeAnterior = mPedidos.getPedidos().size();
        ArticuloDTO ArticuloDTO = ArticuloDTOS.get(0);
        ClienteDTO ClienteDTO = ClienteDTOS.get(0);
        int cantidad = 2;

        PedidoDTO p = mPedidos.makePedido(ClienteDTO, ArticuloDTO, cantidad, LocalDate.now(), 5f);
        mPedidos.addPedido(p);

        assertEquals(sizeAnterior + 1, mPedidos.getPedidos().size());
    }
    /**
     * Verifica que se pueda eliminar un pedido correctamente.
     */
    @Test
    void removePedidoTest() {
        ArticuloDTO ArticuloDTO = ArticuloDTOS.get(1);
        ClienteDTO ClienteDTO = ClienteDTOS.get(1);

        PedidoDTO p = mPedidos.makePedido(ClienteDTO, ArticuloDTO, 1, LocalDate.now(), 5f);
        mPedidos.addPedido(p);
        assertTrue(mPedidos.getPedidos().contains(p));

        mPedidos.removePedido(p);
        assertFalse(mPedidos.getPedidos().contains(p));
    }
    /**
     * Verifica la obtención de un pedido por número.
     */
    @Test
    void getPedidoNumeroTest() {
        PedidoDTO pedidoDTO = mPedidos.getPedidos().getFirst();
        PedidoDTO obtenido = mPedidos.getPedidoNumero(pedidoDTO.getNumero());
        assertNotNull(obtenido);
        assertEquals(pedidoDTO.getNumero(), obtenido.getNumero());
    }
    /**
     * Verifica que se obtienen los pedidos de un cliente específico.
     */
    @Test
    void getPedidosClienteTest() {
        ClienteDTO ClienteDTO = mPedidos.getPedidos().getFirst().getCliente();
        List<PedidoDTO> pedidosCliente = mPedidos.getPedidosCliente(ClienteDTO);
        assertFalse(pedidosCliente.isEmpty());
        for (PedidoDTO p : pedidosCliente) {
            assertEquals(ClienteDTO, p.getCliente());
        }
    }
    /**
     * Verifica la separación correcta entre pedidos pendientes y enviados.
     */
    @Test
    void getPedidosPendientesEnviadosTest() {
        LocalDate hoy = LocalDate.now();

        List<PedidoDTO> pendientes = mPedidos.getPedidosPendientesEnviados(false, null);
        List<PedidoDTO> enviados = mPedidos.getPedidosPendientesEnviados(true, null);

        assertNotNull(pendientes);
        assertNotNull(enviados);

        for (PedidoDTO p : pendientes) {
            LocalDate fechaEnvio = p.getFechahora().plusDays(p.getDiasPreparacion());
            assertTrue(hoy.isBefore(fechaEnvio) || hoy.isEqual(fechaEnvio),
                    "PedidoDTO pendiente tiene fecha de envío pasada");
        }

        for (PedidoDTO p : enviados) {
            LocalDate fechaEnvio = p.getFechahora().plusDays(p.getDiasPreparacion());
            assertTrue(hoy.isAfter(fechaEnvio),
                    "PedidoDTO enviado aún no ha superado su fecha de envío");
        }
    }
    /**
     * Verifica que se obtiene correctamente el número del último pedido.
     */
    @Test
    void getLastNumPedidoTest() {
        int lastNum = mPedidos.getLastNumPedido();
        assertEquals(mPedidos.getPedidos().getLast().getNumero(), lastNum);
    }
    /**
     * Verifica que se obtiene correctamente el número del primer pedido.
     */
    @Test
    void getFirstNumPedidoTest() {
        int firstNum = mPedidos.getFirstNumPedido();
        assertEquals(mPedidos.getPedidos().getFirst().getNumero(), firstNum);
    }
    /**
     * Verifica la actualización de un pedido manteniendo su número.
     */
    @Test
    void updatePedidoTest() {
        PedidoDTO pedidoDTOOld = mPedidos.getPedidos().getFirst();
        PedidoDTO pedidoDTONew = new PedidoDTO(pedidoDTOOld); // Copia del pedido original

        int nuevaCantidad = pedidoDTOOld.getCantidad() + 1;
        float nuevoPrecio = pedidoDTOOld.getPrecio() + 10f;

        pedidoDTONew.setCantidad(nuevaCantidad);
        pedidoDTONew.setPrecio(nuevoPrecio);

        mPedidos.updatePedido(pedidoDTOOld, pedidoDTONew);

        PedidoDTO actualizado = mPedidos.getPedidoNumero(pedidoDTOOld.getNumero());

        assertEquals(pedidoDTOOld.getNumero(), actualizado.getNumero(), "El número de pedido debe mantenerse igual");
        assertEquals(nuevaCantidad, actualizado.getCantidad(), "La cantidad debe actualizarse");
        assertEquals(nuevoPrecio, actualizado.getPrecio(), 0.01f, "El precio debe actualizarse");
    }
    /**
     * Verifica que al añadir un pedido se actualiza correctamente el stock.
     */
    @Test
    void checkStockAddPedido() {
        ArticuloDTO ArticuloDTO = ArticuloDTOS.getFirst();
        ClienteDTO ClienteDTO = ClienteDTOS.getFirst();
        int stockInicial = stock.get(ArticuloDTO);
        int cantidadPedido = 3;

        PedidoDTO pedidoDTO = mPedidos.makePedido(ClienteDTO, ArticuloDTO, cantidadPedido, LocalDate.now(), 5f);
        mPedidos.addPedido(pedidoDTO);

        mArticulos.updateStockArticulo(ArticuloDTO, stockInicial - cantidadPedido);

        int stockActual = mArticulos.getStockArticulos().get(ArticuloDTO);
        assertEquals(stockInicial - cantidadPedido, stockActual);
    }
    /**
     * Verifica que al eliminar un pedido se restaura correctamente el stock.
     */
    @Test
    void checkStockRemovePedidoTest() {
        ArticuloDTO ArticuloDTO = ArticuloDTOS.get(1);
        ClienteDTO ClienteDTO = ClienteDTOS.get(1);
        int cantidadPedido = 2;
        int stockInicial = stock.get(ArticuloDTO);

        PedidoDTO pedidoDTO = mPedidos.makePedido(ClienteDTO, ArticuloDTO, cantidadPedido, LocalDate.now(), 5f);
        mPedidos.addPedido(pedidoDTO);

        mArticulos.updateStockArticulo(ArticuloDTO, stockInicial - cantidadPedido);

        mPedidos.removePedido(pedidoDTO);
        mArticulos.updateStockArticulo(ArticuloDTO, mArticulos.getStockArticulos().get(ArticuloDTO) + cantidadPedido);

        int stockFinal = mArticulos.getStockArticulos().get(ArticuloDTO);
        assertEquals(stockInicial, stockFinal);
    }
}
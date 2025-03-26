package javalinos.onlinestore;

import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloPedidos;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModeloPedidosTest {

    ModeloPedidos mPedidos;
    ModeloArticulos mArticulos;
    ModeloClientes mClientes;
    List<Cliente> clientes;
    List<Articulo> articulos;

    /**
     * Acciones antes de cada test
     */
    @BeforeEach
    void setUp() {
        mArticulos = new ModeloArticulos();
        loadArticulosTest();
        articulos = mArticulos.getArticulos();
        mClientes = new ModeloClientes();
        loadClientesTest();
        clientes = mClientes.getClientes();
        mPedidos = new ModeloPedidos();
        mPedidos.loadPedidos(1, clientes, articulos);
    }

    /**
     * Acciones después de cada test
     */
    @AfterEach
    void tearDown() {
        mClientes = null;
        mArticulos = null;
        mPedidos = null;
        clientes = null;
        articulos = null;
    }

    /**
     * Precarga de artículos
     */
    @Test
    void loadArticulosTest() {
        assertTrue(mArticulos.loadArticulos(0));
    }

    /**
     * Precarga de clientes
     */
    @Test
    void loadClientesTest() {
        assertTrue(mClientes.loadClientes(0));
    }

    /**
     * Obtención de artículos
     */
    @Test
    void getArticulosTest() {
        assertFalse(mArticulos.getArticulos().isEmpty());
    }

    /**
     * Obtención de clientes
     */
    @Test
    void getClientesTest() {
        assertFalse(mClientes.getClientes().isEmpty());
    }

    /**
     * Adición de pedido
     */
    @Test
    void addPedidoTest() {
        int cantidad = mPedidos.getPedidos().size();
        Pedido p = mPedidos.makePedido(clientes.get(0), articulos.get(0), 1, LocalDate.now(), 5f, 10f);
        mPedidos.addPedido(p);
        assertEquals(cantidad + 1, mPedidos.getPedidos().size());
    }

    /**
     * Eliminación de pedido
     */
    @Test
    void removePedidoTest() {
        Pedido p = mPedidos.makePedido(clientes.get(0), articulos.get(0), 1, LocalDate.now(), 5f, 10f);
        mPedidos.addPedido(p);
        mPedidos.removePedido(p);
        assertFalse(mPedidos.getPedidos().contains(p));
    }

    /**
     * Obtención de número de pedido
     */
    @Test
    void getPedidoNumeroTest() {
        Pedido pedido = mPedidos.getPedidos().get(0);
        assertEquals(pedido, mPedidos.getPedidoNumero(0));
    }

    /**
     * Obtención de pedidos de un cliente
     */
    @Test
    void getPedidosClienteTest() {
        Cliente cliente = mPedidos.getPedidos().get(0).getCliente();
        List<Pedido> pedidosCliente = mPedidos.getPedidosCliente(cliente);
        for (Pedido p : pedidosCliente) {
            assertEquals(cliente, p.getCliente());
        }
    }

    /**
     * Obtención de pedidos pendientes y enviados
     */
    @Test
    void getPedidosPendientesEnviadosTest() {
        LocalDate fechaPendientes = LocalDate.now().plusDays(10);
        LocalDate fechaEnviados = LocalDate.now().plusDays(10);
        List<Pedido> pendientes = mPedidos.getPedidosPendientesEnviados(fechaPendientes, false, null);
        List<Pedido> enviados = mPedidos.getPedidosPendientesEnviados(fechaEnviados, true, null);

        assertNotNull(pendientes);
        assertNotNull(enviados);

        for (Pedido p : pendientes) {
            float dias = p.getArticulo().getPreparacion() * p.getCantidad();
            assertTrue(fechaPendientes.isBefore(p.getFechahora().plusDays((int) Math.ceil(dias))));
        }

        for (Pedido p : enviados) {
            float dias = p.getArticulo().getPreparacion() * p.getCantidad();
            assertTrue(fechaPendientes.isAfter(p.getFechahora().plusDays((int) Math.ceil(dias))));
        }
    }

    /**
     * Obtención del último número de pedido
     */
    @Test
    void getLastNumPedidoTest() {
        int lastNum = mPedidos.getLastNumPedido();
        assertEquals(mPedidos.getPedidos().getLast().getNumero(), lastNum);
    }

    /**
     * Obtención del primer número de pedido
     */
    @Test
    void getFirstNumPedidoTest() {
        int firstNum = mPedidos.getFirstNumPedido();
        assertEquals(mPedidos.getPedidos().getFirst().getNumero(), firstNum);
    }

    /**
     * Precarga de pedidos
     */
    @Test
    void loadPedidosTest() {
        boolean cargado = mPedidos.loadPedidos(1, clientes, articulos);
        assertTrue(cargado);
        assertEquals(9, mPedidos.getPedidos().size());
    }
}
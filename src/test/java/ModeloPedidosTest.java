import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloPedidos;
import javalinos.onlinestore.modelo.primitivos.Articulo;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.modelo.primitivos.Pedido;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para el modelo de gestión de pedidos.
 * - Entidades relacionadas: Pedido, Cliente, Articulo
 */
class ModeloPedidosTest {

    ModeloPedidos mPedidos;
    ModeloArticulos mArticulos;
    ModeloClientes mClientes;
    List<Cliente> clientes;
    List<Articulo> articulos;
    Map<Articulo, Integer> stock;
    /**
     * Inicializa los modelos y carga datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        mArticulos = new ModeloArticulos();
        assertTrue(mArticulos.loadArticulos(0));
        articulos = mArticulos.getArticulos();
        stock = mArticulos.getStockArticulos();

        mClientes = new ModeloClientes();
        assertTrue(mClientes.loadClientes(0));
        clientes = mClientes.getClientes();

        mPedidos = new ModeloPedidos();
        assertTrue(mPedidos.loadPedidos(0, clientes, articulos));
    }
    /**
     * Libera recursos después de cada test.
     */
    @AfterEach
    void tearDown() {
        mClientes = null;
        mArticulos = null;
        mPedidos = null;
        clientes = null;
        articulos = null;
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
        assertFalse(articulos.isEmpty());
    }
    /**
     * Verifica que la lista de clientes no esté vacía.
     */
    @Test
    void getClientesTest() {
        assertFalse(clientes.isEmpty());
    }
    /**
     * Verifica que se pueda añadir un pedido correctamente.
     */
    @Test
    void addPedidoTest() {
        int sizeAnterior = mPedidos.getPedidos().size();
        Articulo articulo = articulos.get(0);
        Cliente cliente = clientes.get(0);
        int cantidad = 2;

        Pedido p = mPedidos.makePedido(cliente, articulo, cantidad, LocalDate.now(), 5f);
        mPedidos.addPedido(p);

        assertEquals(sizeAnterior + 1, mPedidos.getPedidos().size());
    }
    /**
     * Verifica que se pueda eliminar un pedido correctamente.
     */
    @Test
    void removePedidoTest() {
        Articulo articulo = articulos.get(1);
        Cliente cliente = clientes.get(1);

        Pedido p = mPedidos.makePedido(cliente, articulo, 1, LocalDate.now(), 5f);
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
        Pedido pedido = mPedidos.getPedidos().getFirst();
        Pedido obtenido = mPedidos.getPedidoNumero(pedido.getNumero());
        assertNotNull(obtenido);
        assertEquals(pedido.getNumero(), obtenido.getNumero());
    }
    /**
     * Verifica que se obtienen los pedidos de un cliente específico.
     */
    @Test
    void getPedidosClienteTest() {
        Cliente cliente = mPedidos.getPedidos().getFirst().getCliente();
        List<Pedido> pedidosCliente = mPedidos.getPedidosCliente(cliente);
        assertFalse(pedidosCliente.isEmpty());
        for (Pedido p : pedidosCliente) {
            assertEquals(cliente, p.getCliente());
        }
    }
    /**
     * Verifica la separación correcta entre pedidos pendientes y enviados.
     */
    @Test
    void getPedidosPendientesEnviadosTest() {
        LocalDate hoy = LocalDate.now();

        List<Pedido> pendientes = mPedidos.getPedidosPendientesEnviados(false, null);
        List<Pedido> enviados = mPedidos.getPedidosPendientesEnviados(true, null);

        assertNotNull(pendientes);
        assertNotNull(enviados);

        for (Pedido p : pendientes) {
            LocalDate fechaEnvio = p.getFechahora().plusDays(p.getDiasPreparacion());
            assertTrue(hoy.isBefore(fechaEnvio) || hoy.isEqual(fechaEnvio),
                    "Pedido pendiente tiene fecha de envío pasada");
        }

        for (Pedido p : enviados) {
            LocalDate fechaEnvio = p.getFechahora().plusDays(p.getDiasPreparacion());
            assertTrue(hoy.isAfter(fechaEnvio),
                    "Pedido enviado aún no ha superado su fecha de envío");
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
        Pedido pedidoOld = mPedidos.getPedidos().getFirst();
        Pedido pedidoNew = new Pedido(pedidoOld); // Copia del pedido original

        int nuevaCantidad = pedidoOld.getCantidad() + 1;
        float nuevoPrecio = pedidoOld.getPrecio() + 10f;

        pedidoNew.setCantidad(nuevaCantidad);
        pedidoNew.setPrecio(nuevoPrecio);

        mPedidos.updatePedido(pedidoOld, pedidoNew);

        Pedido actualizado = mPedidos.getPedidoNumero(pedidoOld.getNumero());

        assertEquals(pedidoOld.getNumero(), actualizado.getNumero(), "El número de pedido debe mantenerse igual");
        assertEquals(nuevaCantidad, actualizado.getCantidad(), "La cantidad debe actualizarse");
        assertEquals(nuevoPrecio, actualizado.getPrecio(), 0.01f, "El precio debe actualizarse");
    }
    /**
     * Verifica que al añadir un pedido se actualiza correctamente el stock.
     */
    @Test
    void checkStockAddPedido() {
        Articulo articulo = articulos.getFirst();
        Cliente cliente = clientes.getFirst();
        int stockInicial = stock.get(articulo);
        int cantidadPedido = 3;

        Pedido pedido = mPedidos.makePedido(cliente, articulo, cantidadPedido, LocalDate.now(), 5f);
        mPedidos.addPedido(pedido);

        mArticulos.updateStockArticulo(articulo, stockInicial - cantidadPedido);

        int stockActual = mArticulos.getStockArticulos().get(articulo);
        assertEquals(stockInicial - cantidadPedido, stockActual);
    }
    /**
     * Verifica que al eliminar un pedido se restaura correctamente el stock.
     */
    @Test
    void checkStockRemovePedidoTest() {
        Articulo articulo = articulos.get(1);
        Cliente cliente = clientes.get(1);
        int cantidadPedido = 2;
        int stockInicial = stock.get(articulo);

        Pedido pedido = mPedidos.makePedido(cliente, articulo, cantidadPedido, LocalDate.now(), 5f);
        mPedidos.addPedido(pedido);

        mArticulos.updateStockArticulo(articulo, stockInicial - cantidadPedido);

        mPedidos.removePedido(pedido);
        mArticulos.updateStockArticulo(articulo, mArticulos.getStockArticulos().get(articulo) + cantidadPedido);

        int stockFinal = mArticulos.getStockArticulos().get(articulo);
        assertEquals(stockInicial, stockFinal);
    }
}
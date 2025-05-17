package javalinos.onlinestore.vista.JavaFX;

public enum TipoVentana {
    MenuPrincipal("MenuPrincipal", "/javalinos/onlinestore/Vistas/VistaMenuPrincipalJavaFX.fxml", "Menú Principal"),
    GestionClientes("GestionClientes","/javalinos/onlinestore/Vistas/VistaClientesJavaFX.fxml", "Gestión Clientes"),
    GestionArticulos("GestionArticulos", "/javalinos/onlinestore/Vistas/VistaArticulosJavaFX.fxml", "Gestion Artículos"),
    GestionPedidos("GestionPedidos", "/javalinos/onlinestore/Vistas/VistaPedidosJavaFX.fxml", "Gestion Pedidos");

    final String nombre;
    final String ruta;
    final String titulo;

    TipoVentana(String nombre, String ruta, String titulo) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.titulo = titulo;
    }
}
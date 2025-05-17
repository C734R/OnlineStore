package javalinos.onlinestore.vista;

import javalinos.onlinestore.Configuracion;
import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.vista.Consola.VistaArticulos;
import javalinos.onlinestore.vista.Consola.VistaClientes;
import javalinos.onlinestore.vista.Consola.VistaMenuPrincipal;
import javalinos.onlinestore.vista.Consola.VistaPedidos;
import javalinos.onlinestore.vista.Interfaces.IVistaArticulos;
import javalinos.onlinestore.vista.Interfaces.IVistaClientes;
import javalinos.onlinestore.vista.Interfaces.IVistaMenuPrincipal;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;
import javalinos.onlinestore.vista.JavaFX.VistaArticulosJavaFX;
import javalinos.onlinestore.vista.JavaFX.VistaClientesJavaFX;
import javalinos.onlinestore.vista.JavaFX.VistaMenuPrincipalJavaFX;
import javalinos.onlinestore.vista.JavaFX.VistaPedidosJavaFX;

import java.util.List;
import java.util.Map;

import static javalinos.onlinestore.OnlineStore.*;

public class FactoryVista {

    public FactoryVista() {}

    public IVistaClientes getVistaClientes() {

        if(configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) return new VistaClientesJavaFX(cClientes);
        else return new VistaClientes();
    }

    public IVistaArticulos getVistaArticulos() {
        if(configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) return new VistaArticulosJavaFX(cArticulos) {
            @Override
            public float askPrecio(float min, float max) {
                return 0;
            }

            @Override
            public void showListArticulos(List<ArticuloDTO> articulosDTO) {

            }

            @Override
            public void showListArticulosStock(Map<ArticuloDTO, Integer> articuloStockMap) {

            }

            @Override
            public void showListArticulosNumerada(List<ArticuloDTO> articulosDTO) {

            }

            @Override
            public void showArticulo(ArticuloDTO articuloDTO) {

            }

            @Override
            public void showStockArticulos(Map<ArticuloDTO, Integer> articuloStockMap) {

            }
        };
        else return new VistaArticulos();
    }

    public IVistaMenuPrincipal getVistaMenuPrincipal() {
        if(configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) return new VistaMenuPrincipalJavaFX();
        else return new VistaMenuPrincipal();
    }

    public IVistaPedidos getVistaPedidos() {
        if(configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) return new VistaPedidosJavaFX();
        else return new VistaPedidos();
    }
}

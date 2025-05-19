package javalinos.onlinestore.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javalinos.onlinestore.Configuracion;
import javalinos.onlinestore.vista.Consola.VistaArticulos;
import javalinos.onlinestore.vista.Consola.VistaClientes;
import javalinos.onlinestore.vista.Consola.VistaMenuPrincipal;
import javalinos.onlinestore.vista.Consola.VistaPedidos;
import javalinos.onlinestore.vista.Interfaces.IVistaArticulos;
import javalinos.onlinestore.vista.Interfaces.IVistaClientes;
import javalinos.onlinestore.vista.Interfaces.IVistaMenuPrincipal;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.io.IOException;

import static javalinos.onlinestore.OnlineStore.configuracion;

public class FactoryVista {

    private Parent vClientesRaiz;
    private Parent vArticulosRaiz;
    private Parent vPedidosRaiz;
    private Parent vMenuPrincipalRaiz;

    public FactoryVista() {}

    public Parent getVClientesRaiz() {
        return vClientesRaiz;
    }
    public Parent getVArticulosRaiz() {
        return vArticulosRaiz;
    }
    public Parent getVPedidosRaiz() {
        return vPedidosRaiz;
    }
    public Parent getVMenuPrincipalRaiz() {
        return vMenuPrincipalRaiz;
    }

    public IVistaClientes getVistaClientes() {
        if (configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javalinos/onlinestore/Vistas/VistaClientesJavaFX.fxml"));
                vClientesRaiz = loader.load();
                return loader.getController();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo cargar el FXML Clientes", e);
            }
        } else {
            return new VistaClientes();
        }
    }

    public IVistaArticulos getVistaArticulos() {
        if (configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javalinos/onlinestore/Vistas/VistaArticulosJavaFX.fxml"));
                vArticulosRaiz = loader.load();
                return loader.getController();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo cargar el FXML Articulos", e);
            }
        } else {
            return new VistaArticulos();
        }
    }

    public IVistaMenuPrincipal getVistaMenuPrincipal() {
        if (configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javalinos/onlinestore/Vistas/VistaMenuPrincipalJavaFX.fxml"));
                vMenuPrincipalRaiz = loader.load();
                return loader.getController();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo cargar el FXML MenuPrincipal", e);
            }
        } else {
            return new VistaMenuPrincipal();
        }
    }

    public IVistaPedidos getVistaPedidos() {
        if (configuracion == Configuracion.JAVAFX_ORM_HIBERNATE_MYSQL) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javalinos/onlinestore/Vistas/VistaPedidosJavaFX.fxml"));
                vPedidosRaiz = loader.load();
                return loader.getController();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo cargar el FXML Pedidos", e);
            }
        } else {
            return new VistaPedidos();
        }
    }
}

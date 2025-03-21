package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.vista.VistaClientes;

public class ControlClientes extends ControlBase {

    private VistaClientes vClientes;
    private ModeloClientes mClientes;

    public ControlClientes(ModeloStore mStore, VistaClientes vClientes) {
        super(mStore);
        this.vClientes = vClientes;
    }

    public ControlClientes() {
        super();
        this.vClientes = null;
    }

    public VistaClientes getVistaClientes() {
        return vClientes;
    }

    public void setVistaClientes(VistaClientes vClientes) {
        this.vClientes = vClientes;
    }

    public void iniciar() {
        int opcion;
        while (true) {
            vClientes.showCabecera();
            vClientes.showMenu(2);
            opcion = vClientes.askInt("Seleccione una opción", 0, 7, false, false);
            switch (opcion) {
                case 1:
                    addCliente();
                    break;
                case 2:
                    removeCliente();
                    break;
                case 3:
                    showCliente();
                    break;
                case 4:
                    modCliente();
                    break;
                case 5:
                    showListClientes();
                    break;
                case 6:
                    showListClientesTipo(vClientes.askTipoCliente());
                    break;
                case 0:
                    vClientes.showMensaje("Volviendo al menú principal...", true);
                    return;
                default:
                    vClientes.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.", true);
            }
        }
    }

    public void addCliente() {
        String nombre = vClientes.askString("Ingrese el nombre del cliente:", 50);
        String domicilio = vClientes.askString("Ingrese el domicilio del cliente:", 100);
        String nif = vClientes.askString("Ingrese el NIF del cliente:", 9);
        String email = vClientes.askString("Ingrese el email del cliente:", 50);

        Categoria categoria = vClientes.askTipoCliente();

        Cliente nuevoCliente = new Cliente(nombre, domicilio, nif, email, categoria);
        mClientes.addCliente(nuevoCliente);
        vClientes.showMensaje("Cliente agregado con éxito.", true);
    }

    public void removeCliente() {
        String nif = vClientes.askString("Ingrese el NIF del cliente a eliminar:", 9);
        Cliente cliente = mClientes.getClienteNif(nif);
        if (cliente != null) {
            mClientes.removeCliente(cliente);
            vClientes.showMensaje("Cliente eliminado correctamente.", true);
        } else {
            vClientes.showMensaje("Cliente no encontrado.", true);
        }
    }

    public void modCliente() {
        String nif = vClientes.askString("Ingrese el NIF del cliente a modificar:", 9);
        Cliente cliente = mClientes.getClienteNif(nif);
        if (cliente != null) {
            cliente.setNombre(vClientes.askString("Nuevo nombre:", cliente.getNombre()));
            cliente.setDomicilio(vClientes.askString("Nuevo domicilio:", cliente.getDomicilio()));
            cliente.setEmail(vClientes.askString("Nuevo email:", cliente.getEmail()));
            cliente.setCategoria(vClientes.askTipoCliente());
            vClientes.showMensaje("Cliente actualizado correctamente.", true);
        } else {
            vClientes.showMensaje("Cliente no encontrado.", true);
        }
    }

    public void showListClientesTipo(int tipoCliente) {
    }
    public void showCliente() {
        String nif = vClientes.askString("Ingrese el NIF del cliente:", 9);
        Cliente cliente = mClientes.getClienteNif(nif);
        if (cliente != null) {
            vClientes.showMensaje(cliente.toString(), true);
        } else {
            vClientes.showMensaje("Cliente no encontrado.", true);
        }
    }
    public void showListClientes() {
        for (Cliente cliente : mClientes.getClientes()) {
            vClientes.showMensaje(cliente.toString(), true);
        }
    }

    public void showListClientesTipo(Categoria categoria) {
        for (Cliente cliente : mClientes.getClientes()) {
            if (cliente.getCategoria() == categoria) {
                vClientes.showMensaje(cliente.toString(), true);
            }
        }
    }

    public void listClientes() {}

    public void listClientesTipo(int tipoCliente) {}

    public boolean loadClientes(int configuracion) {
        if (configuracion == 0) {
            return mClientes.loadClientes(configuracion);
        }
        else {
            return false;
        }
    }
}

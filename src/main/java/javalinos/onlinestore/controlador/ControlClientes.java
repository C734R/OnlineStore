package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.vista.VistaClientes;

public class ControlClientes extends ControlBase {

    private VistaClientes vClientes;

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
        while(true){
            vClientes.showCabecera();
            vClientes.showMenu(2);
            opcion = vClientes.askInt("Seleccione una opción",0,7,false, false);
            switch(opcion) {
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
                case 7:

                    break;
                case 0:
                    vClientes.showMensaje("Volviendo al menú principal...",true);
                    return;
                default:
                    vClientes.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.",true);
            }
        }
    }

    public void addCliente() {
        //TODO
    }

    public void removeCliente() {
        //TODO
    }

    public void modCliente() {
        //TODO
    }

    public void showListClientesTipo(int tipoCliente) {

    }
    public void showCliente() {}

    public void showListClientes() {}

    public void listClientes() {}

    public void listClientesTipo(int tipoCliente) {}

    public boolean loadClientes(int configuracion) {
        if (configuracion == 0) {
            return false;
        }
        else {
            return false;
        }

    }


}


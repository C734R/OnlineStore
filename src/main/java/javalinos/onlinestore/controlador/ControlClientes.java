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

    }

    public void addCliente() {

    }

    public void removeCliente() {}

    public void showCliente() {}

    public void listClientes() {}

    public void listClientesTipo(int tipoCliente) {}

    public boolean loadClientes() {
        return false;
    }


}


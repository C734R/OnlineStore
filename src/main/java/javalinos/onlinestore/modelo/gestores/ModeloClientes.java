package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.List;

public class ModeloClientes {

    private List<String> opciones;
    private List<Cliente> clientes;

    public ModeloClientes(List<String> opciones, List<Cliente> clientes) {
        this.opciones = opciones;
        this.clientes = clientes;
    }

    public ModeloClientes() {
        opciones = null;
        clientes = null;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void removeCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Cliente getClienteIndex(int index) {
        return clientes.get(index);
    }

    public int sizeClientes() {
        return clientes.size();
    }

    public Cliente getClienteNif(String nif) {
        return new Cliente();
    }

    public Cliente getClienteEmail(String email) {
        return new Cliente();
    }

    public void loadClientes() {

    }
}

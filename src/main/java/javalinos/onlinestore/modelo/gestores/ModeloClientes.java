package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Categoria;
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

    public Cliente makeCliente(String nombre, String domicilio, String nif, String email, Categoria categoria) {
        return new Cliente(nombre, domicilio, nif, email, categoria);
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

    public boolean loadClientes(int configuracion) {
        if (configuracion == 0) {
            try {
                this.clientes.clear();

                addCliente(makeCliente("Antonio López", "Calle Mayor, 10, Madrid", "12345678A", "antonio.lopez@email.com", Categoria.PREMIUM));
                addCliente(makeCliente("María García", "Avenida Andalucía, 25, Sevilla", "23456789B", "maria.garcia@email.com", Categoria.ESTANDAR));
                addCliente(makeCliente("José Martínez", "Paseo de Gracia, 15, Barcelona", "34567890C", "jose.martinez@email.com", Categoria.ESTANDAR));
                addCliente(makeCliente("Isabel Fernández", "Calle Larios, 5, Málaga", "45678901D", "isabel.fernandez@email.com", Categoria.PREMIUM));
                addCliente(makeCliente("Manuel Sánchez", "Plaza del Pilar, 20, Zaragoza", "56789012E", "manuel.sanchez@email.com", Categoria.ESTANDAR));
                addCliente(makeCliente("Carmen Rodríguez", "Gran Vía, 30, Bilbao", "67890123F", "carmen.rodriguez@email.com", Categoria.ESTANDAR));
                addCliente(makeCliente("Francisco Pérez", "Calle Serrano, 45, Madrid", "78901234G", "francisco.perez@email.com", Categoria.PREMIUM));
                addCliente(makeCliente("Ana Torres", "Rambla de Cataluña, 12, Barcelona", "89012345H", "ana.torres@email.com", Categoria.ESTANDAR));
                addCliente(makeCliente("Luis Ramírez", "Avenida Constitución, 8, Valencia", "90123456I", "luis.ramirez@email.com", Categoria.ESTANDAR));
                addCliente(makeCliente("Teresa Gómez", "Paseo de la Castellana, 50, Madrid", "01234567J", "teresa.gomez@email.com", Categoria.PREMIUM));

                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
}

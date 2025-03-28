package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ModeloClientes {

    private List<Cliente> clientes;

    public ModeloClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ModeloClientes() {
        clientes = new ArrayList<>();
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve lista con todos los clientes
     * @return Lista con los clientes
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Establece la lista de clientes.
     * @param clientes Lista de clientes.
     */
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    //*************************** CRUD ***************************//

    /**
     * Añadir un cliente a la lista de todos los clientes
     * @param cliente El cliente a añadir
     */
    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Elimianr un cliente
     * @param cliente El cliente a eliminar
     */
    public void removeCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    /**
     * Obtener el cliente de una lista
     * @param index El cliente seleccionado de una lista numérica
     * @return Cliente - El cliente deseado
     */
    public Cliente getClienteIndex(int index) {
        if (index >= 0 && index < clientes.size()) {
            return clientes.get(index);
        }
        return null;
    }

    /**
     * Modificar a un cliente
     * @param clienteOld Cliente antiguo
     * @param clienteNew Nuevo cliente
     */
    public void updateCliente(Cliente clienteOld, Cliente clienteNew) {
        int index = this.clientes.indexOf(clienteOld);
        if (index != -1) {
            clientes.set(index, clienteNew);
        }
    }

    //*************************** Obtener datos ***************************//

    /**
     * Obtener cantidad de clientes
     * @return Int con el numero de clientes
     */
    public int sizeClientes() {
        return clientes.size();
    }

    /**
     * Obtener Cliente a partir del NIF
     * @param nif NIF del cliente deseado
     * @return Cliente con ese NIF
     */
    public Cliente getClienteNif(String nif) {
        for (Cliente cliente : clientes) {
            if (cliente.getNif().equalsIgnoreCase(nif)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Obtener Cliente a partir del correo
     * @param email Email del cliente deseado
     * @return El cliente con ese correo
     */
    public Cliente getClienteEmail(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Obtener la lista de clientes según la categoria
     * @param categoria La categoria de la que se quiere obtener la lista
     * @return Lista con los clientes de esa categoria
     */
    public List<Cliente> getClientesCategoria(Categoria categoria) {
        if (clientes.isEmpty()) return null;
        List<Cliente> clientesCategoria = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getCategoria().equals(categoria)) {
                clientesCategoria.add(cliente);
            }
        }
        return clientesCategoria;
    }

    /**
     * Obtener categoria por Int
     *
     * @param opcion La categoria que se quiere ver según un Int
     * @return Categoria deseada
     */
    public Categoria getCategoria(int opcion) {
        return switch (opcion) {
            case 1 -> Categoria.ESTANDAR;
            case 2 -> Categoria.PREMIUM;
            default -> null;
        };
    }

    /**
     * Devuelve el número del último cliente.
     * @return int devuelve el número del último cliente, o -1 si la lista está vacía.
     */
    public int getLastIndexCliente() {
        if (clientes.isEmpty()) return -1;
        return clientes.size() - 1; // Último índice
    }

    /**
     * Devuelve el número del primer cliente.
     * @return int devuelve el número del primer cliente, o -1 si la lista está vacía.
     */
    public int getFirstIndexCliente() {
        if (clientes.isEmpty()) return -1;
        return 0; // Primer índice
    }

    //*************************** Crear datos ***************************//

    /**
     * Crear cliente
     * @param nombre Nombre del cliente
     * @param domicilio Domicilio del cliente
     * @param nif NIF del cliente
     * @param email Email del cliente
     * @param categoria Categoria del cliente
     * @return Cliente nuevo
     */
    public Cliente makeCliente(String nombre, String domicilio, String nif, String email, Categoria categoria) {
        return new Cliente(nombre, domicilio, nif, email, categoria);
    }

    /**
     * Cargar los clientes existentes
     * @param configuracion define la configuración seleccionada.
     * @return Boolean si se han cargado bien o no
     */
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
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}

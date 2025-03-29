package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;

import java.util.ArrayList;
import java.util.List;
/**
 * Modelo encargado de gestionar las operaciones relacionadas con los clientes.
 */
public class ModeloClientes {

    private List<Cliente> clientes;
    /**
     * Constructor por defecto. Inicializa la lista de clientes.
     */
    public ModeloClientes() {
        clientes = new ArrayList<>();
    }
    /**
     * Constructor con clientes iniciales.
     * @param clientes lista de clientes precargada.
     */
    public ModeloClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve lista con todos los clientes.
     * @return lista de clientes.
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Establece la lista de clientes.
     * @param clientes nueva lista de clientes.
     */
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    //*************************** CRUD ***************************//

    /**
     * Añade un cliente a la lista.
     * @param cliente cliente a añadir.
     */
    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Elimina un cliente de la lista.
     * @param cliente cliente a eliminar.
     */
    public void removeCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    /**
     * Obtiene un cliente según su posición en la lista.
     * @param index índice del cliente.
     * @return cliente en la posición dada o null si está fuera de rango.
     */
    public Cliente getClienteIndex(int index) {
        if (index >= 0 && index < clientes.size()) {
            return clientes.get(index);
        }
        return null;
    }

    /**
     * Modifica un cliente reemplazándolo por uno nuevo.
     * @param clienteOld cliente original.
     * @param clienteNew cliente actualizado.
     */
    public void updateCliente(Cliente clienteOld, Cliente clienteNew) {
        int index = this.clientes.indexOf(clienteOld);
        if (index != -1) {
            clientes.set(index, clienteNew);
        }
    }

    //*************************** Obtener datos ***************************//

    /**
     * Devuelve la cantidad de clientes registrados.
     * @return número total de clientes.
     */
    public int sizeClientes() {
        return clientes.size();
    }

    /**
     * Busca un cliente por su NIF.
     * @param nif NIF del cliente.
     * @return cliente correspondiente o null si no se encuentra.
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
     * Busca un cliente por su correo electrónico.
     * @param email correo del cliente.
     * @return cliente correspondiente o null si no se encuentra.
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
     * Devuelve los clientes de una categoría determinada.
     * @param categoria categoría deseada.
     * @return lista de clientes de esa categoría o null si está vacía.
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
     * Obtiene la categoría en base a un número entero.
     * @param opcion 1 para estándar, 2 para premium.
     * @return categoría correspondiente o null si no válida.
     */
    public Categoria getCategoria(int opcion) {
        return switch (opcion) {
            case 1 -> Categoria.ESTANDAR;
            case 2 -> Categoria.PREMIUM;
            default -> null;
        };
    }

    /**
     * Devuelve el índice del último cliente.
     * @return índice del último cliente o -1 si no hay clientes.
     */
    public int getLastIndexCliente() {
        if (clientes.isEmpty()) return -1;
        return clientes.size() - 1; // Último índice
    }

    /**
     * Devuelve el índice del primer cliente.
     * @return índice del primer cliente o -1 si no hay clientes.
     */
    public int getFirstIndexCliente() {
        if (clientes.isEmpty()) return -1;
        return 0; // Primer índice
    }

    //*************************** Crear datos ***************************//

    /**
     * Crea un nuevo cliente.
     * @param nombre nombre del cliente.
     * @param domicilio domicilio del cliente.
     * @param nif NIF del cliente.
     * @param email correo del cliente.
     * @param categoria categoría del cliente.
     * @return instancia del cliente creado.
     */
    public Cliente makeCliente(String nombre, String domicilio, String nif, String email, Categoria categoria) {
        return new Cliente(nombre, domicilio, nif, email, categoria);
    }

    /**
     * Precarga una lista de clientes de ejemplo.
     * @param configuracion determina si se debe precargar.
     * @return true si se cargó correctamente, false si ocurrió un error.
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

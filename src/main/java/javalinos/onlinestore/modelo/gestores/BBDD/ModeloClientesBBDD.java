package javalinos.onlinestore.modelo.gestores.BBDD;

import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.DAO.Interfaces.ICategoriaDAO;
import javalinos.onlinestore.modelo.DAO.Interfaces.IClienteDAO;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.Entidades.Categoria;
import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo encargado de gestionar las operaciones relacionadas con los clienteDTOS.
 */
public class ModeloClientesBBDD implements IModeloClientes {

    private FactoryDAO factoryDAO;

    /**
     * Constructor por defecto. Inicializa la lista de clienteDTOS.
     */
    public ModeloClientesBBDD(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    //*************************** Getters & Setters ***************************//

    /**
     * Devuelve lista con todos los clienteDTOS.
     * @return lista de clienteDTOS.
     */
    public List<ClienteDTO> getClientes() throws Exception {
        List<Cliente> clientes = factoryDAO.getDAOCliente().getTodos();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            // Obtener categoríaDTO en base a Id categoría
            CategoriaDTO categoriaDTO = new CategoriaDTO(factoryDAO.getDAOCategoria().getPorId(cliente.getCategoria()));

            clientesDTO.add(new ClienteDTO(
                    cliente.getNombre(),
                    cliente.getDomicilio(),
                    cliente.getNif(),
                    cliente.getEmail(),
                    categoriaDTO
            ));
        }
        return clientesDTO;
    }

    /**
     * Establece la lista de clientes.
     * @param clientes nueva lista de clientes.
     */
    public void setClientes(List<ClienteDTO> clientes) throws Exception {
        factoryDAO.getDAOCliente().eliminarTodos();
        factoryDAO.getDAOCategoria().get
        for (ClienteDTO clienteDTO : clientes) {
            factoryDAO.getDAOCliente().insertar(new Cliente());
        }

        this.clientes = clientes;
    }

    public List<CategoriaDTO> getCategorias() throws Exception {
        ICategoriaDAO categoriaDAO = factoryDAO.getDAOCategoria();
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<CategoriaDTO> categoriasDTO= new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasDTO.add(new CategoriaDTO(categoria));
        }
        return categoriasDTO;
    }



    public void setCategorias(List<CategoriaDTO> categorias) throws Exception {
        this.categorias = categorias;
    }

    //*************************** CRUD CLIENTES ***************************//

    /**
     * Añade un clienteDTO a la lista.
     * @param ClienteDTO clienteDTO a añadir.
     */
    public void addCliente(ClienteDTO ClienteDTO) throws Exception {
        clientes.add(ClienteDTO);
    }

    /**
     * Elimina un clienteDTO de la lista.
     * @param ClienteDTO clienteDTO a eliminar.
     */
    public void removeCliente(ClienteDTO ClienteDTO) throws Exception {
        clientes.remove(ClienteDTO);
    }

    /**
     * Obtiene un cliente según su posición en la lista.
     * @param index índice del cliente.
     * @return cliente en la posición dada o null si está fuera de rango.
     */
    public ClienteDTO getClienteIndex(int index) throws Exception {
        if (index >= 0 && index < clientes.size()) {
            return clientes.get(index);
        }
        return null;
    }

    /**
     * Modifica un cliente reemplazándolo por uno nuevo.
     * @param ClienteDTOOld cliente original.
     * @param ClienteDTONew cliente actualizado.
     */
    public void updateCliente(ClienteDTO ClienteDTOOld, ClienteDTO ClienteDTONew) throws Exception {
        int index = this.clientes.indexOf(ClienteDTOOld);
        if (index != -1) {
            clientes.set(index, ClienteDTONew);
        }
    }

    //*************************** OBTENER DATOS CLIENTES ***************************//

    /**
     * Devuelve la cantidad de clienteDTOS registrados.
     * @return número total de clienteDTOS.
     */
    public int sizeClientes() throws Exception {
        return clientes.size();
    }

    /**
     * Busca un cliente por su NIF.
     * @param nif NIF del cliente.
     * @return cliente correspondiente o null si no se encuentra.
     */
    public ClienteDTO getClienteNif(String nif) throws Exception {
        for (ClienteDTO ClienteDTO : clientes) {
            if (ClienteDTO.getNif().equalsIgnoreCase(nif)) {
                return ClienteDTO;
            }
        }
        return null;
    }

    /**
     * Busca un cliente por su correo electrónico.
     * @param email correo del cliente.
     * @return cliente correspondiente o null si no se encuentra.
     */
    public ClienteDTO getClienteEmail(String email) throws Exception {
        for (ClienteDTO ClienteDTO : clientes) {
            if (ClienteDTO.getEmail().equalsIgnoreCase(email)) {
                return ClienteDTO;
            }
        }
        return null;
    }

    /**
     * Devuelve los clienteDTOS de una categoría determinada.
     * @param categoriaDTO categoría deseada.
     * @return lista de clienteDTOS de esa categoría o null si está vacía.
     */
    public List<ClienteDTO> getClientesCategoria(CategoriaDTO categoriaDTO) throws Exception {
        if (clientes.isEmpty()) return null;
        List<ClienteDTO> clientesCategoria = new ArrayList<>();
        for (ClienteDTO ClienteDTO : clientes) {
            if (ClienteDTO.getCategoria().equals(categoriaDTO)) {
                clientesCategoria.add(ClienteDTO);
            }
        }
        return clientesCategoria;
    }

    public CategoriaDTO getCategoria(int opcion) throws Exception {
        return categorias.get(opcion-1);
    }

    /**
     * Devuelve el índice del último cliente.
     * @return índice del último cliente o -1 si no hay clienteDTOS.
     */
    public int getLastIndexCliente() throws Exception {
        if (clientes.isEmpty()) return -1;
        return clientes.size() - 1; // Último índice
    }

    /**
     * Devuelve el índice del primer cliente.
     * @return índice del primer cliente o -1 si no hay clienteDTOS.
     */
    public int getFirstIndexCliente() throws Exception {
        if (clientes.isEmpty()) return -1;
        return 0; // Primer índice
    }

    //*************************** CRUD CATEGORIAS ***************************//

    public void addCategoria(CategoriaDTO categoriaDTO) throws Exception {
        categorias.add(categoriaDTO);
    }

    public void removeCategoria(CategoriaDTO categoriaDTO) throws Exception {
        categorias.remove(categoriaDTO);
    }

    public void updateCategoria(CategoriaDTO categoriaDTOOld, CategoriaDTO categoriaDTONew) throws Exception {
        int index = this.categorias.indexOf(categoriaDTOOld);
        if (index != -1) {
            categorias.set(index, categoriaDTONew);
        }

    }

    public CategoriaDTO getCategoriaIndex(int index) throws Exception {
        if (index >= 0 && index < categorias.size()) {
            return categorias.get(index);
        }
        return null;
    }

    //*************************** Crear datos ***************************//

    /**
     * Crea un nuevo cliente.
     * @param nombre nombre del cliente.
     * @param domicilio domicilio del cliente.
     * @param nif NIF del cliente.
     * @param email correo del cliente.
     * @param categoriaDTO categoría del cliente.
     * @return instancia del cliente creado.
     */
    public ClienteDTO makeCliente(String nombre, String domicilio, String nif, String email, CategoriaDTO categoriaDTO) {
        return new ClienteDTO(nombre, domicilio, nif, email, categoriaDTO);
    }

    public CategoriaDTO makeCategoria(String nombre, Float cuota, Float descuento) {
        return new CategoriaDTO(nombre, cuota, descuento);
    }

    /**
     * Precarga una lista de clientesDTO de ejemplo.
     */
    public void loadClientes() throws Exception {
            this.clientes.clear();
            this.categorias.clear();

            addCategoria(makeCategoria("Estándar", 0f, 0f));
            addCategoria(makeCategoria("Premium", 30f, 0.20f));

            addCliente(makeCliente("Antonio López", "Calle Mayor, 10, Madrid", "12345678A", "antonio.lopez@email.com", getCategoria(2)));
            addCliente(makeCliente("María García", "Avenida Andalucía, 25, Sevilla", "23456789B", "maria.garcia@email.com", getCategoria(1)));
            addCliente(makeCliente("José Martínez", "Paseo de Gracia, 15, Barcelona", "34567890C", "jose.martinez@email.com", getCategoria(1)));
            addCliente(makeCliente("Isabel Fernández", "Calle Larios, 5, Málaga", "45678901D", "isabel.fernandez@email.com", getCategoria(2)));
            addCliente(makeCliente("Manuel Sánchez", "Plaza del Pilar, 20, Zaragoza", "56789012E", "manuel.sanchez@email.com", getCategoria(1)));
            addCliente(makeCliente("Carmen Rodríguez", "Gran Vía, 30, Bilbao", "67890123F", "carmen.rodriguez@email.com", getCategoria(1)));
            addCliente(makeCliente("Francisco Pérez", "Calle Serrano, 45, Madrid", "78901234G", "francisco.perez@email.com", getCategoria(2)));
            addCliente(makeCliente("Ana Torres", "Rambla de Cataluña, 12, Barcelona", "89012345H", "ana.torres@email.com", getCategoria(1)));
            addCliente(makeCliente("Luis Ramírez", "Avenida Constitución, 8, Valencia", "90123456I", "luis.ramirez@email.com", getCategoria(1)));
            addCliente(makeCliente("Teresa Gómez", "Paseo de la Castellana, 50, Madrid", "01234567J", "teresa.gomez@email.com", getCategoria(2)));
    }
}

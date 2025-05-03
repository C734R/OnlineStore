package javalinos.onlinestore.modelo.gestores.BBDD;

import javalinos.onlinestore.modelo.DAO.FactoryDAO;
import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.Entidades.Categoria;
import javalinos.onlinestore.modelo.Entidades.Cliente;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;

import java.util.ArrayList;
import java.util.List;

import static javalinos.onlinestore.OnlineStore.configuracion;

/**
 * Modelo encargado de gestionar las operaciones relacionadas con los clienteDTOS.
 */
public class ModeloClientesBBDD implements IModeloClientes
{

    private final FactoryDAO factoryDAO;

    /**
     * Constructor por defecto. Inicializa la lista de clienteDTOS.
     */
    public ModeloClientesBBDD(FactoryDAO factoryDAO)
    {
        this.factoryDAO = factoryDAO;
    }

    //*************************** CRUD CLIENTES ***************************//

    /**
     * Añade un clienteDTO a la lista.
     * @param clienteDTO clienteDTO a añadir.
     */
    public void addCliente(ClienteDTO clienteDTO) throws Exception
    {
        Categoria categoria = getCategoriaEntidadNombre(clienteDTO.getCategoria().getNombre());
        if (categoria == null) throw new Exception("Categoria no encontrada");
        factoryDAO.getDAOCliente().insertar(new Cliente(clienteDTO, categoria));
    }

    /**
     * Elimina un clienteDTO de la lista.
     * @param clienteDTO clienteDTO a eliminar.
     */
    public void removeCliente(ClienteDTO clienteDTO) throws Exception
    {
        Cliente cliente = getClienteEntidadNIF(clienteDTO);
        if (cliente == null) throw new Exception("Cliente no encontrado");
        factoryDAO.getDAOCliente().eliminar(cliente.getId());
    }

    public void removeClientesAll() throws Exception
    {
        factoryDAO.getDAOCliente().eliminarTodos();
    }

    /**
     * Obtiene un cliente según su posición en la lista.
     * @param index índice del cliente.
     * @return cliente en la posición dada o null si está fuera de rango.
     */
    public ClienteDTO getClienteDTOIndex(int index) throws Exception
    {
        List<ClienteDTO> clientesDTO = getClientesDTO();
        if (index >= 0 && index < clientesDTO.size()) {
            return clientesDTO.get(index);
        }
        return null;
    }

    private Cliente getClienteId(int id) throws Exception
    {
        return factoryDAO.getDAOCliente().getPorId(id);
    }

    public Integer getIdClienteDTO(ClienteDTO clienteDTO) throws Exception
    {
        Cliente cliente = getClienteEntidadEmail(clienteDTO.getEmail());
        if (cliente == null) throw new Exception("Cliente no encontrado");
        return cliente.getId();
    }

    public ClienteDTO getClienteDTOId(int id) throws Exception
    {
        Cliente cliente = getClienteId(id);
        if (cliente == null) throw new Exception("Cliente no encontrado.");
        CategoriaDTO categoriaDTO = getCategoriaDTOClienteEntidad(cliente);
        if (categoriaDTO == null) throw new Exception("CategoriaDTO no obtenido.");
        return new ClienteDTO(cliente, categoriaDTO);
    }

    private Integer getCategoriaIdClienteEntidad(Cliente cliente)
    {
        return switch (configuracion) {
            case JDBC_MYSQL -> cliente.getCategoriaId();
            case JPA_HIBERNATE_MYSQL -> cliente.getCategoria().getId();
            default -> null;
        };
    }

    private Cliente getClienteEntidadNombre(String nombre) throws Exception
    {
        return factoryDAO.getDAOCliente().getPorNombreUnico(nombre);
    }

    private Cliente getClienteEntidadNIF(ClienteDTO clienteDTO) throws Exception
    {
        return factoryDAO.getDAOCliente().getClienteNIF(clienteDTO.getNif());
    }
    /**
     * Modifica un cliente reemplazándolo por uno nuevo.
     * @param clienteDTOOld cliente original.
     * @param clienteDTONew cliente actualizado.
     */
    public void updateCliente(ClienteDTO clienteDTOOld, ClienteDTO clienteDTONew) throws Exception
    {
        Cliente clienteOld = getClienteEntidadNIF(clienteDTOOld);
        if(clienteOld == null) throw new Exception("Cliente antigüo no encontrado");
        Categoria categoriaNew = getCategoriaEntidadNombre(clienteDTONew.getCategoria().getNombre());
        if(categoriaNew == null) throw new Exception("Categoria no encontrado");
        Cliente clienteNew = new Cliente(
                clienteDTONew,
                categoriaNew);
        clienteNew.setId(clienteOld.getId());
        factoryDAO.getDAOCliente().actualizar(clienteNew);
    }

    //*************************** GETTERS & SETTERS CLIENTES ***************************//

    /**
     * Devuelve lista con todos los clienteDTOS.
     * @return lista de clienteDTOS.
     */
    public List<ClienteDTO> getClientesDTO() throws Exception
    {
        return clientesEntidadToDTO(getClientesEntidad());
    }

    private List<Cliente> getClientesEntidad() throws Exception
    {
        return factoryDAO.getDAOCliente().getTodos();
    }

    /**
     * Establece la lista de clientes.
     * @param clientesDTO nueva lista de clientes.
     */
    public void setClientes(List<ClienteDTO> clientesDTO) throws Exception {
        removeClientesAll();
        factoryDAO.getDAOCliente().insertarTodos(clientesDTOtoEntidad(clientesDTO));
    }

    //*************************** OBTENER DATOS CLIENTES ***************************//

    /**
     * Devuelve la cantidad de clienteDTOS registrados.
     * @return número total de clienteDTOS.
     */
    public int sizeClientes() throws Exception
    {
        List<ClienteDTO> clientesDTO = getClientesDTO();
        return clientesDTO.size();
    }

    /**
     * Busca un cliente por su NIF.
     * @param nif NIF del cliente.
     * @return cliente correspondiente o null si no se encuentra.
     */
    public ClienteDTO getClienteDTONif(String nif) throws Exception
    {
        Cliente cliente = getClienteEntidadNif(nif);
        if (cliente == null) throw new Exception("Cliente no encontrado.");
        CategoriaDTO categoriaDTO = getCategoriaDTOClienteEntidad(cliente);
        if (categoriaDTO == null) throw new Exception("Categoria no encontrado.");
        return new ClienteDTO(cliente, categoriaDTO);
    }

    private CategoriaDTO getCategoriaDTOClienteEntidad(Cliente cliente) throws Exception {
        Integer categoriaId = getCategoriaIdClienteEntidad(cliente);
        return getCategoriaDTOId(categoriaId);
    }

    public Cliente getClienteEntidadNif(String nif) throws Exception
    {
        return factoryDAO.getDAOCliente().getClienteNIF(nif);
    }

    /**
     * Busca un cliente por su correo electrónico.
     * @param email correo del cliente.
     * @return cliente correspondiente o null si no se encuentra.
     */
    public ClienteDTO getClienteDTOEmail(String email) throws Exception
    {
        Cliente cliente = getClienteEntidadEmail(email);
        if (cliente == null) throw new Exception("Cliente no encontrado.");
        CategoriaDTO categoriaDTO = getCategoriaDTOClienteEntidad(cliente);
        if (categoriaDTO == null) throw new Exception("Categoria no encontrado.");
        return new ClienteDTO(cliente, categoriaDTO);
    }

    public Cliente getClienteEntidadEmail(String email) throws  Exception
    {
        return factoryDAO.getDAOCliente().getClienteEmail(email);
    }

    @Override
    public Cliente getClienteEntidadId(int id) throws Exception {
        return factoryDAO.getDAOCliente().getPorId(id);
    }

    /**
     * Devuelve los clienteDTOS de una categoría determinada.
     * @param categoriaDTO categoría deseada.
     * @return lista de clienteDTOS de esa categoría o null si está vacía.
     */
    public List<ClienteDTO> getClientesCategoria(CategoriaDTO categoriaDTO) throws Exception
    {
        Categoria categoria = getCategoriaEntidadNombre(categoriaDTO.getNombre());
        if (categoria == null) throw new Exception("Categoria no encontrada.");
        List<Cliente> clientes = getClientesEntidadCategoria(categoria);
        if (clientes == null) throw new Exception("Cliente no encontrado.");
        return clientesEntidadToDTO(clientes);
    }

    private List<Cliente> getClientesEntidadCategoria(Categoria categoria) throws Exception
    {
        return factoryDAO.getDAOCliente().getClientesCategoria(categoria);
    }

    private List<ClienteDTO> clientesEntidadToDTO(List<Cliente> clientes) throws Exception
    {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes)
        {
            CategoriaDTO categoriaDTO = getCategoriaDTOClienteEntidad(cliente);
            clientesDTO.add(new ClienteDTO(cliente, categoriaDTO));
        }
        return clientesDTO;
    }

    private List<Cliente> clientesDTOtoEntidad(List<ClienteDTO> clientesDTO) throws Exception
    {
        List<Cliente> clientes = new ArrayList<>();
        for (ClienteDTO clienteDTO : clientesDTO)
        {
            Categoria categoria = getCategoriaEntidadNombre(clienteDTO.getCategoria().getNombre());
            clientes.add(new Cliente(clienteDTO, categoria));
        }
        return clientes;
    }


    /**
     * Devuelve el índice del último cliente.
     * @return índice del último cliente o -1 si no hay clienteDTOS.
     */
    public int getLastIndexCliente() throws Exception
    {
        List<ClienteDTO> clientesDTO = getClientesDTO();
        if (clientesDTO.isEmpty()) throw new Exception("No se encontraron clientes.");
        return clientesDTO.size() - 1; // Último índice
    }

    /**
     * Devuelve el índice del primer cliente.
     * @return índice del primer cliente o -1 si no hay clienteDTOS.
     */
    public int getFirstIndexCliente() throws Exception
    {
        List<ClienteDTO> clientesDTO = getClientesDTO();
        if (clientesDTO.isEmpty()) throw new Exception("No se encontraron clientes.");
        return clientesDTO.indexOf(clientesDTO.getFirst()); // Primer índice
    }

    //*************************** CRUD CATEGORIAS ***************************//

    public void addCategoria(CategoriaDTO categoriaDTO) throws Exception
    {
        Categoria categoria = new Categoria(categoriaDTO);
        factoryDAO.getDAOCategoria().insertar(categoria);
    }

    public void removeCategoria(CategoriaDTO categoriaDTO) throws Exception
    {
        Categoria categoria = getCategoriaEntidadNombre(categoriaDTO.getNombre());
        if (categoria == null) throw new Exception("Categoria no encontrada");
        factoryDAO.getDAOCliente().eliminar(categoria.getId());
    }

    public void removeCategoriasAll() throws Exception
    {
        factoryDAO.getDAOCategoria().eliminarTodos();
    }

    public void updateCategoria(CategoriaDTO categoriaDTOOld, CategoriaDTO categoriaDTONew) throws Exception
    {
        Categoria categoriaOld = getCategoriaEntidadNombre(categoriaDTOOld.getNombre());
        if (categoriaOld == null) throw new Exception("Categoria no encontrada");
        Categoria categoriaNew = new Categoria(categoriaDTOOld);
        categoriaNew.setId(categoriaOld.getId());
        factoryDAO.getDAOCategoria().actualizar(categoriaNew);
    }

    public CategoriaDTO getCategoriaDTOId(Integer id) throws Exception
    {
        return new CategoriaDTO(factoryDAO.getDAOCategoria().getPorId(id));
    }

    public CategoriaDTO getCategoriaDTOIndex(int index) throws Exception
    {
        List<CategoriaDTO> categoriasDTO = getCategoriasDTO();
        if (index >= 0 && index < categoriasDTO.size()) {
            return categoriasDTO.get(index);
        }
        return null;
    }

    //*************************** GETTERS & SETTERS CATEGORÍAS ***************************//

    public List<CategoriaDTO> getCategoriasDTO() throws Exception
    {
        List<Categoria> categorias = getCategoriasEntidad();
        if (categorias.isEmpty()) return new ArrayList<>();
        List<CategoriaDTO> categoriasDTO= new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasDTO.add(new CategoriaDTO(categoria));
        }
        return categoriasDTO;
    }

    private List<Categoria> getCategoriasEntidad() throws Exception
    {
        return factoryDAO.getDAOCategoria().getTodos();
    }

    public void setCategorias(List<CategoriaDTO> categoriasDTO) throws Exception
    {
        removeCategoriasAll();
        List<Categoria> categorias= new ArrayList<>();
        for (CategoriaDTO categoriaDTO : categoriasDTO) {
            categorias.add(getCategoriaEntidadNombre(categoriaDTO.getNombre()));
        }
        factoryDAO.getDAOCategoria().insertarTodos(categorias);
    }

    //*************************** OBTENER DATOS CATEGORIAS ***************************//

    public CategoriaDTO getCategoriaDTOOpcion(int opcion) throws Exception
    {
        return getCategoriaDTOIndex(opcion-1);
    }

    public CategoriaDTO getCategoriaDTOId(int id) throws Exception
    {
        return new CategoriaDTO(factoryDAO.getDAOCategoria().getPorId(id));
    }

    public CategoriaDTO getCategoriaDTONombre(String nombre) throws Exception
    {
        return new CategoriaDTO(getCategoriaEntidadNombre(nombre));
    }

    private Categoria getCategoriaEntidadNombre(String nombre) throws Exception
    {
        return factoryDAO.getDAOCategoria().getPorNombreUnico(nombre);
    }


    //*************************** CREAR DATOS ***************************//

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

        try{
            removeClientesAll();
            removeCategoriasAll();

            addCategoria(makeCategoria("Estándar", 0f, 0f));
            addCategoria(makeCategoria("Premium", 30f, 0.20f));

            addCliente(makeCliente("Antonio López", "Calle Mayor, 10, Madrid", "12345678A", "antonio.lopez@email.com", getCategoriaDTOOpcion(2)));
            addCliente(makeCliente("María García", "Avenida Andalucía, 25, Sevilla", "23456789B", "maria.garcia@email.com", getCategoriaDTOOpcion(1)));
            addCliente(makeCliente("José Martínez", "Paseo de Gracia, 15, Barcelona", "34567890C", "jose.martinez@email.com", getCategoriaDTOOpcion(1)));
            addCliente(makeCliente("Isabel Fernández", "Calle Larios, 5, Málaga", "45678901D", "isabel.fernandez@email.com", getCategoriaDTOOpcion(2)));
            addCliente(makeCliente("Manuel Sánchez", "Plaza del Pilar, 20, Zaragoza", "56789012E", "manuel.sanchez@email.com", getCategoriaDTOOpcion(1)));
            addCliente(makeCliente("Carmen Rodríguez", "Gran Vía, 30, Bilbao", "67890123F", "carmen.rodriguez@email.com", getCategoriaDTOOpcion(1)));
            addCliente(makeCliente("Francisco Pérez", "Calle Serrano, 45, Madrid", "78901234G", "francisco.perez@email.com", getCategoriaDTOOpcion(2)));
            addCliente(makeCliente("Ana Torres", "Rambla de Cataluña, 12, Barcelona", "89012345H", "ana.torres@email.com", getCategoriaDTOOpcion(1)));
            addCliente(makeCliente("Luis Ramírez", "Avenida Constitución, 8, Valencia", "90123456I", "luis.ramirez@email.com", getCategoriaDTOOpcion(1)));
            addCliente(makeCliente("Teresa Gómez", "Paseo de la Castellana, 50, Madrid", "01234567J", "teresa.gomez@email.com", getCategoriaDTOOpcion(2)));

        }
        catch (Exception e)
        {
            throw new Exception("Error precargar clientes.", e);
        }
   }
}

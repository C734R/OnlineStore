package javalinos.onlinestore.modelo.gestores.Interfaces;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;

import java.util.List;

public interface IModeloClientes {
    List<ClienteDTO> getClientes() throws Exception;
    void setClientes(List<ClienteDTO> clientes) throws Exception;
    List<CategoriaDTO> getCategorias() throws Exception;
    void setCategorias(List<CategoriaDTO> categorias) throws Exception;
    void addCliente(ClienteDTO clienteDTO) throws Exception;
    void removeCliente(ClienteDTO clienteDTO) throws Exception;
    ClienteDTO getClienteIndex(int index) throws Exception;
    void updateCliente(ClienteDTO clienteDTOOld, ClienteDTO clienteDTONew) throws Exception;
    int sizeClientes() throws Exception;
    ClienteDTO getClienteNif(String nif) throws Exception;
    ClienteDTO getClienteEmail(String email) throws Exception;
    List<ClienteDTO> getClientesCategoria(CategoriaDTO categoriaDTO) throws Exception;
    CategoriaDTO getCategoria(int opcion) throws Exception;
    int getLastIndexCliente() throws Exception;
    int getFirstIndexCliente() throws Exception;
    void addCategoria(CategoriaDTO categoriaDTO) throws Exception;
    void removeCategoria(CategoriaDTO categoriaDTO) throws Exception;
    void updateCategoria(CategoriaDTO categoriaDTOOld, CategoriaDTO categoriaDTONew) throws Exception;
    CategoriaDTO getCategoriaIndex(int index) throws Exception;
    ClienteDTO makeCliente(String nombre, String domicilio, String nif, String email, CategoriaDTO categoriaDTO);
    CategoriaDTO makeCategoria(String nombre, Float cuota, Float descuento);
    void loadClientes() throws Exception;


}

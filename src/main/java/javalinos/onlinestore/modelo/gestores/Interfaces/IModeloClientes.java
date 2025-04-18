package javalinos.onlinestore.modelo.gestores.Interfaces;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;

import java.util.List;

public interface IModeloClientes {
    List<ClienteDTO> getClientesDTO() throws Exception;
    void setClientes(List<ClienteDTO> clientes) throws Exception;
    List<CategoriaDTO> getCategoriasDTO() throws Exception;
    void setCategorias(List<CategoriaDTO> categorias) throws Exception;
    void addCliente(ClienteDTO clienteDTO) throws Exception;
    void removeCliente(ClienteDTO clienteDTO) throws Exception;
    void removeClientesAll() throws Exception;
    ClienteDTO getClienteIndex(int index) throws Exception;
    ClienteDTO getClienteDTOId(int id) throws Exception;
    Integer getIdClienteDTO(ClienteDTO clienteDTO) throws Exception;
    void updateCliente(ClienteDTO clienteDTOOld, ClienteDTO clienteDTONew) throws Exception;
    int sizeClientes() throws Exception;
    ClienteDTO getClienteDTONif(String nif) throws Exception;
    ClienteDTO getClienteDTOEmail(String email) throws Exception;
    List<ClienteDTO> getClientesCategoria(CategoriaDTO categoriaDTO) throws Exception;
    CategoriaDTO getCategoriaDTOOpcion(int opcion) throws Exception;
    int getLastIndexCliente() throws Exception;
    int getFirstIndexCliente() throws Exception;
    void addCategoria(CategoriaDTO categoriaDTO) throws Exception;
    void removeCategoria(CategoriaDTO categoriaDTO) throws Exception;
    void updateCategoria(CategoriaDTO categoriaDTOOld, CategoriaDTO categoriaDTONew) throws Exception;
    CategoriaDTO getCategoriaDTOIndex(int index) throws Exception;
     CategoriaDTO getCategoriaDTOId(Integer id) throws Exception;
    ClienteDTO makeCliente(String nombre, String domicilio, String nif, String email, CategoriaDTO categoriaDTO);
    CategoriaDTO makeCategoria(String nombre, Float cuota, Float descuento);
    void loadClientes() throws Exception;


}

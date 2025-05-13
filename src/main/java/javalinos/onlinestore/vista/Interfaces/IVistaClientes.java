package javalinos.onlinestore.vista.Interfaces;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;

import java.util.List;

public interface IVistaClientes extends IVistaBase {
    String askNIF(boolean modificar, boolean reintentar, boolean sinFin);
    String askEmail(boolean modificar, boolean reintentar, boolean sinFin);
    int askMetodoEliminar();
    int askCategoriaCliente();
    void showListClientes(List<ClienteDTO> clientesDTO);
    void showListClientesNumerada(List<ClienteDTO> clientesDTO);
    void showListClientesCategoria(List<ClienteDTO> clientesDTO, CategoriaDTO categoriaDTO);
    void showMods();
    void showCategorias();
    void showMetodosEliminar();
    void showCliente(ClienteDTO clienteDTO);
}

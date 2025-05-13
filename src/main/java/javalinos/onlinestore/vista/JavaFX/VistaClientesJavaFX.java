package javalinos.onlinestore.vista.JavaFX;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaClientes;

import java.util.List;

public class VistaClientesJavaFX extends VistaBaseJavaFX implements IVistaClientes {
    @Override
    public String askNIF(boolean modificar, boolean reintentar, boolean sinFin) {
        return "";
    }

    @Override
    public String askEmail(boolean modificar, boolean reintentar, boolean sinFin) {
        return "";
    }

    @Override
    public int askMetodoEliminar() {
        return 0;
    }

    @Override
    public int askCategoriaCliente() {
        return 0;
    }

    @Override
    public void showListClientes(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesNumerada(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesCategoria(List<ClienteDTO> clientesDTO, CategoriaDTO categoriaDTO) {

    }

    @Override
    public void showMods() {

    }

    @Override
    public void showCategorias() {

    }

    @Override
    public void showMetodosEliminar() {

    }

    @Override
    public void showCliente(ClienteDTO clienteDTO) {

    }
}

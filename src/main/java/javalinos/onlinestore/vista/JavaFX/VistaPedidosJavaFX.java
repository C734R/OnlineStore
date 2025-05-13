package javalinos.onlinestore.vista.JavaFX;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;
import javalinos.onlinestore.vista.Interfaces.IVistaPedidos;

import java.util.List;

public class VistaPedidosJavaFX extends VistaBaseJavaFX implements IVistaPedidos {
    @Override
    public void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion) {

    }

    @Override
    public void showListClientes(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesPedidos(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesPedidosPendientes(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListClientesPedidosEnviados(List<ClienteDTO> clientesDTO) {

    }

    @Override
    public void showListArticulos(List<ArticuloDTO> articulosDTO) {

    }

    @Override
    public void showPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {

    }

    @Override
    public void showListPedidosPendientes(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {

    }

    @Override
    public void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO) {

    }

    @Override
    public ClienteDTO askClienteOpcional(List<ClienteDTO> clientesDTO, ClienteDTO clienteDTOActual) {
        return null;
    }
}

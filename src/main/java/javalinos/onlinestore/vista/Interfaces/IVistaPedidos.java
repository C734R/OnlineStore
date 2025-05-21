package javalinos.onlinestore.vista.Interfaces;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;

import java.util.List;

public interface IVistaPedidos extends IVistaBase {
    void showListPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO, boolean opcion);
    void showListClientes(List<ClienteDTO> clientesDTO);
    void showListClientesPedidos(List<ClienteDTO> clientesDTO);
    void showListClientesPedidosPendientes(List<ClienteDTO> clientesDTO);
    void showListClientesPedidosEnviados(List<ClienteDTO> clientesDTO);
    void showListArticulos(List<ArticuloDTO> articulosDTO);
    void showPedidos(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO);
    void showListPedidosPendientes(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO);
    void showListPedidosEnviados(List<PedidoDTO> pedidosDTO, ClienteDTO clienteDTO);
    ClienteDTO askClienteOpcional(List<ClienteDTO> clientesDTO, ClienteDTO clienteDTOActual);
    int askPedidoModificar(List<PedidoDTO> pedidosDTO);
    int askClienteFiltro(int tipoFiltrado, List<ClienteDTO> clientesPedidos);
    int askPedidoRemove(List<PedidoDTO> pedidosDTO);
}

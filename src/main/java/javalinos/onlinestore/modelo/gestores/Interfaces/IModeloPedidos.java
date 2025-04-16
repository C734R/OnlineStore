package javalinos.onlinestore.modelo.gestores.Interfaces;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IModeloPedidos {

    List<PedidoDTO> getPedidosDTO() throws Exception;
    void setPedidos(List<PedidoDTO> pedidos) throws Exception;
    void addPedido(PedidoDTO pedidos) throws Exception;
    void removePedido(PedidoDTO pedidos) throws Exception;
    PedidoDTO getPedidoDTO(int id) throws Exception;
    void updatePedido(PedidoDTO pedidoDTOOld, PedidoDTO pedidoDTONew) throws Exception;
    PedidoDTO getPedidoDTONumero(int numero) throws Exception;
    List<PedidoDTO> getPedidosDTOCliente(ClienteDTO clienteDTO) throws Exception;
    List<PedidoDTO> getPedidosPendientesEnviados(Boolean enviado, ClienteDTO clienteDTO) throws Exception;
    boolean checkEnviado(PedidoDTO pedidoDTO);
    int getLastNumPedido() throws Exception;
    int getFirstNumPedido() throws Exception;
    PedidoDTO makePedido(ClienteDTO clienteDTO, ArticuloDTO articuloDTO, Integer cantidad, LocalDate fechahora, Float envio) throws Exception;
    private float calcPrecioTotal(ArticuloDTO articuloDTO, int stockComprado, float precioEnvio, ClienteDTO clienteDTO) {
        return (((articuloDTO.getPrecio() * stockComprado) + calcEnvioTotal(stockComprado, precioEnvio))) * (((100f - (100f * clienteDTO.getDescuento())) / 100f));
    }
    default Float calcEnvioTotal(Integer cantidad, Float precioEnvio) {
        return precioEnvio + (1.05f * cantidad);
    }
    void loadPedidos(List<ClienteDTO> ClienteDTOS, List<ArticuloDTO> ArticuloDTOS) throws Exception;
}


package javalinos.onlinestore.modelo.gestores.Interfaces;

import javalinos.onlinestore.modelo.DTO.ArticuloDTO;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.modelo.DTO.PedidoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IModeloPedidos {

    List<PedidoDTO> pedidos = new ArrayList<>();

    List<PedidoDTO> getPedidos();

    void setPedidos(List<PedidoDTO> pedidos);

    void addPedido(PedidoDTO pedidos);

    void removePedido(PedidoDTO pedidos);

    PedidoDTO getPedido(int id);

    void updatePedido(PedidoDTO pedidoDTOOld, PedidoDTO pedidoDTONew);

    PedidoDTO getPedidoNumero(int numero);

    List<PedidoDTO> getPedidosCliente(ClienteDTO clienteDTO);

    List<PedidoDTO> getPedidosPendientesEnviados(Boolean enviado, ClienteDTO clienteDTO);

    boolean checkEnviado(PedidoDTO pedidoDTO);

    int getLastNumPedido();

    int getFirstNumPedido();

    PedidoDTO makePedido(ClienteDTO clienteDTO, ArticuloDTO articuloDTO, Integer cantidad, LocalDate fechahora, Float envio);

    private float calcPrecioTotal(ArticuloDTO articuloDTO, int stockComprado, float precioEnvio, ClienteDTO clienteDTO) {
        return (((articuloDTO.getPrecio() * stockComprado) + calcEnvioTotal(stockComprado, precioEnvio))) * (((100f - (100f * clienteDTO.getDescuento())) / 100f));
    }

    default Float calcEnvioTotal(Integer cantidad, Float precioEnvio) {
        return precioEnvio + (1.05f * cantidad);
    }
    void loadPedidos(List<ClienteDTO> ClienteDTOS, List<ArticuloDTO> ArticuloDTOS);
}


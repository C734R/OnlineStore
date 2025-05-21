-- Conmprobar pedidos
SELECT p.numero, c.nombre, a.descripcion, fechahora, p.cantidad, p.envio, p.precio
    FROM pedido p
    JOIN onlinestore.articulo a on a.id = p.articuloId
    JOIN onlinestore.cliente c on c.id = p.clienteId
    ORDER BY p.numero;
-- Comprobar clientes
SELECT c.id, c.nombre, c.domicilio, c.email, c.nif, ca.nombre, ca.cuota, ca.descuento
    FROM cliente c
    JOIN categoria ca on c.categoriaId = ca.id
    ORDER BY c.id;
-- Comprobar articulostock
SELECT  a.descripcion, sa.stock
    FROM articulostock sa
    JOIN onlinestore.articulo a on a.id = sa.articuloId
    ORDER BY sa.articuloId;
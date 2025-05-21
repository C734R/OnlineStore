Select p.numero, c.nombre, a.descripcion, fechahora, p.envio, p.precio
    FROM pedido p
    JOIN onlinestore.articulo a on a.id = p.articuloId
    JOIN onlinestore.cliente c on c.id = p.clienteId
    ORDER BY p.numero;
select c.id, c.nombre, c.domicilio, c.email, c.nif, ca.nombre, ca.cuota, ca.descuento
    from cliente c
   JOIN categoria ca on c.categoriaId = ca.id
ORDER BY c.id;
Select * FROM articulostock;

-- Procedimientos almacenados

-- Procedimiento para actualizar artículo con stock
DROP PROCEDURE IF EXISTS actualizar_articulo_con_stock;
DELIMITER //
CREATE PROCEDURE actualizar_articulo_con_stock(
    IN p_id INT,
    IN p_codigo VARCHAR(10),
    IN p_descripcion VARCHAR(255),
    IN p_precio DECIMAL(10,2),
    IN p_minutosPreparacion INT,
    IN p_stockNuevo INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            ROLLBACK;
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Error al actualizar artículo o su stock.';
        END;

    START TRANSACTION;

    UPDATE Articulo
    SET codigo = p_codigo,
        descripcion = p_descripcion,
        precio = p_precio,
        minutosPreparacion = p_minutosPreparacion
    WHERE id = p_id;

    UPDATE ArticuloStock
    SET stock = p_stockNuevo
    WHERE articulo = p_id;

    COMMIT;
END //
DELIMITER ;

-- Procedimiento para insertar pedido con actualización de stock
DROP PROCEDURE IF EXISTS insertar_pedido_con_stock;
DELIMITER //
CREATE PROCEDURE insertar_pedido_con_stock(
    IN p_numero INT,
    IN p_cliente_id INT,
    IN p_articulo_id INT,
    IN p_cantidad INT,
    IN p_fechahora DATE,
    IN p_envio DECIMAL(10,2),
    IN p_precio DECIMAL(10,2)
)
BEGIN
    DECLARE exit handler for SQLEXCEPTION
        BEGIN
            ROLLBACK;
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al insertar pedido o actualizar stock.';
        END;

    START TRANSACTION;

    INSERT INTO pedido (numero, cliente, articulo, cantidad, fechahora, envio, precio)
    VALUES (p_numero, p_cliente_id, p_articulo_id, p_cantidad, p_fechahora, p_envio, p_precio);

    UPDATE articulostock
    SET stock = stock - p_cantidad
    WHERE articulo = p_articulo_id;

    COMMIT;
END //
DELIMITER ;

-- Procedimiento para eliminar pedido con devolución de stock
DROP PROCEDURE IF EXISTS eliminar_pedido_con_stock;
DELIMITER //
CREATE PROCEDURE eliminar_pedido_con_stock(
    IN p_id INT
)
BEGIN
    DECLARE p_articulo INT;
    DECLARE p_cantidad INT;
    DECLARE stock_actual INT;
    DECLARE exit handler for SQLEXCEPTION
        BEGIN
            ROLLBACK;
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar pedido o actualizar stock.';
        END;

    START TRANSACTION;

    SELECT articulo, cantidad INTO p_articulo, p_cantidad
    FROM pedido
    WHERE id = p_id FOR UPDATE;

    IF p_articulo IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Pedido no encontrado';
    END IF;

    DELETE FROM pedido WHERE id = p_id;

    SELECT stock INTO stock_actual
    FROM articulostock
    WHERE articulo = p_articulo FOR UPDATE;

    UPDATE articulostock
    SET stock = stock_actual + p_cantidad
    WHERE articulo = p_articulo;

    COMMIT;
END //
DELIMITER ;

-- Procedimiento para actualizar pedido y stock
DROP PROCEDURE IF EXISTS actualizar_pedido_con_stock;
DELIMITER //
CREATE PROCEDURE actualizar_pedido_con_stock(
    IN p_id INT,
    IN p_numero INT,
    IN p_cliente INT,
    IN p_articulo INT,
    IN p_cantidad INT,
    IN p_fechahora DATE,
    IN p_envio FLOAT,
    IN p_precio FLOAT,
    IN p_diferenciaStock INT
)
BEGIN
    DECLARE stock_actual INT;
    DECLARE exit handler for SQLEXCEPTION
        BEGIN
            ROLLBACK;
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al actualizar pedido o actualizar stock.';
        END;
    START TRANSACTION;

    UPDATE pedido
    SET numero = p_numero,
        cliente = p_cliente,
        articulo = p_articulo,
        cantidad = p_cantidad,
        fechahora = p_fechahora,
        envio = p_envio,
        precio = p_precio
    WHERE id = p_id;

    SELECT stock INTO stock_actual
    FROM articulostock
    WHERE articulo = p_articulo FOR UPDATE;

    UPDATE articulostock
    SET stock = stock_actual + p_diferenciaStock
    WHERE articulo = p_articulo;

    COMMIT;
END //
DELIMITER ;
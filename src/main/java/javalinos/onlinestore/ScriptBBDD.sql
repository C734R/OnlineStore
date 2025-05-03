# DROP DATABASE IF EXISTS OnlineStore;
# CREATE DATABASE IF NOT EXISTS OnlineStore;
# USE OnlineStore;

SET FOREIGN_KEY_CHECKS = 0;

/*------ CREACIÓN DE TABLAS ------*/
DROP TABLE IF EXISTS Categoria;
-- Tabla para categorías
CREATE TABLE IF NOT EXISTS Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL UNIQUE,
    cuota DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) NOT NULL
);

# INSERT INTO Categoria (nombre, cuota, descuento) VALUES
#     ('Estándar', 0.0, 0.0),
#     ('Premium', 30.0, 0.20);

DROP TABLE IF EXISTS Cliente;
-- Tabla para clientes
CREATE TABLE IF NOT EXISTS Cliente (

    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    domicilio VARCHAR(250) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    nif VARCHAR(15) NOT NULL UNIQUE,
    categoriaId INT NOT NULL,
    FOREIGN KEY FK_cliente_categoria(categoriaId) REFERENCES Categoria(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

DROP TABLE IF EXISTS Articulo;
-- Tabla para artículos
CREATE TABLE IF NOT EXISTS Articulo (

    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    minutosPreparacion INT NOT NULL
);

DROP TABLE IF EXISTS ArticuloStock;
-- Tabla para stock artículos
CREATE TABLE IF NOT EXISTS ArticuloStock (
    articuloId INT PRIMARY KEY,
    stock INT DEFAULT 0 NOT NULL,
    FOREIGN KEY (articuloId) REFERENCES Articulo(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS Pedido;
-- Tabla para pedidos
CREATE TABLE IF NOT EXISTS Pedido (

    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    clienteId INT NOT NULL,
    articuloId INT NOT NULL,
    cantidad INT NOT NULL,
    fechahora DATE NOT NULL,
    envio DECIMAL(10,2) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY FK_pedido_cliente(clienteId) REFERENCES Cliente(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT ,
    FOREIGN KEY FK_pedido_articulo(articuloId) REFERENCES Articulo(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

SET FOREIGN_KEY_CHECKS = 1;
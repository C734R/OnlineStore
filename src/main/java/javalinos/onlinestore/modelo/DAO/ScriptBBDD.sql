drop database if exists OnlineStore;
create database if not exists OnlineStore;
use OnlineStore;

/*------ CREACIÓN DE TABLAS ------*/

DROP TABLE IF EXISTS Categoria;
-- Tabla para categorías
CREATE TABLE IF NOT EXISTS Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL UNIQUE,
    cuota DECIMAL(10,2),
    descuento DECIMAL(10,2)
);

INSERT INTO Categoria (nombre, cuota, descuento) VALUES
    ('Estándar', 0.0, 0.0),
    ('Premium', 30.0, 0.20);

DROP TABLE IF EXISTS Cliente;

-- Tabla para clientes
CREATE TABLE IF NOT EXISTS Cliente (

    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    domicilio VARCHAR(250) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    nif VARCHAR(15) NOT NULL UNIQUE,
    categoria INT NOT NULL,
    FOREIGN KEY FK_cliente_categoria(categoria) REFERENCES Categoria(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
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
    articulo INT PRIMARY KEY,
    stock INT DEFAULT 0 NOT NULL,
    FOREIGN KEY (articulo) REFERENCES Articulo(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS Pedido;

-- Tabla para pedidos
CREATE TABLE IF NOT EXISTS Pedido (

    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    cliente INT NOT NULL,
    articulo INT NOT NULL,
    cantidad INT NOT NULL,
    fechahora DATE NOT NULL,
    envio DECIMAL(10,2) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY FK_pedido_cliente(cliente) REFERENCES Cliente(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY FK_pedido_articulo(articulo) REFERENCES Articulo(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

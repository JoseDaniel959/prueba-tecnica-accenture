CREATE DATABASE test;

---  El siguiente ddl se crea dentro de la base de datos prueba que está en un contenedor llamado postgres_db
--- Estas tablas se crean dentro del schema public.

CREATE TABLE FRANQUICIA(
	id bigserial CONSTRAINT primary_key_franquicia PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_franquicia_not_null NOT NULL
);


CREATE TABLE SUCURSAL(
	id bigserial CONSTRAINT primary_key_sucursal PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_sucursal_not_null NOT NULL,
	franquicia_id bigint REFERENCES franquicia(id)
);

CREATE TABLE PRODUCTO(
	id bigserial CONSTRAINT primary_key_producto PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_producto_not_null NOT NULL,
	stock bigint NOT null CHECK (stock >= 0),
	sucursal_id bigint REFERENCES sucursal(id)
);


-- Los siguientes son datos de prueba para la aplicación

-- FRANQUICIAS
INSERT INTO franquicia (nombre) VALUES
('Franquicia Medellín'),
('Franquicia Bogotá'),
('Franquicia Cali'),
('Franquicia Barranquilla');


-- SUCURSALES
INSERT INTO sucursal (nombre, franquicia_id) VALUES
-- Franquicia Medellín
('Sucursal Poblado', 1),
('Sucursal Laureles', 1),
('Sucursal Centro', 1),

-- Franquicia Bogotá
('Sucursal Chapinero', 2),
('Sucursal Usaquén', 2),
('Sucursal Suba', 2),

-- Franquicia Cali
('Sucursal Granada', 3),
('Sucursal San Fernando', 3),

-- Franquicia Barranquilla
('Sucursal Norte', 4),
('Sucursal Centro', 4);


-- PRODUCTOS
INSERT INTO producto (nombre, stock, sucursal_id) VALUES

-- Franquicia Medellín
('Laptop Lenovo', 25, 1),
('Mouse Logitech', 80, 1),
('Teclado Mecánico', 45, 1),

('Monitor Samsung', 60, 2),
('Audífonos Sony', 120, 2),
('Webcam Logitech', 35, 2),

('Tablet Samsung', 90, 3),
('Disco SSD 1TB', 150, 3),
('Memoria RAM 16GB', 70, 3),


-- Franquicia Bogotá
('Laptop HP', 40, 4),
('Mouse HP', 55, 4),
('Teclado HP', 30, 4),

('Monitor LG', 100, 5),
('Audífonos JBL', 75, 5),
('Webcam HP', 65, 5),

('Tablet Lenovo', 110, 6),
('Disco SSD 512GB', 95, 6),
('Memoria RAM 8GB', 130, 6),


-- Franquicia Cali
('Laptop Asus', 50, 7),
('Mouse Asus', 45, 7),
('Teclado Asus', 60, 7),

('Monitor Asus', 85, 8),
('Audífonos Xiaomi', 140, 8),
('Webcam Xiaomi', 55, 8),


-- Franquicia Barranquilla
('Laptop Acer', 70, 9),
('Mouse Genius', 35, 9),
('Teclado Genius', 50, 9),

('Monitor Acer', 125, 10),
('Audífonos Philips', 90, 10),
('Webcam Acer', 40, 10);

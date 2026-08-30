#!/bin/bash

# Comando para levantar contenedor de java y postgres
sudo docker compose up -d --build  ;
sudo docker exec postgres_db psql -U postgres -d test -c "
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
    stock bigint NOT NULL CHECK (stock > 0),
    sucursal_id bigint REFERENCES sucursal(id)
);
"
sudo docker stop java_app


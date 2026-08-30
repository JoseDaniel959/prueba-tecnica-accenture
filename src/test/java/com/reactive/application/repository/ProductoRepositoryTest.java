package com.reactive.application.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import com.reactive.application.model.Franquicia;
import com.reactive.application.model.Producto;
import com.reactive.application.model.Sucursal;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductoRepositoryTest {

    @Autowired
    FranquiciaRepository franquiciaRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ProductoRepository productoRepository;

    Franquicia franquicia;
    Producto producto;
    Sucursal sucursal;

    @BeforeAll
    public void setup() {
        //Se borran todos los elementos de la base de datos antes de ejecutar cualquier test
        productoRepository.deleteAll().block();
        sucursalRepository.deleteAll().block();
        franquiciaRepository.deleteAll().block();

        //Creación de franquicia en DB y asignación de su id a objeto Sucursal
        Franquicia franquiciaAcrear = new Franquicia(null, "franquicia 1");
        franquiciaRepository.save(franquiciaAcrear).block();
        franquicia = franquiciaRepository.findOne(Example.of(franquiciaAcrear)).block();

        //Creación de Sucursal en DB
        Sucursal sucursalACrear = new Sucursal(null, "sucursal 1", (long) franquicia.getId());
        sucursalRepository.save(sucursalACrear).block();
        sucursal = sucursalRepository.findOne(Example.of(sucursalACrear)).block();

        producto = new Producto(null,"prueba",10L,sucursal.getId());
    
    }   



    @Test
    @Order(0)
    public void tablaSucursalVacia(){
        StepVerifier
        .create(productoRepository.findAll())
        .expectNextCount(0)
        .verifyComplete();
    }


    @Test
    @Order(1)
    public void guardarProductoASucursal(){
        StepVerifier
        .create(productoRepository.save(producto))
        .expectSubscription()
        .assertNext((productoCreado)->{
            assertEquals(productoCreado.getNombre(), producto.getNombre());
            assertEquals(productoCreado.getStock(), producto.getStock());
            assertEquals(productoCreado.getSucursalId(), producto.getSucursalId());
        })
        .verifyComplete();
    }

    @Test
    @Order(2)
    public void buscarProductoGuardadoEnTablaSucursal(){

        StepVerifier
        .create(productoRepository.save(producto))
        .expectSubscription()
        .assertNext((productoCreado)->{
            assertEquals(productoCreado.getNombre(), producto.getNombre());
            assertEquals(productoCreado.getStock(), producto.getStock());
            assertEquals(productoCreado.getSucursalId(), producto.getSucursalId());
        })
        .verifyComplete();

        StepVerifier
        .create(productoRepository.findOne(Example.of(producto)))
        .expectSubscription()
        .expectNextCount(1)
        .verifyComplete();

    }


    @Test
    @Order(3)
    public void guardarProductoASucursalqueNoExiste(){
        StepVerifier
        .create(productoRepository.save(new Producto(null, "prueba",10L, 10000000000L)))
        .expectSubscription()
        .expectError()
        .verify();
    }
}
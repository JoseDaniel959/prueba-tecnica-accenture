package com.reactive.application.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.reactive.application.model.Franquicia;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
public class FranquiciaRepositoryTest {

    @Autowired
    FranquiciaRepository franquiciaRepository;
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    ProductoRepository productoRepository;

    Franquicia franquicia;

    @BeforeAll
    public void setup() {
         //Se borran todos los elementos de la base de datos antes de ejecutar cualquier test
        productoRepository.deleteAll().block();
        sucursalRepository.deleteAll().block();
        franquiciaRepository.deleteAll().block();

        franquicia = new Franquicia(null, "franquicia 1");
    }

    @Test
    @Order(0)
    public void listarTablaFranquiciaVacia() {
        StepVerifier.create(franquiciaRepository.findAll())
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void guardarFranquicia() {
        StepVerifier.create(franquiciaRepository.save(franquicia))
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    @Order(2)
    public void guardarFranquiciaYChequearNombre() {
        StepVerifier.create(franquiciaRepository.save(franquicia))
                .expectNextMatches(franquiciaCreada -> franquiciaCreada.getNombre().equals(franquicia.getNombre()))
                .verifyComplete();
    }

}
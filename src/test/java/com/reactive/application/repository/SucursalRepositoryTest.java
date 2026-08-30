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
import com.reactive.application.model.Sucursal;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SucursalRepositoryTest {

    @Autowired
    FranquiciaRepository franquiciaRepository;
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    ProductoRepository productoRepository;

    Sucursal sucursal;
    Franquicia franquicia;

    @BeforeAll
    public void setup() {
         //Se borran todos los elementos de la base de datos antes de ejecutar cualquier test
        productoRepository.deleteAll().block();
        sucursalRepository.deleteAll().block();
        franquiciaRepository.deleteAll().block();

        // Creación de franquicia en DB y asignación de su id a objeto Sucursal
        Franquicia franquiciaAcrear = new Franquicia(null, "franquicia 1");
        franquiciaRepository.save(franquiciaAcrear).block();
        franquicia = franquiciaRepository.findOne(Example.of(franquiciaAcrear)).block();
        sucursal = new Sucursal(null, "sucursal 1", (long) franquicia.getId());

    }

    @Test
    @Order(0)
    public void guardarSucursalAFranquicia() {
        StepVerifier.create(sucursalRepository.save(sucursal))
                .assertNext((sucursalGuardada) -> {
                    assertEquals(sucursalGuardada.getNombre(), sucursal.getNombre());
                    assertEquals(sucursalGuardada.getFranquiciaId(), sucursal.getFranquiciaId());
                })
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void guardarSucursalAFranquiciaQueNoexiste() {
        Sucursal sucursal = Sucursal.builder()
                .id(null)
                .nombre("sucursal 2")
                .franquiciaId(10000000000000000L)
                .build();

        StepVerifier.create(sucursalRepository.save(sucursal))
                .expectError()
                .verify();
    }

}

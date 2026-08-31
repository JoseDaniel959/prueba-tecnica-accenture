package com.reactive.application.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.reactive.application.DTO.FranquiciaDTO;
import com.reactive.application.DTO.RequestUpdateFranquicia;
import com.reactive.application.service.Franquicia.FranquiciaServiceImp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class FranquiciaControllerTest {

    private WebTestClient webTestClient;
    private FranquiciaServiceImp franquiciaServiceImp;


    // Set up mock UserService and WebTestClient before each test
    @BeforeEach
    void setUp() {
        franquiciaServiceImp = mock(FranquiciaServiceImp.class);
        webTestClient = WebTestClient.bindToController(new FranquiciaController(franquiciaServiceImp)).build();
    }

    @Test
    @Order(0)
    public void testCrearFranquicia() {
        FranquiciaDTO franquiciaDTONueva = new FranquiciaDTO(null, "franquicia 1");
        when(franquiciaServiceImp.agregarFranquicia(franquiciaDTONueva)).thenReturn(Mono.just(franquiciaDTONueva));
        Flux<FranquiciaDTO> franquiciaNueva = webTestClient
                .post()
                .uri("/franquicia")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new FranquiciaDTO(null, "franquicia 1")))
                .exchange()
                .expectStatus().isOk()
                .returnResult(FranquiciaDTO.class)
                .getResponseBody();

        StepVerifier.create(franquiciaNueva)
                .expectSubscription()
                .expectNextMatches(franquiciaCreada -> franquiciaCreada.getNombre().equals("franquicia 1"))
                .verifyComplete();

    }

    @Test
    @Order(1)
    public void testPatchMapping() {

        FranquiciaDTO franquiciaDTONuevaEditada = FranquiciaDTO.builder()
                .id(1L)
                .nombre("editado")
                .build();
        RequestUpdateFranquicia requestUpdateFranquicia = new RequestUpdateFranquicia("Editado 1");


        when(franquiciaServiceImp.editarNombre(1L,requestUpdateFranquicia.getNombre())).thenReturn(Mono.just(franquiciaDTONuevaEditada));

        webTestClient
                .patch()
                .uri("/franquicia/{id}/editar-nombre",1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestUpdateFranquicia))
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(FranquiciaDTO.class)
                .getResponseBody();

    }

}
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

import com.reactive.application.DTO.RequestUpdateSucursal;
import com.reactive.application.DTO.SucursalDTO;
import com.reactive.application.service.Sucursal.SucursalServiceImp;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SucursalControllerTest {

    private WebTestClient webTestClient;
    private SucursalServiceImp sucursalServiceImp;

    @BeforeEach
    void setUp() {
        sucursalServiceImp = mock(SucursalServiceImp.class);
        webTestClient = WebTestClient.bindToController(new SucursalController(sucursalServiceImp)).build();
    }

    @Test
    @Order(0)
    public void testCrearSucursal() {
        SucursalDTO sucursalNuevaDTO = SucursalDTO
                .builder()
                .id(null)
                .nombre("Sucursal Nueva")
                .franquiciaId(1L)
                .build();

        SucursalDTO sucursalCreadaDTO = SucursalDTO
                .builder()
                .id(1L)
                .nombre("Sucursal Nueva")
                .franquiciaId(1L)
                .build();
        when(sucursalServiceImp.crearSucursalAFranquicia(sucursalNuevaDTO)).thenReturn(Mono.just(sucursalCreadaDTO));

        webTestClient
                .post()
                .uri("/sucursal")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(sucursalNuevaDTO))
                .exchange()
                .expectStatus().isOk()
                .returnResult(SucursalDTO.class);

    }

    @Test
    @Order(1)
    public void testEditarNombreSucursal() {
        
        
        RequestUpdateSucursal requestUpdateSucursal = new RequestUpdateSucursal("nombre nuevo");
        
        
        SucursalDTO sucursalEditadaDTO = SucursalDTO
                .builder()
                .id(1L)
                .nombre("nombre nuevo")
                .franquiciaId(1L)
                .build();
        when(sucursalServiceImp.editarNombre(1L,requestUpdateSucursal.getNombre())).thenReturn(Mono.just(sucursalEditadaDTO));

        webTestClient
                .post()
                .uri("/sucursal")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestUpdateSucursal))
                .exchange()
                .expectStatus().isOk()
                .returnResult(SucursalDTO.class);

    }
}

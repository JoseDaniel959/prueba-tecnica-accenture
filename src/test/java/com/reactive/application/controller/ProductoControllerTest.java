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

import com.reactive.application.DTO.ProductoDTO;
import com.reactive.application.DTO.RequestUpdateProducto;
import com.reactive.application.DTO.RequestUpdateStock;
import com.reactive.application.service.Franquicia.FranquiciaServiceImp;
import com.reactive.application.service.Producto.ProductoServiceImp;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoControllerTest {

    private WebTestClient webTestClient;
    private ProductoServiceImp productoServiceImp;

    @BeforeEach
    void setUp() {
        productoServiceImp = mock(ProductoServiceImp.class);
        webTestClient = WebTestClient.bindToController(new ProductoController(productoServiceImp)).build();
    }

    @Test
    @Order(0)
    public void testCrearProducto() {
        ProductoDTO productoDTONuevo = new ProductoDTO(null, "producto", 20L, 1L);
        when(productoServiceImp.agregarProductoASucursal(null)).thenReturn(Mono.just(productoDTONuevo));

        webTestClient
                .post()
                .uri("/producto")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productoDTONuevo))
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductoDTO.class);

    }

    @Test
    @Order(1)
    public void testEliminarProducto() {
        when(productoServiceImp.eliminarProductoDeSucursal(1L)).thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/producto/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Void.class);

    }

    @Test
    @Order(2)
    public void testModificarStock() {
        RequestUpdateStock requestUpdateStock = new RequestUpdateStock(50L);
        ProductoDTO productoDTOeditado = ProductoDTO
                .builder()
                .id(1L)
                .nombre("Producto nuevo")
                .stock(50L)
                .sucursalId(1L)
                .build();

        when(productoServiceImp.modificarStockAProducto(2L, requestUpdateStock.getStock()))
                .thenReturn(Mono.just(productoDTOeditado));

        webTestClient
                .patch()
                .uri("/producto/{id}/stock", 1)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestUpdateStock))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductoDTO.class);

    }

    @Test
    @Order(2)
    public void testEditarNombreFranquicia() {
        RequestUpdateProducto requestUpdateProducto = new RequestUpdateProducto("producto Editado");
        ProductoDTO productoDTOeditado = ProductoDTO
                .builder()
                .id(1L)
                .nombre("Producto nuevo")
                .stock(50L)
                .sucursalId(1L)
                .build();

        when(productoServiceImp.editarNombre(2L, requestUpdateProducto.getNombre()))
                .thenReturn(Mono.just(productoDTOeditado));

        webTestClient
                .patch()
                .uri("/producto/{id}/editar-nombre", 1)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestUpdateProducto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductoDTO.class);

    }
}

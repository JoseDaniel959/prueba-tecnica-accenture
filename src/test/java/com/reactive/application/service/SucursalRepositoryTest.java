package com.reactive.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reactive.application.DTO.SucursalDTO;
import com.reactive.application.mapper.Mapper;
import com.reactive.application.model.Franquicia;
import com.reactive.application.model.Sucursal;
import com.reactive.application.repository.FranquiciaRepository;
import com.reactive.application.repository.SucursalRepository;
import com.reactive.application.service.Sucursal.SucursalServiceImp;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SucursalRepositoryTest {

    @Mock
    private SucursalRepository sucursalRepository;
    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private SucursalServiceImp sucursalServiceImp;

    private Franquicia franquicia;
    private Sucursal sucursal, sucursalEditada;

    @BeforeEach
    public void setup() {

        franquicia = Franquicia
                .builder()
                .id(1L)
                .nombre("franquicia 1")
                .build();

        sucursal = Sucursal
                .builder()
                .id(1L)
                .nombre("sucursal")
                .franquiciaId(1L)
                .build();

        sucursalEditada = Sucursal
                .builder()
                .id(1L)
                .nombre("editada")
                .franquiciaId(1L)
                .build();

    }

    @Test
    @Order(0)
    public void testCrearSucursalAFranquicia() {
        when(franquiciaRepository.findById(anyLong())).thenReturn(Mono.just(franquicia));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(Mono.just(sucursal));

        SucursalDTO sucursalDTO = Mapper.toSucursalDTO(sucursal);

        StepVerifier
                .create(sucursalServiceImp.crearSucursalAFranquicia(sucursalDTO))
                .expectSubscription()
                .assertNext(sucursalCreada -> {
                    assertEquals(sucursalCreada.getId(), sucursalDTO.getId());
                    assertEquals(sucursalCreada.getNombre(), sucursalDTO.getNombre());
                    assertEquals(sucursalCreada.getFranquiciaId(), sucursalDTO.getFranquiciaId());

                })
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void testEditarNombre() {
        when(sucursalRepository.findById(anyLong())).thenReturn(Mono.just(sucursal));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(Mono.just(sucursal));
        


        StepVerifier
                .create(sucursalServiceImp.editarNombre(1L, "editada"))
                .expectSubscription()
                .assertNext(sucursalEditadaEnDB -> {
                    assertEquals(sucursalEditadaEnDB.getId(), sucursalEditada.getId());
                    assertEquals(sucursalEditadaEnDB.getNombre(), sucursalEditada.getNombre());
                    assertEquals(sucursalEditadaEnDB.getFranquiciaId(), sucursalEditada.getFranquiciaId());
                })
                .verifyComplete();
    }
}

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

import com.reactive.application.DTO.FranquiciaDTO;
import com.reactive.application.mapper.Mapper;
import com.reactive.application.model.Franquicia;
import com.reactive.application.repository.FranquiciaRepository;
import com.reactive.application.service.Franquicia.FranquiciaServiceImp;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FranquiciaServiceTest {

    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private FranquiciaServiceImp franquiciaServiceImp;

    private Franquicia franquiciaCreada;
    private FranquiciaDTO franquiciaDTO;
    private Franquicia franquicia;
    private FranquiciaDTO franquiciaCreadaDTO;
    private Franquicia franquiciaEditada;
    private FranquiciaDTO franquiciaEditadaDTO;

    @BeforeEach
    public void setUp() {

        franquicia = Franquicia
                .builder()
                .id(1L)
                .nombre("franquicia 1")
                .build();

        franquiciaDTO = Mapper.toFranquiciaDTO(franquicia);

        franquiciaCreada = Franquicia
                .builder()
                .id(null)
                .nombre("franquicia 1")
                .build();
        franquiciaCreadaDTO = Mapper.toFranquiciaDTO(franquiciaCreada);

        franquiciaEditada = Franquicia
                .builder()
                .id(1L)
                .nombre("editado")
                .build();
        franquiciaEditadaDTO = Mapper.toFranquiciaDTO(franquiciaEditada);

    }

    @Test
    @Order(0)
    public void testAgregarFranquicia() {
        when(franquiciaRepository.save(any(Franquicia.class))).thenReturn(Mono.just(franquiciaCreada));
        StepVerifier.create(franquiciaServiceImp.agregarFranquicia(franquiciaDTO))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void testBuscarFranquiciaPorId(){
        when(franquiciaRepository.findById(1L)).thenReturn(Mono.just(franquicia));
        StepVerifier
        .create(franquiciaServiceImp.buscarFranquiciaPorId(1L))
        .assertNext((franquiciaEncontrada) ->{
                    assertEquals(franquicia.getId(), franquiciaEncontrada.getId());
                    assertEquals(franquicia.getNombre(), franquiciaEncontrada.getNombre());
            })
      .verifyComplete();
    }
        

    @Test
    @Order(2)
    public void testEditarFranquicia() {
        when(franquiciaRepository.findById(anyLong())).thenReturn(Mono.just(franquicia));

        when(franquiciaRepository.save(any(Franquicia.class))).thenReturn(Mono.just(franquiciaEditada));

        StepVerifier.create(franquiciaServiceImp.editarNombre(1L, "editado"))
                .assertNext((franquiciaNueva) -> {
                    assertEquals(franquiciaEditada.getNombre(), franquiciaNueva.getNombre());
                    assertEquals(franquiciaEditada.getId(), franquiciaNueva.getId());
                })
                .verifyComplete();
    }
}

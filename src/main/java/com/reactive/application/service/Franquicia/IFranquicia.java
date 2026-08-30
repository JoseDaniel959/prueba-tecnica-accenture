package com.reactive.application.service.Franquicia;

import com.reactive.application.DTO.FranquiciaDTO;

import reactor.core.publisher.Mono;

public interface IFranquicia {
    public Mono<FranquiciaDTO> agregarFranquicia(FranquiciaDTO franquiciaDTO);
    public Mono<FranquiciaDTO> buscarFranquiciaPorId(Long idFranquicia);
    public Mono<FranquiciaDTO> editarNombre(Long idFranquicia, String nombreNuevo);
}

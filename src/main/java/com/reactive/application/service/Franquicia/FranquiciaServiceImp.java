package com.reactive.application.service.Franquicia;

import org.springframework.stereotype.Service;

import com.reactive.application.DTO.FranquiciaDTO;
import com.reactive.application.exception.RecursoNoEncontrado;
import com.reactive.application.mapper.Mapper;
import com.reactive.application.model.Franquicia;
import com.reactive.application.repository.FranquiciaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class FranquiciaServiceImp implements IFranquicia {

    final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> agregarFranquicia(FranquiciaDTO franquiciaDTO) {
        Franquicia franquicia = Mapper.toFranquicia(franquiciaDTO);

        return franquiciaRepository.save(franquicia)
                .map(Mapper::toFranquiciaDTO);
    }

    @Override
    public Mono<FranquiciaDTO> buscarFranquiciaPorId(Long idFranquicia) {
        return franquiciaRepository.findById(idFranquicia).map(Mapper::toFranquiciaDTO);
    }

    @Override
    public Mono<FranquiciaDTO> editarNombre(Long idFranquicia, String nombreNuevo) {
     return franquiciaRepository
     .findById(idFranquicia)
     .switchIfEmpty(Mono.error(new RecursoNoEncontrado("No existe la franquicia con id")))
     .flatMap(franquicia ->{
        franquicia.setNombre(nombreNuevo);
        return franquiciaRepository.save(franquicia);
     })
     .map(Mapper::toFranquiciaDTO);
    }

    

}

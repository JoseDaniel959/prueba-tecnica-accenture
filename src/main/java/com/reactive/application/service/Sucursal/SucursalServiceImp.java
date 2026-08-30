package com.reactive.application.service.Sucursal;

import org.springframework.stereotype.Service;

import com.reactive.application.DTO.SucursalDTO;
import com.reactive.application.exception.RecursoNoEncontrado;
import com.reactive.application.mapper.Mapper;
import com.reactive.application.model.Sucursal;
import com.reactive.application.repository.FranquiciaRepository;
import com.reactive.application.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SucursalServiceImp implements ISucursal {

    final SucursalRepository sucursalRepository;
    final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<SucursalDTO> crearSucursalAFranquicia(SucursalDTO sucursalDTO) {

        return franquiciaRepository
                .findById(sucursalDTO.getFranquiciaId())
                .switchIfEmpty(Mono.error(new RecursoNoEncontrado("No existe la franquicia")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = Mapper.toSucursal(sucursalDTO);
                    return sucursalRepository.save(sucursal);
                })
                .map(Mapper::toSucursalDTO);

    }

    @Override
    public Mono<SucursalDTO> editarNombre(Long idSucursal, String nombreNuevo) {
        return sucursalRepository
                .findById(idSucursal)
                .switchIfEmpty(Mono.error(new RecursoNoEncontrado("no existe la sucursal")))
                .flatMap((sucursal) -> {
                    sucursal.setNombre(nombreNuevo);
                    return sucursalRepository.save(sucursal);
                })
                .map(Mapper::toSucursalDTO);
    }

}

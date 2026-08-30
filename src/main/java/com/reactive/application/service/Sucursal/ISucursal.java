package com.reactive.application.service.Sucursal;

import com.reactive.application.DTO.SucursalDTO;

import reactor.core.publisher.Mono;

public interface ISucursal {
    public Mono<SucursalDTO> crearSucursalAFranquicia(SucursalDTO sucursalDTO);
    public Mono<SucursalDTO> editarNombre(Long idSucursal, String nombreNuevo);
}

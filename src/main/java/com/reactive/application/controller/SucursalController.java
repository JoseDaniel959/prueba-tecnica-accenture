package com.reactive.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.application.DTO.RequestUpdateSucursal;
import com.reactive.application.DTO.SucursalDTO;
import com.reactive.application.service.Sucursal.SucursalServiceImp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sucursal")
@RequiredArgsConstructor
public class SucursalController {

    final private SucursalServiceImp sucursalServiceImp;

    @PostMapping
    public Mono<SucursalDTO> crearSucursal(@RequestBody @Valid SucursalDTO sucursalNuevaDTO) {
        return sucursalServiceImp.crearSucursalAFranquicia(sucursalNuevaDTO);
    }

    @PatchMapping("/{id}/editar-nombre")
    public Mono<SucursalDTO> editarNombreSucursal(@PathVariable Long id,
            @RequestBody @Valid RequestUpdateSucursal requestUpdateSucursal) {
        return sucursalServiceImp.editarNombre(id, requestUpdateSucursal.getNombre());
    }

}

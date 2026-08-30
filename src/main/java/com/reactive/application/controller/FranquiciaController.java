package com.reactive.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.application.DTO.FranquiciaDTO;
import com.reactive.application.DTO.RequestUpdateFranquicia;
import com.reactive.application.service.Franquicia.FranquiciaServiceImp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/franquicia")
@RequiredArgsConstructor
public class FranquiciaController {
    
    final FranquiciaServiceImp franquiciaServiceImp;

  
    @PostMapping
    public Mono<FranquiciaDTO> crearFranquicia(@RequestBody @Valid FranquiciaDTO franquicianNuevaDTO) {        
        return franquiciaServiceImp.agregarFranquicia(franquicianNuevaDTO);
    }

    @PatchMapping("/{id}/editar-nombre")
    public Mono<FranquiciaDTO> editarNombreFranquicia(@PathVariable Long id , @RequestBody @Valid RequestUpdateFranquicia requestUpdateFranquicia){
        return franquiciaServiceImp.editarNombre(id, requestUpdateFranquicia.getNombre());
    }
    
}

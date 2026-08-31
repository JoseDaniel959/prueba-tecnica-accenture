package com.reactive.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.application.DTO.ProductoDTO;
import com.reactive.application.DTO.RequestUpdateProducto;
import com.reactive.application.DTO.RequestUpdateStock;
import com.reactive.application.service.Producto.ProductoServiceImp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    final ProductoServiceImp productoServiceImp;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoDTO> crearProducto(@RequestBody @Valid ProductoDTO productoDTO) {        
        return productoServiceImp.agregarProductoASucursal(productoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> eliminarProducto(@PathVariable Long id){
        return productoServiceImp.eliminarProductoDeSucursal(id);
    }

    @PatchMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoDTO> modificarStock(@PathVariable Long id, @RequestBody @Valid RequestUpdateStock requestUpdateStock){
        return productoServiceImp.modificarStockAProducto(id, requestUpdateStock.getStock());
    }

    @GetMapping("/conMayorStockPorSucursalPorFranquicia/{idFranquicia}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductoDTO> productosConMayorStockPorSucursalPorFranqucia(@PathVariable Long idFranquicia) {
        return productoServiceImp.obtenerProductosConMayorStockPorFranquicia(idFranquicia);
    }
    
    @PatchMapping("/{id}/editar-nombre")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoDTO> editarNombreFranquicia(@PathVariable Long id , @RequestBody @Valid RequestUpdateProducto requestUpdateProducto){
        return productoServiceImp.editarNombre(id, requestUpdateProducto.getNombre());
    }

    
    
}

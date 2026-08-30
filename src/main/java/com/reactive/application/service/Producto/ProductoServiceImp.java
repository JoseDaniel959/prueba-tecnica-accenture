package com.reactive.application.service.Producto;

import org.springframework.stereotype.Service;

import com.reactive.application.DTO.ProductoDTO;
import com.reactive.application.exception.RecursoNoEncontrado;
import com.reactive.application.mapper.Mapper;
import com.reactive.application.model.Producto;
import com.reactive.application.repository.ProductoRepository;
import com.reactive.application.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductoServiceImp implements IProducto {

    final ProductoRepository productoRepository;
    final SucursalRepository sucursalRepository;

    @Override
    public Mono<ProductoDTO> agregarProductoASucursal(ProductoDTO productoDTO) {
        return sucursalRepository
                .findById(productoDTO.getSucursalId())
                .switchIfEmpty(Mono.error(new RecursoNoEncontrado("No existe la sucursal")))
                .flatMap(sucursal -> {
                    Producto productoAcrear = Mapper.toProducto(productoDTO);
                    return productoRepository.save(productoAcrear);
                })
                .map(Mapper::toProductoDTO);
    }

    @Override
    public Mono<Void> eliminarProductoDeSucursal(Long idProducto) {
        return productoRepository.deleteById(idProducto);
    }

    @Override
    public Mono<ProductoDTO> modificarStockAProducto(Long idProducto, Long stockNuevo) {
        return productoRepository
                .findById(idProducto)
                .switchIfEmpty(Mono.error(new RecursoNoEncontrado("No existe el producto")))
                .flatMap(producto -> {
                    producto.setStock(stockNuevo);
                    return productoRepository.save(producto);
                })
                .map(Mapper::toProductoDTO);

    }

    @Override
    public Flux<ProductoDTO> obtenerProductosConMayorStockPorFranquicia(Long idFranquicia) {
        return productoRepository
        .encontrarProductosConMayorStockPorFranquicia(idFranquicia)
        .switchIfEmpty(Mono.error(new RecursoNoEncontrado("No existe el producto")))
        .map(Mapper::toProductoDTO);
    }

    @Override
    public Mono<ProductoDTO> editarNombre(Long idProducto, String nombre) {
        return productoRepository
        .findById(idProducto)
        .switchIfEmpty(Mono.error(new RecursoNoEncontrado("No existe el producto")))
        .flatMap((producto)->{
            producto.setNombre(nombre);
            return productoRepository.save(producto);
        })
        .map(Mapper::toProductoDTO);
    }

}

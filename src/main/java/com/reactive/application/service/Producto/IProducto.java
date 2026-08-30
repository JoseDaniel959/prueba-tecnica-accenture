package com.reactive.application.service.Producto;

import com.reactive.application.DTO.ProductoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProducto {
    public Mono<ProductoDTO> agregarProductoASucursal(ProductoDTO productoDTO);
    public Mono<Void> eliminarProductoDeSucursal(Long idProducto);
    public Mono<ProductoDTO> modificarStockAProducto(Long idProducto, Long stockNuevo);
    public Flux<ProductoDTO> obtenerProductosConMayorStockPorFranquicia(Long idFranquicia);
    public Mono<ProductoDTO> editarNombre(Long idProducto, String nombre);

}

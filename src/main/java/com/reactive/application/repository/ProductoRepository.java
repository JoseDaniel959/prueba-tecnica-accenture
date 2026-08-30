package com.reactive.application.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.reactive.application.model.Producto;

import reactor.core.publisher.Flux;

public interface ProductoRepository extends R2dbcRepository<Producto, Long> {

    @Query("""
            SELECT DISTINCT ON (producto.sucursal_id)
                producto.id,
                producto.nombre,
                producto.stock,
                producto.sucursal_id
            FROM franquicia
            JOIN sucursal
                ON franquicia.id = sucursal.franquicia_id
            JOIN producto
                ON sucursal.id = producto.sucursal_id
            WHERE franquicia.id = :idFranquicia
            ORDER BY producto.sucursal_id, producto.stock DESC
            """)
    Flux<Producto> encontrarProductosConMayorStockPorFranquicia(Long idFranquicia);

}

package com.reactive.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Table(name = "producto", schema = "public")
@Data
@Builder
@AllArgsConstructor
public class Producto {
    
    @Id
    @Column("id")
    private Long id;

    @Column("nombre")
    private String nombre;

    @Column("stock")
    private Long stock;

    @Column("sucursal_id")
    private Long sucursalId;
}

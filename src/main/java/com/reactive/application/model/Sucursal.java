package com.reactive.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Table(name = "sucursal", schema = "public")
@Data
@Builder
@AllArgsConstructor

public class Sucursal {
    
    @Id
    @Column("id")
    private Long id;
    
    @Column("nombre")
    private String nombre;

    @Column("franquicia_id")
    private Long franquiciaId;
}

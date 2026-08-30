package com.reactive.application.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Table(name = "franquicia",schema = "public")
@Data
@Builder
@AllArgsConstructor
public class Franquicia {
    
    @Id
    @Column("id")
    private Long id;
    
    @Column("nombre")
    private String nombre;
}

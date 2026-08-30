package com.reactive.application.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;

     @NotNull(message = "nombre no puede ser nulo")
    private String nombre;
    private Long stock;
     @NotNull(message = "sucursalID no puede ser nulo")
    private Long sucursalId;
}


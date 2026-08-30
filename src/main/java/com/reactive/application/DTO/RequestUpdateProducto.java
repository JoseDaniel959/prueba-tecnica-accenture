package com.reactive.application.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUpdateProducto {
    @NotNull(message = "nombre no puede ser nulo")
    private String nombre;
}

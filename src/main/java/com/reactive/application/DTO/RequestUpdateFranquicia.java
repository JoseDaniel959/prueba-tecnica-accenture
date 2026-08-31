package com.reactive.application.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestUpdateFranquicia{
     @NotNull(message = "nombre no puede ser nulo")
    private String nombre;
}

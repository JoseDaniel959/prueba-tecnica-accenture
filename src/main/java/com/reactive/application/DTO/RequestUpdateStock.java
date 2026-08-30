package com.reactive.application.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUpdateStock {
     @NotNull(message = "stock no puede ser nulo")
    Long stock;
}

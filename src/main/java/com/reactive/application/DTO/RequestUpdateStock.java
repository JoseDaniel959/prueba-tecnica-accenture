package com.reactive.application.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUpdateStock {
       
    @Min(value = 0, message = "Stock no puede ser negativo")
    @NotNull(message = "stock no puede ser nulo")
    Long stock;
}

package br.com.xvidros.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemCreateDTO(
    @NotNull(message = "ID do produto é obrigatório")
    Long productId,
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    Integer quantity
) {}


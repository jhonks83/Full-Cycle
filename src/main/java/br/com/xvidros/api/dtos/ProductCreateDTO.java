package br.com.xvidros.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCreateDTO(
    @NotBlank(message = "Nome é obrigatório")
    String name,
    
    @NotBlank(message = "Descrição é obrigatória")
    String description,
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    Float price,
    
    @NotBlank(message = "Categoria é obrigatória")
    String category,
    
    @NotBlank(message = "Marca é obrigatória")
    String brand,
    
    String imageUrl
) {}


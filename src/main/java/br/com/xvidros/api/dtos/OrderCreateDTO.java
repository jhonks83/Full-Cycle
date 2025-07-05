package br.com.xvidros.api.dtos;

import jakarta.validation.constraints.NotNull;

public record OrderCreateDTO(
    @NotNull(message = "ID do usuário é obrigatório")
    Long userId
) {}
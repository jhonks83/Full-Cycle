package br.com.xvidros.api.dtos;

import java.util.List;

public record CartResponseDTO(
    Long id,
    Long userId,
    String userName,
    List<CartItemResponseDTO> items,
    Float totalPrice
) {}


package br.com.xvidros.api.dtos;

public record CartItemResponseDTO(
    Long id,
    Integer quantity,
    Float subtotal,
    ProductResponseDTO product
) {}


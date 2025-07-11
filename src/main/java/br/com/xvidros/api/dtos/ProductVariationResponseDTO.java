package br.com.xvidros.api.dtos;

public record ProductVariationResponseDTO(
    Long id,
    Long productId,
    String size,
    String color,
    Float price,
    Integer quantity,
    String sku
) {}
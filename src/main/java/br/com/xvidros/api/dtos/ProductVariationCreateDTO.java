package br.com.xvidros.api.dtos;

public record ProductVariationCreateDTO(
    Long productId,
    String size,
    String color,
    Float price,
    Integer quantity,
    String sku
) {}
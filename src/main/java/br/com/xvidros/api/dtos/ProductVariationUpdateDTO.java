package br.com.xvidros.api.dtos;

public record ProductVariationUpdateDTO(
    String size,
    String color,
    Float price,
    Integer quantity,
    String sku
) {}
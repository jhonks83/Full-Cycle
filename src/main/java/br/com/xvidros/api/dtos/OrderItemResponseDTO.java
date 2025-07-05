package br.com.xvidros.api.dtos;

public record OrderItemResponseDTO(
    Long id,
    String productName,
    String color,
    String size,
    Float price,
    Integer quantity,
    Float subtotal
) {}

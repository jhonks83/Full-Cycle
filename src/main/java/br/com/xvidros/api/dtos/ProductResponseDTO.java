package br.com.xvidros.api.dtos;

import java.sql.Date;

public record ProductResponseDTO(
    Long id,
    String name,
    String description,
    Float price,
    String category,
    String brand,
    String imageUrl,
    Float rating,
    Date createdAt,
    Date updatedAt
) {}


package br.com.xvidros.api.dtos;

import java.sql.Date;
import java.util.List;

import br.com.xvidros.api.enuns.OrderStatus;

public record OrderResponseDTO(
    Long id, 
    Long userId,
    String userName,
    Float totalPrice,
    OrderStatus status,
    String trackingCode,
    Date createdAt,
    Date updatedAt,
    List<OrderItemResponseDTO> items
) {}

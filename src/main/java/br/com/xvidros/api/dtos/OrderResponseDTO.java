package br.com.xvidros.api.dtos;

import java.time.LocalDateTime; 
import java.util.List;

import br.com.xvidros.api.enuns.OrderStatus;

public record OrderResponseDTO(
    Long id,
    Long userId,
    String userName,
    Float totalPrice,
    OrderStatus status,
    String trackingCode,
   
    LocalDateTime createdAt, 
    LocalDateTime updatedAt, 
    List<OrderItemResponseDTO> items
) {}
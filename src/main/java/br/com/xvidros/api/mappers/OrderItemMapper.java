package br.com.xvidros.api.mappers;

import org.springframework.stereotype.Component;

import br.com.xvidros.api.dtos.OrderItemResponseDTO;
import br.com.xvidros.api.entities.OrderItem;

@Component
public class OrderItemMapper {

    public OrderItemResponseDTO toResponseDTO(OrderItem orderItem) {
                float subtotal = orderItem.getPrice() * orderItem.getQuantity();

        return new OrderItemResponseDTO(
            orderItem.getId(),
            orderItem.getProductVariation().getProduct().getName(),
            orderItem.getProductVariation().getColor(),
            String.valueOf(orderItem.getProductVariation().getSize()),
            orderItem.getPrice(), 
            orderItem.getQuantity(),
            subtotal 
        );
    }
}
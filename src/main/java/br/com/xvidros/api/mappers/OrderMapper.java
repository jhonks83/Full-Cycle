package br.com.xvidros.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.xvidros.api.dtos.OrderResponseDTO;
import br.com.xvidros.api.dtos.OrderItemResponseDTO;
import br.com.xvidros.api.entities.Order;
import br.com.xvidros.api.entities.OrderItem;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper; 

    
    public OrderResponseDTO toResponseDTO(Order order, List<OrderItem> orderItems) {

        
        List<OrderItemResponseDTO> itemsDTO = orderItems.stream()
            .map(orderItemMapper::toResponseDTO)
            .collect(Collectors.toList());

        return new OrderResponseDTO(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getName(),
            order.getTotalPrice(),
            order.getStatus(),
            order.getTrackingCode(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            itemsDTO 
        );
    }
}
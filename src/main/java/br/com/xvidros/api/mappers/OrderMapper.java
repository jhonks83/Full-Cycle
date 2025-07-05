package br.com.xvidros.api.mappers;

import org.springframework.stereotype.Component;
import br.com.xvidros.api.dtos.OrderResponseDTO;
import br.com.xvidros.api.dtos.OrderItemResponseDTO;
import br.com.xvidros.api.entities.Order;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDTO toResponseDTO(Order order, List<OrderItemResponseDTO> orderItems) {
        return new OrderResponseDTO(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getName(),
            order.getTotalPrice(),
            order.getStatus(),
            order.getTrackingCode(),
            order.getCreated_at(),
            order.getUpdated_at(),
            orderItems
        );
    }
}

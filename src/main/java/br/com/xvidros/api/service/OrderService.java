package br.com.xvidros.api.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.xvidros.api.dtos.OrderCreateDTO;
import br.com.xvidros.api.dtos.OrderItemResponseDTO;
import br.com.xvidros.api.dtos.OrderResponseDTO;
import br.com.xvidros.api.entities.Cart;
import br.com.xvidros.api.entities.CartItem;
import br.com.xvidros.api.entities.Order;
import br.com.xvidros.api.entities.OrderItem;
import br.com.xvidros.api.entities.User;
import br.com.xvidros.api.enuns.OrderStatus;
import br.com.xvidros.api.mappers.OrderItemMapper;
import br.com.xvidros.api.mappers.OrderMapper;
import br.com.xvidros.api.repository.CartItemRepository;
import br.com.xvidros.api.repository.CartRepository;
import br.com.xvidros.api.repository.OrderItemRepository;
import br.com.xvidros.api.repository.OrderRepository;
import br.com.xvidros.api.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartService cartService;

    @Transactional
    public OrderResponseDTO createOrderFromCart(OrderCreateDTO orderCreateDTO) {
        User user = userRepository.findById(orderCreateDTO.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Carrinho está vazio");
        }

        Float totalPrice = cartItems.stream()
            .map(CartItem::getSubtotal)
            .reduce(0.0f, Float::sum);

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order.setTrackingCode(generateTrackingCode());
        order.setCreated_at(Date.valueOf(LocalDate.now()));
        order.setUpdated_at(Date.valueOf(LocalDate.now()));

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream()
            .map(cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProductVariation(cartItem.getProductVariation());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getSubtotal());
                return orderItem;
            })
            .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        cartService.clearCart(user.getId());

        List<OrderItemResponseDTO> orderItemDTOs = orderItems.stream()
            .map(orderItemMapper::toResponseDTO)
            .collect(Collectors.toList());

        return orderMapper.toResponseDTO(savedOrder, orderItemDTOs);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
            .stream()
            .map(order -> {
                List<OrderItemResponseDTO> orderItems = orderItemRepository.findByOrder(order)
                    .stream()
                    .map(orderItemMapper::toResponseDTO)
                    .collect(Collectors.toList());
                return orderMapper.toResponseDTO(order, orderItems);
            })
            .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
            .stream()
            .map(order -> {
                List<OrderItemResponseDTO> orderItems = orderItemRepository.findByOrder(order)
                    .stream()
                    .map(orderItemMapper::toResponseDTO)
                    .collect(Collectors.toList());
                return orderMapper.toResponseDTO(order, orderItems);
            })
            .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        List<OrderItemResponseDTO> orderItems = orderItemRepository.findByOrder(order)
            .stream()
            .map(orderItemMapper::toResponseDTO)
            .collect(Collectors.toList());

        return orderMapper.toResponseDTO(order, orderItems);
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setStatus(status);
        order.setUpdated_at(Date.valueOf(LocalDate.now()));

        Order updatedOrder = orderRepository.save(order);

        List<OrderItemResponseDTO> orderItems = orderItemRepository.findByOrder(updatedOrder)
            .stream()
            .map(orderItemMapper::toResponseDTO)
            .collect(Collectors.toList());

        return orderMapper.toResponseDTO(updatedOrder, orderItems);
    }

    public void cancelOrder(Long id) {
        updateOrderStatus(id, OrderStatus.CANCELLED);
    }

    private String generateTrackingCode() {
        return "VID-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

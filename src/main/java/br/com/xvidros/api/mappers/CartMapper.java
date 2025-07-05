package br.com.xvidros.api.mappers;

import br.com.xvidros.api.dtos.CartResponseDTO;
import br.com.xvidros.api.entities.Cart;
import br.com.xvidros.api.entities.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    public CartResponseDTO toResponseDTO(Cart cart, List<CartItem> cartItems) {
        Float totalPrice = cartItems.stream()
            .map(CartItem::getSubtotal)
            .reduce(0.0f, Float::sum);

        return new CartResponseDTO(
            cart.getId(),
            cart.getUser().getId(),
            cart.getUser().getName(),
            cartItems.stream()
                .map(cartItemMapper::toResponseDTO)
                .collect(Collectors.toList()),
            totalPrice
        );
    }
}


package br.com.xvidros.api.mappers;

import br.com.xvidros.api.dtos.CartItemCreateDTO;
import br.com.xvidros.api.dtos.CartItemResponseDTO;
import br.com.xvidros.api.entities.Cart;
import br.com.xvidros.api.entities.CartItem;
import br.com.xvidros.api.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    @Autowired
    private ProductMapper productMapper;

    public CartItem toEntity(CartItemCreateDTO dto, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(dto.quantity());
        cartItem.setSubtotal(product.getPrice() * dto.quantity());
        // Para simplificar, vamos usar o produto diretamente sem variação
        // cartItem.setProductVariation(productVariation);
        return cartItem;
    }

    public CartItemResponseDTO toResponseDTO(CartItem cartItem) {
        // Assumindo que temos acesso ao produto através da variação
        Product product = cartItem.getProductVariation() != null ? 
            cartItem.getProductVariation().getProduct() : null;
        
        return new CartItemResponseDTO(
            cartItem.getId(),
            cartItem.getQuantity(),
            cartItem.getSubtotal(),
            product != null ? productMapper.toResponseDTO(product) : null
        );
    }
}


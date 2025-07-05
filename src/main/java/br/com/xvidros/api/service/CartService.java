package br.com.xvidros.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.xvidros.api.dtos.CartItemCreateDTO;
import br.com.xvidros.api.dtos.CartResponseDTO;
import br.com.xvidros.api.entities.Cart;
import br.com.xvidros.api.entities.CartItem;
import br.com.xvidros.api.entities.Product;
import br.com.xvidros.api.entities.User;
import br.com.xvidros.api.mappers.CartMapper;
import br.com.xvidros.api.repository.CartRepository;
import br.com.xvidros.api.repository.CartItemRepository;
import br.com.xvidros.api.repository.ProductRepository;
import br.com.xvidros.api.repository.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartMapper cartMapper;

    public CartResponseDTO getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Cart cart = cartRepository.findByUser(user)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartMapper.toResponseDTO(cart, cartItems);
    }

    @Transactional
    public CartResponseDTO addItemToCart(Long userId, CartItemCreateDTO cartItemCreateDTO) {
        Cart cart = getOrCreateCartEntity(userId);
        Product product = productRepository.findById(cartItemCreateDTO.productId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                Optional<CartItem> existingItem = cartItemRepository.findByCart(cart)
            .stream()
            .filter(item -> item.getProductVariation() != null && 
                           item.getProductVariation().getProduct().getId() == product.getId())
            .findFirst();

        CartItem cartItem;
        if (existingItem.isPresent()) {
                        cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemCreateDTO.quantity());
            cartItem.setSubtotal(cartItem.getQuantity() * product.getPrice());
        } else {
                        cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(cartItemCreateDTO.quantity());
            cartItem.setSubtotal(product.getPrice() * cartItemCreateDTO.quantity());
            
        }

        cartItemRepository.save(cartItem);
        
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartMapper.toResponseDTO(cart, cartItems);
    }

    @Transactional
    public CartResponseDTO updateItemQuantity(Long userId, Long itemId, Integer quantity) {
        Cart cart = getOrCreateCartEntity(userId);
        CartItem cartItem = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado"));

        if (cartItem.getCart().getId() != cart.getId()) {
            throw new RuntimeException("Item não pertence ao carrinho do usuário");
        }
        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
                        if (cartItem.getProductVariation() != null) {
                Float productPrice = cartItem.getProductVariation().getProduct().getPrice();
                cartItem.setSubtotal(productPrice * quantity);
            }
            cartItemRepository.save(cartItem);
        }

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartMapper.toResponseDTO(cart, cartItems);
    }

    @Transactional
    public CartResponseDTO removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCartEntity(userId);
        CartItem cartItem = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado"));

        if (cartItem.getCart().getId() != cart.getId()) {
            throw new RuntimeException("Item não pertence ao carrinho do usuário");
        }

        cartItemRepository.delete(cartItem);

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartMapper.toResponseDTO(cart, cartItems);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCartEntity(userId);
        cartItemRepository.deleteByCart(cart);
    }

    private Cart getOrCreateCartEntity(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return cartRepository.findByUser(user)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });
    }
}


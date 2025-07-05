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
import br.com.xvidros.api.entities.ProductVariation;
import br.com.xvidros.api.entities.User;
import br.com.xvidros.api.mappers.CartMapper;
import br.com.xvidros.api.repository.CartRepository;
import br.com.xvidros.api.repository.CartItemRepository;
import br.com.xvidros.api.repository.ProductRepository;
import br.com.xvidros.api.repository.ProductVariationRepository;
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

    @Autowired
    private ProductVariationRepository productVariationRepository; // Dependência adicionada

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

        // Lógica corrigida para encontrar a variação do produto.
        // O ideal seria o DTO especificar qual variação, mas aqui pegamos a primeira.
        ProductVariation productVariation = productVariationRepository.findByProduct(product).stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Variação do produto não encontrada para o produto ID: " + product.getId()));

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProductVariation(cart, productVariation);

        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            // Se o item já existe, atualiza a quantidade e o subtotal
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemCreateDTO.quantity());
            cartItem.setSubtotal(productVariation.getPrice() * cartItem.getQuantity());
        } else {
            // Se for um item novo, cria e define todos os campos
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductVariation(productVariation); // Campo importante que faltava
            cartItem.setQuantity(cartItemCreateDTO.quantity());
            cartItem.setSubtotal(productVariation.getPrice() * cartItemCreateDTO.quantity());
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
                Float productPrice = cartItem.getProductVariation().getPrice();
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
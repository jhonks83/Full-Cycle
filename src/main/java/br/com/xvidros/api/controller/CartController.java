package br.com.xvidros.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.xvidros.api.dtos.CartItemCreateDTO;
import br.com.xvidros.api.dtos.CartResponseDTO;
import br.com.xvidros.api.service.CartService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long userId) {
        CartResponseDTO cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartResponseDTO> addItemToCart(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemCreateDTO cartItemCreateDTO) {
        CartResponseDTO cart = cartService.addItemToCart(userId, cartItemCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/user/{userId}/items/{itemId}")
    public ResponseEntity<CartResponseDTO> updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        CartResponseDTO cart = cartService.updateItemQuantity(userId, itemId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}/items/{itemId}")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        CartResponseDTO cart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}


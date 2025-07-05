package br.com.xvidros.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xvidros.api.entities.Cart;
import br.com.xvidros.api.entities.CartItem;
import br.com.xvidros.api.entities.ProductVariation;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);
    
    List<CartItem> findByCartId(Long cartId);
    
    Optional<CartItem> findByCartAndProductVariation(Cart cart, ProductVariation productVariation);
    
    void deleteByCart(Cart cart);
}


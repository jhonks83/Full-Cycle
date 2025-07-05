package br.com.xvidros.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xvidros.api.entities.Cart;
import br.com.xvidros.api.entities.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
    
    Optional<Cart> findByUserId(Long userId);
}


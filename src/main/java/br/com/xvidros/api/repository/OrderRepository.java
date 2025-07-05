package br.com.xvidros.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xvidros.api.entities.Order;
import br.com.xvidros.api.entities.User;
import br.com.xvidros.api.enuns.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUser(User user);
    
    List<Order> findByUserId(Long userId);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
}


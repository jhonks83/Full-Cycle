package br.com.xvidros.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xvidros.api.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}

package br.com.xvidros.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xvidros.api.entities.Product;
import br.com.xvidros.api.entities.ProductVariation;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    List<ProductVariation> findByProduct(Product product);
}
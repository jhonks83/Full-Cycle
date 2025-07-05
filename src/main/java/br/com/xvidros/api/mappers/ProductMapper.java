package br.com.xvidros.api.mappers;

import br.com.xvidros.api.dtos.ProductCreateDTO;
import br.com.xvidros.api.dtos.ProductResponseDTO;
import br.com.xvidros.api.entities.Product;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCategory(dto.category());
        product.setBrand(dto.brand());
        product.setImageUrl(dto.imageUrl());
        product.setRating(0.0f);
        product.setCreated_at(Date.valueOf(LocalDate.now()));
        product.setUpdated_at(Date.valueOf(LocalDate.now()));
        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getCategory(),
            product.getBrand(),
            product.getImageUrl(),
            product.getRating(),
            product.getCreated_at(),
            product.getUpdated_at()
        );
    }

    public void updateEntity(Product product, ProductCreateDTO dto) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCategory(dto.category());
        product.setBrand(dto.brand());
        product.setImageUrl(dto.imageUrl());
        product.setUpdated_at(Date.valueOf(LocalDate.now()));
    }
}


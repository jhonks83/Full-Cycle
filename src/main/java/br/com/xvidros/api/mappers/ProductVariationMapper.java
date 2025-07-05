package br.com.xvidros.api.mappers;

import org.springframework.stereotype.Component;
import br.com.xvidros.api.dtos.ProductVariationResponseDTO;
import br.com.xvidros.api.entities.ProductVariation;

@Component
public class ProductVariationMapper {

    public ProductVariationResponseDTO toResponseDTO(ProductVariation productVariation) {
        if (productVariation == null) {
            return null;
        }
        return new ProductVariationResponseDTO(
            productVariation.getId(),
            productVariation.getProduct().getId(),
            productVariation.getSize(),
            productVariation.getColor(),
            productVariation.getPrice(),
            productVariation.getQuantity(),
            productVariation.getSku()
        );
    }
}
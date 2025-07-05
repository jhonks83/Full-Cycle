package br.com.xvidros.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.xvidros.api.dtos.ProductVariationCreateDTO;
import br.com.xvidros.api.dtos.ProductVariationResponseDTO;
import br.com.xvidros.api.dtos.ProductVariationUpdateDTO;
import br.com.xvidros.api.entities.Product;
import br.com.xvidros.api.entities.ProductVariation;
import br.com.xvidros.api.mappers.ProductVariationMapper;
import br.com.xvidros.api.repository.ProductRepository;
import br.com.xvidros.api.repository.ProductVariationRepository;

@Service
public class ProductVariationService {

    @Autowired
    private ProductVariationRepository variationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationMapper variationMapper;

    @Transactional
    public ProductVariationResponseDTO createVariation(ProductVariationCreateDTO createDTO) {
        Product product = productRepository.findById(createDTO.productId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + createDTO.productId()));

        ProductVariation variation = new ProductVariation();
        variation.setProduct(product);
        variation.setSize(createDTO.size());
        variation.setColor(createDTO.color());
        variation.setPrice(createDTO.price());
        variation.setQuantity(createDTO.quantity());
        variation.setSku(createDTO.sku());

        ProductVariation savedVariation = variationRepository.save(variation);
        return variationMapper.toResponseDTO(savedVariation);
    }

    public ProductVariationResponseDTO findVariationById(Long id) {
        ProductVariation variation = variationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Variação de produto não encontrada com o ID: " + id));
        return variationMapper.toResponseDTO(variation);
    }

    public List<ProductVariationResponseDTO> findVariationsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + productId));

        return variationRepository.findByProduct(product).stream()
            .map(variationMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProductVariationResponseDTO updateVariation(Long id, ProductVariationUpdateDTO updateDTO) {
        ProductVariation variation = variationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Variação de produto não encontrada com o ID: " + id));

        if (updateDTO.size() != null) {
            variation.setSize(updateDTO.size());
        }
        if (updateDTO.color() != null) {
            variation.setColor(updateDTO.color());
        }
        if (updateDTO.price() != null) {
            variation.setPrice(updateDTO.price());
        }
        if (updateDTO.quantity() != null) {
            variation.setQuantity(updateDTO.quantity());
        }
        if (updateDTO.sku() != null) {
            variation.setSku(updateDTO.sku());
        }

        ProductVariation updatedVariation = variationRepository.save(variation);
        return variationMapper.toResponseDTO(updatedVariation);
    }

    @Transactional
    public void deleteVariation(Long id) {
        if (!variationRepository.existsById(id)) {
            throw new RuntimeException("Variação de produto não encontrada com o ID: " + id);
        }
        variationRepository.deleteById(id);
    }
}
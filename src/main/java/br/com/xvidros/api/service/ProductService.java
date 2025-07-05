package br.com.xvidros.api.service;

import br.com.xvidros.api.dtos.ProductCreateDTO;
import br.com.xvidros.api.dtos.ProductResponseDTO;
import br.com.xvidros.api.entities.Product;
import br.com.xvidros.api.mappers.ProductMapper;
import br.com.xvidros.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(productMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id)
            .map(productMapper::toResponseDTO);
    }

    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO) {
        Product product = productMapper.toEntity(productCreateDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    public Optional<ProductResponseDTO> updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        return productRepository.findById(id)
            .map(product -> {
                productMapper.updateEntity(product, productCreateDTO);
                Product updatedProduct = productRepository.save(product);
                return productMapper.toResponseDTO(updatedProduct);
            });
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
            .stream()
            .map(productMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
            .stream()
            .map(productMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name)
            .stream()
            .map(productMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getProductsByPriceRange(Float minPrice, Float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
            .stream()
            .map(productMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }

    public List<String> getAllBrands() {
        return productRepository.findAllBrands();
    }
}


package br.com.xvidros.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.xvidros.api.dtos.ProductVariationCreateDTO;
import br.com.xvidros.api.dtos.ProductVariationResponseDTO;
import br.com.xvidros.api.dtos.ProductVariationUpdateDTO;
import br.com.xvidros.api.service.ProductVariationService;

@RestController
@RequestMapping("/api/variations")
public class ProductVariationController {

    @Autowired
    private ProductVariationService variationService;

    @PostMapping
    public ResponseEntity<ProductVariationResponseDTO> createVariation(@RequestBody ProductVariationCreateDTO createDTO) {
        ProductVariationResponseDTO newVariation = variationService.createVariation(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(newVariation.id()).toUri();
        return ResponseEntity.created(uri).body(newVariation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariationResponseDTO> getVariationById(@PathVariable Long id) {
        return ResponseEntity.ok(variationService.findVariationById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductVariationResponseDTO>> getVariationsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(variationService.findVariationsByProductId(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductVariationResponseDTO> updateVariation(@PathVariable Long id, @RequestBody ProductVariationUpdateDTO updateDTO) {
        return ResponseEntity.ok(variationService.updateVariation(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariation(@PathVariable Long id) {
        variationService.deleteVariation(id);
        return ResponseEntity.noContent().build();
    }
}
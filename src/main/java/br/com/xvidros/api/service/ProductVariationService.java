package br.com.xvidros.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xvidros.api.entities.Product;
import br.com.xvidros.api.repository.ProductRepository;

@Service
public class ProductVariationService{
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * Save new product
	 * @param product
	 */
	public Product store(Product product)
	{
		return productRepository.save(product);
	}
	
	public List<Product> getAll()
	{
		return productRepository.findAll();
	}

}
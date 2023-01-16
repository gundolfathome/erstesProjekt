package dev.enterprise.product.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.enterprise.product.application.entity.Product;
import dev.enterprise.product.application.repository.ProductRepository;
import dev.enterprise.product.application.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
    public Optional<Product> getProduct(Long productId) {
	   log.info("Finding details of product, id {}", productId);
	   return Optional.ofNullable(productRepository.findById(productId).get());
    }
  
	@Override
	public List<Product> getAllProducts() {
		log.info("Finding all products");
		return (List<Product>)productRepository.findAll();
	}
  
	@Override
	public Product save(Product product) {
		log.info("Saving product {}", product);
		return productRepository.save(product);
	}

}

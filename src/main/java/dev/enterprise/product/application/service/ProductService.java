package dev.enterprise.product.application.service;

import java.util.List;
import java.util.Optional;

import dev.enterprise.product.application.entity.Product;

public interface ProductService {

    Optional<Product> getProduct(Long productId);

    List<Product> getAllProducts();
    
    Product save(Product product);
    
}

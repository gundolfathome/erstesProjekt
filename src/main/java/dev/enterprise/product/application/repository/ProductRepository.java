package dev.enterprise.product.application.repository;

import org.springframework.data.repository.CrudRepository;

import dev.enterprise.product.application.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
    
}

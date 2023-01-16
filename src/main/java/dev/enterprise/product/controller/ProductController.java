package dev.enterprise.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.enterprise.product.application.entity.Product;
import dev.enterprise.product.application.error.RecordNotFoundException;
import dev.enterprise.product.application.service.ProductService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class ProductController {

	@Autowired private ProductService productService;

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
		log.info("Fetching product {}", productId);
		var productOptional = productService.getProduct(productId);
		Product product = productOptional.orElseThrow(RecordNotFoundException::new);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
  
	@GetMapping("/products/")
	public ResponseEntity<?> getProducts(){
		log.info("Retrieving all products");
		List<Product> entries =  productService.getAllProducts();
		return ResponseEntity.ok(entries);
	}

	@PostMapping("/products/")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
	log.info("Saving product");
	var savedProduct = productService.save(product);
	return new ResponseEntity<>(savedProduct, HttpStatus.OK);
	}

}

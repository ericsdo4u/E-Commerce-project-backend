package com.ericsdo_e_commerce.ericsdo_ecommerce.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductRequestDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductResponseDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.InsufficientStockException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.ResourceNotFoundException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Product;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.ProductRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
  
    @Autowired
    private ProductService productService;
    @Autowired
    private final ProductRepository productRepository;

    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO dto) {
        return productService.createProduct(dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

     @PutMapping("/{id}/increase-stock")
    public ResponseEntity<Product> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setQuantityInStock(product.getQuantityInStock() + quantity);
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/{id}/decrease-stock")
    public ResponseEntity<Product> decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getQuantityInStock() < quantity) {
            throw new InsufficientStockException("Not enough stock to remove");
        }
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
        return ResponseEntity.ok(productRepository.save(product));
    }

}

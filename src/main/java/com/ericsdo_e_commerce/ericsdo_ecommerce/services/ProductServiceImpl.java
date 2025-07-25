package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductRequestDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductResponseDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.InvalidRequestException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.ProductNotFoundException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Product;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private ProductResponseDTO mapToResponse(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getQuantityInStock());
        return dto;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        validateRequest(dto);

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getStock());

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO getProduct(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        validateRequest(dto);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getStock());

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    private void validateRequest(ProductRequestDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidRequestException("Product name is required");
        }

        if (dto.getPrice() == null || dto.getPrice().doubleValue() < 0) {
            throw new InvalidRequestException("Product price must be a positive number");
        }

        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new InvalidRequestException("Stock quantity must be zero or more");
        }
    }
}

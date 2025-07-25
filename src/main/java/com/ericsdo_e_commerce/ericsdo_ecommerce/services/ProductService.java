package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductRequestDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductResponseDTO;

@Service
public interface ProductService {
  
  ProductResponseDTO createProduct(ProductRequestDTO dto);
  ProductResponseDTO getProduct(Long id);
  List<ProductResponseDTO> getAllProducts();
  ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);
  void deleteProduct(Long id);
}

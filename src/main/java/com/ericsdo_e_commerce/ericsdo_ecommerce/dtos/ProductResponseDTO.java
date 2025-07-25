package com.ericsdo_e_commerce.ericsdo_ecommerce.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
// @Builder
public class ProductResponseDTO {
  
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
}

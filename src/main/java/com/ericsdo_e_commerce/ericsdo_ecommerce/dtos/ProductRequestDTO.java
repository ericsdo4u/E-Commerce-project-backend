package com.ericsdo_e_commerce.ericsdo_ecommerce.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequestDTO {
  
   private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}

package com.ericsdo_e_commerce.ericsdo_ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
  
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    // private int stock;
    @Column(nullable = false)
    private Integer quantityInStock;


    public boolean hasSufficientStock(int requestedQuantity) {
        return this.quantityInStock >= requestedQuantity;
    }

    public void reduceStock(int quantity) {
        this.quantityInStock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.quantityInStock += quantity;
    }


}

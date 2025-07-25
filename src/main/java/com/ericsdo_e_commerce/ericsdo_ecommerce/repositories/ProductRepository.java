package com.ericsdo_e_commerce.ericsdo_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

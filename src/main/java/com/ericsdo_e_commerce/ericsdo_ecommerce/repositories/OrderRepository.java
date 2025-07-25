package com.ericsdo_e_commerce.ericsdo_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
  
}

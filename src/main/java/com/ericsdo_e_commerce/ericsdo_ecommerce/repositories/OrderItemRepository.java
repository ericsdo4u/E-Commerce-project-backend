package com.ericsdo_e_commerce.ericsdo_ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  List<OrderItem> findAllByOrderId(Long orderId);

  
}

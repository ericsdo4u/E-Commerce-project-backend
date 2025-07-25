package com.ericsdo_e_commerce.ericsdo_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  
}

package com.ericsdo_e_commerce.ericsdo_ecommerce.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
  
  private Long id;
  private Long orderId;
  private BigDecimal amount;
  private PaymentStatus status;
  private LocalDateTime paymentDate;
  private String paymentMethod;
}

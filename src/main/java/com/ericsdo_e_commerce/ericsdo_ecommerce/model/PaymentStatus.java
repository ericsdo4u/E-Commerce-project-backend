package com.ericsdo_e_commerce.ericsdo_ecommerce.model;

import lombok.ToString;

@ToString
public enum PaymentStatus {
  
  PENDING,
  COMPLETED,
  FAILED,
  CANCELLED
}

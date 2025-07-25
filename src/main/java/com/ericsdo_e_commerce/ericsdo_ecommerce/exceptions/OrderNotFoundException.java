package com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions;

public class OrderNotFoundException extends BaseException {

   public OrderNotFoundException(Long id) {
        super("Order with ID " + id + " not found.");
  }
  
}

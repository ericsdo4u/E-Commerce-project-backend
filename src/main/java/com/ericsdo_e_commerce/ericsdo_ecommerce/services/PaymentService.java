package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.PaymentRequest;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.PaymentResponse;

public interface PaymentService {
  
  PaymentResponse makePayment(PaymentRequest request);
  PaymentResponse getPaymentById(Long id);
  List<PaymentResponse> getAllPayments();
  PaymentResponse updatePayment(Long id, PaymentRequest request);
  void deletePayment(Long id);
}

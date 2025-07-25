package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.PaymentRequest;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.PaymentResponse;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.InvalidRequestException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.ResourceNotFoundException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Payment;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.PaymentStatus;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    private Payment mapToEntity(PaymentRequest dto) {
        return Payment.builder()
                .orderId(dto.getOrderId())
                .amount(dto.getAmount())
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .build();
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {
        if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {
            throw new InvalidRequestException("Payment amount must be greater than zero.");
        }

        boolean orderExists = orderRepository.existsById(request.getOrderId());
        if (!orderExists) {
            throw new ResourceNotFoundException("Order not found with ID: " + request.getOrderId());
        }

        Payment saved = paymentRepository.save(mapToEntity(request));
        return mapToResponse(saved);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest dto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        if (dto.getAmount() == null || dto.getAmount().doubleValue() <= 0) {
            throw new InvalidRequestException("Updated payment amount must be greater than zero.");
        }

        existing.setAmount(dto.getAmount());

        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        return mapToResponse(paymentRepository.save(existing));
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }
}

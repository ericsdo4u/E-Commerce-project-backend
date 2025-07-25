package com.ericsdo_e_commerce.ericsdo_ecommerce.paymentTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.PaymentRequest;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.PaymentResponse;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Order;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.PaymentStatus;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.PaymentRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.PaymentServiceImpl;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PaymentServiceImplTest {
  
  @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentServiceImpl(paymentRepository, orderRepository);
    }

    @Test
    public void testCreateAndFetchPayment() {
        Order order = Order.builder()
            .userId(1L)
            .orderDate(LocalDateTime.now())
            .totalAmount(BigDecimal.valueOf(100))
            .paymentStatus(PaymentStatus.PENDING)
            .build();
        Order savedOrder = orderRepository.save(order);

        PaymentRequest request = new PaymentRequest();
        request.setOrderId(savedOrder.getId());
        request.setAmount(BigDecimal.valueOf(100));
        request.setPaymentMethod("CARD");

        PaymentResponse response = paymentService.makePayment(request);

        assertNotNull(response.getId());
        assertEquals(PaymentStatus.PENDING, response.getStatus());
    }

}

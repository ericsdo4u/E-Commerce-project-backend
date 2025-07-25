package com.ericsdo_e_commerce.ericsdo_ecommerce.orderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.OrderService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceImplTest {
  
  
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private OrderRequestDto sampleRequest;

    @BeforeEach
    public void setUp() {
        sampleRequest = OrderRequestDto.builder()
                .userId(100L)
                .orderDate(LocalDateTime.now())
                .status("PENDING")
                .totalAmount(new BigDecimal("1299.99"))
                .build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrder() {
        OrderResponseDto response = orderService.createOrder(sampleRequest);
        assertNotNull(response.getId());
        assertEquals(sampleRequest.getUserId(), response.getUserId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testGetAllOrders() {
        orderService.createOrder(sampleRequest);
        List<OrderResponseDto> orders = orderService.getAllOrders();
        assertFalse(orders.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testGetOrderById() {
        OrderResponseDto created = orderService.createOrder(sampleRequest);
        OrderResponseDto found = orderService.getOrderById(created.getId());
        assertEquals(created.getId(), found.getId());
        assertEquals("PENDING", found.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void testUpdateOrder() {
        OrderResponseDto created = orderService.createOrder(sampleRequest);
        OrderRequestDto updateRequest = OrderRequestDto.builder()
                .userId(200L)
                .orderDate(LocalDateTime.now())
                .status("SHIPPED")
                .totalAmount(new BigDecimal("1500.00"))
                .build();

        OrderResponseDto updated = orderService.updateOrder(created.getId(), updateRequest);
        assertEquals("SHIPPED", updated.getStatus());
        assertEquals(new BigDecimal("1500.00"), updated.getTotalAmount());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    public void testDeleteOrder() {
        OrderResponseDto created = orderService.createOrder(sampleRequest);
        Long id = created.getId();
        orderService.deleteOrder(id);
        assertFalse(orderRepository.findById(id).isPresent());
    }
  }

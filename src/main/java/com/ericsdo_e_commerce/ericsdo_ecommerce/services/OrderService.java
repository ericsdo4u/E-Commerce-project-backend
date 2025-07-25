package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderResponseDto;

public interface OrderService {

  
  OrderResponseDto createOrder(OrderRequestDto orderRequest);
  List<OrderResponseDto> getAllOrders();
  OrderResponseDto getOrderById(Long id);
  OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequest);
  void deleteOrder(Long id);
}

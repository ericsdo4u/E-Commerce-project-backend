package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemResponseDto;

public interface OrderItemService {
  
    OrderItemResponseDto createOrderItem(OrderItemRequestDto request);
    OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto request);
    void deleteOrderItem(Long id);
    OrderItemResponseDto getOrderItemById(Long id);
    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);
}

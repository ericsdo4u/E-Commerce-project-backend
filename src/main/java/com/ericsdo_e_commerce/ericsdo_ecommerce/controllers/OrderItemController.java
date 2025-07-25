package com.ericsdo_e_commerce.ericsdo_ecommerce.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.OrderItemService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
  

  private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemResponseDto> createOrderItem(
      @RequestBody OrderItemRequestDto request) {
        return ResponseEntity.ok(orderItemService.createOrderItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @PathVariable Long id,
            @RequestBody OrderItemRequestDto request) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDto> getOrderItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<OrderItemResponseDto>> getItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByOrderId(orderId));
    }

}

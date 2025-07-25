package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.InsufficientStockException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.ResourceNotFoundException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Order;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.OrderItem;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Product;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderItemRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    private OrderItemResponseDto mapToResponse(OrderItem item) {
        return OrderItemResponseDto.builder()
                .id(item.getId())
                .orderId(item.getOrder().getId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }

    private OrderItem mapToEntity(OrderItemRequestDto dto) {
        return OrderItem.builder()
                .order(Order.builder().id(dto.getOrderId()).build())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));

        if (product.getQuantityInStock() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        product.setQuantityInStock(product.getQuantityInStock() - request.getQuantity());
        productRepository.save(product);

        OrderItem item = mapToEntity(request);
        return mapToResponse(orderItemRepository.save(item));
    }

    @Override
    public OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto request) {
        OrderItem existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with ID: " + id));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));

        if (product.getQuantityInStock() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        existing.setProductId(request.getProductId());
        existing.setQuantity(request.getQuantity());
        existing.setPrice(request.getPrice());
        return mapToResponse(orderItemRepository.save(existing));
    }

    @Override
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order item not found with ID: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with ID: " + id));
        return mapToResponse(item);
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}

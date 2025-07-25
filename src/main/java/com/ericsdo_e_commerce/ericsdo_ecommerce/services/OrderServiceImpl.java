package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderItemResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.OrderResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.exceptions.OrderNotFoundException;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Order;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.OrderItem;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  
    private final OrderRepository orderRepository;

    private OrderResponseDto mapToResponse(Order order) {
    return OrderResponseDto.builder()
            .id(order.getId())
            .userId(order.getUserId())
            .orderDate(order.getOrderDate())
            .status(order.getStatus())
            .totalAmount(order.getTotalAmount())
            .orderItems(order.getOrderItems() != null
                    ? order.getOrderItems().stream().map(this::mapToItemResponse).toList()
                    : null)
            .build();
}

    private Order mapToEntity(OrderRequestDto dto) {
        Order order = Order.builder()
            .userId(dto.getUserId())
            .orderDate(dto.getOrderDate())
            .status(dto.getStatus())
            .totalAmount(dto.getTotalAmount())
            .build();

        if (dto.getOrderItems() != null) {
            List<OrderItem> items = dto.getOrderItems().stream()
                .map(itemDto -> mapToItemEntity(itemDto, order))
                .collect(Collectors.toList());
            order.setOrderItems(items);
    }

        return order;
}
    private OrderItem mapToItemEntity(OrderItemRequestDto dto, Order order) {
        return OrderItem.builder()
            .productId(dto.getProductId())
            .quantity(dto.getQuantity())
            .price(dto.getPrice())
            .order(order)
            .build();
}


    private OrderItemResponseDto mapToItemResponse(OrderItem item) {
        return OrderItemResponseDto.builder()
            .id(item.getId())
            .productId(item.getProductId())
            .quantity(item.getQuantity())
            .price(item.getPrice())
            .build();
}

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        Order saved = orderRepository.save(mapToEntity(orderRequest));
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));
        return mapToResponse(order);
}

    @Override
    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
        Order existing = orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));

        existing.setStatus(dto.getStatus());
        existing.setTotalAmount(dto.getTotalAmount());
        existing.setOrderDate(dto.getOrderDate());
        existing.setUserId(dto.getUserId());

        return mapToResponse(orderRepository.save(existing));
}

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
        throw new OrderNotFoundException(id);
    }
        orderRepository.deleteById(id);
}

}

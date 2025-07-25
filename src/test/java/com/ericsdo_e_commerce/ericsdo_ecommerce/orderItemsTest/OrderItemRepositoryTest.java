package com.ericsdo_e_commerce.ericsdo_ecommerce.orderItemsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Order;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.OrderItem;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Product;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderItemRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.OrderRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.ProductRepository;

// import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderItemRepositoryTest {
  
    // @Autowired
    // private OrderItemRepository orderItemRepository;

    // @Autowired
    // private OrderRepository orderRepository;

    // private Order order;

    // @BeforeEach
    // public void setup() {
    //     order = Order.builder()
    //             .userId(1L)
    //             .orderDate(LocalDateTime.now())
    //             .status("PENDING")
    //             .totalAmount(BigDecimal.valueOf(200.00))
    //             .build();

    //     order = orderRepository.save(order);
    // }

    // @Test
    // @Transactional
    // public void testSaveAndFindOrderItem() {
    //     OrderItem item = OrderItem.builder()
    //             .productId(101L)
    //             .quantity(2)
    //             .price(BigDecimal.valueOf(50.00))
    //             .order(order)
    //             .build();

    //     orderItemRepository.save(item);

    //     List<OrderItem> items = orderItemRepository.findAll();
    //     assertEquals(1, items.size());

    //     OrderItem savedItem = items.get(0);
    //     assertEquals(101L, savedItem.getProductId());
    //     assertEquals(2, savedItem.getQuantity());
    //     assertEquals(BigDecimal.valueOf(50.00), savedItem.getPrice());
    //     assertEquals(order.getId(), savedItem.getOrder().getId());
    // }


    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindAllByOrderId() {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalAmount(BigDecimal.valueOf(100));
        order = orderRepository.save(order);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(20));
        product.setQuantityInStock(10);
        product = productRepository.save(product);

        OrderItem item1 = new OrderItem();
        item1.setOrder(order);
        item1.setProductId(product.getId());
        item1.setPrice(product.getPrice());
        item1.setQuantity(2);
        orderItemRepository.save(item1);

        OrderItem item2 = new OrderItem();
        item2.setOrder(order);
        item2.setProductId(product.getId());
        item2.setPrice(product.getPrice());
        item2.setQuantity(1);
        orderItemRepository.save(item2);

        List<OrderItem> items = orderItemRepository.findAllByOrderId(order.getId());

        assertEquals(2, items.size());
    }

}
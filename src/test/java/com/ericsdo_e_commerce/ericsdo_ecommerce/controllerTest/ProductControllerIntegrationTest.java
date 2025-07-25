package com.ericsdo_e_commerce.ericsdo_ecommerce.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.Product;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {
  
   @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testIncreaseStock() throws Exception {
        Product product = productRepository.save(Product.builder().name("TV").quantityInStock(10).build());

        mockMvc.perform(put("/api/products/" + product.getId() + "/increase-stock")
                .param("quantity", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityInStock").value(15));
    }

    @Test
    public void testDecreaseStockSuccess() throws Exception {
        Product product = productRepository.save(Product.builder().name("Fridge").quantityInStock(8).build());

        mockMvc.perform(put("/api/products/" + product.getId() + "/decrease-stock")
                .param("quantity", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityInStock").value(5));
    }

    @Test
    public void testDecreaseStockFailureDueToInsufficientStock() throws Exception {
        Product product = productRepository.save(Product.builder().name("Microwave").quantityInStock(2).build());

        mockMvc.perform(put("/api/products/" + product.getId() + "/decrease-stock")
                .param("quantity", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

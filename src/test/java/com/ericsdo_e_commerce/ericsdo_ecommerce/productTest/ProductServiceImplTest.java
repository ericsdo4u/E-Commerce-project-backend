package com.ericsdo_e_commerce.ericsdo_ecommerce.productTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductRequestDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.ProductResponseDTO;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.ProductRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.ProductServiceImpl;


@SpringBootTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceImplTest {
  @Autowired
  private ProductServiceImpl productService;

  @Autowired
  private ProductRepository productRepository;

  private ProductRequestDTO sampleRequest;

  @BeforeEach
  public void setUp() {
    productRepository.deleteAll(); 
    sampleRequest = new ProductRequestDTO();
    sampleRequest.setName("Test Product");
    sampleRequest.setDescription("Sample product description");
    sampleRequest.setPrice(new BigDecimal("49.99"));
    sampleRequest.setStock(30);
  }

  @Test
  public void testCreateProduct() {
    ProductResponseDTO response = productService.createProduct(sampleRequest);

    assertNotNull(response.getId());
    assertEquals("Test Product", response.getName());
    assertEquals("Sample product description", response.getDescription());
    assertEquals(new BigDecimal("49.99"), response.getPrice());
    assertEquals(30, response.getStock());
    }

    @Test
    public void testGetProduct() {
        ProductResponseDTO created = productService.createProduct(sampleRequest);
        ProductResponseDTO fetched = productService.getProduct(created.getId());

        assertEquals(created.getId(), fetched.getId());
    }

    @Test
    public void testGetAllProducts() {
        productService.createProduct(sampleRequest);
        productService.createProduct(sampleRequest);

        List<ProductResponseDTO> all = productService.getAllProducts();
        assertEquals(2, all.size());
    }

    @Test
    public void testUpdateProduct() {
        ProductResponseDTO created = productService.createProduct(sampleRequest);

        ProductRequestDTO updated = new ProductRequestDTO();
        updated.setName("Updated Product");
        updated.setDescription("Updated Description");
        updated.setPrice(new BigDecimal("79.99"));
        updated.setStock(10);

        ProductResponseDTO response = productService.updateProduct(created.getId(), updated);

        assertEquals("Updated Product", response.getName());
        assertEquals("Updated Description", response.getDescription());
        assertEquals(new BigDecimal("79.99"), response.getPrice());
        assertEquals(10, response.getStock());
    }

    @Test
    void testDeleteProduct() {
        ProductResponseDTO created = productService.createProduct(sampleRequest);

        productService.deleteProduct(created.getId());

        assertThrows(RuntimeException.class, () -> productService.getProduct(created.getId()));
    }
}

package com.handmade.service;

import com.handmade.model.Product;
import com.handmade.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceUnitTests {

    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();

        product = Product.builder()
                .slug("service-slug")
                .name("Service Product")
                .description("desc")
                .price(20.0)
                .stockQuantity(7)
                .category("cat")
                .build();

        product = productRepository.save(product);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    @Order(1)
    public void saveProductTest() {
        Product newP = Product.builder()
                .slug("new-slug")
                .name("New")
                .description("desc")
                .price(33.0)
                .stockQuantity(2)
                .category("cat2")
                .build();

        Product saved = productService.saveProduct(newP);
        Assertions.assertThat(saved.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void getByIdTest() {
        Optional<Product> opt = productService.getProductById(product.getId());
        Assertions.assertThat(opt).isPresent();
    }

    @Test
    @Order(3)
    public void updateTest() {
        Product update = Product.builder()
                .name("Updated Name")
                .price(44.0)
                .build();

        Product updated = productService.updateProduct(product.getId(), update);
        Assertions.assertThat(updated.getName()).isEqualTo("Updated Name");
        Assertions.assertThat(updated.getPrice()).isEqualTo(44.0);
    }

    @Test
    @Order(4)
    public void deleteProductTest() {
        productService.deleteProduct(product.getId());
        Optional<Product> opt = productRepository.findById(product.getId());
        Assertions.assertThat(opt).isEmpty();
    }
}

package com.handmade.repository;

import com.handmade.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Используеться для тестирования JPA репозиториев (Загружает минимальный контекст Spring, связанный с БД)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveProduct() {
        // Given
        Product product = new Product();
        product.setSlug("test-scarf");
        product.setName("Test Scarf");
        product.setDescription("Test description");
        product.setPrice(15.99);
        product.setCategory("accessories");
        product.setStockQuantity(5);

        // When
        Product savedProduct = productRepository.save(product);

        // Then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getSlug()).isEqualTo("test-scarf");
        assertThat(savedProduct.getName()).isEqualTo("Test Scarf");
    }

    @Test
    void shouldFindProductBySlug() {
        // Given
        Product product = new Product();
        product.setSlug("wool-hat");
        product.setName("Wool Hat");
        product.setPrice(19.99);
        product.setCategory("headwear");
        product.setStockQuantity(3);
        productRepository.save(product);

        // When
        Optional<Product> foundProduct = productRepository.findBySlug("wool-hat");

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Wool Hat");
        assertThat(foundProduct.get().getPrice()).isEqualTo(19.99);
    }

    @Test
    void shouldReturnEmptyWhenProductNotFoundBySlug() {
        // When
        Optional<Product> foundProduct = productRepository.findBySlug("non-existent");

        // Then
        assertThat(foundProduct).isEmpty();
    }

    @Test
    void shouldCheckIfProductExistsBySlug() {
        // Given
        Product product = new Product();
        product.setSlug("existing-product");
        product.setName("Existing Product");
        product.setPrice(10.0);
        product.setCategory("test");
        product.setStockQuantity(1);
        productRepository.save(product);

        // When & Then
        assertThat(productRepository.existsBySlug("existing-product")).isTrue();
        assertThat(productRepository.existsBySlug("non-existing")).isFalse();
    }

    @Test
    void shouldFindAllProducts() {
        // Given
        Product product1 = new Product();
        product1.setSlug("product-1");
        product1.setName("Product 1");
        product1.setPrice(10.0);
        product1.setCategory("test");
        product1.setStockQuantity(1);

        Product product2 = new Product();
        product2.setSlug("product-2");
        product2.setName("Product 2");
        product2.setPrice(20.0);
        product2.setCategory("test");
        product2.setStockQuantity(2);

        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> products = productRepository.findAll();

        // Then
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName)
                .containsExactlyInAnyOrder("Product 1", "Product 2");
    }
}
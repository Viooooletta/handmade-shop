package com.handmade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.dto.ProductCreateDTO;
import com.handmade.dto.ProductDTO;
import com.handmade.mapper.ProductMapper;
import com.handmade.model.Product;
import com.handmade.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    private Product testProduct;
    private ProductDTO testProductDTO;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setSlug("test-product");
        testProduct.setName("Test Product");
        testProduct.setPrice(19.99);

        testProductDTO = new ProductDTO();
        testProductDTO.setId(1L);
        testProductDTO.setSlug("test-product");
        testProductDTO.setName("Test Product");
        testProductDTO.setPrice(19.99);
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        // Given
        given(productRepository.findAll()).willReturn(Arrays.asList(testProduct));
        given(productMapper.toDTO(any(Product.class))).willReturn(testProductDTO);

        // When & Then
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        // Given
        given(productRepository.findById(1L)).willReturn(Optional.of(testProduct));
        given(productMapper.toDTO(any(Product.class))).willReturn(testProductDTO);

        // When & Then
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        // Given
        ProductCreateDTO createDTO = new ProductCreateDTO();
        createDTO.setSlug("new-product");
        createDTO.setName("New Product");
        createDTO.setPrice(25.99);

        given(productRepository.existsBySlug("new-product")).willReturn(false);
        given(productMapper.toEntity(any(ProductCreateDTO.class))).willReturn(testProduct);
        given(productRepository.save(any(Product.class))).willReturn(testProduct);
        given(productMapper.toDTO(any(Product.class))).willReturn(testProductDTO);

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }
}
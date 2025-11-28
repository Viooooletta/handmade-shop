package com.handmade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.model.Product;
import com.handmade.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class) // Используеться для тестирования Spring MVC контроллеров
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    public void setup() {
        product = Product.builder()
                .id(1L)
                .name("Handmade Vase")
                .description("Beautiful handmade ceramic vase")
                .price(45.99)
                .stockQuantity(10)
                .category("Home Decor")
                .slug("handmade-vase")
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Создание товара")
    public void createProductTest() throws Exception {
        given(productService.saveProduct(any(Product.class))).willReturn(product);

        ResultActions response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.category", is(product.getCategory())))
                .andExpect(jsonPath("$.price", is(45.99)));
    }

    @Test
    @Order(2)
    @DisplayName("Получение всех товаров")
    public void getAllProductsTest() throws Exception {
        Product product2 = Product.builder()
                .id(2L)
                .name("Wooden Bowl")
                .description("Handcrafted wooden bowl")
                .price(29.99)
                .stockQuantity(5)
                .category("Kitchen")
                .slug("wooden-bowl")
                .build();

        given(productService.getAllProducts()).willReturn(List.of(product, product2));

        ResultActions response = mockMvc.perform(get("/api/products"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Handmade Vase")))
                .andExpect(jsonPath("$[1].name", is("Wooden Bowl")));
    }

    @Test
    @Order(3)
    @DisplayName("Получение товара по ID")
    public void getProductByIdTest() throws Exception {
        given(productService.getProductById(1L)).willReturn(Optional.of(product));

        ResultActions response = mockMvc.perform(get("/api/products/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.category", is(product.getCategory())))
                .andExpect(jsonPath("$.price", is(45.99)));
    }

    @Test
    @Order(4)
    @DisplayName("Обновление товара")
    public void updateProductTest() throws Exception {
        Product updated = Product.builder()
                .id(1L)
                .name("Updated Vase")
                .description("Updated description")
                .price(55.99)
                .stockQuantity(8)
                .category("Home Decor")
                .slug("updated-vase")
                .build();

        // Важно: матчеры — т.к. объект, переданный в контроллере, другой по ссылке
        given(productService.updateProduct(eq(1L), any(Product.class))).willReturn(updated);

        ResultActions response = mockMvc.perform(put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Vase")))
                .andExpect(jsonPath("$.price", is(55.99)));
    }

    @Test
    @Order(5)
    @DisplayName("Удаление товара")
    public void deleteProductTest() throws Exception {
        willDoNothing().given(productService).deleteProduct(1L);

        ResultActions response = mockMvc.perform(delete("/api/products/{id}", 1L));
        response.andDo(print())
                .andExpect(status().isNoContent());
    }
}

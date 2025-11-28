package com.handmade.mapper;

import com.handmade.dto.ProductDTO;
import com.handmade.model.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    @Test
    void mapEntityToDto_allFields() {
        LocalDateTime now = LocalDateTime.of(2025, 11, 10, 12, 0);

        Product product = Product.builder()
                .id(123L)
                .slug("prod-123")
                .name("Test product")
                .description("Description text")
                .price(55.5)
                .category("HOME")
                .stockQuantity(42)
                .createdAt(now.minusDays(1))
                .updatedAt(now)
                .build();

        ProductDTO dto = mapper.toDto(product);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(123L);
        assertThat(dto.getSlug()).isEqualTo("prod-123");
        assertThat(dto.getName()).isEqualTo("Test product");
        assertThat(dto.getDescription()).isEqualTo("Description text");
        assertThat(dto.getPrice()).isEqualTo(55.5);
        assertThat(dto.getCategory()).isEqualTo("HOME");
        assertThat(dto.getStockQuantity()).isEqualTo(42);
        assertThat(dto.getCreatedAt()).isEqualTo(now.minusDays(1));
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void mapDtoToEntity_allFields() {
        LocalDateTime now = LocalDateTime.of(2025, 11, 10, 12, 0);

        ProductDTO dto = ProductDTO.builder()
                .id(555L)
                .slug("dto-555")
                .name("DTO product")
                .description("dto desc")
                .price(10.0)
                .category("ACCESSORIES")
                .stockQuantity(7)
                .createdAt(now.minusHours(2))
                .updatedAt(now)
                .build();

        Product entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(555L);
        assertThat(entity.getSlug()).isEqualTo("dto-555");
        assertThat(entity.getName()).isEqualTo("DTO product");
        assertThat(entity.getDescription()).isEqualTo("dto desc");
        assertThat(entity.getPrice()).isEqualTo(10.0);
        assertThat(entity.getCategory()).isEqualTo("ACCESSORIES");
        assertThat(entity.getStockQuantity()).isEqualTo(7);
        assertThat(entity.getCreatedAt()).isEqualTo(now.minusHours(2));
        assertThat(entity.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void mapEntityToDto_nullInput_returnsNull() {
        ProductDTO dto = mapper.toDto(null);
        assertThat(dto).isNull();
    }

    @Test
    void mapDtoToEntity_nullInput_returnsNull() {
        Product entity = mapper.toEntity(null);
        assertThat(entity).isNull();
    }
}

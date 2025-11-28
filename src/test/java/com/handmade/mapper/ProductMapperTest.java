package com.handmade.mapper;

import com.handmade.dto.ProductCreateDTO;
import com.handmade.dto.ProductUpdateDTO;
import com.handmade.model.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperCreateUpdateTest {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    @Test
    void createDto_toEntity() {
        ProductCreateDTO create = ProductCreateDTO.builder()
                .slug("create-slug")
                .name("Create")
                .description("desc")
                .price(12.0)
                .category("CAT")
                .stockQuantity(4)
                .build();

        Product entity = mapper.toEntity(create);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getSlug()).isEqualTo("create-slug");
        assertThat(entity.getPrice()).isEqualTo(12.0);
    }

    @Test
    void updateDto_updatesEntityFields() {
        Product existing = Product.builder()
                .id(1L)
                .slug("old")
                .name("Old")
                .description("old")
                .price(5.0)
                .category("OLD")
                .stockQuantity(1)
                .build();

        ProductUpdateDTO upd = new ProductUpdateDTO();
        upd.setName("NewName");
        upd.setPrice(99.0);
        // slug left null -> behavior depends on mapper null strategy

        mapper.updateFromDto(upd, existing);

        assertThat(existing.getName()).isEqualTo("NewName");
        assertThat(existing.getPrice()).isEqualTo(99.0);
    }
}

package com.handmade.mapper;

import com.handmade.dto.ProductCreateDTO;
import com.handmade.dto.ProductDTO;
import com.handmade.dto.ProductUpdateDTO;
import com.handmade.model.Product;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    Product toEntity(ProductCreateDTO dto);

    ProductDTO toDTO(Product product);

    void updateFromDTO(ProductUpdateDTO dto, @MappingTarget Product product);
}
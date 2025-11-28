package com.handmade.mapper;

import com.handmade.dto.ProductCreateDTO;
import com.handmade.dto.ProductDTO;
import com.handmade.dto.ProductUpdateDTO;
import com.handmade.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Entity -> DTO
    ProductDTO toDto(Product product);

    // DTO -> Entity
    Product toEntity(ProductDTO productDto);

    // Create DTO -> Entity
    Product toEntity(ProductCreateDTO productCreateDto);

    // Update existing entity from update DTO (will overwrite fields present in DTO)
    void updateFromDto(ProductUpdateDTO dto, @MappingTarget Product entity);

}

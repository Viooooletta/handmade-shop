package com.handmade.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private Long id;
    private String slug;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
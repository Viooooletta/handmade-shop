package com.handmade.dto;

import lombok.Data;

@Data
public class ProductUpdateDTO {
    private String slug;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
}
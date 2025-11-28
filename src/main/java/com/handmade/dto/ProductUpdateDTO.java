package com.handmade.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class ProductUpdateDTO {
    private JsonNullable<String> slug;
    private JsonNullable<String> name;
    private JsonNullable<String> description;
    private JsonNullable<Double> price;
    private JsonNullable<String> category;
    private JsonNullable<Integer> stockQuantity;
}
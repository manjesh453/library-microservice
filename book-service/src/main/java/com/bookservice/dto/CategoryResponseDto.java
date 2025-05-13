package com.bookservice.dto;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private String id;
    private String categoryName;
    private String categoryDescription;
    private String status;
}

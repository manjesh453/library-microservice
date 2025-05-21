package com.bookservice.dto;

import lombok.Data;

@Data
public class BookResponseDto {

    private String id;
    private String title;
    private String description;
    private String authorName;
    private String categoryId;
    private String imageName;
    private String status;
    private int numberOfBooks;
}

package com.bookservice.dto;

import lombok.Data;

@Data
public class BookRequestDto {
    private String title;
    private String description;
    private String authorName;
    private int numberOfBooks;
    private String categoryId;
}

package com.bookservice.service;

import com.bookservice.dto.CategoryRequestDto;
import com.bookservice.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    String addCategory(CategoryRequestDto category);

    String updateCategory(CategoryRequestDto category, String id);

    String deleteCategory(String id);

    String changeCategoryStatus(String id, String status);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategoryById(String id);

    List<CategoryResponseDto> getCategoryByStatus(String status);

}

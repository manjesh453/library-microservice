package com.bookservice.service.impl;

import com.bookservice.dto.CategoryRequestDto;
import com.bookservice.dto.CategoryResponseDto;
import com.bookservice.entity.Category;
import com.bookservice.exception.DataNotFoundException;
import com.bookservice.exception.ResourceNotFoundException;
import com.bookservice.repo.CategoryRepo;
import com.bookservice.service.CategoryService;
import com.bookservice.shared.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public String addCategory(CategoryRequestDto category) {
        Category categoryEntity = modelMapper.map(category, Category.class);
        categoryEntity.setStatus(Status.ACTIVE);
        categoryRepo.save(categoryEntity);
        return "Category has benn added successfully";
    }

    @Override
    public String updateCategory(CategoryRequestDto category, String id) {
        Category categoryEntity = getAlreadyExistsCategoryById(id);
        categoryEntity.setCategoryName(category.getCategoryName());
        categoryEntity.setCategoryDescription(category.getCategoryDescription());
        categoryRepo.save(categoryEntity);
        return "Category has benn updated successfully";


    }

    private Category getAlreadyExistsCategoryById(String id) {
        return categoryRepo.findById(id).orElseThrow(() ->
        {
            log.error("Category with id {} not found", id);
            return new DataNotFoundException("Category not found");
        });
    }

    @Override
    public String deleteCategory(String id) {
        Category category= getAlreadyExistsCategoryById(id);
        category.setStatus(Status.DELETED);
        categoryRepo.save(category);
        return "Category has been deleted successfully";
    }

    @Override
    public String changeCategoryStatus(String id, String status) {
        Category category = getAlreadyExistsCategoryById(id);
        category.setStatus(Status.valueOf(status));
        categoryRepo.save(category);
        return "Category has been changed successfully";
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList.stream().map(li -> modelMapper.map(li, CategoryResponseDto.class)).toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(String id) {
        return modelMapper.map(getAlreadyExistsCategoryById(id), CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> getCategoryByStatus(String status) {
        List<Category> categoryList = categoryRepo.findByStatus(Status.valueOf(status));
        return categoryList.stream().map(li -> modelMapper.map(li, CategoryResponseDto.class)).toList();
    }
}

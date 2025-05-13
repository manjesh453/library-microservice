package com.bookservice.controller;

import com.bookservice.dto.CategoryRequestDto;
import com.bookservice.dto.CategoryResponseDto;
import com.bookservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@RequestBody CategoryRequestDto category) {
        return categoryService.addCategory(category);
    }

    @PostMapping("/update/{id}")
    public String updateBook(@RequestBody CategoryRequestDto category, @PathVariable String id) {
        return categoryService.updateCategory(category, id);
    }

    @GetMapping("/getById/{id}")
    public CategoryResponseDto getCategory(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/getAllCategory")
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@RequestBody String status, @PathVariable String id) {
        return categoryService.changeCategoryStatus(status, id);
    }

    @GetMapping("/getByStatus/{status}")
    public List<CategoryResponseDto> getCategoryByStatus(@PathVariable String status) {
        return categoryService.getCategoryByStatus(status);
    }
}

package com.kipchirchirlangat.blog.controllers;


import com.kipchirchirlangat.blog.domain.dtos.CategoryDto;
import com.kipchirchirlangat.blog.domain.dtos.CreateCategoryDto;
import com.kipchirchirlangat.blog.domain.entities.Category;
import com.kipchirchirlangat.blog.mappers.CategoryMapper;
import com.kipchirchirlangat.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories () {
//        TODO: come up and create a service
        List<CategoryDto> categories =  categoryService.listCategories().stream()
                        .map(categoryMapper::toDto).toList();
        System.out.println(categories);
        return  ResponseEntity.ok(categories);
    }

    @PostMapping
    public  ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category categoryToCreate = categoryMapper.toEntity(createCategoryDto);
        Category savedCategory = categoryService.createCategory(categoryToCreate);
        return  new ResponseEntity<>(
                categoryMapper.toDto(savedCategory),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

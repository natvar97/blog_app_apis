package com.indialone.blogapp.controllers;

import com.indialone.blogapp.models.ApiResponse;
import com.indialone.blogapp.payloads.CategoryDTO;
import com.indialone.blogapp.services.CategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO createdCategoryDTO = categoryService.createCategory(categoryDTO);
            return new ResponseEntity<>(new ApiResponse("Success", true, createdCategoryDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("categoryId") Integer categoryId) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(new ApiResponse("Success", true, updatedCategory), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
            return new ResponseEntity<>(new ApiResponse("Success", true, categoryDTOS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("categoryId") Integer categoryId) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(new ApiResponse("Success", true, categoryDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("categoryId") Integer categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return new ResponseEntity<>(new ApiResponse("Success", true, Map.of()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

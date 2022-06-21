package com.indialone.blogapp.controllers;

import com.indialone.blogapp.models.ApiResponse;
import com.indialone.blogapp.payloads.CategoryDTO;
import com.indialone.blogapp.services.CategoryService;
import com.indialone.blogapp.utils.AppConstants;
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
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, createdCategoryDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_FAILED, false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{" + AppConstants.PATH_VARIABLE_CATEGORY_ID + "}")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable(AppConstants.PATH_VARIABLE_CATEGORY_ID) Integer categoryId) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, updatedCategory), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_FAILED, false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, categoryDTOS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_FAILED, false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{" + AppConstants.PATH_VARIABLE_CATEGORY_ID + "}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable(AppConstants.PATH_VARIABLE_CATEGORY_ID) Integer categoryId) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, categoryDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_FAILED, false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{" + AppConstants.PATH_VARIABLE_CATEGORY_ID + "}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable(AppConstants.PATH_VARIABLE_CATEGORY_ID) Integer categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, Map.of()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_FAILED, false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

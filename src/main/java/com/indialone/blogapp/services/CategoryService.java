package com.indialone.blogapp.services;

import com.indialone.blogapp.payloads.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Integer categoryId);

    void deleteCategoryById(Integer categoryId);

}

package com.wastech.Expense_Tracker.service;

import com.wastech.Expense_Tracker.payload.CategoryDTO;
import com.wastech.Expense_Tracker.payload.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO getCategoryById(Long categoryId);


    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

}
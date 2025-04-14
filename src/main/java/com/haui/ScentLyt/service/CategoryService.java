package com.haui.ScentLyt.service;

import com.haui.ScentLyt.DTO.CategoryDTO;
import com.haui.ScentLyt.response.category.CategoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    @Transactional
    Category save(CategoryDTO categoryDTO);

    Page<CategoryResponse> getAllCategories(String name, Boolean status, Pageable pageable);

    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getAllCategories(String name, Boolean status);

    Category getCategory(Integer id);

    @Transactional
    Category update(Integer id, CategoryDTO categoryDTO);

    void delete(Integer id);
}

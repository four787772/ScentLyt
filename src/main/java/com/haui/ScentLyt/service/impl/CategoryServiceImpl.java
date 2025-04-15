package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.CategoryDTO;
import com.haui.ScentLyt.entity.Category;
import com.haui.ScentLyt.exception.DataNotFoundException;
import com.haui.ScentLyt.entity.Category;
import com.haui.ScentLyt.repository.CategoryRepository;
import com.haui.ScentLyt.response.category.CategoryResponse;
import com.haui.ScentLyt.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setActive(true); // Gán mặc định true khi tạo mới
        return categoryRepository.save(category);
    }


    @Override
    public Page<CategoryResponse> getAllCategories(String name, Boolean status, Pageable pageable) {
        return categoryRepository.findAllCategories(name, status, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getAllCategories(String name, Boolean status) {
        return categoryRepository.findAllCategories(name, status)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Category update(Integer id, CategoryDTO categoryDTO) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow();
        BeanUtils.copyProperties(categoryDTO, existing);
        return categoryRepository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();
        categoryRepository.delete(category);
    }

    private CategoryResponse convertToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        response.setId(category.getId()); // Gán id thủ công nếu cần
        return response;
    }
}

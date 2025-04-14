package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.CategoryDTO;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        return categoryRepository.save(category);
    }

    @Override
    public Page<CategoryResponse> getAllCategories(String name, Boolean status, Pageable pageable) {
        throw new UnsupportedOperationException("Chức năng phân trang chưa được hỗ trợ.");
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return convertToResponseList(categories);
    }

    @Override
    public List<CategoryResponse> getAllCategories(String name, Boolean status) {
        List<Category> categories;

        if (status != null) {
            categories = categoryRepository.findByActive(status);
        } else {
            categories = categoryRepository.findAll();
        }

        if (name != null && !name.trim().isEmpty()) {
            String keyword = name.trim().toLowerCase();
            categories = categories.stream()
                    .filter(c -> c.getCategoryName() != null && c.getCategoryName().toLowerCase().contains(keyword))
                    .toList();
        }

        return convertToResponseList(categories);
    }

    @Override
    public Category getCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        try {
            throw new DataNotFoundException("Category not found");
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Category update(Integer id, CategoryDTO categoryDTO) {
        Category category = getCategory(id);
        BeanUtils.copyProperties(categoryDTO, category);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    // Helper để chuyển danh sách entity sang response
    private List<CategoryResponse> convertToResponseList(List<Category> categories) {
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponse response = new CategoryResponse();
            BeanUtils.copyProperties(category, response);
            response.setId(category.getCategoryId());
            responses.add(response);
        }
        return responses;
    }
}

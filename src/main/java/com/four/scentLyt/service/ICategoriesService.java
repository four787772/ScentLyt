package com.four.scentLyt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.four.scentLyt.entity.Categories;
import com.four.scentLyt.form.categories.CreatingCategoryForm;
import com.four.scentLyt.form.categories.UpdatingCategoryForm;

public interface ICategoriesService {
	public Categories findByName(String name);
	
	public Page<Categories> getAllCategories(Pageable pageable);
	
	public void createCategory(CreatingCategoryForm form);
	
	public void updateCategory(String id, UpdatingCategoryForm form);
	
	public void deleteCategories(List<String> ids);
	
	public boolean isCategoryExistsByName(String name);
	
	public boolean isCategoryExistsById(String id);
	
	
	

}

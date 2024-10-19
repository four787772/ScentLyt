package com.four.scentLyt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.four.scentLyt.entity.Categories;
import com.four.scentLyt.form.categories.CreatingCategoryForm;
import com.four.scentLyt.form.categories.UpdatingCategoryForm;
import com.four.scentLyt.repository.ICategoriesRepository;

public class CategoriesService implements ICategoriesService {
	@Autowired
	private ICategoriesRepository repository;
	
	@Override
	public Categories findByName(String name) {
		// TODO Auto-generated method stub
		return repository.findByName(name);
	}

	@Override
	public Page<Categories> getAllCategories(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}

	@Override
	public void createCategory(CreatingCategoryForm form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCategory(String id, UpdatingCategoryForm form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCategories(List<String> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCategoryExistsByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCategoryExistsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	

}

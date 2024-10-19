package com.four.scentLyt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.four.scentLyt.entity.Categories;
@Repository
public interface ICategoriesRepository 
extends JpaRepository<Categories, String>,
JpaSpecificationExecutor<Categories>
{
	public Categories findByName(String name);
	
	public void deleteByIdIn(List<String> ids);
	
	public boolean existByCategoriesName(String name);
	
}

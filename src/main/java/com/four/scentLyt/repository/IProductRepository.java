package com.four.scentLyt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.four.scentLyt.entity.Product;
@Repository
public interface IProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product>{
	
	boolean existsByName(String name);
	
	Product findByName(String name);
	
	public void deleteByIdIn(List<String> ids);
	
}

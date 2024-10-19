package com.four.scentLyt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.four.scentLyt.entity.ProductImg;
@Repository
public interface IProductImgRepository extends JpaRepository<ProductImg, Integer>, JpaSpecificationExecutor<ProductImg>{
	@Query(value = "SELECT * FROM productimg WHERE product_id = :productId", nativeQuery = true)
	public List<ProductImg> findAllImgByProductId(String productId);
	
	public void deleteByProductIdIn(List<String> ids);
	
	public boolean existByProductid(String productId);

}

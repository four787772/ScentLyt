package com.four.scentLyt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.four.scentLyt.entity.CartItem;
@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, String>, JpaSpecificationExecutor<CartItem>{
	
	

}

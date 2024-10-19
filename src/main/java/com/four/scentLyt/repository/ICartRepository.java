package com.four.scentLyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.four.scentLyt.entity.Cart;
import com.four.scentLyt.entity.User;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String>, JpaSpecificationExecutor<Cart> {
	@Query("SELECT c FROM Cart c WHERE c.user.id = :userId ORDER BY c.createAt DESC, c.cardId DESC")
	public Optional<Cart> findCartByUserId(String id);
	
	public List<Cart> findCartByUser(User user);
}

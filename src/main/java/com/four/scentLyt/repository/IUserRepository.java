package com.four.scentLyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.four.scentLyt.entity.User;
@Repository
public interface IUserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{
	public boolean existsByUserName(String userName);
	
	public boolean existsByEmail(String email);
	
	public Optional<User> findByUsername(String userName);
	
	public User findByEmail(String email);
	
	public void deleteByIdIn (List<Integer> ids);
}

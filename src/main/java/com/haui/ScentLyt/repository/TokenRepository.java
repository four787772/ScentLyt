package com.haui.ScentLyt.repository;


import com.haui.ScentLyt.entity.Token;
import com.haui.ScentLyt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
    Token findByRefreshToken(String refreshToken);
    List<Token> findByUser(User user);
}
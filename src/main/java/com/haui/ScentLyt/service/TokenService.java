package com.haui.ScentLyt.service;

import com.haui.ScentLyt.entity.Token;
import com.haui.ScentLyt.entity.User;
import jakarta.transaction.Transactional;

public interface TokenService {
    @Transactional
    Token refreshToken(String refreshToken, User user) throws Exception;

    @Transactional
    Token addToken(User user, String token, boolean isMobileDevice);
}

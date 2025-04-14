package com.haui.ScentLyt.filter;

import com.haui.ScentLyt.entity.User;
import com.haui.ScentLyt.exception.ExpiredTokenException;
import com.haui.ScentLyt.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass - cho đi qua hết
                return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null && !authHeader.startsWith("Bearer ")) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED, "authHeader null or not started with Bearer");
                return;
            }

            String token = authHeader.substring(7);
            String phoneNumber = jwtTokenUtil.getSubject(token);

            boolean isNullPhoneNumber = phoneNumber == null;
            boolean isNullAuthentication = SecurityContextHolder.getContext().getAuthentication() == null;
            if (!isNullPhoneNumber && isNullAuthentication) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);

                boolean isValid = jwtTokenUtil.validateToken(token, userDetails);
                if (isValid) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new ExpiredTokenException(
                            "Token is expired or invalid");
                }
            }

            filterChain.doFilter(request, response); //enable bypass
        } catch (ExpiredTokenException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is expired: " + e.getMessage());
        } catch (AccessDeniedException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied: You do not have the required role.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication error: " + e.getMessage());
        }
    }


    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                //day la nhung api khong can phai dang nhap ma van dung duoc
                // Healthcheck request, no JWT token required

                Pair.of(String.format("%s/open-api/**", apiPrefix), "POST"),
                Pair.of(String.format("%s/open-api/**", apiPrefix), "GET")
        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : bypassTokens) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request path and method match any pair in the bypassTokens list
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}

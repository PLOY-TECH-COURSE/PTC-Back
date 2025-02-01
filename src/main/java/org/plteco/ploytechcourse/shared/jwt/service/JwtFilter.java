package org.plteco.ploytechcourse.shared.jwt.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.shared.jwt.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.split(" ")[1];
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(User.builder()
                .uid(jwtUtil.getUid(token))
                .email(jwtUtil.getEmail(token))
                .name("홍길동")
                .bio("없음")
                .grade(1L)
                .role(jwtUtil.getRole(token))
                .classNumber(1L)
                .profile("없음")
                .number(1L)
                .password("password")
                .build());
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
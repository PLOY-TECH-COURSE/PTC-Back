package org.plteco.ploytechcourse.application.user.logout;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * 이 필터는 사용자가 로그아웃을 진행할 때 로그아웃 절차를 처리하는 필터입니다.
 *
 * 기본적인 로그아웃 로직은 이 필터 내에서 처리되며, HTTP 요청이 로그아웃 요청일 경우
 * 관련된 리프레시 토큰을 검증하고 삭제합니다.
 *
 *
 * 필터는 Spring Security에서 제공하는 기본적인 필터 체인에서 동작하므로, 별도의 컨트롤러 구현은 필요하지 않습니다.
 *
 */
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            log.error("로그아웃 시 리프레시 토큰이 없습니다.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.error("리프레시 토큰이 만료되었습니다. refresh token: {}", refresh);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            log.error("유효하지 않은 리프레시 토큰입니다. refresh token: {}", refresh);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Boolean isExist = refreshRepository.existsByToken(refresh);
        if (!isExist) {
            log.error("리프레시 토큰이 데이터베이스에 존재하지 않습니다. refresh token: {}", refresh);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        refreshRepository.deleteByToken(refresh);
        log.info("리프레시 토큰이 데이터베이스에서 삭제되었습니다. refresh token: {}", refresh);

        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        log.info("리프레시 토큰 쿠키가 삭제되었습니다.");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

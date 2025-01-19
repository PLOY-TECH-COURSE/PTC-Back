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
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    /**
     * 로그아웃 요청을 처리하는 필터 메서드입니다.
     *
     * 이 메서드는 HTTP 요청의 URI와 메서드를 확인하고, 로그아웃 요청에 대해서만
     * 리프레시 토큰을 삭제하는 처리를 수행합니다.
     *
     *
     * @param request 클라이언트 요청 객체
     * @param response 클라이언트 응답 객체
     * @param chain 필터 체인의 다음 필터를 호출하는 객체
     * @throws IOException 입출력 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    /**
     * 로그아웃 요청을 처리하는 필터 메서드입니다.
     *
     * URI가 "/logout"이고 HTTP 메서드가 POST일 경우, 리프레시 토큰을 검증하고 삭제하는 작업을 수행합니다.
     *
     *
     * @param request 클라이언트 요청 객체
     * @param response 클라이언트 응답 객체
     * @param filterChain 필터 체인의 다음 필터를 호출하는 객체
     * @throws IOException 입출력 예외
     * @throws ServletException 서블릿 예외
     */
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 로그아웃 URI 확인
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        // POST 요청만 처리
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 리프레시 토큰 추출
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        // 리프레시 토큰이 없는 경우
        if (refresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 리프레시 토큰 만료 여부 검사
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에 대해 400 오류 응답
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 리프레시 토큰이 맞는지 확인 (페이로드 검증)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 리프레시 토큰이 데이터베이스에 존재하는지 확인
        Boolean isExist = refreshRepository.existsByToken(refresh);
        if (!isExist) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 로그아웃 처리: 리프레시 토큰 DB에서 삭제
        refreshRepository.deleteByToken(refresh);

        // 리프레시 토큰 쿠키 삭제
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);  // 쿠키를 삭제하는 방식으로 만료 시간 설정
        cookie.setPath("/");  // 쿠키의 유효 범위를 전체 경로로 설정

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

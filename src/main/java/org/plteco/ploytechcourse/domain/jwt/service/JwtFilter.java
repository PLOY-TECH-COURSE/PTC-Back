package org.plteco.ploytechcourse.domain.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.login.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JwtFilter 클래스는 HTTP 요청에서 JWT 토큰을 검사하여 인증을 처리하는 필터입니다.
 * 이 필터는 Spring Security의 `OncePerRequestFilter`를 상속하여, 요청마다 한 번만 실행됩니다.
 * <p>
 * JWT 토큰을 검증하고, 유효한 경우 사용자 정보를 SecurityContext에 설정하여 인증을 처리합니다.
 * </p>
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * HTTP 요청에 포함된 JWT 토큰을 검증하고, 인증 정보를 SecurityContext에 설정하는 메서드입니다.
     *
     * @param request  HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 필터 체인
     * @throws ServletException 예외 발생 시
     * @throws IOException 예외 발생 시
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 Authorization에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("Authorization");

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);  // "Bearer "의 길이는 7
        }

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null|| accessToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰일 경우 응답 본문에 메시지 출력 및 상태 코드 설정
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            // 유효하지 않은 토큰일 경우 응답 본문에 메시지 출력 및 상태 코드 설정
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 사용자 정보 추출 및 CustomUserDetails 객체 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(User.builder()
                .uid(jwtUtil.getUid(accessToken))
                .email(jwtUtil.getEmail(accessToken))
                .name("홍길동") // 예시로 사용자 이름 설정
                .bio("없음")   // 예시로 bio 설정
                .grade(1L)     // 예시로 학년 설정
                .role(jwtUtil.getRole(accessToken)) // JWT에서 role 정보 가져옴
                .classNumber(1L) // 예시로 학급 번호 설정
                .profile("없음") // 예시로 프로필 설정
                .number(1L)     // 예시로 번호 설정
                .password("password") // 예시로 패스워드 설정
                .build());

        // 인증 정보를 SecurityContext에 설정
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 필터 체인 실행
        filterChain.doFilter(request, response);
    }
}

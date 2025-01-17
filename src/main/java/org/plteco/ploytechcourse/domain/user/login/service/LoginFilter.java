package org.plteco.ploytechcourse.domain.user.login.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.jwt.model.entity.RefreshToken;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.login.dto.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * LoginFilter 클래스는 사용자 로그인 시 인증을 처리하고, 성공적으로 인증된 경우 JWT 토큰을 생성하여
 * 응답 헤더와 쿠키에 전달하는 필터입니다. 실패 시 401 상태 코드와 함께 응답합니다.
 */
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    /**
     * 사용자의 로그인 정보를 이용해 인증을 시도합니다.
     *
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @return 인증된 Authentication 객체
     * @throws AuthenticationException 인증 실패 시 예외 발생
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = obtainEmail(request);
        String password = obtainPassword(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        return authenticationManager.authenticate(authToken);
    }

    /**
     * HTTP 요청에서 사용자의 이메일을 추출합니다.
     *
     * @param request HTTP 요청
     * @return 사용자의 이메일
     */
    private String obtainEmail(HttpServletRequest request) {
        return request.getParameter("email");
    }

    /**
     * 인증에 성공한 후 호출되어, JWT 토큰을 생성하고 응답에 추가합니다.
     *
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param chain 필터 체인
     * @param authentication 인증된 사용자 정보
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getEmail();
        String uid = customUserDetails.getUid();

        // 사용자의 권한 정보를 얻어옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        // 사용자의 역할을 기반으로 JWT를 생성
        RoleEnum role = RoleEnum.valueOf(auth.getAuthority());
        String access = jwtUtil.createJwt("access", email, uid, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", email, uid, role, 86400000L);

        // refresh 토큰을 저장
        addRefreshEntity(uid, email, refresh, 86400000L);

        // 응답 헤더에 access 토큰을 추가하고, refresh 토큰은 쿠키에 추가
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * 인증 실패 시 호출되며, 401 상태 코드로 응답을 설정합니다.
     *
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param failed 인증 실패 예외
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    /**
     * refresh 토큰을 쿠키에 저장합니다.
     *
     * @param key 쿠키의 이름
     * @param value 쿠키의 값
     * @return 생성된 쿠키
     */
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60); // 쿠키의 최대 수명: 1일
        cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
        return cookie;
    }

    /**
     * refresh 토큰을 데이터베이스에 저장합니다.
     *
     * @param uid 사용자 ID
     * @param email 사용자 이메일
     * @param refresh refresh 토큰
     * @param expiredMs 만료 시간 (밀리초 단위)
     */
    private void addRefreshEntity(String uid, String email, String refresh, Long expiredMs) {
        Date CrDate = new Date(System.currentTimeMillis());
        Date ExDate = new Date(System.currentTimeMillis() + expiredMs);
        refreshRepository.save(
                RefreshToken.builder()
                        .uid(uid)
                        .email(email)
                        .token(refresh)
                        .createdAt(CrDate)
                        .expiresAt(ExDate)
                        .build()
        );
    }
}

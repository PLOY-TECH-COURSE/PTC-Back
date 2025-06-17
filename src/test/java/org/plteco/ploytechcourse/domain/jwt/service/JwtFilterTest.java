package org.plteco.ploytechcourse.domain.jwt.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        jwtFilter = new JwtFilter(jwtUtil);
        // SecurityContext를 초기화
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Authorization 헤더가 없는 경우 다음 필터로 진행한다")
    void doFilterInternal_WithNoAuthorizationHeader_ProceedsToNextFilter() throws ServletException, IOException {
        // given
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        jwtFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
        // SecurityContext에 인증 정보가 설정되지 않았는지 확인
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Authorization 헤더가 비어있는 경우 다음 필터로 진행한다")
    void doFilterInternal_WithEmptyAuthorizationHeader_ProceedsToNextFilter() throws ServletException, IOException {
        // given
        when(request.getHeader("Authorization")).thenReturn("");

        // when
        jwtFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
        // SecurityContext에 인증 정보가 설정되지 않았는지 확인
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("만료된 토큰인 경우 401 응답을 반환한다")
    void doFilterInternal_WithExpiredToken_Returns401Response() throws ServletException, IOException {
        // given
        String token = "expired-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isExpired(token)).thenReturn(true);

        // PrintWriter 설정
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // when
        jwtFilter.doFilterInternal(request, response, filterChain);

        // then
        // 다음 필터로 진행하지 않음
        verify(filterChain, never()).doFilter(any(), any());
        
        // 401 상태 코드 설정
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // 응답 메시지 확인
        assertEquals("access token expired", stringWriter.toString());
    }

    @Test
    @DisplayName("유효하지 않은 토큰인 경우 401 응답을 반환한다")
    void doFilterInternal_WithInvalidToken_Returns401Response() throws ServletException, IOException {
        // given
        String token = "invalid-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isExpired(token)).thenThrow(new RuntimeException("invalid token"));

        // PrintWriter 설정
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // when
        jwtFilter.doFilterInternal(request, response, filterChain);

        // then
        // 다음 필터로 진행하지 않음
        verify(filterChain, never()).doFilter(any(), any());
        
        // 401 상태 코드 설정
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // 응답 메시지 확인
        assertEquals("invalid access token", stringWriter.toString());
    }

    @Test
    @DisplayName("access 토큰이 아닌 경우 401 응답을 반환한다")
    void doFilterInternal_WithNonAccessToken_Returns401Response() throws ServletException, IOException {
        // given
        String token = "refresh-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isExpired(token)).thenReturn(false);
        when(jwtUtil.getCategory(token)).thenReturn("refresh");

        // PrintWriter 설정
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // when
        jwtFilter.doFilterInternal(request, response, filterChain);

        // then
        // 다음 필터로 진행하지 않음
        verify(filterChain, never()).doFilter(any(), any());
        
        // 401 상태 코드 설정
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // 응답 메시지 확인
        assertEquals("invalid access token", stringWriter.toString());
    }

    @Test
    @DisplayName("유효한 access 토큰인 경우 인증 정보를 설정하고 다음 필터로 진행한다")
    void doFilterInternal_WithValidAccessToken_SetsAuthenticationAndProceedsToNextFilter() throws ServletException, IOException {
        // given
        String token = "valid-access-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isExpired(token)).thenReturn(false);
        when(jwtUtil.getCategory(token)).thenReturn("access");
        when(jwtUtil.getUid(token)).thenReturn("test-uid");
        when(jwtUtil.getEmail(token)).thenReturn("test@example.com");
        when(jwtUtil.getRole(token)).thenReturn(RoleEnum.ROLE_USER);

        // SecurityContext 설정
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);

        // when
        jwtFilter.doFilterInternal(request, response, filterChain);

        // then
        // 다음 필터로 진행
        verify(filterChain).doFilter(request, response);
        
        // SecurityContext에 인증 정보가 설정되었는지 확인
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        
        // 인증 정보의 권한이 올바르게 설정되었는지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(RoleEnum.ROLE_USER.name())));
    }
}
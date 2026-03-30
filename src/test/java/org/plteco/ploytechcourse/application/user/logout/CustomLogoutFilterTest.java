package org.plteco.ploytechcourse.application.user.logout;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomLogoutFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshRepository refreshRepository;

    @Mock
    private FilterChain filterChain;

    @Test
    @DisplayName("로그아웃 요청에서 쿠키가 없으면 400을 반환한다")
    void doFilter_WithNoCookies_ReturnsBadRequest() throws Exception {
        // given
        CustomLogoutFilter filter = new CustomLogoutFilter(jwtUtil, refreshRepository);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/logout");
        request.setMethod("POST");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        filter.doFilter(request, response, filterChain);

        // then
        assertEquals(400, response.getStatus());
        verify(filterChain, never()).doFilter(request, response);
    }
}

package org.plteco.ploytechcourse.application.user.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.AddRefreshEntity;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ProcessTokenReissueImplTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshRepository refreshRepository;

    @Mock
    private AddRefreshEntity addRefreshEntity;

    @Test
    @DisplayName("리프레시 재발급 요청에 쿠키가 없으면 400을 반환한다")
    void reissue_WithNoCookies_ReturnsBadRequest() {
        // given
        ProcessTokenReissueImpl service = new ProcessTokenReissueImpl(jwtUtil, refreshRepository, addRefreshEntity);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        var result = service.reissue(request, response);

        // then
        assertEquals(400, result.getStatusCode().value());
        assertEquals("refresh token null", result.getBody());
        verifyNoInteractions(jwtUtil, refreshRepository, addRefreshEntity);
    }
}

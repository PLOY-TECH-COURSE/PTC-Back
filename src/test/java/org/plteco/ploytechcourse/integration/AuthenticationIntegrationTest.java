package org.plteco.ploytechcourse.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshRepository refreshRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String testEmail = "integration-test@example.com";
    private final String testPassword = "password123";
    private final String testUid = "integration-test-uid";
    private final String testName = "Integration Test User";

    @BeforeEach
    void setUp() {
        // 테스트 사용자 생성 및 저장
        User testUser = User.builder()
                .uid(testUid)
                .name(testName)
                .email(testEmail)
                .password(passwordEncoder.encode(testPassword))
                .role(RoleEnum.ROLE_USER)
                .grade(1L)
                .classNumber(1L)
                .number(1L)
                .build();

        // 기존 사용자가 있으면 삭제
        userRepository.findByEmail(testEmail);
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("로그인 성공 시 JWT 토큰을 반환한다")
    void login_WithValidCredentials_ReturnsJwtToken() throws Exception {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", testEmail);
        loginRequest.put("password", testPassword);

        // when & then
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(cookie().exists("refresh"))
                .andReturn();

        // JWT 토큰이 반환되었는지 확인
        String authorizationHeader = result.getResponse().getHeader("Authorization");
        assertNotNull(authorizationHeader);
        assertTrue(authorizationHeader.length() > 0);

        // refresh 토큰이 저장소에 저장되었는지 확인
        // 쿠키에서 refresh 토큰 추출
        String refreshToken = result.getResponse().getCookie("refresh").getValue();
        assertNotNull(refreshToken);
        assertTrue(refreshRepository.existsByToken(refreshToken));
    }

    @Test
    @DisplayName("잘못된 자격 증명으로 로그인 시 401 응답을 반환한다")
    void login_WithInvalidCredentials_Returns401() throws Exception {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", testEmail);
        loginRequest.put("password", "wrong-password");

        // when & then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("INVALID_CREDENTIALS")));
    }

    @Test
    @DisplayName("JWT 토큰으로 보호된 리소스에 접근할 수 있다")
    void accessProtectedResource_WithValidJwtToken_Succeeds() throws Exception {
        // given - 먼저 로그인하여 JWT 토큰 획득
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", testEmail);
        loginRequest.put("password", testPassword);

        MvcResult loginResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String jwtToken = loginResult.getResponse().getHeader("Authorization");

        // when & then - 보호된 리소스에 접근
        mockMvc.perform(get("/real-mypage")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("JWT 토큰 없이 보호된 리소스에 접근하면 401 응답을 반환한다")
    void accessProtectedResource_WithoutJwtToken_Returns401() throws Exception {
        // when & then
        mockMvc.perform(get("/real-mypage"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("만료된 JWT 토큰으로 보호된 리소스에 접근하면 401 응답을 반환한다")
    void accessProtectedResource_WithExpiredJwtToken_Returns401() throws Exception {
        // given - 만료된 JWT 토큰 (임의로 생성)
        String expiredToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInVpZCI6InRlc3QtdWlkIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTYxNTIyNTYwMCwiZXhwIjoxNjE1MjI1NjAxfQ.8y7repUFu3hSaGA7aBXNQJk9UYBPEKgSrjIssLtQ4YQ";

        // when & then
        mockMvc.perform(get("/real-mypage")
                        .header("Authorization", expiredToken))
                .andExpect(status().isUnauthorized());
    }
}

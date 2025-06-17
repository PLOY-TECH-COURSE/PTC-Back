package org.plteco.ploytechcourse.domain.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String testSecret = "testSecretKeyForJwtTestingMustBeAtLeast256BitsLong";
    private final String testEmail = "test@example.com";
    private final String testUid = "test-uid-123";
    private final RoleEnum testRole = RoleEnum.ROLE_USER;
    private final String accessCategory = "access";
    private final String refreshCategory = "refresh";

    @BeforeEach
    void setUp() {
        // JwtUtil 인스턴스 생성 및 비밀키 설정
        jwtUtil = new JwtUtil(testSecret);
    }

    @Test
    @DisplayName("액세스 토큰을 생성하고 검증한다")
    void createAndValidateAccessToken() {
        // given
        long expirationMs = 3600000; // 1시간

        // when
        String token = jwtUtil.createJwt(accessCategory, testEmail, testUid, testRole, expirationMs);

        // then
        assertNotNull(token);
        assertEquals(testEmail, jwtUtil.getEmail(token));
        assertEquals(testUid, jwtUtil.getUid(token));
        assertEquals(testRole, jwtUtil.getRole(token));
        assertEquals(accessCategory, jwtUtil.getCategory(token));
        assertFalse(jwtUtil.isExpired(token));
    }

    @Test
    @DisplayName("리프레시 토큰을 생성하고 검증한다")
    void createAndValidateRefreshToken() {
        // given
        long expirationMs = 86400000; // 24시간

        // when
        String token = jwtUtil.createJwt(refreshCategory, testEmail, testUid, testRole, expirationMs);

        // then
        assertNotNull(token);
        assertEquals(testEmail, jwtUtil.getEmail(token));
        assertEquals(testUid, jwtUtil.getUid(token));
        assertEquals(testRole, jwtUtil.getRole(token));
        assertEquals(refreshCategory, jwtUtil.getCategory(token));
        assertFalse(jwtUtil.isExpired(token));
    }

    @Test
    @DisplayName("만료된 토큰은 isExpired가 true를 반환한다")
    void isExpired_WithExpiredToken_ReturnsTrue() throws Exception {
        // given
        long expirationMs = -10000; // 이미 만료된 토큰 (현재 시간보다 10초 전)

        // when
        String token = jwtUtil.createJwt(accessCategory, testEmail, testUid, testRole, expirationMs);

        // then
        assertTrue(jwtUtil.isExpired(token));
    }

    @Test
    @DisplayName("만료된 토큰에서 이메일을 추출하면 예외가 발생한다")
    void getEmail_WithExpiredToken_ThrowsException() {
        // given
        long expirationMs = -10000; // 이미 만료된 토큰
        String token = jwtUtil.createJwt(accessCategory, testEmail, testUid, testRole, expirationMs);

        // when & then
        assertThrows(RuntimeException.class, () -> jwtUtil.getEmail(token));
    }

    @Test
    @DisplayName("유효하지 않은 토큰에서 정보를 추출하면 예외가 발생한다")
    void getEmail_WithInvalidToken_ThrowsException() {
        // given
        String invalidToken = "invalid.token.string";

        // when & then
        assertThrows(RuntimeException.class, () -> jwtUtil.getEmail(invalidToken));
    }

    @Test
    @DisplayName("토큰의 만료 시간이 정확하게 설정된다")
    void tokenExpirationTimeIsSetCorrectly() throws Exception {
        // given
        long expirationMs = 3600000; // 1시간
        long currentTimeMs = System.currentTimeMillis();
        
        // when
        String token = jwtUtil.createJwt(accessCategory, testEmail, testUid, testRole, expirationMs);
        
        // then
        // 토큰의 만료 시간을 직접 확인하기 위해 Jwts 파서 사용
        Date expirationDate = io.jsonwebtoken.Jwts.parser()
                .verifyWith(new javax.crypto.spec.SecretKeySpec(
                        testSecret.getBytes(java.nio.charset.StandardCharsets.UTF_8), 
                        io.jsonwebtoken.Jwts.SIG.HS256.key().build().getAlgorithm()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        
        // 만료 시간이 현재 시간 + expirationMs와 근접한지 확인 (1초 오차 허용)
        long expectedExpirationMs = currentTimeMs + expirationMs;
        long actualExpirationMs = expirationDate.getTime();
        
        assertTrue(Math.abs(expectedExpirationMs - actualExpirationMs) < 1000, 
                "Expected expiration time to be close to " + expectedExpirationMs + 
                " but was " + actualExpirationMs);
    }
}
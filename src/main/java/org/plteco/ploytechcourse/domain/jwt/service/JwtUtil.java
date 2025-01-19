package org.plteco.ploytechcourse.domain.jwt.service;

import io.jsonwebtoken.Jwts;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JwtUtil 클래스는 JWT 토큰의 생성 및 검증을 담당하는 유틸리티 클래스입니다.
 * <p>
 * 이 클래스는 JWT 토큰을 생성하고, 토큰에서 필요한 정보를 추출하거나 검증할 수 있는 메서드를 제공합니다.
 * </p>
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    /**
     * JwtUtil의 생성자로, JWT 서명을 위한 비밀키를 주입받습니다.
     *
     * @param secret 비밀키 문자열
     */
    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        // 비밀키를 설정하여 JWT 서명을 위한 SecretKey 객체를 생성합니다.
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * JWT 토큰에서 이메일 정보를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 이메일 정보
     */
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    /**
     * JWT 토큰에서 UID 정보를 추출합니다.
     *
     * @param token JWT 토큰
     * @return UID 정보
     */
    public String getUid(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("uid", String.class);
    }

    /**
     * JWT 토큰에서 역할(Role) 정보를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 역할(RoleEnum)
     */
    public RoleEnum getRole(String token) {
        return RoleEnum.valueOf(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class));
    }

    /**
     * JWT 토큰이 만료되었는지 확인합니다.
     *
     * @param token JWT 토큰
     * @return 만료 여부
     */
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    /**
     * JWT 토큰에서 카테고리 정보를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 카테고리 정보
     */
    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    /**
     * JWT 토큰을 생성합니다.
     *
     * @param category  토큰의 카테고리 (예: access, refresh)
     * @param email     사용자 이메일
     * @param uid       사용자 UID
     * @param role      사용자 역할 (RoleEnum)
     * @param expiredMs 토큰 만료 시간 (밀리초)
     * @return 생성된 JWT 토큰
     */
    public String createJwt(String category, String email, String uid, RoleEnum role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)   // 카테고리 정보 설정
                .claim("email", email)         // 이메일 정보 설정
                .claim("uid", uid)             // UID 정보 설정
                .claim("role", role)           // 역할 정보 설정
                .issuedAt(new Date(System.currentTimeMillis())) // 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 시간 설정
                .signWith(secretKey)           // 서명 설정
                .compact();                    // JWT 토큰 생성
    }
}

package org.plteco.ploytechcourse.application.user.login;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.domain.jwt.model.entity.RefreshToken;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.AddRefreshEntity;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 이 클래스는 리프레시 토큰을 재발급하고 새 액세스 토큰을 발급하는 서비스 클래스입니다.
 *
 * 리프레시 토큰을 이용해 새로운 액세스 토큰과 리프레시 토큰을 발급하며,
 * 유효성 검사 및 만료된 토큰에 대한 처리를 포함합니다.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProcessTokenReissueImpl implements ProcessTokenReissue {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final AddRefreshEntity addRefreshEntity;

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        log.info("리프레시토큰 재발급 시작");

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.error("리프레시 토큰이 없습니다.");
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            log.error("리프레시 토큰이 없습니다.");
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.error("리프레시 토큰이 만료되었습니다. token: {}", refresh);
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            log.error("유효하지 않은 리프레시 토큰입니다. token: {}", refresh);
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        if (!refreshRepository.existsByToken(refresh)) {
            log.error("리프레시 토큰이 존재하지 않습니다. token: {}", refresh);
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);
        String uid = jwtUtil.getUid(refresh);
        RoleEnum role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", email, uid, role, 86400000L);
        String newRefresh = jwtUtil.createJwt("refresh", email, uid, role, 1209600000L);

        refreshRepository.deleteByToken(refresh);
        addRefreshEntity.addRefreshEntity(uid, email, newRefresh, 1209600000L);

        log.info("새로운 액세스 토큰과 리프레시 토큰을 발급했습니다. 새로운 리프레시 토큰: {}", newRefresh);

        response.setHeader("Authorization", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        log.info("리프레시 토큰 재발급 완료");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        log.info("쿠키 생성 시작");
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60 * 14);  // 14일 유효
        cookie.setHttpOnly(true); // 클라이언트 측에서 접근 불가
        cookie.setPath("/");
        log.info("쿠키 생성 끝");
        return cookie;
    }
}

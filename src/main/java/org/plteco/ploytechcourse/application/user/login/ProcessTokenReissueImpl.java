package org.plteco.ploytechcourse.application.user.login;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class ProcessTokenReissueImpl implements ProcessTokenReissue {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final AddRefreshEntity addRefreshEntity;

    /**
     * 주어진 이메일 주소로 인증 코드를 전송하고 리프레시 토큰을 재발급합니다.
     *
     * 1. 클라이언트의 요청에서 리프레시 토큰을 추출합니다.
     * 2. 리프레시 토큰의 만료 여부를 검사하고 만료된 경우 예외를 처리합니다.
     * 3. 리프레시 토큰의 유효성 검사 후 새로운 액세스 및 리프레시 토큰을 발급합니다.
     * 4. 새로 발급된 토큰들을 응답 헤더 및 쿠키로 전송합니다.
     *
     *
     * @param request 클라이언트 요청 객체로, 리프레시 토큰을 포함하고 있음
     * @param response 클라이언트 응답 객체로, 새로운 액세스 및 리프레시 토큰을 전달
     * @return 리프레시 토큰 재발급 결과를 담은 HTTP 응답
     * @throws RuntimeException 이메일 전송 실패 시 예외 발생
     */
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 리프레시 토큰 추출
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            // 리프레시 토큰이 없으면 클라이언트에 오류 응답
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰 만료 여부 검사
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에 대한 오류 응답
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰이 맞는지 확인 (페이로드 확인)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            // 유효하지 않은 리프레시 토큰에 대한 오류 응답
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        if (!refreshRepository.existsByToken(refresh)) {
            // 리프레시 토큰이 존재하지 않으면 오류 응답
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // 이메일, UID 및 역할 정보 추출
        String email = jwtUtil.getEmail(refresh);
        String uid = jwtUtil.getUid(refresh);
        RoleEnum role = jwtUtil.getRole(refresh);

        // 새 액세스 토큰과 리프레시 토큰 생성
        String newAccess = jwtUtil.createJwt("access", email, uid, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", email, uid, role, 86400000L);

        // 기존 리프레시 토큰 삭제 및 새 리프레시 토큰 엔티티 저장
        refreshRepository.deleteByToken(refresh);
        addRefreshEntity.addRefreshEntity(uid, email, newRefresh, 86400000L);

        // 응답 헤더와 쿠키에 새 토큰 추가
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 쿠키를 생성하는 메서드입니다.
     *
     * @param key 쿠키의 이름
     * @param value 쿠키의 값
     * @return 생성된 쿠키
     */
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);  // 1일 유효
        cookie.setHttpOnly(true); // 클라이언트 측에서 접근 불가
        return cookie;
    }


}

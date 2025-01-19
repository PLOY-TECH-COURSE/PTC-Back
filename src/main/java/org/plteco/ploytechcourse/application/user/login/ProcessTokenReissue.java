package org.plteco.ploytechcourse.application.user.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/**
 * 토큰 재발급을 처리하는 서비스 인터페이스입니다.
 *
 * 이 인터페이스는 클라이언트에서 발급받은 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급하는 로직을 정의합니다.
 *
 */
public interface ProcessTokenReissue {

    /**
     * 클라이언트 요청에 따라 새로운 액세스 토큰을 발급합니다.
     *
     * 요청에서 리프레시 토큰을 추출하여 유효성을 검사하고, 만약 유효하다면 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.
     *
     *
     * @param request 클라이언트 요청 객체
     * @param response 클라이언트 응답 객체
     * @return 새로운 액세스 토큰과 리프레시 토큰을 포함하는 응답
     */
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);
}

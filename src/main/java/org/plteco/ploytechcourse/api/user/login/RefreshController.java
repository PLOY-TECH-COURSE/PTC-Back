package org.plteco.ploytechcourse.api.user.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.login.ProcessTokenReissue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 리프레시 토큰을 사용해 새로운 액세스 토큰을 발급하는 API 컨트롤러입니다.
 *
 * 클라이언트가 `/refresh` 엔드포인트로 리프레시 토큰을 전송하면,
 * 이 컨트롤러가 이를 처리하여 새로운 액세스 토큰을 발급하고 응답합니다.
 *
 */
@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final ProcessTokenReissue processTokenReissue;

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
     *
     * 클라이언트가 리프레시 토큰을 포함하여 요청하면, 해당 토큰의 유효성을 검사하고
     * 새로운 액세스 토큰을 발급하여 응답합니다.
     *
     *
     * @param request 클라이언트 요청 객체
     * @param response 클라이언트 응답 객체
     * @return 새로운 액세스 토큰을 포함하는 응답
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return processTokenReissue.reissue(request, response);
    }
}

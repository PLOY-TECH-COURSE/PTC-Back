package org.plteco.ploytechcourse.api.user.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.login.ProcessTokenReissue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Operation(
            summary = "리프레시 토큰으로 액세스 토큰 재발급",
            description = "사용자가 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "새로운 액세스 토큰 발급 완료"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 (예: 유효하지 않은 리프레시 토큰)"),
                    @ApiResponse(responseCode = "401", description = "인증 실패 (예: 토큰 만료 또는 유효하지 않음)"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @PostMapping("/refresh")
    public ResponseEntity<?> reissue(
            @Parameter(description = "클라이언트 요청 객체 (리프레시 토큰 포함)", required = true, in = ParameterIn.COOKIE)
            HttpServletRequest request,
            @Parameter(description = "클라이언트 응답 객체 (새로운 액세스 토큰을 포함한 응답)", required = true)
            HttpServletResponse response) {
        return processTokenReissue.reissue(request, response);
    }
}

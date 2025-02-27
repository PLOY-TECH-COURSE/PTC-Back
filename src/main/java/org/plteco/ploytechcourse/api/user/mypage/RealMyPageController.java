package org.plteco.ploytechcourse.api.user.mypage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.mypage.dto.ResponseMypage;
import org.plteco.ploytechcourse.application.user.mypage.service.MyPageApplication;
import org.plteco.ploytechcourse.application.user.realMyPage.service.RealMyPageServiceApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/real-mypage")
public class RealMyPageController {

    private final RealMyPageServiceApplication realMyPageServiceApplication;

    @Operation(
            summary = "진짜 마이 페이지에 필요한 정보를 가져옵니다",
            description = "네 가져옵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping
    public ResponseEntity<ResponseMypage> mypage(
    ){
        return ResponseEntity.ok(realMyPageServiceApplication.getMyPage());
    }
}

package org.plteco.ploytechcourse.api.user.mypage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.mypage.dto.ResponseMypage;
import org.plteco.ploytechcourse.application.user.mypage.service.MyPageApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "MyPage-controller : 허동운")
public class MyPageController {

    private final MyPageApplication myPageApplication;

    @Operation(
            summary = "마이 페이지에 필요한 정보를 가져옵니다",
            description = "네 가져옵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/{user-id}")
    public ResponseEntity<ResponseMypage> mypage(
            @PathVariable("user-id")
            Long userId
    ){
        return ResponseEntity.ok(myPageApplication.getMyPage(userId));
    }
}

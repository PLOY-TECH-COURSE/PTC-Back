package org.plteco.ploytechcourse.api.user.rebio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.profile.dto.RequestProfile;
import org.plteco.ploytechcourse.application.user.profile.service.UpdateProfileApplication;
import org.plteco.ploytechcourse.application.user.realMyPage.dto.RequestBioDTO;
import org.plteco.ploytechcourse.application.user.realMyPage.service.ReBioServiceApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/re")
@Tag(name = "ReBio-controller : 허동운")
public class ReBioController {

    private final ReBioServiceApplication reBioServiceApplication;

    @Operation(
            summary = "자기소개 업데이트",
            description = "자기소개 업데이트됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/bio")
    public ResponseEntity<Void> profile(
            @RequestBody
            RequestBioDTO requestBioDTO
    ){
        reBioServiceApplication.change(requestBioDTO);
        return ResponseEntity.ok().build();
    }
}

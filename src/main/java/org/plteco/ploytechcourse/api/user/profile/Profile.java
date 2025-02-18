package org.plteco.ploytechcourse.api.user.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.lesson.dto.TechCourseDto;
import org.plteco.ploytechcourse.application.user.profile.dto.RequestProfile;
import org.plteco.ploytechcourse.application.user.profile.service.UpdateProfileApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class Profile {

    private final UpdateProfileApplication updateProfileApplication;

    @Operation(
            summary = "프로필 이미지 저장",
            description = "url를 받아서 이미지를 저장시킵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<Void> profile(
            @Parameter(description = "프로필 이미지", required = true)
            @RequestBody RequestProfile requestProfile
    ){
        updateProfileApplication.updateProfile(requestProfile);
        return ResponseEntity.ok().build();
    }
}

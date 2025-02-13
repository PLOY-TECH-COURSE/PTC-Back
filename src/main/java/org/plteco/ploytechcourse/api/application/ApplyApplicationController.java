package org.plteco.ploytechcourse.api.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.application.application.dto.ShowApplicationDto;
import org.plteco.ploytechcourse.application.application.service.ApplyApplicationApplication;
import org.plteco.ploytechcourse.application.application.service.ShowApplicationApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplyApplicationController {
    private final ApplyApplicationApplication applicationApplication;
    private final ShowApplicationApplication showApplicationApplication;

    @Operation(
            summary = "테크코스 신청",
            description = "사용자가 테크코스를 신청합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "테크코스 신청이 완료되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 오류",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @PostMapping
    public ResponseEntity<String> apply(
            @Parameter(description = "테크코스 신청에 필요한 자기소개와 다짐", required = true)
            @Valid @RequestBody ApplyApplicationDto applyApplicationDto) {
        applicationApplication.applyApplication(applyApplicationDto);
        return ResponseEntity.ok("테크코스 신청이 완료되었습니다.");
    }

    @Operation(
            summary = "테크코스 조회",
            description = "신청한 테크코스를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "테크코스 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 오류",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping
    public ResponseEntity<List<ShowApplicationDto>> getApplications() {
        return ResponseEntity.ok(showApplicationApplication.showApplication());
    }
}

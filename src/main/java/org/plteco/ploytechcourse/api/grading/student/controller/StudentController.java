package org.plteco.ploytechcourse.api.grading.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.api.grading.student.dto.res.StudentResponse;
import org.plteco.ploytechcourse.application.student.service.StudentApplicationService;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@Tag(name = "Student", description = "학생 관련 API")
public class StudentController {

    private final StudentApplicationService studentApplicationService;

    @GetMapping("/latest-generation")
    @Operation(
        summary = "최신 기수 학생 목록 조회",
        description = "가장 최근 기수의 학생 목록을 조회합니다. 학생 ID와 이름 정보를 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "학생 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = StudentResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증 실패",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "학생을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<List<StudentResponse>> getStudentsLatestGeneration() {
        List<StudentResponse> students = studentApplicationService.getStudentsLatestGeneration();
        return new ResponseEntity<>(students ,HttpStatus.OK);
    }

}

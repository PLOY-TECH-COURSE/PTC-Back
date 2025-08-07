package org.plteco.ploytechcourse.api.grade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.api.grade.dto.req.CreateFormDto;
import org.plteco.ploytechcourse.api.grade.dto.res.GradingFormResponseDto;
import org.plteco.ploytechcourse.application.grade.service.GradeServiceApplication;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
@Tag(name = "Grade", description = "평가 관련 API : 조재민")
public class GradingController {

    private final GradeServiceApplication gradeServiceApplication;

    @PostMapping("/forms")
    @Operation(
        summary = "평가 폼 생성",
        description = "새로운 평가 폼을 생성합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "평가 폼 생성 성공"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
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
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<?> createGrade(@RequestBody CreateFormDto createFormDto) {
        try {
            gradeServiceApplication.createGradingForm(createFormDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                new PltecoException("평가 폼 생성 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/forms")
    @Operation(
        summary = "평가 폼 전체 조회",
        description = "모든 평가 폼을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "평가 폼 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = GradingFormResponseDto.class))
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
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<List<GradingFormResponseDto>> getAllGrades() {
        try {
            List<GradingFormResponseDto> gradingFormResponseDtoList = gradeServiceApplication.getAllGradingForm();
            return new ResponseEntity<>(gradingFormResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            throw new PltecoException("평가 폼 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/forms/order")
//    public ResponseEntity<List<>> getAllGradesByOrder() {
//
//    }


}

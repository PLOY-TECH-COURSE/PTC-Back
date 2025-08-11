package org.plteco.ploytechcourse.api.grading.form.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.api.grading.form.dto.req.CreateFormDto;
import org.plteco.ploytechcourse.api.grading.form.dto.req.PresentationOrderDto;
import org.plteco.ploytechcourse.api.grading.form.dto.req.RequestScoreDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.GradingFormDetailResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.GradingFormResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.MessageResponse;
import org.plteco.ploytechcourse.api.grading.form.dto.res.PresentationOrderResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.StudentScoreDto;
import org.plteco.ploytechcourse.application.grading.command.PresentationOrderCommand;
import org.plteco.ploytechcourse.application.grading.service.GradingServiceApplication;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
@Tag(name = "Grade", description = "평가 관련 API : 조재민")
public class GradingController {

    private final GradingServiceApplication gradingServiceApplication;

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
            gradingServiceApplication.createGradingForm(createFormDto);
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
            List<GradingFormResponseDto> gradingFormResponseDtoList = gradingServiceApplication.getAllGradingForm();
            return new ResponseEntity<>(gradingFormResponseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            throw new PltecoException("평가 폼 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/forms/{form_id}/presentation-order")
    @Operation(
        summary = "발표 순서 업데이트",
        description = "특정 평가 폼의 발표 순서를 업데이트합니다. 이미 채점된 학생의 순서는 변경할 수 없습니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "발표 순서 정보",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PresentationOrderDto.class)
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "발표 순서 업데이트 성공",
            content = @Content(schema = @Schema(implementation = MessageResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (이미 채점된 학생 포함, 순서 중복 등)",
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
            responseCode = "404",
            description = "평가 폼을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<MessageResponse> updatePresentationOrder(
        @Parameter(description = "평가 폼 ID", required = true)
        @PathVariable("form_id") Long formId,
        @RequestBody PresentationOrderDto dto) {
        gradingServiceApplication.applyPresentationOrders(formId, PresentationOrderCommand.fromDto(dto));

        return new ResponseEntity<>(new MessageResponse("발표 순서가 정상적으로 저장되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/forms/{form_id}/presentation-order")
    @Operation(
        summary = "발표 순서 조회",
        description = "특정 평가 폼의 발표 순서를 조회합니다. 학생들의 발표 순서와 관련 정보를 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "발표 순서 조회 성공",
            content = @Content(schema = @Schema(implementation = PresentationOrderResponseDto.class))
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
            description = "평가 폼을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<List<PresentationOrderResponseDto>> getPresentationOrder(
        @Parameter(description = "평가 폼 ID", required = true)
        @PathVariable("form_id") Long formId) {
        List<PresentationOrderResponseDto> presentationOrderResponseDtos = gradingServiceApplication.getPresentationOrder(formId);
        return new ResponseEntity<>(presentationOrderResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/forms/{form_id}")
    @Operation(
        summary = "평가 폼 상세 조회",
        description = "특정 평가 폼의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "평가 폼 상세 조회 성공",
            content = @Content(schema = @Schema(implementation = GradingFormDetailResponseDto.class))
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
            description = "평가 폼을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<GradingFormDetailResponseDto> getGradingFormById(
        @Parameter(description = "평가 폼 ID", required = true)
        @PathVariable("form_id") Long formId){
        GradingFormDetailResponseDto gradingFormDetailResponseDto = gradingServiceApplication.getGradingFormByFormId(formId);
        return new ResponseEntity<>(gradingFormDetailResponseDto, HttpStatus.OK);
    }


    @PostMapping("/forms/{form_id}/score")
    @Operation(
        summary = "평가 점수 등록",
        description = "특정 평가 폼에 학생의 점수를 등록합니다. 모든 점수가 등록되면 자동으로 결과 공지사항이 생성됩니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "평가 점수 정보",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RequestScoreDto.class)
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "평가 점수 등록 성공",
            content = @Content(schema = @Schema(implementation = MessageResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (유효하지 않은 점수, 중복 평가 등)",
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
            responseCode = "404",
            description = "평가 폼 또는 학생을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<MessageResponse> addScore(
        @Parameter(description = "평가 폼 ID", required = true)
        @PathVariable("form_id") Long formId, 
        @RequestBody RequestScoreDto requestScoreDto) {
        gradingServiceApplication.addScore(formId, requestScoreDto);

        return new ResponseEntity<>(new MessageResponse("평가가 성공적으로 완료되었습니다."), HttpStatus.OK);
    }

}

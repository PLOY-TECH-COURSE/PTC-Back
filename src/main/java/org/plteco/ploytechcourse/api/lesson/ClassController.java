package org.plteco.ploytechcourse.api.lesson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.lesson.dto.ClassDto;
import org.plteco.ploytechcourse.domain.lesson.service.ClassServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
@Tag(name = "class-controller : 허동운")
public class ClassController {

    private final ClassServiceImpl classService;

    @PostMapping
    @Operation(
            summary = "클래스 저장",
            description = "새로운 클래스를 저장합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "클래스 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<Void> saveClass(
            @Parameter(description = "저장할 클래스 데이터", required = true)
            @RequestBody @Valid ClassDto classDto) {
        classService.Saveclass(classDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "전체 클래스 조회",
            description = "모든 클래스를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "클래스 리스트 조회 성공")
    })
    public ResponseEntity<List<ClassDto>> showClasses() {
        List<ClassDto> classList = classService.ShowClass();
        return ResponseEntity.ok(classList);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "특정 클래스 조회",
            description = "트랙의 ID로 클래스를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "클래스 조회 성공"),
            @ApiResponse(responseCode = "404", description = "클래스를 찾을 수 없음")
    })
    public ResponseEntity<List<ClassDto>> getClasses(
            @Parameter(description = "조회할 클래스의 ID", required = true)
            @PathVariable Long id) {
        List<ClassDto> classList = classService.getClass(id);
        return ResponseEntity.ok(classList);
    }
}

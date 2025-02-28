package org.plteco.ploytechcourse.api.lesson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.lesson.dto.TechCourseDto;
import org.plteco.ploytechcourse.domain.lesson.service.ClassServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tech-course")
@RequiredArgsConstructor
@Tag(name = "TechCourse-controller : 허동운")
public class TechCourseController {

    private final ClassServiceImpl classService;

    @PostMapping
    @Operation(
            summary = "기술 과정 저장",
            description = "새로운 기술 과정을 저장합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기술 과정 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<Void> saveTechCourse(
            @Parameter(description = "저장할 기술 과정 데이터", required = true)
            @RequestBody TechCourseDto techCourseDto) {
        classService.SavetechCourse(techCourseDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "전체 기술 과정 조회",
            description = "저장된 모든 기술 과정을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기술 과정 리스트 조회 성공")
    })
    public ResponseEntity<List<TechCourseDto>> showTechCourses() {
        List<TechCourseDto> techCourseList = classService.ShowTechCourse();
        return ResponseEntity.ok(techCourseList);
    }
}

package org.plteco.ploytechcourse.api.lesson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.lesson.dto.TrackDto;
import org.plteco.ploytechcourse.domain.lesson.service.ClassServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
@RequiredArgsConstructor
public class TrackController {

    private final ClassServiceImpl classService;

    @PostMapping
    @Operation(
            summary = "트랙 저장",
            description = "새로운 트랙을 저장합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "트랙 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<Void> saveTrack(
            @Parameter(description = "저장할 트랙 데이터", required = true)
            @RequestBody TrackDto trackDto) {
        classService.Savetrack(trackDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "전체 트랙 조회",
            description = "저장된 모든 트랙을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "트랙 리스트 조회 성공")
    })
    public ResponseEntity<List<TrackDto>> showTracks() {
        List<TrackDto> trackList = classService.ShowTrack();
        return ResponseEntity.ok(trackList);
    }

    @GetMapping("/{generation}")
    @Operation(
            summary = "특정 트랙 조회",
            description = "특정 세대에 대한 트랙을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "트랙 조회 성공"),
            @ApiResponse(responseCode = "404", description = "트랙을 찾을 수 없음")
    })
    public ResponseEntity<List<TrackDto>> getTracks(
            @Parameter(description = "조회할 트랙의 세대", required = true)
            @PathVariable Long generation) {
        List<TrackDto> trackList = classService.getTrack(generation);
        return ResponseEntity.ok(trackList);
    }
}
